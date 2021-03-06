package org.pharmgkb.account;

import com.google.common.base.Preconditions;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.lang3.StringUtils;
import org.pharmgkb.account.data.Field;
import org.pharmgkb.account.file.AbstractDataFile;
import org.pharmgkb.account.file.ClopidogrelDataFile;
import org.pharmgkb.account.file.NOACDataFile;
import org.pharmgkb.account.file.WarfarinDataFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This App validates CSV data files from the account consortium
 * 
 * @author Ryan Whaley
 */
class FileProcessor {
  private static final Logger sf_logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
  private static final String VALIDATION_HEADER = "Site\tSubject ID\tCell Address\tField Name\tBad Value\n";

  private final List<AbstractDataFile> dataFiles = new ArrayList<>();

  public static void main(String[] args) {
    CommandLineParser cliParser = new DefaultParser();
    Options o = new Options();
    o.addOption("c", "clopidogrel-file", true, "File of clopidogrel field names");
    o.addOption("n", "noac-file", true, "File of NOAC field names");
    o.addOption("w", "warfarin-file", true, "File of warfarin field names");
    o.addOption("s", "site-key", true, "File of SITE=ID keys");

    try {
      CommandLine cli = cliParser.parse(o, args);
      FileProcessor app = new FileProcessor(
          Paths.get(cli.getOptionValue("c")),
          Paths.get(cli.getOptionValue("n")),
          Paths.get(cli.getOptionValue("w")),
          Paths.get(cli.getOptionValue("s"))
      );
      app.validate();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private FileProcessor(Path clopidogrelPath, Path noacPath, Path warfarinPath, Path siteKeyPath) throws IOException {
    Preconditions.checkArgument(clopidogrelPath.toFile().exists(), "Clopidogrel file not found");
    Preconditions.checkArgument(noacPath.toFile().exists(), "NOAC file not found");
    Preconditions.checkArgument(warfarinPath.toFile().exists(), "Warfarin file not found");
    Preconditions.checkArgument(siteKeyPath.toFile().exists(), "Site key file not found");

    Map<String,String> siteKeyMap = new HashMap<>();
    Files.lines(siteKeyPath).forEach((line) -> {
      if (StringUtils.isNotBlank(line)) {
        String[] fields = line.split("=");
        if (fields.length != 2) {
          throw new RuntimeException("site key malformed: " + line);
        }
        siteKeyMap.put(fields[0], fields[1]);
      }
    });

    if (siteKeyMap.size() == 0) {
      throw new RuntimeException("No site keys specified");
    }

    this.dataFiles.add(new ClopidogrelDataFile(clopidogrelPath, siteKeyMap));
    this.dataFiles.add(new NOACDataFile(noacPath, siteKeyMap));
    this.dataFiles.add(new WarfarinDataFile(warfarinPath, siteKeyMap));
  }

  private void validate() throws Exception {
    sf_logger.info("Starting validation");

    for (Field field : Field.values()) {
      if (field.isUnvalidated()) {
        sf_logger.warn("WARNING: The field \"{}\" will NOT be checked for validation", field.getDisplayName());
      }
    }
    
    for (AbstractDataFile dataFile : this.dataFiles) {
      Path validationFilePath = Paths.get("out", dataFile.getFilename() + ".validation.tsv");
      try (FileWriter fileWriter = new FileWriter(validationFilePath.toFile())) {
        fileWriter.write(VALIDATION_HEADER);
        for (String m : dataFile.validate()) {
          fileWriter.write(m);
        }
        fileWriter.write("\n");
        
        Path processedFile = dataFile.makeProcessedFile();
        sf_logger.info("Wrote {}", processedFile);
      }
      sf_logger.info("Wrote validation to {}", validationFilePath);
    }
  }
}
