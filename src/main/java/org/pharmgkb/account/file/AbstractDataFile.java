package org.pharmgkb.account.file;

import com.google.common.collect.Lists;
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

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
  private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("MM-dd-yyyy hh:mm aaa");
  private List<CSVRecord> m_records = new ArrayList<>();

  abstract Field[] getExpectedFields();
  
  abstract Map<Field, Field> getCalculationMap();
  
  abstract String getOutputFilename();

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
    List<Field> possibleFieldList = Lists.newArrayList(getExpectedFields());

    int enrollmentIdx = possibleFieldList.indexOf(Field.ENROLLMENT_DATE);

    Date bleedingEvent = FieldPattern.parseDate(dateString);
    Date enrollment = FieldPattern.parseDate(record.get(enrollmentIdx));

    if (bleedingEvent != null && enrollment != null) {
      return Optional.of((bleedingEvent.getTime() - enrollment.getTime()) / DateUtils.MILLIS_PER_DAY);
    } else {
      return Optional.empty();
    }
  }

  private Optional<Long> timeToBloodDraw(CSVRecord record) throws ParseException {
    List<Field> possibleFieldList = Lists.newArrayList(getExpectedFields());

    int doseDateIdx = possibleFieldList.indexOf(Field.DATE_OF_LAST_DOSE);
    int doseTimeIdx = possibleFieldList.indexOf(Field.TIME_OF_LAST_DOSE);
    String doseDate = record.get(doseDateIdx);
    String doseTime = record.get(doseTimeIdx);
    
    int drawDateIdx = possibleFieldList.indexOf(Field.DATE_OF_BLOOD_DRAW);
    int drawTimeIdx = possibleFieldList.indexOf(Field.TIME_OF_BLOOD_DRAW);
    String drawDate = record.get(drawDateIdx);
    String drawTime = record.get(drawTimeIdx);
    
    if ( isMissing(doseDate) || isMissing(doseTime) || isMissing(drawDate) || isMissing(drawTime)) return Optional.empty();

    Date drawStamp = TIMESTAMP_FORMAT.parse((drawDate + " " + drawTime).replaceAll("/", "-").replaceAll("\\s+", " "));
    Date doseStamp = TIMESTAMP_FORMAT.parse((doseDate + " " + doseTime).replaceAll("/", "-").replaceAll("\\s+", " "));
    return Optional.of((drawStamp.getTime() - doseStamp.getTime()) / DateUtils.MILLIS_PER_HOUR);
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
