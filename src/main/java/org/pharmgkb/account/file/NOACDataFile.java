package org.pharmgkb.account.file;

import org.pharmgkb.account.data.Field;

import java.nio.file.Path;

/**
 * The NOAC data file
 *
 * @author Ryan Whaley
 */
public class NOACDataFile extends AbstractDataFile {
  private static final String OUTPUT_FILE = "account_noac_processed.csv";
  public static final Field[] FIELDS = new Field[]{
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
      Field.INDICATION_FOR_NOAC_TREATMENT_CHOICE_1,
      Field.INDICATION_FOR_NOAC_TREATMENT_CHOICE_2,
      Field.INDICATION_FOR_NOAC_TREATMENT_CHOICE_3,
      Field.INDICATION_FOR_NOAC_TREATMENT_CHOICE_4,
      Field.INDICATION_FOR_NOAC_TREATMENT_CHOICE_5,
      Field.INDICATION_FOR_NOAC_TREATMENT_CHOICE_MD,
      Field.COMPLETE,
      Field.WHICH_NOAC_DRUG_USED_CHOICE_1,
      Field.WHICH_NOAC_DRUG_USED_CHOICE_2,
      Field.WHICH_NOAC_DRUG_USED_CHOICE_3,
      Field.WHICH_NOAC_DRUG_USED_CHOICE_4,
      Field.THERAPEUTIC_DOSE_MG_DAY,
      Field.APPROXIMATE_TIME_ON_THERAPY_AT_ENROLLMENT,
      Field.DATE_OF_LAST_DOSE,
      Field.TIME_OF_LAST_DOSE,
      Field.DATE_OF_BLOOD_DRAW,
      Field.TIME_OF_BLOOD_DRAW,
      Field.LIST_OF_ACTIVE_MEDICATIONS_RX_AND_OTC,
      Field.ASPIRIN,
      Field.ASPIRIN_DAILY_DOSE_MG_DAY,
      Field.ASPIRIN_PRN_DOSE_MG_DAY,
      Field.ACETAMINOPHEN_OR_PARACETAMOL_TYLENOL,
      Field.ACETAMINOPHEN_PARACETAMOL_DAILY_DOSE_MG_DAY,
      Field.ACETAMINOPHEN_PARACETAMOL_PRN_DOSE_MG_DAY,
      Field.NSAIDS,
      Field.NSAIDS_DAILY_DOSE_MG_DAY,
      Field.NSAIDS_PRN_DOSE_MG_DAY,
      Field.NSAID_NAMES,
      Field.COMPLETE,
      Field.DURATION_OF_FOLLOW_UP,
      Field.PATIENT_LOST_TO_FOLLOW_UP,
      Field.BLEEDING_ACADEMIC_RESEARCH_CONSORTIUM_BARC_BLEEDING_SCORE,
      Field.DATE_OF_BLEEDING_EVENT,
      Field.EMBOLIC_EVENT_CHOICE_0,
      Field.EMBOLIC_EVENT_CHOICE_1,
      Field.EMBOLIC_EVENT_CHOICE_2,
      Field.EMBOLIC_EVENT_CHOICE_3,
      Field.EMBOLIC_EVENT_CHOICE_4,
      Field.EMBOLIC_EVENT_CHOICE_5,
      Field.EMBOLIC_EVENT_CHOICE_MD,
      Field.DATE_OF_EMBOLIC_EVENT,
      Field.PATIENT_DECEASED,
      Field.DATE_OF_DEATH,
      Field.DATE_OF_LAST_FOLLOW_UP,
      Field.COMPLETE,
      Field.DURATION_OF_FOLLOW_UP,
      Field.BLEEDING_ACADEMIC_RESEARCH_CONSORTIUM_BARC_BLEEDING_SCORE,
      Field.DATE_OF_BLEEDING_EVENT,
      Field.EMBOLIC_EVENT_CHOICE_0,
      Field.EMBOLIC_EVENT_CHOICE_1,
      Field.EMBOLIC_EVENT_CHOICE_2,
      Field.EMBOLIC_EVENT_CHOICE_3,
      Field.EMBOLIC_EVENT_CHOICE_4,
      Field.EMBOLIC_EVENT_CHOICE_5,
      Field.EMBOLIC_EVENT_CHOICE_MD,
      Field.DATE_OF_EMBOLIC_EVENT,
      Field.PATIENT_DECEASED,
      Field.DATE_OF_DEATH,
      Field.DATE_OF_LAST_FOLLOW_UP,
      Field.COMPLETE,
      Field.DURATION_OF_FOLLOW_UP,
      Field.BLEEDING_ACADEMIC_RESEARCH_CONSORTIUM_BARC_BLEEDING_SCORE,
      Field.DATE_OF_BLEEDING_EVENT,
      Field.EMBOLIC_EVENT_CHOICE_0,
      Field.EMBOLIC_EVENT_CHOICE_1,
      Field.EMBOLIC_EVENT_CHOICE_2,
      Field.EMBOLIC_EVENT_CHOICE_3,
      Field.EMBOLIC_EVENT_CHOICE_4,
      Field.EMBOLIC_EVENT_CHOICE_5,
      Field.EMBOLIC_EVENT_CHOICE_MD,
      Field.DATE_OF_EMBOLIC_EVENT,
      Field.PATIENT_DECEASED,
      Field.DATE_OF_DEATH,
      Field.DATE_OF_LAST_FOLLOW_UP,
      Field.COMPLETE,
      Field.DURATION_OF_FOLLOW_UP,
      Field.BLEEDING_ACADEMIC_RESEARCH_CONSORTIUM_BARC_BLEEDING_SCORE,
      Field.DATE_OF_BLEEDING_EVENT,
      Field.EMBOLIC_EVENT_CHOICE_0,
      Field.EMBOLIC_EVENT_CHOICE_1,
      Field.EMBOLIC_EVENT_CHOICE_2,
      Field.EMBOLIC_EVENT_CHOICE_3,
      Field.EMBOLIC_EVENT_CHOICE_4,
      Field.EMBOLIC_EVENT_CHOICE_5,
      Field.EMBOLIC_EVENT_CHOICE_MD,
      Field.DATE_OF_EMBOLIC_EVENT,
      Field.PATIENT_DECEASED,
      Field.DATE_OF_DEATH,
      Field.DATE_OF_LAST_FOLLOW_UP,
      Field.COMPLETE,
      Field.DURATION_OF_FOLLOW_UP,
      Field.BLEEDING_ACADEMIC_RESEARCH_CONSORTIUM_BARC_BLEEDING_SCORE,
      Field.DATE_OF_BLEEDING_EVENT,
      Field.EMBOLIC_EVENT_CHOICE_0,
      Field.EMBOLIC_EVENT_CHOICE_1,
      Field.EMBOLIC_EVENT_CHOICE_2,
      Field.EMBOLIC_EVENT_CHOICE_3,
      Field.EMBOLIC_EVENT_CHOICE_4,
      Field.EMBOLIC_EVENT_CHOICE_5,
      Field.EMBOLIC_EVENT_CHOICE_MD,
      Field.DATE_OF_EMBOLIC_EVENT,
      Field.PATIENT_DECEASED,
      Field.DATE_OF_DEATH,
      Field.DATE_OF_LAST_FOLLOW_UP,
      Field.COMPLETE,
      Field.PLATELET_COUNT,
      Field.CREATININE_CLEARANCE_CRCL,
      Field.HEMATOCRIT,
      Field.HEMOGLOBIN_G_DL,
      Field.DILUTED_T_TIME_MEASUREMENT_DABIGATRAN,
      Field.ANTI_FACTOR_XA_ACTIVITY,
      Field.COMPLETE,
  };
  private static final Field[] OUTPUT_FIELDS = new Field[]{
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
      Field.INDICATION_FOR_NOAC_TREATMENT,
      Field.COMPLETE,
      Field.WHICH_NOAC_DRUG_USED,
      Field.THERAPEUTIC_DOSE_MG_DAY,
      Field.APPROXIMATE_TIME_ON_THERAPY_AT_ENROLLMENT,
      Field.DATE_OF_LAST_DOSE,
      Field.TIME_OF_LAST_DOSE,
      Field.DATE_OF_BLOOD_DRAW,
      Field.TIME_OF_BLOOD_DRAW,
      Field.TIME_TO_BLOOD_DRAW,
      Field.LIST_OF_ACTIVE_MEDICATIONS_RX_AND_OTC,
      Field.ASPIRIN,
      Field.ASPIRIN_DAILY_DOSE_MG_DAY,
      Field.ASPIRIN_PRN_DOSE_MG_DAY,
      Field.ACETAMINOPHEN_OR_PARACETAMOL_TYLENOL,
      Field.ACETAMINOPHEN_PARACETAMOL_DAILY_DOSE_MG_DAY,
      Field.ACETAMINOPHEN_PARACETAMOL_PRN_DOSE_MG_DAY,
      Field.NSAIDS,
      Field.NSAIDS_DAILY_DOSE_MG_DAY,
      Field.NSAIDS_PRN_DOSE_MG_DAY,
      Field.NSAID_NAMES,
      Field.COMPLETE,
      Field.DURATION_OF_FOLLOW_UP,
      Field.PATIENT_LOST_TO_FOLLOW_UP,
      Field.BLEEDING_ACADEMIC_RESEARCH_CONSORTIUM_BARC_BLEEDING_SCORE,
      Field.DATE_OF_BLEEDING_EVENT,
      Field.TIME_TO_BLEEDING_EVENT,
      Field.EMBOLIC_EVENT,
      Field.DATE_OF_EMBOLIC_EVENT,
      Field.TIME_TO_EMBOLIC_EVENT,
      Field.PATIENT_DECEASED,
      Field.DATE_OF_DEATH,
      Field.TIME_TO_DEATH,
      Field.DATE_OF_LAST_FOLLOW_UP,
      Field.DURATION_FOLLOWUP,
      Field.COMPLETE,
      Field.DURATION_OF_FOLLOW_UP,
      Field.BLEEDING_ACADEMIC_RESEARCH_CONSORTIUM_BARC_BLEEDING_SCORE,
      Field.DATE_OF_BLEEDING_EVENT,
      Field.TIME_TO_BLEEDING_EVENT,
      Field.EMBOLIC_EVENT,
      Field.DATE_OF_EMBOLIC_EVENT,
      Field.TIME_TO_EMBOLIC_EVENT,
      Field.PATIENT_DECEASED,
      Field.DATE_OF_DEATH,
      Field.TIME_TO_DEATH,
      Field.DATE_OF_LAST_FOLLOW_UP,
      Field.DURATION_FOLLOWUP,
      Field.COMPLETE,
      Field.DURATION_OF_FOLLOW_UP,
      Field.BLEEDING_ACADEMIC_RESEARCH_CONSORTIUM_BARC_BLEEDING_SCORE,
      Field.DATE_OF_BLEEDING_EVENT,
      Field.TIME_TO_BLEEDING_EVENT,
      Field.EMBOLIC_EVENT,
      Field.DATE_OF_EMBOLIC_EVENT,
      Field.TIME_TO_EMBOLIC_EVENT,
      Field.PATIENT_DECEASED,
      Field.DATE_OF_DEATH,
      Field.TIME_TO_DEATH,
      Field.DATE_OF_LAST_FOLLOW_UP,
      Field.DURATION_FOLLOWUP,
      Field.COMPLETE,
      Field.DURATION_OF_FOLLOW_UP,
      Field.BLEEDING_ACADEMIC_RESEARCH_CONSORTIUM_BARC_BLEEDING_SCORE,
      Field.DATE_OF_BLEEDING_EVENT,
      Field.TIME_TO_BLEEDING_EVENT,
      Field.EMBOLIC_EVENT,
      Field.DATE_OF_EMBOLIC_EVENT,
      Field.TIME_TO_EMBOLIC_EVENT,
      Field.PATIENT_DECEASED,
      Field.DATE_OF_DEATH,
      Field.TIME_TO_DEATH,
      Field.DATE_OF_LAST_FOLLOW_UP,
      Field.DURATION_FOLLOWUP,
      Field.COMPLETE,
      Field.DURATION_OF_FOLLOW_UP,
      Field.BLEEDING_ACADEMIC_RESEARCH_CONSORTIUM_BARC_BLEEDING_SCORE,
      Field.DATE_OF_BLEEDING_EVENT,
      Field.TIME_TO_BLEEDING_EVENT,
      Field.EMBOLIC_EVENT,
      Field.DATE_OF_EMBOLIC_EVENT,
      Field.TIME_TO_EMBOLIC_EVENT,
      Field.PATIENT_DECEASED,
      Field.DATE_OF_DEATH,
      Field.TIME_TO_DEATH,
      Field.DATE_OF_LAST_FOLLOW_UP,
      Field.DURATION_FOLLOWUP,
      Field.COMPLETE,
      Field.PLATELET_COUNT,
      Field.CREATININE_CLEARANCE_CRCL,
      Field.HEMATOCRIT,
      Field.HEMOGLOBIN_G_DL,
      Field.DILUTED_T_TIME_MEASUREMENT_DABIGATRAN,
      Field.ANTI_FACTOR_XA_ACTIVITY,
      Field.COMPLETE,
  };
  
  public NOACDataFile(Path filePath) {
    super();
    setFilePath(filePath);
  }

  @Override
  public Field[] getExpectedFields() {
    return FIELDS;
  }

  @Override
  Field[] getOutputFields() {
    return OUTPUT_FIELDS;
  }

  @Override
  String getOutputFilename() {
    return OUTPUT_FILE;
  }
}
