package org.pharmgkb.account.file;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.bag.HashBag;
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
import java.util.stream.Collectors;

import static org.pharmgkb.account.data.FieldPattern.MISSING_DATA;
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
  private static final String LIST_SEPARATOR = "; ";

  private final List<CSVRecord> m_records = new ArrayList<>();
  private final Multimap<Field, Integer> fieldIndexMap = LinkedListMultimap.create();

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
    Bag<Field> fieldBag = new HashBag<>();

    for (Field field : getOutputFields()) {
      int seenCount = fieldBag.getCount(field);
      List<String> indications = new ArrayList<>();

      switch (field) {
        case PROJECT_SITE:
          String key = siteKeyMap.get(getRecordValue(record, Field.PROJECT_SITE));
          if (StringUtils.isBlank(key)) throw new RuntimeException("Project site not mapped: " + getRecordValue(record, Field.PROJECT_SITE));
          cells.add(key);
          break;
        case TIME_TO_BLEEDING_EVENT:
          cells.add(diffFromEnrollment(record, getRecordValue(record, Field.DATE_OF_BLEEDING_EVENT, seenCount)).map(String::valueOf).orElse(""));
          break;
        case TIME_TO_DEATH:
          cells.add(diffFromEnrollment(record, getRecordValue(record, Field.DATE_OF_DEATH, seenCount)).map(String::valueOf).orElse(""));
          break;
        case TIME_TO_EMBOLIC_EVENT:
          cells.add(diffFromEnrollment(record, getRecordValue(record, Field.DATE_OF_EMBOLIC_EVENT, seenCount)).map(String::valueOf).orElse(""));
          break;
        case DURATION_FOLLOWUP:
          cells.add(diffFromEnrollment(record, getRecordValue(record, Field.DATE_OF_LAST_FOLLOW_UP, seenCount)).map(String::valueOf).orElse(""));
          break;
        case TIME_TO_MACE:
          cells.add(diffFromEnrollment(record, getRecordValue(record, Field.DATE_OF_MACE, seenCount)).map(String::valueOf).orElse(""));
          break;
        case TIME_TO_STEMI:
          cells.add(diffFromEnrollment(record, getRecordValue(record, Field.DATE_OF_THE_FIRST_STEMI, seenCount)).map(String::valueOf).orElse(""));
          break;
        case TIME_TO_NSTEMI:
          cells.add(diffFromEnrollment(record, getRecordValue(record, Field.DATE_OF_THE_FIRST_NSTEMI, seenCount)).map(String::valueOf).orElse(""));
          break;
        case TIME_TO_ANGINA:
          cells.add(diffFromEnrollment(record, getRecordValue(record, Field.DATE_OF_THE_FIRST_UNSTABLE_ANGINA_DURING_FOLLOW_UP, seenCount)).map(String::valueOf).orElse(""));
          break;
        case TIME_TO_THROMB:
          cells.add(diffFromEnrollment(record, getRecordValue(record, Field.DATE_OF_THROMBOSIS, seenCount)).map(String::valueOf).orElse(""));
          break;
        case TIME_TO_CARD_DEATH:
          cells.add(diffFromEnrollment(record, getRecordValue(record, Field.DATE_OF_CARDIAC_DEATH, seenCount)).map(String::valueOf).orElse(""));
          break;
        case TIME_TO_MI:
          cells.add(diffFromEnrollment(record, getRecordValue(record, Field.DATE_OF_THE_FIRST_MI, seenCount)).map(String::valueOf).orElse(""));
          break;
        case TIME_TO_ACS:
          cells.add(diffFromEnrollment(record, getRecordValue(record, Field.DATE_OF_THE_FIRST_ACS, seenCount)).map(String::valueOf).orElse(""));
          break;
        case TIME_TO_ISC_STROKE:
          cells.add(diffFromEnrollment(record, getRecordValue(record, Field.DATE_OF_ISCHEMIC_STROKE, seenCount)).map(String::valueOf).orElse(""));
          break;
        case TIME_TO_HEM_STROKE:
          cells.add(diffFromEnrollment(record, getRecordValue(record, Field.DATE_OF_HEMORRHAGIC_STROKE, seenCount)).map(String::valueOf).orElse(""));
          break;
        case TIME_TO_BLOOD_DRAW:
          cells.add(timeToBloodDraw(record).map(String::valueOf).orElse(""));
          break;
        case EMBOLIC_EVENT:
          if (isChecked(record, Field.EMBOLIC_EVENT_CHOICE_0, seenCount)) indications.add("None");
          if (isChecked(record, Field.EMBOLIC_EVENT_CHOICE_1, seenCount)) indications.add("Stroke");
          if (isChecked(record, Field.EMBOLIC_EVENT_CHOICE_2, seenCount)) indications.add("DVT");
          if (isChecked(record, Field.EMBOLIC_EVENT_CHOICE_3, seenCount)) indications.add("PE");
          if (isChecked(record, Field.EMBOLIC_EVENT_CHOICE_4, seenCount)) indications.add("DVT/PE");
          if (isChecked(record, Field.EMBOLIC_EVENT_CHOICE_5, seenCount)) indications.add("Myocardial Infarction");
          if (isChecked(record, Field.EMBOLIC_EVENT_CHOICE_MD, seenCount)) indications.add(FieldPattern.MISSING_DATA);
          cells.add(String.join(LIST_SEPARATOR, indications));
          break;
        case INDICATION_FOR_CLOPIDOGREL_TREATMENT:
          if (isChecked(record, Field.INDICATION_FOR_CLOPIDOGREL_TREATMENT_CHOICE_1, seenCount)) indications.add("Coronary artery disease");
          if (isChecked(record, Field.INDICATION_FOR_CLOPIDOGREL_TREATMENT_CHOICE_2, seenCount)) indications.add("Peripheral arterial disease");
          if (isChecked(record, Field.INDICATION_FOR_CLOPIDOGREL_TREATMENT_CHOICE_3, seenCount)) indications.add("Ischemic stroke");
          if (isChecked(record, Field.INDICATION_FOR_CLOPIDOGREL_TREATMENT_CHOICE_4, seenCount)) indications.add("Acute cornary syndrome");
          if (isChecked(record, Field.INDICATION_FOR_CLOPIDOGREL_TREATMENT_CHOICE_5, seenCount)) indications.add("Other");
          cells.add(String.join(LIST_SEPARATOR, indications));
          break;
        case INDICATION_FOR_NOAC_TREATMENT:
          if (isChecked(record, Field.INDICATION_FOR_NOAC_TREATMENT_CHOICE_1, seenCount)) indications.add("DVT");
          if (isChecked(record, Field.INDICATION_FOR_NOAC_TREATMENT_CHOICE_2, seenCount)) indications.add("PE");
          if (isChecked(record, Field.INDICATION_FOR_NOAC_TREATMENT_CHOICE_3, seenCount)) indications.add("DVT/PE");
          if (isChecked(record, Field.INDICATION_FOR_NOAC_TREATMENT_CHOICE_4, seenCount)) indications.add("Atrial Fiberlation");
          if (isChecked(record, Field.INDICATION_FOR_NOAC_TREATMENT_CHOICE_5, seenCount)) indications.add("Other");
          if (isChecked(record, Field.INDICATION_FOR_NOAC_TREATMENT_CHOICE_MD, seenCount)) indications.add(FieldPattern.MISSING_DATA);
          cells.add(String.join(LIST_SEPARATOR, indications));
          break;
        case INDICATION_FOR_WARFARIN_TREATMENT:
          if (isChecked(record, Field.INDICATION_FOR_WARFARIN_TREATMENT_CHOICE_1, seenCount)) indications.add("DVT");
          if (isChecked(record, Field.INDICATION_FOR_WARFARIN_TREATMENT_CHOICE_2, seenCount)) indications.add("PE");
          if (isChecked(record, Field.INDICATION_FOR_WARFARIN_TREATMENT_CHOICE_3, seenCount)) indications.add("Afib/flutter");
          if (isChecked(record, Field.INDICATION_FOR_WARFARIN_TREATMENT_CHOICE_4, seenCount)) indications.add("Heart Valve");
          if (isChecked(record, Field.INDICATION_FOR_WARFARIN_TREATMENT_CHOICE_5, seenCount)) indications.add("Cardiomyopathy/LV Dilation");
          if (isChecked(record, Field.INDICATION_FOR_WARFARIN_TREATMENT_CHOICE_6, seenCount)) indications.add("Stroke");
          if (isChecked(record, Field.INDICATION_FOR_WARFARIN_TREATMENT_CHOICE_7, seenCount)) indications.add("Post-Orthopedic");
          if (isChecked(record, Field.INDICATION_FOR_WARFARIN_TREATMENT_CHOICE_8, seenCount)) indications.add("Other");
          if (isChecked(record, Field.INDICATION_FOR_WARFARIN_TREATMENT_CHOICE_MD, seenCount)) indications.add(FieldPattern.MISSING_DATA);
          cells.add(String.join(LIST_SEPARATOR, indications));
          break;
        case WHICH_NOAC_DRUG_USED:
          if (isChecked(record, Field.WHICH_NOAC_DRUG_USED_CHOICE_1, seenCount)) indications.add("apixaban");
          if (isChecked(record, Field.WHICH_NOAC_DRUG_USED_CHOICE_2, seenCount)) indications.add("rivaroxaban");
          if (isChecked(record, Field.WHICH_NOAC_DRUG_USED_CHOICE_3, seenCount)) indications.add("edoxaban");
          if (isChecked(record, Field.WHICH_NOAC_DRUG_USED_CHOICE_4, seenCount)) indications.add("dabigatran");
          cells.add(String.join(LIST_SEPARATOR, indications));
          break;
        case CARDIAC_DEATH:
          switch (getRecordValue(record, Field.CARDIAC_DEATH, seenCount)) {
            case "1":
              cells.add("Y");
              break;
            case "0":
              cells.add("N");
              break;
            case "MD":
              cells.add("MD");
              break;
            default:
              cells.add("");
          }
          break;
        case BINNED_AGE:
          String ageString = getRecordValue(record, Field.AGE_AT_ENROLLMENT, seenCount);
          if (isMissing(ageString)) {
            cells.add(MISSING_DATA);
          }
          try {
            float age = Float.parseFloat(ageString);
            if (age >= 90) {
              cells.add("90 and over");
            } else {
              cells.add(ageString);
            }
          } catch (NumberFormatException ex) {
            sf_logger.warn("Bad age number: " + ageString, ex);
            cells.add("BAD VALUE");
          }
          break;
        default:
          cells.add(getRecordValue(record, field, seenCount));
      }
      fieldBag.add(field);
    }

    return cells.stream()
        .map(c -> {
          if (FieldPattern.isMissing(c)) {
            return MISSING_DATA;
          } else {
            return c;
          }
        })
        .collect(Collectors.toList());
  }

  private String getRecordValue(@Nonnull CSVRecord record, @Nonnull Field field) {
    if (!fieldIndexMap.containsKey(field)) {
      throw new RuntimeException("Field not in dataset " + field);
    }
    Collection<Integer> indexes = fieldIndexMap.get(field);
    return record.get(indexes.iterator().next());
  }
  
  private String getRecordValue(@Nonnull CSVRecord record, @Nonnull Field field, int groupNumber) {
    if (!fieldIndexMap.containsKey(field)) {
      throw new RuntimeException("Field not in dataset " + field);
    }
    
    int n=0;
    for (Integer idx : fieldIndexMap.get(field)) {
      if (groupNumber == n) {
        return record.get(idx);
      } else {
        n += 1;
      }
    }
    return "";
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
  private Map<String,String> siteKeyMap;
  
  public String getFilename() {
    return this.filePath.getFileName().toString();
  }

  void setFilePath(Path filePath) {
    this.filePath = filePath;
  }

  void setSiteKeyMap(Map<String,String> siteKeyMap) {
    this.siteKeyMap = siteKeyMap;
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
      String fieldValue = record.get(i);
      if (!field.validate(fieldValue)) {
        messages.add(String.format("%s\t%s\t%s%d\tinvalid %s\t%s\n",
            siteId,
            subjectId,
            ExcelUtils.getExcelColumnName(i+1), lineNumber,
            field.name(),
            record.get(i)
        ));
      } else {
        try {
          if (field.getRangeTest() != null && !isMissing(fieldValue) && !field.getRangeTest().test(fieldValue)) {
            messages.add(String.format("%s\t%s\t%s%d\tout of range %s [%s]\t%s\n",
                siteId,
                subjectId,
                ExcelUtils.getExcelColumnName(i + 1), lineNumber,
                field.name(),
                field.getRangeDescription(),
                record.get(i)
            ));
          }
        } catch (NumberFormatException ex) {
          messages.add(String.format("%s\t%s\t%s%d\tbad numerical value for %s\t%s\n",
              siteId,
              subjectId,
              ExcelUtils.getExcelColumnName(i + 1), lineNumber,
              field.name(),
              record.get(i)
          ));
        }
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
      sf_logger.warn("Could not parse DateTime for " + record.get(0), ex);
      return Optional.empty();
    }
  }
  
  private boolean isChecked(CSVRecord record, Field field, int count) {
    return StringUtils.equals(getRecordValue(record, field, count), CHECKED);
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
