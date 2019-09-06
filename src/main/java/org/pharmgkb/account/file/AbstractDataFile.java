package org.pharmgkb.account.file;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.pharmgkb.account.data.Field;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * An abstract class to implement for a data file
 *
 * @author Ryan Whaley
 */
public abstract class AbstractDataFile {
  
  abstract Field[] getExpectedFields();

  private Path filePath;
  
  private String getFilename() {
    return this.filePath.getFileName().toString();
  }

  void setFilePath(Path filePath) {
    this.filePath = filePath;
  }

  public List<String> validate() throws IOException {
    System.out.println("-- validating " + getFilename());

    List<String> messages = new ArrayList<>();
    int validSubjects = 0;
    int totalSubjects = 0;

    try (Reader reader = new FileReader(this.filePath.toFile())) {
      int lineNumber = 1;
      for (CSVRecord record : CSVFormat.DEFAULT.parse(reader)) {
        if (lineNumber != 1) {
          totalSubjects += 1;

          List<String> recordErrors = validateRow(record, lineNumber);
          if (recordErrors.size() > 0) {
            messages.addAll(recordErrors);
          } else {
            validSubjects += 1;
          }
        }
        lineNumber += 1;
      }
    }
    messages.forEach(System.out::println);

    System.out.println(String.format("Valid subjects: %d/%d\n", validSubjects, totalSubjects));
    return messages;
  }

  private List<String> validateRow(CSVRecord record, int lineNumber) {
    List<String> messages = new ArrayList<>();

    if (record.size() != getExpectedFields().length) {
      messages.add(String.format("Line %d unexpected length: expected %d, got %d", lineNumber, getExpectedFields().length, record.size()));
    }

    for (int i = 0; i < getExpectedFields().length; i++) {
      Field field = getExpectedFields()[i];
      if (!field.validate(record.get(i))) {
        messages.add(String.format("Line %d, Column %d: invalid %s [%s]", lineNumber, i+1, field.name(), record.get(i)));
      }
    }

    return messages;
  }
}
