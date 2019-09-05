package org.pharmgkb.account;

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

  ACS_DURING_FOLLOW_UP("ACS during follow up", null), //C3, C4, C5, C6, C7
  ABSOLUTE_WHITE_CELL_COUNT_CELLS_MICROL("Absolute White cell count (cells/microL)", null), //C8
  ABSOLUTE_WHITE_CELL_COUNT_CELLS_MICROL_ON_PLAVIX("Absolute White cell count (cells/microL) on Plavix", null), //C8
  ACETAMINOPHEN_OR_PARACETAMOL_TYLENOL("Acetaminophen or Paracetamol (Tylenol)", null), //C2, N2, W2
  ACETAMINOPHEN_PARACETAMOL_DOSE_MG_DAY("Acetaminophen/Paracetamol Dose (mg/day)", null), //C2, N2, W2
  AGE_AT_ENROLLMENT("Age at enrollment", FieldPattern.INTEGER), //C1, N1, W1
  ALCOHOL("Alcohol", "^([01234]|MD)$"), //C1, N1, W1
  AMIODARONE_CORDARONE("Amiodarone (Cordarone)", null), //W2
  ANTI_FACTOR_XA_ACTIVITY("Anti-factor Xa activity", null), //N8
  ANTI_FUNGAL_AZOLES_INCLUDES_KETOCONAZOLE_FLUCONAZOLE_ITRACONAZOLE_DO_NOT_INCLUDE_OMEPRAZOLE_OR_METRONIDAZOLE("Anti-fungal Azoles (includes ketoconazole, fluconazole, itraconazole; do not include omeprazole or metronidazole)", null), //W2
  APPROXIMATE_TIME_ON_THERAPY_AT_ENROLLMENT("Approximate time on therapy at enrollment", null), //C2, N2, W2
  ASPIRIN("Aspirin", null), //C2, N2, W2
  ASPIRIN_DOSE_MG_DAY("Aspirin Dose (mg/day)", null), //C2, N2, W2
  ATORVASTATIN_LIPITOR("Atorvastatin (Lipitor)", null), //W2
  BMI("BMI", FieldPattern.DECIMAL), //C1, N1, W1
  BUN_MG_DL("BUN (mg/dL)", null), //C8
  BARBITURATES("Barbiturates", null), //C2
  BLEEDING_ACADEMIC_RESEARCH_CONSORTIUM_BARC_BLEEDING_SCORE("Bleeding Academic Research Consortium (BARC) bleeding score", null), //C3, C4, C5, C6, C7, N3, N4, N5, N6, N7, W3, W4, W5, W6, W7
  BLOOD_CELL_COUNT_OPTIONAL("Blood cell count (Optional)", null), //C8
  CARBAMAZEPINE_TEGRETOL("Carbamazepine (Tegretol)", null), //W2
  CARDIAC_DEATH("Cardiac death", null), //C3, C4, C5, C6, C7
  CARDIOGENIC_SHOCK_AT_TIME_OF_PCI("Cardiogenic shock at time of PCI", FieldPattern.YESNONA), //C1
  CERIVASTATIN_BAYCOL("Cerivastatin (Baycol)", null), //W2
  CESSATION_OF_THERAPY_COMPLIANCE("Cessation of therapy (compliance)", null), //C2, W2
  CLOPIDOGREL("Clopidogrel", null), //W2
  CLOPIDOGREL_DOSE_MG_DAY("Clopidogrel dose (mg/day)", null), //C2
  COMPLETE("Complete?", "^(Complete|Incomplete|Unverified)$"), //C1, C2, C3, C4, C5, C6, C7, C8, N1, N2, N3, N4, N5, N6, N7, N8, W1, W2, W3, W4, W5, W6, W7, W8
  CONGESTIVE_HEART_FAILURE_AND_OR_CARDIOMYOPATHY("Congestive Heart Failure and/or Cardiomyopathy", FieldPattern.YESNO), //C1, N1, W1
  CREATININE_CLEARANCE_CRCL("Creatinine clearance (CrCl)", null), //N8
  CREATININE_LEVEL_MG_DL("Creatinine level (mg/dL)", null), //C8
  CURRENT_SMOKER("Current smoker", FieldPattern.YESNO), //C1, N1, W1
  DATE_AND_TIME_OF_LAST_DOSE("Date and time of last dose", null), //W2
  DATE_OF_BIRTH("Date of Birth", FieldPattern.DATE), //C1, N1, W1
  DATE_OF_DEATH("Date of Death", FieldPattern.DATE), //C3, C4, C5, C6, C7, N3, N4, N5, N6, N7, W3, W4, W5, W6, W7
  DATE_OF_HEMORRHAGIC_STROKE("Date of Hemorrhagic Stroke", null), //C3, C4, C5, C6, C7
  DATE_OF_ISCHEMIC_STROKE("Date of Ischemic Stroke", null), //C3, C4, C5, C6, C7
  DATE_OF_LAST_FOLLOW_UP("Date of Last Follow up", null), //C3, C4, C5, C6, C7, N3, N4, N5, N6, N7, W3, W4, W5, W6, W7
  DATE_OF_MACE("Date of MACE", null), //C3, C4, C5, C6, C7
  DATE_OF_BLEEDING_EVENT("Date of bleeding event", null), //C3, C4, C5, C6, C7, N3, N4, N5, N6, N7, W3, W4, W5, W6, W7
  DATE_OF_BLOOD_DRAW("Date of blood draw", null), //N2
  DATE_OF_CARDIAC_DEATH("Date of cardiac death", null), //C3, C4, C5, C6, C7
  DATE_OF_EMBOLIC_EVENT("Date of embolic event", null), //N3, N4, N5, N6, N7, W3, W4, W5, W6, W7
  DATE_OF_LAST_DOSE("Date of last dose", null), //N2
  DATE_OF_THE_FIRST_ACS("Date of the first ACS", null), //C3, C4, C5, C6, C7
  DATE_OF_THE_FIRST_MI("Date of the first MI", null), //C3, C4, C5, C6, C7
  DATE_OF_THE_FIRST_NSTEMI("Date of the first NSTEMI", null), //C3, C4, C5, C6, C7
  DATE_OF_THE_FIRST_STEMI("Date of the first STEMI", null), //C3, C4, C5, C6, C7
  DATE_OF_THE_FIRST_UNSTABLE_ANGINA_DURING_FOLLOW_UP("Date of the first unstable angina during follow up", null), //C3, C4, C5, C6, C7
  DATE_OF_THROMBOSIS("Date of thrombosis", null), //C3, C4, C5, C6, C7
  DIABETES("Diabetes", "^([012]|MD)$"), //C1, N1, W1
  DILUTED_T_TIME_MEASUREMENT_DABIGATRAN("Diluted T time measurement (dabigatran)", null), //N8
  DIPYRIDAMOLE("Dipyridamole", null), //W2
  DURATION_OF_FOLLOW_UP("Duration of follow up", null), //C3, C4, C5, C6, C7, N3, N4, N5, N6, N7, W3, W4, W5, W6, W7
  EFAVIRENZ("Efavirenz", null), //C2
  EMBOLIC_EVENT_CHOICE_0("Embolic Event (choice=0)", null), //N3, N4, N5, N6, N7, W3, W4, W5, W6, W7
  EMBOLIC_EVENT_CHOICE_1("Embolic Event (choice=1)", null), //N3, N4, N5, N6, N7, W3, W4, W5, W6, W7
  EMBOLIC_EVENT_CHOICE_2("Embolic Event (choice=2)", null), //N3, N4, N5, N6, N7, W3, W4, W5, W6, W7
  EMBOLIC_EVENT_CHOICE_3("Embolic Event (choice=3)", null), //N3, N4, N5, N6, N7, W3, W4, W5, W6, W7
  EMBOLIC_EVENT_CHOICE_4("Embolic Event (choice=4)", null), //N3, N4, N5, N6, N7, W3, W4, W5, W6, W7
  EMBOLIC_EVENT_CHOICE_5("Embolic Event (choice=5)", null), //N3, N4, N5, N6, N7, W3, W4, W5, W6, W7
  EMBOLIC_EVENT_CHOICE_MD("Embolic Event (choice=MD)", null), //N3, N4, N5, N6, N7, W3, W4, W5, W6, W7
  ENROLLMENT_DATE("Enrollment date", FieldPattern.DATE), //C1, N1, W1
  ESTIMATED_TARGET_INR_IF_TARGET_NOT_KNOWN("Estimated Target INR, if target not known", null), //W2
  FLUCONAZOLE("Fluconazole", null), //C2
  FLUVASTATIN_LESCOL("Fluvastatin (Lescol)", null), //W2
  FORMER_SMOKER("Former smoker", FieldPattern.YESNO), //C1, N1, W1
  GENDER("Gender", "^[MF]$"), //C1, N1, W1
  GPIIB_IIIA("GpIIb/IIIa", null), //C2
  HDL_MG_DL("HDL (mg/dL)", null), //C8
  HEIGHT_CM("Height (cm)", FieldPattern.DECIMAL), //C1, N1, W1
  HEMATOCRIT("Hematocrit (%)", null), //C8, N8
  HEMATOCRIT_ON_PLAVIX("Hematocrit (%) on Plavix", null), //C8
  HEMOGLOBIN_G_DL("Hemoglobin (g/dL)", null), //N8, W8
  HEMOGLOBIN_G_DL_ON_PLAVIX("Hemoglobin (g/dL) on Plavix", null), //C8
  HEMOGLOBIN_G_DL__PRE_CLOPIDOGREL("Hemoglobin (g/dL)_pre-clopidogrel", null), //C8
  HEMORRHAGIC_STROKE("Hemorrhagic Stroke", null), //C3, C4, C5, C6, C7
  HERBAL_MEDICATIONS_VITAMINS_SUPPLEMENTS_INCLUDES_GARLIC_GINSENG_DANSHEN_DONG_QUAI_ZINC_IRON_MAGNESIUM_ETC("Herbal Medications, Vitamins, Supplements (includes garlic, ginseng, danshen, dong quai, zinc, iron, magnesium, etc)", null), //C2
  HERBAL_MEDICATIONS_VITAMINS_SUPPLEMENTS_INCLUDES_GARLIC_GINSENG_DANSHEN_DONQUAI_VITAMINS_ZINC_IRON_MAGNESIUM_ETC("Herbal Medications, Vitamins, Supplements (includes garlic, ginseng, danshen, donquai, vitamins, zinc, iron, magnesium, etc)", null), //W2
  HOW_LONG_A_SMOKER("How long a smoker?", FieldPattern.INTEGER), //C1, N1, W1
  HYPERCHOLESTEROLEMIA("Hypercholesterolemia", FieldPattern.YESNO), //C1, N1, W1
  HYPERTENSION("Hypertension", FieldPattern.YESNO), //C1, N1, W1
  INR_AT_EMBOLIC_EVENT("INR at Embolic event", null), //W3, W4, W5, W6, W7
  INR_AT_BLEEDING_EVENT("INR at bleeding event", null), //W3, W4, W5, W6, W7
  INR_ON_REPORTED_THERAPEUTIC_DOSE_OF_WARFARIN("INR on Reported therapeutic dose of warfarin", null), //W2
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
  ISCHEMIC_STROKE("Ischemic Stroke", null), //C3, C4, C5, C6, C7
  LDL_MG_DL("LDL (mg/dL)", null), //C8
  LIST_OF_ACTIVE_MEDICATIONS_RX_AND_OTC("List of active medications (RX and OTC)", null), //N2
  LIST_OF_COMORBIDITIES("List of comorbidities", null), //C1, N1, W1
  LOVASTATIN_MEVACOR("Lovastatin (Mevacor)", null), //W2
  MACE("MACE", null), //C3, C4, C5, C6, C7
  MACROLIDE_ANTIBIOTICS_INCLUDES_ERYTHROMYCIN_AZITHROMYCIN_AND_CLARITHROMYCIN("Macrolide Antibiotics (includes erythromycin, azithromycin, and clarithromycin)", null), //W2
  MEAN_PLATELET_VOLUME_FL("Mean platelet volume (fL)", null), //C8
  MEAN_PLATELET_VOLUME_FL_ON_PLAVIX("Mean platelet volume (fL) on Plavix", null), //C8
  MYOCARDIAL_INFARCTION_MI("Myocardial Infarction (MI)", null), //C3, C4, C5, C6, C7
  NSAID_NAMES("NSAID names", null), //N2
  NSAIDS("NSAIDs", null), //C2, N2, W2
  NSAIDS_DOSE_MG_DAY("NSAIDs dose (mg/day)", null), //C2, N2, W2
  NSTEMI_DURING_FOLLOW_UP("NSTEMI during follow up", null), //C3, C4, C5, C6, C7
  NOTES("Notes", null), //C1, N1, W1
  OTHER_MEDICATIONS("Other medications", null), //C2, W2
  PATIENT_DECEASED("Patient Deceased?", null), //C3, C4, C5, C6, C7, N3, N4, N5, N6, N7, W3, W4, W5, W6, W7
  PATIENT_LOST_TO_FOLLOW_UP("Patient lost to follow up?", null), //C3, N3, W3
  PHENYTOIN("Phenytoin", null), //C2
  PHENYTOIN_DILANTIN("Phenytoin (Dilantin)", null), //W2
  PLASMA_UREA_MMOL_L("Plasma Urea (mmol/L)", null), //C8
  PLATELET_COUNT("Platelet count", null), //N8, W8
  PLATELET_COUNT_CELLS_MICROL("Platelet count (cells/microL)", null), //C8
  PLATELET_COUNT_CELLS_MICROL_ON_PLAVIX("Platelet count (cells/microL) on Plavix", null), //C8
  PRAVASTATIN_PRAVACHOL("Pravastatin (Pravachol)", null), //W2
  PRIOR_CORONARY_ARTERY_BYPASS_GRAFTING_CABG("Prior Coronary artery bypass grafting (CABG)", FieldPattern.YESNONA), //C1
  PRIOR_MI("Prior MI", FieldPattern.YESNONA), //C1
  PRIOR_PCI("Prior PCI", FieldPattern.YESNONA), //C1
  PRIOR_ANGIOPLASTY("Prior angioplasty", FieldPattern.YESNONA), //C1
  PROJECT_SITE("Project site", "^NU|UIC|UofC|DCVA|GWU$"), //C1, N1, W1
  PROTON_PUMP_INHIBITORS_PPIS("Proton Pump Inhibitors (PPIs)", null), //C2
  RED_CELL_COUNT_CELLS_MICROL("Red cell count (cells/microL)", null), //C8
  RED_CELL_COUNT_CELLS_MICROL_ON_PLAVIX("Red cell count (cells/microL) on Plavix", null), //C8
  RIFAMPIN("Rifampin", null), //C2
  RIFAMPIN_OR_RIFAMPICIN("Rifampin or Rifampicin", null), //W2
  RITONAVIR("Ritonavir", null), //C2
  ROSUVASTATIN_CRESTOR("Rosuvastatin (Crestor)", null), //W2
  SNRIS("SNRIs", null), //C2
  SSRIS("SSRIs", null), //C2
  STEMI_DURING_FOLLOW_UP("STEMI during follow up", null), //C3, C4, C5, C6, C7
  SIMVASTATIN_ZOCOR("Simvastatin (Zocor)", null), //W2
  STABLE_DOSE_REACHED("Stable dose reached?", null), //W2
  STATINS("Statins", null), //C2
  STENT_THROMBOSIS_DURING_FOLLOW_UP("Stent Thrombosis during follow up", null), //C3, C4, C5, C6, C7
  STENT_THROMBOSIS_TIMING("Stent Thrombosis timing", null), //C3, C4, C5, C6, C7
  STENT_TYPE("Stent type", null), //C3, C4, C5, C6, C7
  STUDY_ID_PHARMGKB_ID("Study ID (PharmGKB ID)", "^PA\\d+"), //C1, N1, W1
  SULFONAMIDE_ANTIBIOTICS_INCLUDES_SEPTRA_BACTRIM_COTRIM_AND_SULFATRIM("Sulfonamide Antibiotics (includes Septra, Bactrim, Cotrim and Sulfatrim)", null), //W2
  TARGET_INR("Target INR", null), //W2
  THERAPEUTIC_DOSE_MG_DAY("Therapeutic dose (mg/day)", null), //N2
  TIME_INTERVAL_BETWEEN_LOADING_DOSE_AND_VERIFYNOW_PLATELET_AGGREGATION_MEASURES("Time interval between loading dose and VerifyNow platelet aggregation measures", null), //C8
  TIME_OF_BLOOD_DRAW("Time of blood draw", FieldPattern.TIME_OF_DAY), //N2
  TIME_OF_LAST_DOSE("Time of last dose", FieldPattern.TIME_OF_DAY), //N2
  TOTAL_CHOLESTEROL_MG_DL("Total Cholesterol (mg/dL)", null), //C8
  TRIGLYCERIDES_MG_DL("Triglycerides (mg/dL)", null), //C8
  TYPE_OF_STENT_THROMBOSIS("Type of stent thrombosis", null), //C3, C4, C5, C6, C7
  UNSTABLE_ANGINA("Unstable angina", null), //C3, C4, C5, C6, C7
  VALVE_REPLACEMENT("Valve Replacement", null), //W1
  VARIOUS_CHOLESTEROL_MEASUREMENT_TOTAL_LDL_HDL_ETC_REQUIRED("Various cholesterol measurement (total, LDL, HDL, etc.) (Required)", null), //C8
  VERIFYNOW_ADP_STIMULATED_AGGREGATION_WHILE_ON_MAINTENANCE_DOSE_OF_CLOPIDOGREL_INHIBITION("VerifyNow ADP stimulated Aggregation (while on maintenance dose of Clopidogrel) % Inhibition", null), //C8
  VERIFYNOW_ADP_STIMULATED_AGGREGATION_WHILE_ON_MAINTENANCE_DOSE_OF_CLOPIDOGREL_PRU("VerifyNow ADP stimulated Aggregation (while on maintenance dose of Clopidogrel) PRU", FieldPattern.INTEGERNA), //C8
  VESSEL_DISEASE_50_STENOSIS("Vessel Disease (> 50% stenosis)", "^([1234]|MD)$"), //C1
  VORICONAZOLE("Voriconazole", null), //C2
  WARFARIN("Warfarin", null), //C2
  WARFARIN_DOSE_MG_WEEK("Warfarin dose (mg/week)", null), //W2
  WEIGHT_KG("Weight (kg)", FieldPattern.DECIMAL), //C1, N1, W1
  WHICH_NOAC_DRUG_USED_CHOICE_1("Which NOAC drug used? (choice=1)", null), //N2
  WHICH_NOAC_DRUG_USED_CHOICE_2("Which NOAC drug used? (choice=2)", null), //N2
  WHICH_NOAC_DRUG_USED_CHOICE_3("Which NOAC drug used? (choice=3)", null), //N2
  WHICH_NOAC_DRUG_USED_CHOICE_4("Which NOAC drug used? (choice=4)", null), //N2
  EGFR_30_ML_MIN_1_73M_2("eGFR < 30 ml/min/1.73m^2", null), //W3, W4, W5, W6, W7
  EGFR_AT_EMBOLIC_EVENT("eGFR at Embolic event", null), //W3, W4, W5, W6, W7
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
    
    if (this.validationPattern == null) {
      return true;
    } else {
      return this.validationPattern.matcher(strippedValue).matches();
    }
  }
}
