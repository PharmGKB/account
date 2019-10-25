package org.pharmgkb.account.file;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.pharmgkb.account.data.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * The clopidogrel data file
 *
 * @author Ryan Whaley
 */
public class ClopidogrelDataFile extends AbstractDataFile {
  private static final Logger sf_logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
  private static final String OUTPUT_FILE = "account_clopidogrel_processed.csv";
  public static final Field[] FIELDS = new org.pharmgkb.account.data.Field[]{
      Field.STUDY_ID_PHARMGKB_ID,
      Field.PROJECT_SITE,
      Field.GENDER,
      Field.DATE_OF_BIRTH,
      Field.ENROLLMENT_DATE,
      Field.AGE_AT_ENROLLMENT,
      Field.HEIGHT_CM,
      Field.WEIGHT_KG,
      Field.BMI,
      Field.NOTES,
      Field.LIST_OF_COMORBIDITIES,
      Field.DIABETES,
      Field.CONGESTIVE_HEART_FAILURE_AND_OR_CARDIOMYOPATHY,
      Field.HYPERTENSION,
      Field.HYPERCHOLESTEROLEMIA,
      Field.CURRENT_SMOKER,
      Field.FORMER_SMOKER,
      Field.HOW_LONG_A_SMOKER,
      Field.ALCOHOL,
      Field.INDICATION_FOR_CLOPIDOGREL_TREATMENT_CHOICE_1,
      Field.INDICATION_FOR_CLOPIDOGREL_TREATMENT_CHOICE_2,
      Field.INDICATION_FOR_CLOPIDOGREL_TREATMENT_CHOICE_3,
      Field.INDICATION_FOR_CLOPIDOGREL_TREATMENT_CHOICE_4,
      Field.INDICATION_FOR_CLOPIDOGREL_TREATMENT_CHOICE_5,
      Field.INDICATION_FOR_PCI,
      Field.PRIOR_PCI,
      Field.CARDIOGENIC_SHOCK_AT_TIME_OF_PCI,
      Field.PRIOR_MI,
      Field.PRIOR_CORONARY_ARTERY_BYPASS_GRAFTING_CABG,
      Field.PRIOR_ANGIOPLASTY,
      Field.VESSEL_DISEASE_50_STENOSIS,
      Field.COMPLETE,
      Field.CLOPIDOGREL_DOSE_MG_DAY,
      Field.APPROXIMATE_TIME_ON_THERAPY_AT_ENROLLMENT,
      Field.CESSATION_OF_THERAPY_COMPLIANCE,
      Field.ASPIRIN,
      Field.ASPIRIN_DAILY_DOSE_MG_DAY,
      Field.ASPIRIN_PRN_DOSE_MG_DAY,
      Field.ACETAMINOPHEN_OR_PARACETAMOL_TYLENOL,
      Field.ACETAMINOPHEN_PARACETAMOL_DAILY_DOSE_MG_DAY,
      Field.ACETAMINOPHEN_PARACETAMOL_PRN_DOSE_MG_DAY,
      Field.NSAIDS,
      Field.NSAIDS_DAILY_DOSE_MG_DAY,
      Field.NSAIDS_PRN_DOSE_MG_DAY,
      Field.PROTON_PUMP_INHIBITORS_PPIS,
      Field.PROTON_PUMP_INHIBITORS_PPIS_NAMES,
      Field.STATINS,
      Field.GPIIB_IIIA,
      Field.WARFARIN,
      Field.SSRIS,
      Field.SNRIS,
      Field.FLUCONAZOLE,
      Field.VORICONAZOLE,
      Field.RIFAMPIN,
      Field.RITONAVIR,
      Field.EFAVIRENZ,
      Field.PHENYTOIN,
      Field.BARBITURATES,
      Field.HERBAL_MEDICATIONS_VITAMINS_SUPPLEMENTS_INCLUDES_GARLIC_GINSENG_DANSHEN_DONG_QUAI_ZINC_IRON_MAGNESIUM_ETC,
      Field.OTHER_MEDICATIONS,
      Field.COMPLETE,
      Field.DURATION_OF_FOLLOW_UP,
      Field.PATIENT_LOST_TO_FOLLOW_UP,
      Field.BLEEDING_ACADEMIC_RESEARCH_CONSORTIUM_BARC_BLEEDING_SCORE,
      Field.DATE_OF_BLEEDING_EVENT,
      Field.MACE,
      Field.DATE_OF_MACE,
      Field.STEMI_DURING_FOLLOW_UP,
      Field.DATE_OF_THE_FIRST_STEMI,
      Field.NSTEMI_DURING_FOLLOW_UP,
      Field.DATE_OF_THE_FIRST_NSTEMI,
      Field.UNSTABLE_ANGINA,
      Field.DATE_OF_THE_FIRST_UNSTABLE_ANGINA_DURING_FOLLOW_UP,
      Field.STENT_TYPE,
      Field.STENT_THROMBOSIS_DURING_FOLLOW_UP,
      Field.STENT_THROMBOSIS_TIMING,
      Field.DATE_OF_THROMBOSIS,
      Field.TYPE_OF_STENT_THROMBOSIS,
      Field.CARDIAC_DEATH,
      Field.DATE_OF_CARDIAC_DEATH,
      Field.MYOCARDIAL_INFARCTION_MI,
      Field.DATE_OF_THE_FIRST_MI,
      Field.ACS_DURING_FOLLOW_UP,
      Field.DATE_OF_THE_FIRST_ACS,
      Field.ISCHEMIC_STROKE,
      Field.DATE_OF_ISCHEMIC_STROKE,
      Field.HEMORRHAGIC_STROKE,
      Field.DATE_OF_HEMORRHAGIC_STROKE,
      Field.PATIENT_DECEASED,
      Field.DATE_OF_DEATH,
      Field.DATE_OF_LAST_FOLLOW_UP,
      Field.COMPLETE,
      Field.DURATION_OF_FOLLOW_UP,
      Field.BLEEDING_ACADEMIC_RESEARCH_CONSORTIUM_BARC_BLEEDING_SCORE,
      Field.DATE_OF_BLEEDING_EVENT,
      Field.MACE,
      Field.DATE_OF_MACE,
      Field.STEMI_DURING_FOLLOW_UP,
      Field.DATE_OF_THE_FIRST_STEMI,
      Field.NSTEMI_DURING_FOLLOW_UP,
      Field.DATE_OF_THE_FIRST_NSTEMI,
      Field.UNSTABLE_ANGINA,
      Field.DATE_OF_THE_FIRST_UNSTABLE_ANGINA_DURING_FOLLOW_UP,
      Field.STENT_TYPE,
      Field.STENT_THROMBOSIS_DURING_FOLLOW_UP,
      Field.STENT_THROMBOSIS_TIMING,
      Field.DATE_OF_THROMBOSIS,
      Field.TYPE_OF_STENT_THROMBOSIS,
      Field.CARDIAC_DEATH,
      Field.DATE_OF_CARDIAC_DEATH,
      Field.MYOCARDIAL_INFARCTION_MI,
      Field.DATE_OF_THE_FIRST_MI,
      Field.ACS_DURING_FOLLOW_UP,
      Field.DATE_OF_THE_FIRST_ACS,
      Field.ISCHEMIC_STROKE,
      Field.DATE_OF_ISCHEMIC_STROKE,
      Field.HEMORRHAGIC_STROKE,
      Field.DATE_OF_HEMORRHAGIC_STROKE,
      Field.PATIENT_DECEASED,
      Field.DATE_OF_DEATH,
      Field.DATE_OF_LAST_FOLLOW_UP,
      Field.COMPLETE,
      Field.DURATION_OF_FOLLOW_UP,
      Field.BLEEDING_ACADEMIC_RESEARCH_CONSORTIUM_BARC_BLEEDING_SCORE,
      Field.DATE_OF_BLEEDING_EVENT,
      Field.MACE,
      Field.DATE_OF_MACE,
      Field.STEMI_DURING_FOLLOW_UP,
      Field.DATE_OF_THE_FIRST_STEMI,
      Field.NSTEMI_DURING_FOLLOW_UP,
      Field.DATE_OF_THE_FIRST_NSTEMI,
      Field.UNSTABLE_ANGINA,
      Field.DATE_OF_THE_FIRST_UNSTABLE_ANGINA_DURING_FOLLOW_UP,
      Field.STENT_TYPE,
      Field.STENT_THROMBOSIS_DURING_FOLLOW_UP,
      Field.STENT_THROMBOSIS_TIMING,
      Field.DATE_OF_THROMBOSIS,
      Field.TYPE_OF_STENT_THROMBOSIS,
      Field.CARDIAC_DEATH,
      Field.DATE_OF_CARDIAC_DEATH,
      Field.MYOCARDIAL_INFARCTION_MI,
      Field.DATE_OF_THE_FIRST_MI,
      Field.ACS_DURING_FOLLOW_UP,
      Field.DATE_OF_THE_FIRST_ACS,
      Field.ISCHEMIC_STROKE,
      Field.DATE_OF_ISCHEMIC_STROKE,
      Field.HEMORRHAGIC_STROKE,
      Field.DATE_OF_HEMORRHAGIC_STROKE,
      Field.PATIENT_DECEASED,
      Field.DATE_OF_DEATH,
      Field.DATE_OF_LAST_FOLLOW_UP,
      Field.COMPLETE,
      Field.DURATION_OF_FOLLOW_UP,
      Field.BLEEDING_ACADEMIC_RESEARCH_CONSORTIUM_BARC_BLEEDING_SCORE,
      Field.DATE_OF_BLEEDING_EVENT,
      Field.MACE,
      Field.DATE_OF_MACE,
      Field.STEMI_DURING_FOLLOW_UP,
      Field.DATE_OF_THE_FIRST_STEMI,
      Field.NSTEMI_DURING_FOLLOW_UP,
      Field.DATE_OF_THE_FIRST_NSTEMI,
      Field.UNSTABLE_ANGINA,
      Field.DATE_OF_THE_FIRST_UNSTABLE_ANGINA_DURING_FOLLOW_UP,
      Field.STENT_TYPE,
      Field.STENT_THROMBOSIS_DURING_FOLLOW_UP,
      Field.STENT_THROMBOSIS_TIMING,
      Field.DATE_OF_THROMBOSIS,
      Field.TYPE_OF_STENT_THROMBOSIS,
      Field.CARDIAC_DEATH,
      Field.DATE_OF_CARDIAC_DEATH,
      Field.MYOCARDIAL_INFARCTION_MI,
      Field.DATE_OF_THE_FIRST_MI,
      Field.ACS_DURING_FOLLOW_UP,
      Field.DATE_OF_THE_FIRST_ACS,
      Field.ISCHEMIC_STROKE,
      Field.DATE_OF_ISCHEMIC_STROKE,
      Field.HEMORRHAGIC_STROKE,
      Field.DATE_OF_HEMORRHAGIC_STROKE,
      Field.PATIENT_DECEASED,
      Field.DATE_OF_DEATH,
      Field.DATE_OF_LAST_FOLLOW_UP,
      Field.COMPLETE,
      Field.DURATION_OF_FOLLOW_UP,
      Field.BLEEDING_ACADEMIC_RESEARCH_CONSORTIUM_BARC_BLEEDING_SCORE,
      Field.DATE_OF_BLEEDING_EVENT,
      Field.MACE,
      Field.DATE_OF_MACE,
      Field.STEMI_DURING_FOLLOW_UP,
      Field.DATE_OF_THE_FIRST_STEMI,
      Field.NSTEMI_DURING_FOLLOW_UP,
      Field.DATE_OF_THE_FIRST_NSTEMI,
      Field.UNSTABLE_ANGINA,
      Field.DATE_OF_THE_FIRST_UNSTABLE_ANGINA_DURING_FOLLOW_UP,
      Field.STENT_TYPE,
      Field.STENT_THROMBOSIS_DURING_FOLLOW_UP,
      Field.STENT_THROMBOSIS_TIMING,
      Field.DATE_OF_THROMBOSIS,
      Field.TYPE_OF_STENT_THROMBOSIS,
      Field.CARDIAC_DEATH,
      Field.DATE_OF_CARDIAC_DEATH,
      Field.MYOCARDIAL_INFARCTION_MI,
      Field.DATE_OF_THE_FIRST_MI,
      Field.ACS_DURING_FOLLOW_UP,
      Field.DATE_OF_THE_FIRST_ACS,
      Field.ISCHEMIC_STROKE,
      Field.DATE_OF_ISCHEMIC_STROKE,
      Field.HEMORRHAGIC_STROKE,
      Field.DATE_OF_HEMORRHAGIC_STROKE,
      Field.PATIENT_DECEASED,
      Field.DATE_OF_DEATH,
      Field.DATE_OF_LAST_FOLLOW_UP,
      Field.COMPLETE,
      Field.TIME_INTERVAL_BETWEEN_LOADING_DOSE_AND_VERIFYNOW_PLATELET_AGGREGATION_MEASURES,
      Field.VERIFYNOW_ADP_STIMULATED_AGGREGATION_WHILE_ON_MAINTENANCE_DOSE_OF_CLOPIDOGREL_PRU,
      Field.VERIFYNOW_ADP_STIMULATED_AGGREGATION_WHILE_ON_MAINTENANCE_DOSE_OF_CLOPIDOGREL_INHIBITION,
      Field.BUN_MG_DL,
      Field.CREATININE_LEVEL_MG_DL,
      Field.BLOOD_CELL_COUNT_OPTIONAL,
      Field.ABSOLUTE_WHITE_CELL_COUNT_CELLS_MICROL,
      Field.RED_CELL_COUNT_CELLS_MICROL,
      Field.PLATELET_COUNT_CELLS_MICROL,
      Field.MEAN_PLATELET_VOLUME_FL,
      Field.HEMATOCRIT,
      Field.HEMOGLOBIN_G_DL__PRE_CLOPIDOGREL,
      Field.PLASMA_UREA_MMOL_L,
      Field.VARIOUS_CHOLESTEROL_MEASUREMENT_TOTAL_LDL_HDL_ETC,
      Field.LDL_MG_DL,
      Field.HDL_MG_DL,
      Field.TOTAL_CHOLESTEROL_MG_DL,
      Field.TRIGLYCERIDES_MG_DL,
      Field.ABSOLUTE_WHITE_CELL_COUNT_CELLS_MICROL_ON_PLAVIX,
      Field.RED_CELL_COUNT_CELLS_MICROL_ON_PLAVIX,
      Field.PLATELET_COUNT_CELLS_MICROL_ON_PLAVIX,
      Field.MEAN_PLATELET_VOLUME_FL_ON_PLAVIX,
      Field.HEMATOCRIT_ON_PLAVIX,
      Field.HEMOGLOBIN_G_DL_ON_PLAVIX,
      Field.COMPLETE,
  };
  private static final Map<Field, Field> CALCULATION_MAP = new HashMap<>();
  static {
    CALCULATION_MAP.put(Field.DATE_OF_BLEEDING_EVENT, Field.TIME_TO_BLEEDING_EVENT);
    CALCULATION_MAP.put(Field.DATE_OF_MACE, Field.TIME_TO_MACE);
    CALCULATION_MAP.put(Field.DATE_OF_THE_FIRST_STEMI, Field.TIME_TO_STEMI);
    CALCULATION_MAP.put(Field.DATE_OF_THE_FIRST_NSTEMI, Field.TIME_TO_NSTEMI);
    CALCULATION_MAP.put(Field.DATE_OF_THE_FIRST_UNSTABLE_ANGINA_DURING_FOLLOW_UP, Field.TIME_TO_ANGINA);
    CALCULATION_MAP.put(Field.DATE_OF_THROMBOSIS, Field.TIME_TO_THROMB);
    CALCULATION_MAP.put(Field.DATE_OF_CARDIAC_DEATH, Field.TIME_TO_CARD_DEATH);
    CALCULATION_MAP.put(Field.DATE_OF_THE_FIRST_MI, Field.TIME_TO_MI);
    CALCULATION_MAP.put(Field.DATE_OF_THE_FIRST_ACS, Field.TIME_TO_ACS);
    CALCULATION_MAP.put(Field.DATE_OF_ISCHEMIC_STROKE, Field.TIME_TO_ISC_STROKE);
    CALCULATION_MAP.put(Field.DATE_OF_HEMORRHAGIC_STROKE, Field.TIME_TO_HEM_STROKE);
    CALCULATION_MAP.put(Field.DATE_OF_DEATH, Field.TIME_TO_DEATH);
    CALCULATION_MAP.put(Field.DATE_OF_LAST_FOLLOW_UP, Field.DURATION_FOLLOWUP);
  }

