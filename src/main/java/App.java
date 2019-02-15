import com.google.common.base.Preconditions;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * This App validates an ACCOuNT TSV file for data consistency
 * 
 * @author Ryan Whaley
 */
public class App {

  private Reader m_reader;

  public static void main(String[] args) {
    Preconditions.checkArgument(args != null && args.length == 1, "Need 1 and only 1 path for file to read");

    try {
      App app = new App(Paths.get(args[0]));
      app.validate();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  App(Reader reader) {
    Preconditions.checkNotNull(reader);
    m_reader = reader;
  }

  private App(Path dataPath) throws FileNotFoundException {
    Preconditions.checkNotNull(dataPath);

    File dataFile = dataPath.toFile();
    Preconditions.checkArgument(dataFile.exists(), "File does not exist");
    Preconditions.checkArgument(dataFile.isFile(), "Path is not a file");

    System.out.println(String.format("Reading from %s", dataPath.toAbsolutePath()));
    m_reader = new FileReader(dataFile);
  }

  private void validate() throws IOException {
    System.out.println("Starting validation");
    CSVParser parser = CSVFormat.TDF.withHeader(SubjectColumn.class).parse(m_reader);
    int errors = parser.getRecords().stream()
        .skip(2)
        .map(r -> {
          String id = r.get(SubjectColumn.SUBJECT_ID);
          return Stream.of(SubjectColumn.values())
              .map(c -> {
                try {
                  if (!c.validate(r.get(c))) {
                    System.out.println(String.format("%s has a bad value for %s: %s", id, c.name(), r.get(c)));
                    return 1;
                  } else {
                    return 0;
                  }
                } catch (IllegalArgumentException ex) {
                  System.out.println(String.format("%s has a blank value for %s", id, c.name()));
                  return 1;
                }
              })
              .mapToInt(i -> i).sum();
        }).mapToInt(i -> i).sum();
    System.out.println("Completed validation");
    if (errors > 0) {
      System.out.println(String.format("Found %d errors", errors));
    }
  }
}