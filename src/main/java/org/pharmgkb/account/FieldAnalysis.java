package org.pharmgkb.account;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Analyze all the CSV files' header rows to see what columns exist
 *
 * @author Ryan Whaley
 */
public class FieldAnalysis {

  private Path clopidogrelPath;
  private Path noacPath;
  private Path warfarinPath;
  private Multimap<String, String> fieldLocationMap = TreeMultimap.create();
  
  private FieldAnalysis(Path clopidogrelPath, Path noacPath, Path warfarinPath) {
    Preconditions.checkArgument(clopidogrelPath.toFile().exists());
    Preconditions.checkArgument(noacPath.toFile().exists());
    Preconditions.checkArgument(warfarinPath.toFile().exists());

    this.clopidogrelPath = clopidogrelPath;
    this.noacPath = noacPath;
    this.warfarinPath = warfarinPath;
  }

  public static void main(String[] args) {
    CommandLineParser cliParser = new DefaultParser();
    Options o = new Options();
    o.addOption("c", "clopidogrel-file", true, "File of clopidogrel field names");
    o.addOption("n", "noac-file", true, "File of NOAC field names");
    o.addOption("w", "warfarin-file", true, "File of warfarin field names");
    
    try {
      CommandLine cli = cliParser.parse(o, args);
      FieldAnalysis fieldAnalysis = new FieldAnalysis(
          Paths.get(cli.getOptionValue("c")),
          Paths.get(cli.getOptionValue("n")),
          Paths.get(cli.getOptionValue("w"))
      );
      fieldAnalysis.execute();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  private void execute() throws IOException {
    gatherFields(this.clopidogrelPath, "C");
    gatherFields(this.noacPath, "N");
    gatherFields(this.warfarinPath, "W");

    for (String key : fieldLocationMap.keySet()) {
      String enumName = makeEnumName(key);
      System.out.println(enumName + "(\""+key+"\"), //" + String.join(", ", fieldLocationMap.get(key)));
    }
  }
  
  private void gatherFields(Path file, String title) throws IOException {
    int group = 1;
    List<String> lines = Files.readAllLines(file);
    String header = lines.get(0);

    List<String> fields = new ArrayList<>();
    try (
        Reader headerReader = new StringReader(header);
        CSVParser csvParser = CSVFormat.EXCEL.parse(headerReader)
    ) {
      CSVRecord record = csvParser.iterator().next();
      fields.addAll(Lists.newArrayList(record));
    }

    System.out.println("-- " + file.getFileName().toString());
    System.out.println("new org.pharmgkb.account.data.Field[]{");
    for (String line : fields) {
      String field = StringUtils.strip(line);
      String enumName = makeEnumName(field);
      System.out.println("org.pharmgkb.account.data.Field." + enumName + ",");

      fieldLocationMap.put(field, title+group);

      if (field.equals("Complete?")) group += 1;
    }
    System.out.println("};");
    System.out.println();
  }

  private static String makeEnumName(String title) {
    if (StringUtils.isBlank(title)) return "";
    return title
        .replaceAll("\\W+$", "")
        .replaceAll("^\\W+", "")
        .replaceAll("\\W+", "_")
        .toUpperCase();
  }
}