  public ClopidogrelDataFile(Path filePath) {
    setFilePath(filePath);
  }
  
  public Field[] getExpectedFields() {
    return FIELDS;
  }
  
  public Path makeProcessedFile() throws IOException {
    Path outputPath = Paths.get("out", OUTPUT_FILE);
    try (
        FileWriter fileWriter = new FileWriter(outputPath.toFile());
        CSVPrinter csv = new CSVPrinter(fileWriter, CSVFormat.EXCEL)
    ) {
      // write the headers
      for (Field field : FIELDS) {
        csv.print(field.getDisplayName());
        if (CALCULATION_MAP.containsKey(field)) {
          csv.print(CALCULATION_MAP.get(field).getDisplayName());
        }
      }
      csv.println();

      // loop through each record of the dataset
      int rowIdx = 0;
      for (CSVRecord record : m_records) {
        int colIdx = 0;
        for(Object recordField : record) {
          csv.print(recordField);
          Field field = FIELDS[colIdx];
          Field calcField = CALCULATION_MAP.get(field);
          if (calcField != null) {
            String diff = diffFromEnrollment(record, (String)recordField).map(String::valueOf).orElse("");
            csv.print(diff);
            
            if (diff.startsWith("-")) {
              sf_logger.warn("Negative date diff in row {}, column {}", rowIdx+2, colIdx+1);
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
}
