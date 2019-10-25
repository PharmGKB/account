package org.pharmgkb.account;

import com.google.common.base.Preconditions;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This App validates CSV data files from the account consortium
 * 
 * @author Ryan Whaley
 */
class FileProcessor {
  private static final Logger sf_logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
  private static final String VALIDATION_HEADER = "Site\tSubject ID\tCell Address\tField Name\tBad Value\n";

  private List<AbstractDataFile> dataFiles = new ArrayList<>();

  public static void main(String[] args) {
    CommandLineParser cliParser = new DefaultParser();
    Options o = new Options();
    o.addOption("c", "clopidogrel-file", true, "File of clopidogrel field names");
    o.addOption("n", "noac-file", true, "File of NOAC field names");
    o.addOption("w", "warfarin-file", true, "File of warfarin field names");

    try {
      CommandLine cli = cliParser.parse(o, args);
      FileProcessor app = new FileProcessor(
          Paths.get(cli.getOptionValue("c")),
          Paths.get(cli.getOptionValue("n")),
          Paths.get(cli.getOptionValue("w"))
      );
      app.validate();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private FileProcessor(Path clopidogrelPath, Path noacPath, Path warfarinPath) {
    Preconditions.checkArgument(clopidogrelPath.toFile().exists(), "Clopidogrel file not found");
    Preconditions.checkArgument(noacPath.toFile().exists(), "NOAC file not found");
    Preconditions.checkArgument(warfarinPath.toFile().exists(), "Warfarin file not found");

    this.dataFiles.add(new ClopidogrelDataFile(clopidogrelPath));
    this.dataFiles.add(new NOACDataFile(noacPath));
    this.dataFiles.add(new WarfarinDataFile(warfarinPath));
  }

  private void validate() throws IOException {
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