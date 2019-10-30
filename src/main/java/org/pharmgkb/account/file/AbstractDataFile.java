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
  private static final String CHECKED = "Checked";

  private List<CSVRecord> m_records = new ArrayList<>();
  private Multimap<Field, Integer> fieldIndexMap = LinkedListMultimap.create();

  abstract Field[] getExpectedFields();
  abstract Field[] getOutputFields();
  abstract String getOutputFilename();
  
  AbstractDataFile() {
    for (int i = 0; i < getExpectedFields().length; i++) {
      fieldIndexMap.put(getExpectedFields()[i], i);
    }
  }

  private List<String> makeOutputHeader() {
    List<String> cells = new ArrayList<>();
    for (Field field : getOutputFields()) {
      cells.add(field.getDisplayName());
    }
    return cells;
  }

  private List<String> makeOutputDescriptions() {
    List<String> cells = new ArrayList<>();
    for (Field field : getOutputFields()) {
      cells.add(getDescription(field.name()));
    }
    return cells;
  }

  private List<String> makeOutputRow(CSVRecord record) {
    List<String> cells = new ArrayList<>();

    for (Field field : getOutputFields()) {
      List<String> indications = new ArrayList<>();

      switch (field) {
        case TIME_TO_BLEEDING_EVENT:
          cells.add(diffFromEnrollment(record, getRecordValue(record, Field.DATE_OF_BLEEDING_EVENT)).map(String::valueOf).orElse(""));
          break;
        case TIME_TO_DEATH:
          cells.add(diffFromEnrollment(record, getRecordValue(record, Field.DATE_OF_DEATH)).map(String::valueOf).orElse(""));
          break;
        case TIME_TO_EMBOLIC_EVENT:
          cells.add(diffFromEnrollment(record, getRecordValue(record, Field.DATE_OF_EMBOLIC_EVENT)).map(String::valueOf).orElse(""));
          break;
        case DURATION_FOLLOWUP:
          cells.add(diffFromEnrollment(record, getRecordValue(record, Field.DATE_OF_LAST_FOLLOW_UP)).map(String::valueOf).orElse(""));
          break;
        case TIME_TO_MACE:
          cells.add(diffFromEnrollment(record, getRecordValue(record, Field.DATE_OF_MACE)).map(String::valueOf).orElse(""));
          break;
        case TIME_TO_STEMI:
          cells.add(diffFromEnrollment(record, getRecordValue(record, Field.DATE_OF_THE_FIRST_STEMI)).map(String::valueOf).orElse(""));
          break;
        case TIME_TO_NSTEMI:
          cells.add(diffFromEnrollment(record, getRecordValue(record, Field.DATE_OF_THE_FIRST_NSTEMI)).map(String::valueOf).orElse(""));
          break;
        case TIME_TO_ANGINA:
          cells.add(diffFromEnrollment(record, getRecordValue(record, Field.DATE_OF_THE_FIRST_UNSTABLE_ANGINA_DURING_FOLLOW_UP)).map(String::valueOf).orElse(""));
          break;
        case TIME_TO_THROMB:
          cells.add(diffFromEnrollment(record, getRecordValue(record, Field.DATE_OF_THROMBOSIS)).map(String::valueOf).orElse(""));
          break;
        case TIME_TO_CARD_DEATH:
          cells.add(diffFromEnrollment(record, getRecordValue(record, Field.DATE_OF_CARDIAC_DEATH)).map(String::valueOf).orElse(""));
          break;
        case TIME_TO_MI:
          cells.add(diffFromEnrollment(record, getRecordValue(record, Field.DATE_OF_THE_FIRST_MI)).map(String::valueOf).orElse(""));
          break;
        case TIME_TO_ACS:
          cells.add(diffFromEnrollment(record, getRecordValue(record, Field.DATE_OF_THE_FIRST_ACS)).map(String::valueOf).orElse(""));
          break;
        case TIME_TO_ISC_STROKE:
          cells.add(diffFromEnrollment(record, getRecordValue(record, Field.DATE_OF_ISCHEMIC_STROKE)).map(String::valueOf).orElse(""));
          break;
        case TIME_TO_HEM_STROKE:
          cells.add(diffFromEnrollment(record, getRecordValue(record, Field.DATE_OF_HEMORRHAGIC_STROKE)).map(String::valueOf).orElse(""));
          break;
        case TIME_TO_BLOOD_DRAW:
          cells.add(timeToBloodDraw(record).map(String::valueOf).orElse(""));
          break;
        case EMBOLIC_EVENT:
          if (isChecked(record, Field.EMBOLIC_EVENT_CHOICE_0)) indications.add("None");
          if (isChecked(record, Field.EMBOLIC_EVENT_CHOICE_1)) indications.add("Stroke");
          if (isChecked(record, Field.EMBOLIC_EVENT_CHOICE_2)) indications.add("DVT");
          if (isChecked(record, Field.EMBOLIC_EVENT_CHOICE_3)) indications.add("PE");
          if (isChecked(record, Field.EMBOLIC_EVENT_CHOICE_4)) indications.add("DVT/PE");
          if (isChecked(record, Field.EMBOLIC_EVENT_CHOICE_5)) indications.add("Myocardial Infarction");
          if (isChecked(record, Field.EMBOLIC_EVENT_CHOICE_MD)) indications.add(FieldPattern.MISSING_DATA);
          cells.add(String.join(";", indications));
          break;
        case INDICATION_FOR_CLOPIDOGREL_TREATMENT:
          if (isChecked(record, Field.INDICATION_FOR_CLOPIDOGREL_TREATMENT_CHOICE_1)) indications.add("Coronary artery disease");
          if (isChecked(record, Field.INDICATION_FOR_CLOPIDOGREL_TREATMENT_CHOICE_2)) indications.add("Peripheral arterial disease");
          if (isChecked(record, Field.INDICATION_FOR_CLOPIDOGREL_TREATMENT_CHOICE_3)) indications.add("Ischemic stroke");
          if (isChecked(record, Field.INDICATION_FOR_CLOPIDOGREL_TREATMENT_CHOICE_4)) indications.add("Acute cornary syndrome");
          if (isChecked(record, Field.INDICATION_FOR_CLOPIDOGREL_TREATMENT_CHOICE_5)) indications.add("Other");
          cells.add(String.join(";", indications));
          break;
        case INDICATION_FOR_NOAC_TREATMENT:
          if (isChecked(record, Field.INDICATION_FOR_NOAC_TREATMENT_CHOICE_1)) indications.add("DVT");
          if (isChecked(record, Field.INDICATION_FOR_NOAC_TREATMENT_CHOICE_2)) indications.add("PE");
          if (isChecked(record, Field.INDICATION_FOR_NOAC_TREATMENT_CHOICE_3)) indications.add("Afib/flutter");
          if (isChecked(record, Field.INDICATION_FOR_NOAC_TREATMENT_CHOICE_4)) indications.add("Atrial Fiberlation");
          if (isChecked(record, Field.INDICATION_FOR_NOAC_TREATMENT_CHOICE_5)) indications.add("Other");
          if (isChecked(record, Field.INDICATION_FOR_NOAC_TREATMENT_CHOICE_MD)) indications.add(FieldPattern.MISSING_DATA);
          cells.add(String.join(";", indications));
          break;
        case INDICATION_FOR_WARFARIN_TREATMENT:
          if (isChecked(record, Field.INDICATION_FOR_WARFARIN_TREATMENT_CHOICE_1)) indications.add("DVT");
          if (isChecked(record, Field.INDICATION_FOR_WARFARIN_TREATMENT_CHOICE_2)) indications.add("PE");
          if (isChecked(record, Field.INDICATION_FOR_WARFARIN_TREATMENT_CHOICE_3)) indications.add("Afib/flutter");
          if (isChecked(record, Field.INDICATION_FOR_WARFARIN_TREATMENT_CHOICE_4)) indications.add("Heart Valve");
          if (isChecked(record, Field.INDICATION_FOR_WARFARIN_TREATMENT_CHOICE_5)) indications.add("Cardiomyopathy/LV Dilation");
          if (isChecked(record, Field.INDICATION_FOR_WARFARIN_TREATMENT_CHOICE_6)) indications.add("Stroke");
          if (isChecked(record, Field.INDICATION_FOR_WARFARIN_TREATMENT_CHOICE_7)) indications.add("Post-Orthopedic");
          if (isChecked(record, Field.INDICATION_FOR_WARFARIN_TREATMENT_CHOICE_8)) indications.add("Other");
          if (isChecked(record, Field.INDICATION_FOR_WARFARIN_TREATMENT_CHOICE_MD)) indications.add(FieldPattern.MISSING_DATA);
          cells.add(String.join(";", indications));
          break;
        case WHICH_NOAC_DRUG_USED:
          if (isChecked(record, Field.WHICH_NOAC_DRUG_USED_CHOICE_1)) indications.add("apixaban");
          if (isChecked(record, Field.WHICH_NOAC_DRUG_USED_CHOICE_2)) indications.add("rivaroxaban");
          if (isChecked(record, Field.WHICH_NOAC_DRUG_USED_CHOICE_3)) indications.add("edoxaban");
          if (isChecked(record, Field.WHICH_NOAC_DRUG_USED_CHOICE_4)) indications.add("dabigatran");
          cells.add(String.join(";", indications));
          break;
        default:
          cells.add(getRecordValue(record, field));
      }
    }

    return cells;
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
      csv.printRecord(makeOutputHeader());
      csv.printRecord(makeOutputDescriptions());

      // loop through each record of the dataset
      for (CSVRecord record : m_records) {
        csv.printRecord(makeOutputRow(record));
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
  
  private boolean isChecked(CSVRecord record, Field field) {
    return StringUtils.equals(getRecordValue(record, field), CHECKED);
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
