package org.pharmgkb.account.data;

import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.regex.Pattern;

/**
 * These are the possible fields found in the data files. Each field has a display value and validation rules.
 *
 * @author Ryan Whaley
 */
@SuppressWarnings("SpellCheckingInspection")
public enum Field {

  ACS_DURING_FOLLOW_UP("ACS during follow up", FieldPattern.YESNONA), //C3, C4, C5, C6, C7
  ABSOLUTE_WHITE_CELL_COUNT_CELLS_MICROL("Absolute White cell count (cells/microL)", FieldPattern.DECIMAL), //C8
  ABSOLUTE_WHITE_CELL_COUNT_CELLS_MICROL_ON_PLAVIX("Absolute White cell count (cells/microL) on Plavix", FieldPattern.DECIMAL), //C8
  ACETAMINOPHEN_OR_PARACETAMOL_TYLENOL("Acetaminophen or Paracetamol (Tylenol)", FieldPattern.YESNONA), //C2, N2, W2
  ACETAMINOPHEN_PARACETAMOL_DOSE_MG_DAY("Acetaminophen/Paracetamol Dose (mg/day)", FieldPattern.INTEGER), //C2, N2, W2
  AGE_AT_ENROLLMENT("Age at enrollment", FieldPattern.INTEGER), //C1, N1, W1
  ALCOHOL("Alcohol", "^([01234]|MD)$"), //C1, N1, W1
  AMIODARONE_CORDARONE("Amiodarone (Cordarone)", FieldPattern.YESNONA), //W2
  ANTI_FACTOR_XA_ACTIVITY("Anti-factor Xa activity", FieldPattern.DECIMAL), //N8
  ANTI_FUNGAL_AZOLES_INCLUDES_KETOCONAZOLE_FLUCONAZOLE_ITRACONAZOLE_DO_NOT_INCLUDE_OMEPRAZOLE_OR_METRONIDAZOLE("Anti-fungal Azoles (includes ketoconazole, fluconazole, itraconazole; do not include omeprazole or metronidazole)", FieldPattern.YESNONA), //W2
  APPROXIMATE_TIME_ON_THERAPY_AT_ENROLLMENT("Approximate time on therapy at enrollment", FieldPattern.DAYS), //C2, N2, W2
  ASPIRIN("Aspirin", FieldPattern.YESNONA), //C2, N2, W2
  ASPIRIN_DOSE_MG_DAY("Aspirin Dose (mg/day)", FieldPattern.INTEGER), //C2, N2, W2
  ATORVASTATIN_LIPITOR("Atorvastatin (Lipitor)", FieldPattern.YESNONA), //W2
  BMI("BMI", FieldPattern.DECIMAL), //C1, N1, W1
  BUN_MG_DL("BUN (mg/dL)", FieldPattern.DECIMAL), //C8
  BARBITURATES("Barbiturates", FieldPattern.YESNONA), //C2
  BLEEDING_ACADEMIC_RESEARCH_CONSORTIUM_BARC_BLEEDING_SCORE("Bleeding Academic Research Consortium (BARC) bleeding score", "(Type ([0235]|3[abc])|MD)"), //C3, C4, C5, C6, C7, N3, N4, N5, N6, N7, W3, W4, W5, W6, W7
  BLOOD_CELL_COUNT_OPTIONAL("Blood cell count (Optional)", FieldPattern.YESNONA), //C8
  CARBAMAZEPINE_TEGRETOL("Carbamazepine (Tegretol)", FieldPattern.YESNONA), //W2
  CARDIAC_DEATH("Cardiac death", "([01]|MD)"), //C3, C4, C5, C6, C7
  CARDIOGENIC_SHOCK_AT_TIME_OF_PCI("Cardiogenic shock at time of PCI", FieldPattern.YESNONA), //C1
  CERIVASTATIN_BAYCOL("Cerivastatin (Baycol)", FieldPattern.YESNONA), //W2
  CESSATION_OF_THERAPY_COMPLIANCE("Cessation of therapy (compliance)", FieldPattern.DATE), //C2, W2
  CLOPIDOGREL("Clopidogrel", FieldPattern.YESNONA), //W2
  CLOPIDOGREL_DOSE_MG_DAY("Clopidogrel dose (mg/day)", FieldPattern.INTEGER), //C2
  COMPLETE("Complete?", "^(Complete|Incomplete|Unverified)$"), //C1, C2, C3, C4, C5, C6, C7, C8, N1, N2, N3, N4, N5, N6, N7, N8, W1, W2, W3, W4, W5, W6, W7, W8
  CONGESTIVE_HEART_FAILURE_AND_OR_CARDIOMYOPATHY("Congestive Heart Failure and/or Cardiomyopathy", FieldPattern.YESNONA), //C1, N1, W1
  CREATININE_CLEARANCE_CRCL("Creatinine clearance (CrCl)", FieldPattern.DECIMAL), //N8
  CREATININE_LEVEL_MG_DL("Creatinine level (mg/dL)", FieldPattern.DECIMAL), //C8
  CURRENT_SMOKER("Current smoker", FieldPattern.YESNONA), //C1, N1, W1
  DATE_AND_TIME_OF_LAST_DOSE("Date and time of last dose", null), //W2
  DATE_OF_BIRTH("Date of Birth", FieldPattern.DATE), //C1, N1, W1
  DATE_OF_DEATH("Date of Death", FieldPattern.DATE), //C3, C4, C5, C6, C7, N3, N4, N5, N6, N7, W3, W4, W5, W6, W7
  DATE_OF_HEMORRHAGIC_STROKE("Date of Hemorrhagic Stroke", FieldPattern.DATE), //C3, C4, C5, C6, C7
  DATE_OF_ISCHEMIC_STROKE("Date of Ischemic Stroke", FieldPattern.DATE), //C3, C4, C5, C6, C7
  DATE_OF_LAST_FOLLOW_UP("Date of Last Follow up", FieldPattern.DATE), //C3, C4, C5, C6, C7, N3, N4, N5, N6, N7, W3, W4, W5, W6, W7
  DATE_OF_MACE("Date of MACE", FieldPattern.DATE), //C3, C4, C5, C6, C7
  DATE_OF_BLEEDING_EVENT("Date of bleeding event", FieldPattern.DATE), //C3, C4, C5, C6, C7, N3, N4, N5, N6, N7, W3, W4, W5, W6, W7
  DATE_OF_BLOOD_DRAW("Date of blood draw", FieldPattern.DATE), //N2
  DATE_OF_CARDIAC_DEATH("Date of cardiac death", FieldPattern.DATE), //C3, C4, C5, C6, C7
  DATE_OF_EMBOLIC_EVENT("Date of embolic event", FieldPattern.DATE), //N3, N4, N5, N6, N7, W3, W4, W5, W6, W7
  DATE_OF_LAST_DOSE("Date of last dose", FieldPattern.DATE), //N2
  DATE_OF_THE_FIRST_ACS("Date of the first ACS", FieldPattern.DATE), //C3, C4, C5, C6, C7
  DATE_OF_THE_FIRST_MI("Date of the first MI", FieldPattern.DATE), //C3, C4, C5, C6, C7
  DATE_OF_THE_FIRST_NSTEMI("Date of the first NSTEMI", FieldPattern.DATE), //C3, C4, C5, C6, C7
  DATE_OF_THE_FIRST_STEMI("Date of the first STEMI", FieldPattern.DATE), //C3, C4, C5, C6, C7
  DATE_OF_THE_FIRST_UNSTABLE_ANGINA_DURING_FOLLOW_UP("Date of the first unstable angina during follow up", FieldPattern.DATE), //C3, C4, C5, C6, C7
  DATE_OF_THROMBOSIS("Date of thrombosis", FieldPattern.DATE), //C3, C4, C5, C6, C7
  DIABETES("Diabetes", "^([012]|MD)$"), //C1, N1, W1
  DILUTED_T_TIME_MEASUREMENT_DABIGATRAN("Diluted T time measurement (dabigatran)", FieldPattern.DECIMAL), //N8
  DIPYRIDAMOLE("Dipyridamole", FieldPattern.YESNONA), //W2
  DURATION_OF_FOLLOW_UP("Duration of follow up", FieldPattern.DAYS), //C3, C4, C5, C6, C7, N3, N4, N5, N6, N7, W3, W4, W5, W6, W7
  EFAVIRENZ("Efavirenz", FieldPattern.YESNONA), //C2
  EMBOLIC_EVENT_CHOICE_0("Embolic Event (choice=0)", FieldPattern.CHECKED), //N3, N4, N5, N6, N7, W3, W4, W5, W6, W7
  EMBOLIC_EVENT_CHOICE_1("Embolic Event (choice=1)", FieldPattern.CHECKED), //N3, N4, N5, N6, N7, W3, W4, W5, W6, W7
  EMBOLIC_EVENT_CHOICE_2("Embolic Event (choice=2)", FieldPattern.CHECKED), //N3, N4, N5, N6, N7, W3, W4, W5, W6, W7
  EMBOLIC_EVENT_CHOICE_3("Embolic Event (choice=3)", FieldPattern.CHECKED), //N3, N4, N5, N6, N7, W3, W4, W5, W6, W7
  EMBOLIC_EVENT_CHOICE_4("Embolic Event (choice=4)", FieldPattern.CHECKED), //N3, N4, N5, N6, N7, W3, W4, W5, W6, W7
  EMBOLIC_EVENT_CHOICE_5("Embolic Event (choice=5)", FieldPattern.CHECKED), //N3, N4, N5, N6, N7, W3, W4, W5, W6, W7
  EMBOLIC_EVENT_CHOICE_MD("Embolic Event (choice=MD)", FieldPattern.CHECKED), //N3, N4, N5, N6, N7, W3, W4, W5, W6, W7
  ENROLLMENT_DATE("Enrollment date", FieldPattern.DATE), //C1, N1, W1
  ESTIMATED_TARGET_INR_IF_TARGET_NOT_KNOWN("Estimated Target INR, if target not known", FieldPattern.DECIMAL_RANGE), //W2
  FLUCONAZOLE("Fluconazole", FieldPattern.YESNONA), //C2
  FLUVASTATIN_LESCOL("Fluvastatin (Lescol)", FieldPattern.YESNONA), //W2
  FORMER_SMOKER("Former smoker", FieldPattern.YESNONA), //C1, N1, W1
  GENDER("Gender", "^[MF]$"), //C1, N1, W1
  GPIIB_IIIA("GpIIb/IIIa", FieldPattern.YESNONA), //C2
  HDL_MG_DL("HDL (mg/dL)", FieldPattern.INTEGER), //C8
  HEIGHT_CM("Height (cm)", FieldPattern.DECIMAL), //C1, N1, W1
  HEMATOCRIT("Hematocrit (%)", FieldPattern.DECIMAL), //C8, N8
  HEMATOCRIT_ON_PLAVIX("Hematocrit (%) on Plavix", FieldPattern.DECIMAL), //C8
  HEMOGLOBIN_G_DL("Hemoglobin (g/dL)", FieldPattern.DECIMAL), //N8, W8
  HEMOGLOBIN_G_DL_ON_PLAVIX("Hemoglobin (g/dL) on Plavix", FieldPattern.DECIMAL), //C8
  HEMOGLOBIN_G_DL__PRE_CLOPIDOGREL("Hemoglobin (g/dL)_pre-clopidogrel", FieldPattern.DECIMAL), //C8
  HEMORRHAGIC_STROKE("Hemorrhagic Stroke", FieldPattern.YESNONA), //C3, C4, C5, C6, C7
  HERBAL_MEDICATIONS_VITAMINS_SUPPLEMENTS_INCLUDES_GARLIC_GINSENG_DANSHEN_DONG_QUAI_ZINC_IRON_MAGNESIUM_ETC("Herbal Medications, Vitamins, Supplements (includes garlic, ginseng, danshen, dong quai, zinc, iron, magnesium, etc)", FieldPattern.YESNONA), //C2
  HERBAL_MEDICATIONS_VITAMINS_SUPPLEMENTS_INCLUDES_GARLIC_GINSENG_DANSHEN_DONQUAI_VITAMINS_ZINC_IRON_MAGNESIUM_ETC("Herbal Medications, Vitamins, Supplements (includes garlic, ginseng, danshen, donquai, vitamins, zinc, iron, magnesium, etc)", FieldPattern.YESNONA), //W2
  HOW_LONG_A_SMOKER("How long a smoker?", FieldPattern.INTEGER), //C1, N1, W1
  HYPERCHOLESTEROLEMIA("Hypercholesterolemia", FieldPattern.YESNONA), //C1, N1, W1
  HYPERTENSION("Hypertension", FieldPattern.YESNONA), //C1, N1, W1
  INR_AT_EMBOLIC_EVENT("INR at Embolic event", FieldPattern.DECIMAL), //W3, W4, W5, W6, W7
  INR_AT_BLEEDING_EVENT("INR at bleeding event", FieldPattern.DECIMAL), //W3, W4, W5, W6, W7
  INR_ON_REPORTED_THERAPEUTIC_DOSE_OF_WARFARIN("INR on Reported therapeutic dose of warfarin", FieldPattern.DECIMAL), //W2
  INDICATION_FOR_CLOPIDOGREL_TREATMENT_CHOICE_1("Indication for Clopidogrel Treatment (choice=1)", FieldPattern.CHECKED), //C1
  INDICATION_FOR_CLOPIDOGREL_TREATMENT_CHOICE_2("Indication for Clopidogrel Treatment (choice=2)", FieldPattern.CHECKED), //C1
  INDICATION_FOR_CLOPIDOGREL_TREATMENT_CHOICE_3("Indication for Clopidogrel Treatment (choice=3)", FieldPattern.CHECKED), //C1
  INDICATION_FOR_CLOPIDOGREL_TREATMENT_CHOICE_4("Indication for Clopidogrel Treatment (choice=4)", FieldPattern.CHECKED), //C1
  INDICATION_FOR_CLOPIDOGREL_TREATMENT_CHOICE_5("Indication for Clopidogrel Treatment (choice=5)", FieldPattern.CHECKED), //C1
  INDICATION_FOR_NOAC_TREATMENT_CHOICE_1("Indication for NOAC Treatment (choice=1)", FieldPattern.CHECKED), //N1
  INDICATION_FOR_NOAC_TREATMENT_CHOICE_2("Indication for NOAC Treatment (choice=2)", FieldPattern.CHECKED), //N1
  INDICATION_FOR_NOAC_TREATMENT_CHOICE_3("Indication for NOAC Treatment (choice=3)", FieldPattern.CHECKED), //N1
  INDICATION_FOR_NOAC_TREATMENT_CHOICE_4("Indication for NOAC Treatment (choice=4)", FieldPattern.CHECKED), //N1
  INDICATION_FOR_NOAC_TREATMENT_CHOICE_5("Indication for NOAC Treatment (choice=5)", FieldPattern.CHECKED), //N1
  INDICATION_FOR_NOAC_TREATMENT_CHOICE_MD("Indication for NOAC Treatment (choice=MD)", FieldPattern.CHECKED), //N1
  INDICATION_FOR_PCI("Indication for PCI", "^([1234]|MD)$"), //C1
  INDICATION_FOR_WARFARIN_TREATMENT_CHOICE_1("Indication for Warfarin Treatment (choice=1)", FieldPattern.CHECKED), //W1
  INDICATION_FOR_WARFARIN_TREATMENT_CHOICE_2("Indication for Warfarin Treatment (choice=2)", FieldPattern.CHECKED), //W1
  INDICATION_FOR_WARFARIN_TREATMENT_CHOICE_3("Indication for Warfarin Treatment (choice=3)", FieldPattern.CHECKED), //W1
  INDICATION_FOR_WARFARIN_TREATMENT_CHOICE_4("Indication for Warfarin Treatment (choice=4)", FieldPattern.CHECKED), //W1
  INDICATION_FOR_WARFARIN_TREATMENT_CHOICE_5("Indication for Warfarin Treatment (choice=5)", FieldPattern.CHECKED), //W1
  INDICATION_FOR_WARFARIN_TREATMENT_CHOICE_6("Indication for Warfarin Treatment (choice=6)", FieldPattern.CHECKED), //W1
  INDICATION_FOR_WARFARIN_TREATMENT_CHOICE_7("Indication for Warfarin Treatment (choice=7)", FieldPattern.CHECKED), //W1
  INDICATION_FOR_WARFARIN_TREATMENT_CHOICE_8("Indication for Warfarin Treatment (choice=8)", FieldPattern.CHECKED), //W1
  INDICATION_FOR_WARFARIN_TREATMENT_CHOICE_MD("Indication for Warfarin Treatment (choice=MD)", FieldPattern.CHECKED), //W1
  ISCHEMIC_STROKE("Ischemic Stroke", FieldPattern.YESNONA), //C3, C4, C5, C6, C7
  LDL_MG_DL("LDL (mg/dL)", FieldPattern.INTEGER), //C8
  LIST_OF_ACTIVE_MEDICATIONS_RX_AND_OTC("List of active medications (RX and OTC)", FieldPattern.ANY), //N2
  LIST_OF_COMORBIDITIES("List of comorbidities", FieldPattern.ANY), //C1, N1, W1
  LOVASTATIN_MEVACOR("Lovastatin (Mevacor)", FieldPattern.YESNONA), //W2
  MACE("MACE", FieldPattern.YESNONA), //C3, C4, C5, C6, C7
  MACROLIDE_ANTIBIOTICS_INCLUDES_ERYTHROMYCIN_AZITHROMYCIN_AND_CLARITHROMYCIN("Macrolide Antibiotics (includes erythromycin, azithromycin, and clarithromycin)", FieldPattern.YESNONA), //W2
  MEAN_PLATELET_VOLUME_FL("Mean platelet volume (fL)", FieldPattern.DECIMAL), //C8
  MEAN_PLATELET_VOLUME_FL_ON_PLAVIX("Mean platelet volume (fL) on Plavix", FieldPattern.DECIMAL), //C8
  MYOCARDIAL_INFARCTION_MI("Myocardial Infarction (MI)", FieldPattern.YESNONA), //C3, C4, C5, C6, C7
  NSAID_NAMES("NSAID names", FieldPattern.ANY), //N2
  NSAIDS("NSAIDs", FieldPattern.YESNONA), //C2, N2, W2
  NSAIDS_DOSE_MG_DAY("NSAIDs dose (mg/day)", FieldPattern.INTEGER), //C2, N2, W2
  NSTEMI_DURING_FOLLOW_UP("NSTEMI during follow up", FieldPattern.YESNONA), //C3, C4, C5, C6, C7
  NOTES("Notes", FieldPattern.ANY), //C1, N1, W1
  OTHER_MEDICATIONS("Other medications", FieldPattern.ANY), //C2, W2
  PATIENT_DECEASED("Patient Deceased?", FieldPattern.YESNONA), //C3, C4, C5, C6, C7, N3, N4, N5, N6, N7, W3, W4, W5, W6, W7
  PATIENT_LOST_TO_FOLLOW_UP("Patient lost to follow up?", FieldPattern.YESNONA), //C3, N3, W3
  PHENYTOIN("Phenytoin", FieldPattern.YESNONA), //C2
  PHENYTOIN_DILANTIN("Phenytoin (Dilantin)", FieldPattern.YESNONA), //W2
  PLASMA_UREA_MMOL_L("Plasma Urea (mmol/L)", FieldPattern.DECIMAL), //C8
  PLATELET_COUNT("Platelet count", FieldPattern.DECIMAL), //N8, W8
  PLATELET_COUNT_CELLS_MICROL("Platelet count (cells/microL)", FieldPattern.INTEGER), //C8
  PLATELET_COUNT_CELLS_MICROL_ON_PLAVIX("Platelet count (cells/microL) on Plavix", FieldPattern.INTEGER), //C8
  PRAVASTATIN_PRAVACHOL("Pravastatin (Pravachol)", FieldPattern.YESNONA), //W2
  PRIOR_CORONARY_ARTERY_BYPASS_GRAFTING_CABG("Prior Coronary artery bypass grafting (CABG)", FieldPattern.YESNONA), //C1
  PRIOR_MI("Prior MI", FieldPattern.YESNONA), //C1
  PRIOR_PCI("Prior PCI", FieldPattern.YESNONA), //C1
  PRIOR_ANGIOPLASTY("Prior angioplasty", FieldPattern.YESNONA), //C1
  PROJECT_SITE("Project site", "^NU|UIC|UofC|DCVA|GWU$"), //C1, N1, W1
  PROTON_PUMP_INHIBITORS_PPIS("Proton Pump Inhibitors (PPIs)", "([YN123456]|MD)"), //C2
  RED_CELL_COUNT_CELLS_MICROL("Red cell count (cells/microL)", FieldPattern.DECIMAL), //C8
  RED_CELL_COUNT_CELLS_MICROL_ON_PLAVIX("Red cell count (cells/microL) on Plavix", FieldPattern.DECIMAL), //C8
  RIFAMPIN("Rifampin", FieldPattern.YESNONA), //C2
  RIFAMPIN_OR_RIFAMPICIN("Rifampin or Rifampicin", FieldPattern.YESNONA), //W2
  RITONAVIR("Ritonavir", FieldPattern.YESNONA), //C2
  ROSUVASTATIN_CRESTOR("Rosuvastatin (Crestor)", FieldPattern.YESNONA), //W2
  SNRIS("SNRIs", FieldPattern.YESNONA), //C2
  SSRIS("SSRIs", FieldPattern.YESNONA), //C2
  STEMI_DURING_FOLLOW_UP("STEMI during follow up", FieldPattern.YESNONA), //C3, C4, C5, C6, C7
  SIMVASTATIN_ZOCOR("Simvastatin (Zocor)", FieldPattern.YESNONA), //W2
  STABLE_DOSE_REACHED("Stable dose reached?", FieldPattern.YESNONA), //W2
  STATINS("Statins", FieldPattern.YESNONA), //C2
  STENT_THROMBOSIS_DURING_FOLLOW_UP("Stent Thrombosis during follow up", FieldPattern.YESNONA), //C3, C4, C5, C6, C7
  STENT_THROMBOSIS_TIMING("Stent Thrombosis timing", FieldPattern.INTEGER), //C3, C4, C5, C6, C7
  STENT_TYPE("Stent type", "[123]|MD"), //C3, C4, C5, C6, C7
  STUDY_ID_PHARMGKB_ID("Study ID (PharmGKB ID)", "^PA\\d+"), //C1, N1, W1
  SULFONAMIDE_ANTIBIOTICS_INCLUDES_SEPTRA_BACTRIM_COTRIM_AND_SULFATRIM("Sulfonamide Antibiotics (includes Septra, Bactrim, Cotrim and Sulfatrim)", FieldPattern.YESNONA), //W2
  TARGET_INR("Target INR", FieldPattern.DECIMAL_RANGE), //W2
  THERAPEUTIC_DOSE_MG_DAY("Therapeutic dose (mg/day)", FieldPattern.DECIMAL), //N2
  TIME_INTERVAL_BETWEEN_LOADING_DOSE_AND_VERIFYNOW_PLATELET_AGGREGATION_MEASURES("Time interval between loading dose and VerifyNow platelet aggregation measures", FieldPattern.DECIMAL), //C8
  TIME_OF_BLOOD_DRAW("Time of blood draw", FieldPattern.TIME_OF_DAY), //N2
  TIME_OF_LAST_DOSE("Time of last dose", FieldPattern.TIME_OF_DAY), //N2
  TOTAL_CHOLESTEROL_MG_DL("Total Cholesterol (mg/dL)", FieldPattern.INTEGER), //C8
  TRIGLYCERIDES_MG_DL("Triglycerides (mg/dL)", FieldPattern.INTEGER), //C8
  TYPE_OF_STENT_THROMBOSIS("Type of stent thrombosis", "([123]|MD)"), //C3, C4, C5, C6, C7
  UNSTABLE_ANGINA("Unstable angina", FieldPattern.YESNONA), //C3, C4, C5, C6, C7
  VALVE_REPLACEMENT("Valve Replacement", FieldPattern.YESNONA), //W1
  VARIOUS_CHOLESTEROL_MEASUREMENT_TOTAL_LDL_HDL_ETC_REQUIRED("Various cholesterol measurement (total, LDL, HDL, etc.) (Required)", FieldPattern.YESNO), //C8
  VERIFYNOW_ADP_STIMULATED_AGGREGATION_WHILE_ON_MAINTENANCE_DOSE_OF_CLOPIDOGREL_INHIBITION("VerifyNow ADP stimulated Aggregation (while on maintenance dose of Clopidogrel) % Inhibition", FieldPattern.DECIMAL), //C8
  VERIFYNOW_ADP_STIMULATED_AGGREGATION_WHILE_ON_MAINTENANCE_DOSE_OF_CLOPIDOGREL_PRU("VerifyNow ADP stimulated Aggregation (while on maintenance dose of Clopidogrel) PRU", FieldPattern.INTEGER), //C8
  VESSEL_DISEASE_50_STENOSIS("Vessel Disease (> 50% stenosis)", "^([1234]|MD)$"), //C1
  VORICONAZOLE("Voriconazole", FieldPattern.YESNONA), //C2
  WARFARIN("Warfarin", FieldPattern.YESNONA), //C2
  WARFARIN_DOSE_MG_WEEK("Warfarin dose (mg/week)", FieldPattern.DECIMAL), //W2
  WEIGHT_KG("Weight (kg)", FieldPattern.DECIMAL), //C1, N1, W1
  WHICH_NOAC_DRUG_USED_CHOICE_1("Which NOAC drug used? (choice=1)", FieldPattern.CHECKED), //N2
  WHICH_NOAC_DRUG_USED_CHOICE_2("Which NOAC drug used? (choice=2)", FieldPattern.CHECKED), //N2
  WHICH_NOAC_DRUG_USED_CHOICE_3("Which NOAC drug used? (choice=3)", FieldPattern.CHECKED), //N2
  WHICH_NOAC_DRUG_USED_CHOICE_4("Which NOAC drug used? (choice=4)", FieldPattern.CHECKED), //N2
  EGFR_30_ML_MIN_1_73M_2("eGFR < 30 ml/min/1.73m^2", FieldPattern.YESNONA), //W3, W4, W5, W6, W7
  EGFR_AT_EMBOLIC_EVENT("eGFR at Embolic event", FieldPattern.DECIMAL), //W3, W4, W5, W6, W7
  ;
  
  private String displayName;
  private Pattern validationPattern = null;

  Field(String displayName, String validation) {
    this.displayName = displayName;
    if (validation != null) {
      this.validationPattern = Pattern.compile(validation);
    }
  }

  /**
   * The name used as the column header in the CSV file
   * @return a String display name
   */
  public String getDisplayName() {
    return this.displayName;
  }

  /**
   * Validates the given "value" for this field. This will convert empty values to "MD", the missing data symbol.
   * @param value a string value to check
   * @return true if this value is valid, false if it's invalid
   */
  public boolean validate(@Nullable String value) {
    String strippedValue = StringUtils.stripToNull(value);
    if (strippedValue == null) {
      strippedValue = FieldPattern.MISSING_DATA;
    }
    if (FieldPattern.MD_SYNONYMS.matcher(strippedValue.toLowerCase()).matches()) {
      strippedValue = FieldPattern.MISSING_DATA;
    }
    
    if (this.validationPattern == null) {
      return true;
    } else {
      return this.validationPattern.matcher(strippedValue).matches();
    }
  }
}
