package org.pharmgkb.account.file;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.pharmgkb.account.ExcelUtils;
import org.pharmgkb.account.data.Field;
import org.pharmgkb.account.data.FieldPattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;

import static org.pharmgkb.account.data.FieldPattern.isMissing;

/**
 * An abstract class to implement for a data file
 *
 * @author Ryan Whaley
 */
public abstract class AbstractDataFile {
  private static final Logger sf_logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
  private static final ResourceBundle sf_descriptions = ResourceBundle.getBundle("fields");

  private List<CSVRecord> m_records = new ArrayList<>();
  private Multimap<Field, Integer> fieldIndexMap = LinkedListMultimap.create();

  abstract Field[] getExpectedFields();
  abstract Map<Field, Field> getCalculationMap();
  abstract String getOutputFilename();
  
  AbstractDataFile() {
    for (int i = 0; i < getExpectedFields().length; i++) {
      fieldIndexMap.put(getExpectedFields()[i], i);
    }
  }
  
  private String getRecordValue(@Nonnull CSVRecord record, @Nonnull Field field) {
    if (!fieldIndexMap.containsKey(field)) {
      throw new RuntimeException("Field not in dataset " + field);
    }
    Collection<Integer> indexes = fieldIndexMap.get(field);
    return record.get(indexes.iterator().next());
  }
  
  List<String> getRecordValues(@Nonnull CSVRecord record, @Nonnull Field field) {
    if (!fieldIndexMap.containsKey(field)) {
      throw new RuntimeException("Field not in dataset " + field);
    }
    
    List<String> results = new ArrayList<>();
    for (Integer idx : fieldIndexMap.get(field)) {
      results.add(record.get(idx));
    }
    return results;
  }

  public Path makeProcessedFile() throws Exception {
    Path outputPath = Paths.get("out", getOutputFilename());
    try (
        FileWriter fileWriter = new FileWriter(outputPath.toFile());
        CSVPrinter csv = new CSVPrinter(fileWriter, CSVFormat.EXCEL)
    ) {
      // write the headers
      for (Field field : getExpectedFields()) {
        csv.print(field.getDisplayName());
        if (getCalculationMap().containsKey(field)) {
          csv.print(getCalculationMap().get(field).getDisplayName());
        }
      }
      csv.println();
      for (Field field : getExpectedFields()) {
        csv.print(getDescription(field.name()));
        if (getCalculationMap().containsKey(field)) {
          csv.print(getDescription(getCalculationMap().get(field).name()));
        }
      }
      csv.println();

      // loop through each record of the dataset
      int rowIdx = 0;
      for (CSVRecord record : m_records) {
        int colIdx = 0;
        for(Object recordField : record) {
          csv.print(recordField);
          Field field = getExpectedFields()[colIdx];
          Field calcField = getCalculationMap().get(field);
          if (calcField != null) {
            if (calcField == Field.TIME_TO_BLOOD_DRAW) {
              csv.print(timeToBloodDraw(record).map(String::valueOf).orElse(""));
            } else {
              String diff = diffFromEnrollment(record, (String) recordField).map(String::valueOf).orElse("");
              csv.print(diff);

              if (diff.startsWith("-")) {
                sf_logger.warn("Negative date diff in row {}, column {}", rowIdx + 2, colIdx + 1);
              }
            }
          }
          colIdx += 1;
        }
        csv.println();
        rowIdx += 1;
      }
    }
    return outputPath;
  }


  private Path filePath;
  
  public String getFilename() {
    return this.filePath.getFileName().toString();
  }

  void setFilePath(Path filePath) {
    this.filePath = filePath;
  }

  public List<String> validate() throws IOException {
    sf_logger.info("Validating {}", getFilename());

    List<String> messages = new ArrayList<>();
    int validSubjects = 0;
    int totalSubjects = 0;

    try (Reader reader = new FileReader(this.filePath.toFile())) {
      int lineNumber = 1;
      for (CSVRecord record : CSVFormat.DEFAULT.parse(reader)) {
        if (lineNumber != 1) {
          m_records.add(record);
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

    sf_logger.info("valid subject count: {}/{}", validSubjects, totalSubjects);
    return messages;
  }

  private List<String> validateRow(CSVRecord record, int lineNumber) {
    List<String> messages = new ArrayList<>();

    if (record.size() != getExpectedFields().length) {
      messages.add(String.format("Line %d unexpected length: expected %d, got %d", lineNumber, getExpectedFields().length, record.size()));
    }

    String subjectId = record.get(0);
    String siteId = record.get(1);
    for (int i = 0; i < getExpectedFields().length; i++) {
      Field field = getExpectedFields()[i];
      if (!field.validate(record.get(i))) {
        messages.add(String.format("%s\t%s\t%s%d\tinvalid %s\t%s\n",
            siteId,
            subjectId,
            ExcelUtils.getExcelColumnName(i+1), lineNumber,
            field.name(),
            record.get(i)
        ));
      }
    }

    return messages;
  }
  
  private Optional<Long> diffFromEnrollment(CSVRecord record, String dateString) {
    Date bleedingEvent = FieldPattern.parseDate(dateString);
    Date enrollment = FieldPattern.parseDate(getRecordValue(record, Field.ENROLLMENT_DATE));

    if (bleedingEvent != null && enrollment != null) {
      return Optional.of((bleedingEvent.getTime() - enrollment.getTime()) / DateUtils.MILLIS_PER_DAY);
    } else {
      return Optional.empty();
    }
  }

  private Optional<String> timeToBloodDraw(CSVRecord record) {
    String doseDate = getRecordValue(record, Field.DATE_OF_LAST_DOSE);
    String doseTime = getRecordValue(record, Field.TIME_OF_LAST_DOSE);
    
    String drawDate = getRecordValue(record, Field.DATE_OF_BLOOD_DRAW);
    String drawTime = getRecordValue(record, Field.TIME_OF_BLOOD_DRAW);
    
    if ( isMissing(doseDate) || isMissing(doseTime) || isMissing(drawDate) || isMissing(drawTime)) return Optional.empty();

    try {
      LocalDateTime doseStamp = org.pharmgkb.account.DateUtils.parseDateTime(doseDate, doseTime);
      LocalDateTime drawStamp = org.pharmgkb.account.DateUtils.parseDateTime(drawDate, drawTime);
      return Optional.of(org.pharmgkb.account.DateUtils.diff(doseStamp, drawStamp));
    } catch (DateTimeParseException ex) {
      sf_logger.warn("Could not parse DateTime", ex);
      return Optional.empty();
    }
  }

  private static String getDescription(String key) {
    if (StringUtils.isBlank(key)) return "";
    try {
      return sf_descriptions.getString(key);
    } catch (MissingResourceException ex) {
      return "";
    }
  } 
}
