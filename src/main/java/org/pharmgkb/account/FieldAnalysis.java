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
import org.pharmgkb.account.data.Field;
import org.pharmgkb.account.file.ClopidogrelDataFile;
import org.pharmgkb.account.file.NOACDataFile;
import org.pharmgkb.account.file.WarfarinDataFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Analyze all the CSV files' header rows to see what columns exist
 *
 * @author Ryan Whaley
 */
public class FieldAnalysis {
  private static final Logger sf_logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

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
    
    writeFieldChanges();

    StringJoiner fieldJoiner = new StringJoiner("\n");
    for (String key : fieldLocationMap.keySet()) {
      fieldJoiner.add(makeEnumName(key) + "(\""+key+"\"), //" + String.join(", ", fieldLocationMap.get(key)));
    }
    sf_logger.info("Full field list\n{}", fieldJoiner.toString());
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
    
    int expectedColumnCount;
    switch (title) {
      case "C":
        expectedColumnCount = ClopidogrelDataFile.FIELDS.length;
        break;
      case "N":
        expectedColumnCount = NOACDataFile.FIELDS.length;
        break;
      case "W":
        expectedColumnCount = WarfarinDataFile.FIELDS.length;
        break;
      default:
        throw new RuntimeException("Unexpected data file type");
    }

    sf_logger.info("validating " + file.getFileName().toString());
    if (fields.size() != expectedColumnCount) {
      sf_logger.warn("WARNING Column count has changed, check the new definition");
      StringJoiner fieldJoiner = new StringJoiner("\n");
      fieldJoiner.add("new Field[]{");
      for (String line : fields) {
        String field = StringUtils.strip(line);
        fieldJoiner.add("    Field." + makeEnumName(field) + ",");

        fieldLocationMap.put(field, title+group);

        if (field.equals("Complete?")) group += 1;
      }
      fieldJoiner.add("};");
      
      sf_logger.info("fields\n{}", fieldJoiner.toString());
    }
    else {
      sf_logger.info("-- column count constant, keep calm");
    }
  }
  
  private void writeFieldChanges() {
    System.out.println();
    SortedSet<Field> clopidogrelInputFields = new TreeSet<>(Arrays.asList(ClopidogrelDataFile.FIELDS));
    Set<Field> clopidogrelOutputFields = new HashSet<>(Arrays.asList(ClopidogrelDataFile.OUTPUT_FIELDS));
    clopidogrelInputFields.removeAll(clopidogrelOutputFields);
    System.out.println("Fields removed from clopidogrel file:");
    for (Field field : clopidogrelInputFields) {
      System.out.println(field.name());
    }

    System.out.println();
    SortedSet<Field> noacInputFields = new TreeSet<>(Arrays.asList(NOACDataFile.FIELDS));
    Set<Field> noacOutputFields = new HashSet<>(Arrays.asList(NOACDataFile.OUTPUT_FIELDS));
    noacInputFields.removeAll(noacOutputFields);
    System.out.println("Fields removed from NOAC file:");
    for (Field field : noacInputFields) {
      System.out.println(field.name());
    }

    System.out.println();
    SortedSet<Field> warfarinInputFields = new TreeSet<>(Arrays.asList(WarfarinDataFile.FIELDS));
    Set<Field> warfarinOutputFields = new HashSet<>(Arrays.asList(WarfarinDataFile.OUTPUT_FIELDS));
    warfarinInputFields.removeAll(warfarinOutputFields);
    System.out.println("Fields removed from warfarin file:");
    for (Field field : warfarinInputFields) {
      System.out.println(field.name());
    }
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
