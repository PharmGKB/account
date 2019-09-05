package org.pharmgkb.account;

import com.google.common.base.Preconditions;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This org.pharmgkb.account.App validates CSV data files from the account consortium
 * 
 * @author Ryan Whaley
 */
public class App {

  private List<AbstractDataFile> dataFiles = new ArrayList<>();

  public static void main(String[] args) {
    CommandLineParser cliParser = new DefaultParser();
    Options o = new Options();
    o.addOption("c", "clopidogrel-file", true, "File of clopidogrel field names");
    o.addOption("n", "noac-file", true, "File of NOAC field names");
    o.addOption("w", "warfarin-file", true, "File of warfarin field names");

    try {
      CommandLine cli = cliParser.parse(o, args);
      App app = new App(
          Paths.get(cli.getOptionValue("c")),
          Paths.get(cli.getOptionValue("n")),
          Paths.get(cli.getOptionValue("w"))
      );
      app.validate();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private App(Path clopidogrelPath, Path noacPath, Path warfarinPath) {
    Preconditions.checkArgument(clopidogrelPath.toFile().exists());
    Preconditions.checkArgument(noacPath.toFile().exists());
    Preconditions.checkArgument(warfarinPath.toFile().exists());

    this.dataFiles.add(new ClopidogrelDataFile(clopidogrelPath));
    this.dataFiles.add(new NOACDataFile(noacPath));
    this.dataFiles.add(new WarfarinDataFile(warfarinPath));
  }

  private void validate() throws IOException {
    System.out.println("Starting validation");
    for (AbstractDataFile dataFile : this.dataFiles) {
      dataFile.validate();
    }
  }
}
