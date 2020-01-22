package org.pharmgkb.account.data;

import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * These are the possible fields found in the data files. Each field has a display value and validation rules.
 *
 * @author Ryan Whaley
 */
@SuppressWarnings("SpellCheckingInspection")
public enum Field {

  ACS_DURING_FOLLOW_UP("ACS during follow up", FieldPattern.YESNONA, null), //C3, C4, C5, C6, C7
  ABSOLUTE_WHITE_CELL_COUNT_CELLS_MICROL("Absolute White cell count (cells/microL)", FieldPattern.DECIMAL, FieldPattern.WHITE_CELL_RANGE), //C8
  ABSOLUTE_WHITE_CELL_COUNT_CELLS_MICROL_ON_PLAVIX("Absolute White cell count (cells/microL) on Plavix", FieldPattern.DECIMAL, FieldPattern.WHITE_CELL_RANGE), //C8
  ACETAMINOPHEN_OR_PARACETAMOL_TYLENOL("Acetaminophen or Paracetamol (Tylenol)", FieldPattern.YESNONA, null), //C2, N2, W2
  ACETAMINOPHEN_PARACETAMOL_DOSE_MG_DAY      ("Acetaminophen/Paracetamol Dose (mg/day)", FieldPattern.INTEGER, FieldPattern.ACETA_DOSE_RANGE), //C2, N2, W2
  ACETAMINOPHEN_PARACETAMOL_DAILY_DOSE_MG_DAY("Acetaminophen/Paracetamol Daily Dose (mg/day)", FieldPattern.DECIMAL, FieldPattern.ACETA_DOSE_RANGE), //C2
  ACETAMINOPHEN_PARACETAMOL_PRN_DOSE_MG_DAY  ("Acetaminophen/Paracetamol PRN Dose (mg/day)", FieldPattern.DECIMAL, FieldPattern.ACETA_DOSE_RANGE), //C2
  AGE_AT_ENROLLMENT("Age at enrollment", FieldPattern.INTEGER, FieldPattern.AGE_RANGE), //C1, N1, W1
  ALCOHOL("Alcohol", "^([01234]|MD)$", null), //C1, N1, W1
  AMIODARONE_CORDARONE("Amiodarone (Cordarone)", FieldPattern.YESNONA, null), //W2
  ANTI_FACTOR_XA_ACTIVITY("Anti-factor Xa activity", FieldPattern.DECIMAL, (v) -> {
    Float value = Float.valueOf(v);
    return value >= 22 && value <= 500;
  }), //N8
  ANTI_FUNGAL_AZOLES_INCLUDES_KETOCONAZOLE_FLUCONAZOLE_ITRACONAZOLE_DO_NOT_INCLUDE_OMEPRAZOLE_OR_METRONIDAZOLE("Anti-fungal Azoles (includes ketoconazole, fluconazole, itraconazole; do not include omeprazole or metronidazole)", FieldPattern.YESNONA, null), //W2
  APPROXIMATE_TIME_ON_THERAPY_AT_ENROLLMENT("Approximate time on therapy at enrollment", FieldPattern.DAYS, null), //C2, N2, W2
  ASPIRIN("Aspirin", FieldPattern.YESNONA, null), //C2, N2, W2
  ASPIRIN_DOSE_MG_DAY("Aspirin Dose (mg/day)", FieldPattern.INTEGER, FieldPattern.ASPIRIN_DOSE_RANGE), //C2, N2, W2
  ASPIRIN_DAILY_DOSE_MG_DAY("Aspirin Daily Dose (mg/day)", FieldPattern.DECIMAL, FieldPattern.ASPIRIN_DOSE_RANGE), //C2
  ASPIRIN_PRN_DOSE_MG_DAY("Aspirin PRN Dose (mg/day)", FieldPattern.DECIMAL, FieldPattern.ASPIRIN_DOSE_RANGE), //C2
  ATORVASTATIN_LIPITOR("Atorvastatin (Lipitor)", FieldPattern.YESNONA, null), //W2
  BMI("BMI", FieldPattern.DECIMAL, (v) -> {
    Float value = Float.valueOf(v);
    return value >= 15 && value <= 50;
  }), //C1, N1, W1
  BUN_MG_DL("BUN (mg/dL)", FieldPattern.DECIMAL, (v) -> {
    Float value = Float.valueOf(v);
    return value >= 1 && value <= 120;
  }), //C8
  BARBITURATES("Barbiturates", FieldPattern.YESNONA, null), //C2
  BLEEDING_ACADEMIC_RESEARCH_CONSORTIUM_BARC_BLEEDING_SCORE("Bleeding Academic Research Consortium (BARC) bleeding score", "(Type ([0235]|3[abc])|MD)", null), //C3, C4, C5, C6, C7, N3, N4, N5, N6, N7, W3, W4, W5, W6, W7
  BLOOD_CELL_COUNT_OPTIONAL("Blood cell count (Optional)", FieldPattern.YESNONA, null), //C8
  CARBAMAZEPINE_TEGRETOL("Carbamazepine (Tegretol)", FieldPattern.YESNONA, null), //W2
  CARDIAC_DEATH("Cardiac death", "([01]|MD)", null), //C3, C4, C5, C6, C7
  CARDIOGENIC_SHOCK_AT_TIME_OF_PCI("Cardiogenic shock at time of PCI", FieldPattern.YESNONA, null), //C1
  CERIVASTATIN_BAYCOL("Cerivastatin (Baycol)", FieldPattern.YESNONA, null), //W2
  CESSATION_OF_THERAPY_COMPLIANCE("Cessation of therapy (compliance)", FieldPattern.DATE, null), //C2, W2
  CLOPIDOGREL("Clopidogrel", FieldPattern.YESNONA, null), //W2
  CLOPIDOGREL_DOSE_MG_DAY("Clopidogrel dose (mg/day)", FieldPattern.INTEGER, (v) -> Float.parseFloat(v) == 75), //C2
  COMPLETE("Complete?", "^(Complete|Incomplete|Unverified)$", null), //C1, C2, C3, C4, C5, C6, C7, C8, N1, N2, N3, N4, N5, N6, N7, N8, W1, W2, W3, W4, W5, W6, W7, W8
  CONGESTIVE_HEART_FAILURE_AND_OR_CARDIOMYOPATHY("Congestive Heart Failure and/or Cardiomyopathy", FieldPattern.YESNONA, null), //C1, N1, W1
  CREATININE_CLEARANCE_CRCL("Creatinine clearance (CrCl)", FieldPattern.DECIMAL, (v) -> {
    Float value = Float.valueOf(v);
    return value >= 5 && value <= 200;
  }), //N8
  CREATININE_LEVEL_MG_DL("Creatinine level (mg/dL)", FieldPattern.DECIMAL, (v) -> {
    Float value = Float.valueOf(v);
    return value >= 0.01 && value <= 15;
  }), //C8
  CURRENT_SMOKER("Current smoker", FieldPattern.YESNONA, null), //C1, N1, W1
  DATE_AND_TIME_OF_LAST_DOSE("Date and time of last dose", null, null), //W2
  DATE_OF_BIRTH("Date of Birth", FieldPattern.DATE, null), //C1, N1, W1
  DATE_OF_DEATH("Date of Death", FieldPattern.DATE, null), //C3, C4, C5, C6, C7, N3, N4, N5, N6, N7, W3, W4, W5, W6, W7
  DATE_OF_HEMORRHAGIC_STROKE("Date of Hemorrhagic Stroke", FieldPattern.DATE, null), //C3, C4, C5, C6, C7
  DATE_OF_ISCHEMIC_STROKE("Date of Ischemic Stroke", FieldPattern.DATE, null), //C3, C4, C5, C6, C7
  DATE_OF_LAST_FOLLOW_UP("Date of Last Follow up", FieldPattern.DATE, null), //C3, C4, C5, C6, C7, N3, N4, N5, N6, N7, W3, W4, W5, W6, W7
  DATE_OF_MACE("Date of MACE", FieldPattern.DATE, null), //C3, C4, C5, C6, C7
  DATE_OF_BLEEDING_EVENT("Date of bleeding event", FieldPattern.DATE, null), //C3, C4, C5, C6, C7, N3, N4, N5, N6, N7, W3, W4, W5, W6, W7
  DATE_OF_BLOOD_DRAW("Date of blood draw", FieldPattern.DATE, null), //N2
  DATE_OF_CARDIAC_DEATH("Date of cardiac death", FieldPattern.DATE, null), //C3, C4, C5, C6, C7
  DATE_OF_EMBOLIC_EVENT("Date of embolic event", FieldPattern.DATE, null), //N3, N4, N5, N6, N7, W3, W4, W5, W6, W7
  DATE_OF_LAST_DOSE("Date of last dose", FieldPattern.DATE, null), //N2
  DATE_OF_THE_FIRST_ACS("Date of the first ACS", FieldPattern.DATE, null), //C3, C4, C5, C6, C7
  DATE_OF_THE_FIRST_MI("Date of the first MI", FieldPattern.DATE, null), //C3, C4, C5, C6, C7
  DATE_OF_THE_FIRST_NSTEMI("Date of the first NSTEMI", FieldPattern.DATE, null), //C3, C4, C5, C6, C7
  DATE_OF_THE_FIRST_STEMI("Date of the first STEMI", FieldPattern.DATE, null), //C3, C4, C5, C6, C7
  DATE_OF_THE_FIRST_UNSTABLE_ANGINA_DURING_FOLLOW_UP("Date of the first unstable angina during follow up", FieldPattern.DATE, null), //C3, C4, C5, C6, C7
  DATE_OF_THROMBOSIS("Date of thrombosis", FieldPattern.DATE, null), //C3, C4, C5, C6, C7
  DIABETES("Diabetes", "^([012]|MD)$", null), //C1, N1, W1
  DILUTED_T_TIME_MEASUREMENT_DABIGATRAN("Diluted T time measurement (dabigatran)", FieldPattern.DECIMAL, (v) -> {
    Float value = Float.valueOf(v);
    return value >= 50 && value <= 250;
  }), //N8
  DIPYRIDAMOLE("Dipyridamole", FieldPattern.YESNONA, null), //W2
  DURATION_OF_FOLLOW_UP("Duration of follow up", FieldPattern.DAYS, null), //C3, C4, C5, C6, C7, N3, N4, N5, N6, N7, W3, W4, W5, W6, W7
  EFAVIRENZ("Efavirenz", FieldPattern.YESNONA, null), //C2

  // Embolic Event group
  EMBOLIC_EVENT_CHOICE_0("Embolic Event (choice=0)", FieldPattern.CHECKED, null), //N3, N4, N5, N6, N7, W3, W4, W5, W6, W7
  EMBOLIC_EVENT_CHOICE_1("Embolic Event (choice=1)", FieldPattern.CHECKED, null), //N3, N4, N5, N6, N7, W3, W4, W5, W6, W7
  EMBOLIC_EVENT_CHOICE_2("Embolic Event (choice=2)", FieldPattern.CHECKED, null), //N3, N4, N5, N6, N7, W3, W4, W5, W6, W7
  EMBOLIC_EVENT_CHOICE_3("Embolic Event (choice=3)", FieldPattern.CHECKED, null), //N3, N4, N5, N6, N7, W3, W4, W5, W6, W7
  EMBOLIC_EVENT_CHOICE_4("Embolic Event (choice=4)", FieldPattern.CHECKED, null), //N3, N4, N5, N6, N7, W3, W4, W5, W6, W7
  EMBOLIC_EVENT_CHOICE_5("Embolic Event (choice=5)", FieldPattern.CHECKED, null), //N3, N4, N5, N6, N7, W3, W4, W5, W6, W7
  EMBOLIC_EVENT_CHOICE_MD("Embolic Event (choice=MD)", FieldPattern.CHECKED, null), //N3, N4, N5, N6, N7, W3, W4, W5, W6, W7
  // end group

  ENROLLMENT_DATE("Enrollment date", FieldPattern.DATE, null), //C1, N1, W1
  ESTIMATED_TARGET_INR_IF_TARGET_NOT_KNOWN("Estimated Target INR, if target not known", FieldPattern.DECIMAL_RANGE, (v) -> {
    Float value = Float.valueOf(v);
    return value >= 1.5 && value <= 3.5;
  }), //W2
  FLUCONAZOLE("Fluconazole", FieldPattern.YESNONA, null), //C2
  FLUVASTATIN_LESCOL("Fluvastatin (Lescol)", FieldPattern.YESNONA, null), //W2
  FORMER_SMOKER("Former smoker", FieldPattern.YESNONA, null), //C1, N1, W1
  GENDER("Gender", "^[MF]$", null), //C1, N1, W1
  GPIIB_IIIA("GpIIb/IIIa", FieldPattern.YESNONA, null), //C2
  HDL_MG_DL("HDL (mg/dL)", FieldPattern.INTEGER, (v) -> {
    Float value = Float.valueOf(v);
    return value >= 20 && value <= 120;
  }), //C8
  HEIGHT_CM("Height (cm)", FieldPattern.DECIMAL, (v) -> {
    Float value = Float.valueOf(v);
    return value >= 134 && value <= 204;
  }), //C1, N1, W1
  HEMATOCRIT("Hematocrit (%)", FieldPattern.DECIMAL, FieldPattern.HEMATOCRIT_RANGE), //C8, N8
  HEMATOCRIT_ON_PLAVIX("Hematocrit (%) on Plavix", FieldPattern.DECIMAL, FieldPattern.HEMATOCRIT_RANGE), //C8
  HEMOGLOBIN_G_DL("Hemoglobin (g/dL)", FieldPattern.DECIMAL, FieldPattern.HEMOGLOBIN_RANGE), //N8, W8
  HEMOGLOBIN_G_DL_ON_PLAVIX("Hemoglobin (g/dL) on Plavix", FieldPattern.DECIMAL, FieldPattern.HEMOGLOBIN_RANGE), //C8
  HEMOGLOBIN_G_DL__PRE_CLOPIDOGREL("Hemoglobin (g/dL)_pre-clopidogrel", FieldPattern.DECIMAL, FieldPattern.HEMOGLOBIN_RANGE), //C8
  HEMORRHAGIC_STROKE("Hemorrhagic Stroke", FieldPattern.YESNONA, null), //C3, C4, C5, C6, C7
  HERBAL_MEDICATIONS_VITAMINS_SUPPLEMENTS_INCLUDES_GARLIC_GINSENG_DANSHEN_DONG_QUAI_ZINC_IRON_MAGNESIUM_ETC("Herbal Medications, Vitamins, Supplements (includes garlic, ginseng, danshen, dong quai, zinc, iron, magnesium, etc)", FieldPattern.YESNONA, null), //C2
  HERBAL_MEDICATIONS_VITAMINS_SUPPLEMENTS_INCLUDES_GARLIC_GINSENG_DANSHEN_DONQUAI_VITAMINS_ZINC_IRON_MAGNESIUM_ETC("Herbal Medications, Vitamins, Supplements (includes garlic, ginseng, danshen, donquai, vitamins, zinc, iron, magnesium, etc)", FieldPattern.YESNONA, null), //W2
  HOW_LONG_A_SMOKER("How long a smoker?", FieldPattern.DECIMAL, null), //C1, N1, W1
  HYPERCHOLESTEROLEMIA("Hypercholesterolemia", FieldPattern.YESNONA, null), //C1, N1, W1
  HYPERTENSION("Hypertension", FieldPattern.YESNONA, null), //C1, N1, W1
  INR_AT_EMBOLIC_EVENT("INR at Embolic event", FieldPattern.DECIMAL, FieldPattern.INR_RANGE), //W3, W4, W5, W6, W7
  INR_AT_BLEEDING_EVENT("INR at bleeding event", FieldPattern.DECIMAL, FieldPattern.INR_RANGE), //W3, W4, W5, W6, W7
  INR_ON_REPORTED_THERAPEUTIC_DOSE_OF_WARFARIN("INR on Reported therapeutic dose of warfarin", FieldPattern.DECIMAL, FieldPattern.INR_RANGE), //W2
  
  // Indication for clopidogrel treatment group
  INDICATION_FOR_CLOPIDOGREL_TREATMENT_CHOICE_1("Indication for Clopidogrel Treatment (choice=1)", FieldPattern.CHECKED, null), //C1
  INDICATION_FOR_CLOPIDOGREL_TREATMENT_CHOICE_2("Indication for Clopidogrel Treatment (choice=2)", FieldPattern.CHECKED, null), //C1
  INDICATION_FOR_CLOPIDOGREL_TREATMENT_CHOICE_3("Indication for Clopidogrel Treatment (choice=3)", FieldPattern.CHECKED, null), //C1
  INDICATION_FOR_CLOPIDOGREL_TREATMENT_CHOICE_4("Indication for Clopidogrel Treatment (choice=4)", FieldPattern.CHECKED, null), //C1
  INDICATION_FOR_CLOPIDOGREL_TREATMENT_CHOICE_5("Indication for Clopidogrel Treatment (choice=5)", FieldPattern.CHECKED, null), //C1
  // end group
  
  // Indication for NOAC treatment group
  INDICATION_FOR_NOAC_TREATMENT_CHOICE_1("Indication for NOAC Treatment (choice=1)", FieldPattern.CHECKED, null), //N1
  INDICATION_FOR_NOAC_TREATMENT_CHOICE_2("Indication for NOAC Treatment (choice=2)", FieldPattern.CHECKED, null), //N1
  INDICATION_FOR_NOAC_TREATMENT_CHOICE_3("Indication for NOAC Treatment (choice=3)", FieldPattern.CHECKED, null), //N1
  INDICATION_FOR_NOAC_TREATMENT_CHOICE_4("Indication for NOAC Treatment (choice=4)", FieldPattern.CHECKED, null), //N1
  INDICATION_FOR_NOAC_TREATMENT_CHOICE_5("Indication for NOAC Treatment (choice=5)", FieldPattern.CHECKED, null), //N1
  INDICATION_FOR_NOAC_TREATMENT_CHOICE_MD("Indication for NOAC Treatment (choice=MD)", FieldPattern.CHECKED, null), //N1
  // end group
  
  INDICATION_FOR_PCI("Indication for PCI", "^([1234]|MD)$", null), //C1
  
  // Indicaiton for warfarin group
  INDICATION_FOR_WARFARIN_TREATMENT_CHOICE_1("Indication for Warfarin Treatment (choice=1)", FieldPattern.CHECKED, null), //W1
  INDICATION_FOR_WARFARIN_TREATMENT_CHOICE_2("Indication for Warfarin Treatment (choice=2)", FieldPattern.CHECKED, null), //W1
  INDICATION_FOR_WARFARIN_TREATMENT_CHOICE_3("Indication for Warfarin Treatment (choice=3)", FieldPattern.CHECKED, null), //W1
  INDICATION_FOR_WARFARIN_TREATMENT_CHOICE_4("Indication for Warfarin Treatment (choice=4)", FieldPattern.CHECKED, null), //W1
  INDICATION_FOR_WARFARIN_TREATMENT_CHOICE_5("Indication for Warfarin Treatment (choice=5)", FieldPattern.CHECKED, null), //W1
  INDICATION_FOR_WARFARIN_TREATMENT_CHOICE_6("Indication for Warfarin Treatment (choice=6)", FieldPattern.CHECKED, null), //W1
  INDICATION_FOR_WARFARIN_TREATMENT_CHOICE_7("Indication for Warfarin Treatment (choice=7)", FieldPattern.CHECKED, null), //W1
  INDICATION_FOR_WARFARIN_TREATMENT_CHOICE_8("Indication for Warfarin Treatment (choice=8)", FieldPattern.CHECKED, null), //W1
  INDICATION_FOR_WARFARIN_TREATMENT_CHOICE_MD("Indication for Warfarin Treatment (choice=MD)", FieldPattern.CHECKED, null), //W1
  // end group
  
  ISCHEMIC_STROKE("Ischemic Stroke", FieldPattern.YESNONA, null), //C3, C4, C5, C6, C7
  LDL_MG_DL("LDL (mg/dL)", FieldPattern.INTEGER, (v) -> {
    Float value = Float.valueOf(v);
    return value >= 45 && value <= 200;
  }), //C8
  LIST_OF_ACTIVE_MEDICATIONS_RX_AND_OTC("List of active medications (RX and OTC)", FieldPattern.ANY, null), //N2
  LIST_OF_COMORBIDITIES("List of comorbidities", FieldPattern.ANY, null), //C1, N1, W1
  LOVASTATIN_MEVACOR("Lovastatin (Mevacor)", FieldPattern.YESNONA, null), //W2
  MACE("MACE", FieldPattern.YESNONA, null), //C3, C4, C5, C6, C7
  MACROLIDE_ANTIBIOTICS_INCLUDES_ERYTHROMYCIN_AZITHROMYCIN_AND_CLARITHROMYCIN("Macrolide Antibiotics (includes erythromycin, azithromycin, and clarithromycin)", FieldPattern.YESNONA, null), //W2
  MEAN_PLATELET_VOLUME_FL("Mean platelet volume (fL)", FieldPattern.DECIMAL, null), //C8
  MEAN_PLATELET_VOLUME_FL_ON_PLAVIX("Mean platelet volume (fL) on Plavix", FieldPattern.DECIMAL, null), //C8
  MYOCARDIAL_INFARCTION_MI("Myocardial Infarction (MI)", FieldPattern.YESNONA, null), //C3, C4, C5, C6, C7
  NSAID_NAMES("NSAID names", FieldPattern.ANY, null), //N2
  NSAIDS("NSAIDs", FieldPattern.YESNONA, null), //C2, N2, W2
  NSAIDS_DOSE_MG_DAY("NSAIDs dose (mg/day)", FieldPattern.INTEGER, FieldPattern.NSAID_DOSE_RANGE), //C2, N2, W2
  NSAIDS_DAILY_DOSE_MG_DAY("NSAIDs Daily Dose (mg/day)", FieldPattern.DECIMAL, FieldPattern.NSAID_DOSE_RANGE), //C2
  NSAIDS_PRN_DOSE_MG_DAY("NSAIDs PRN Dose (mg/day)", FieldPattern.DECIMAL, FieldPattern.NSAID_DOSE_RANGE), //C2
  NSTEMI_DURING_FOLLOW_UP("NSTEMI during follow up", FieldPattern.YESNONA, null), //C3, C4, C5, C6, C7
  NOTES("Notes", FieldPattern.ANY, null), //C1, N1, W1
  OTHER_MEDICATIONS("Other medications", FieldPattern.ANY, null), //C2, W2
  PATIENT_DECEASED("Patient Deceased?", FieldPattern.YESNONA, null), //C3, C4, C5, C6, C7, N3, N4, N5, N6, N7, W3, W4, W5, W6, W7
  PATIENT_LOST_TO_FOLLOW_UP("Patient lost to follow up?", FieldPattern.YESNONA, null), //C3, N3, W3
  PHENYTOIN("Phenytoin", FieldPattern.YESNONA, null), //C2
  PHENYTOIN_DILANTIN("Phenytoin (Dilantin)", FieldPattern.YESNONA, null), //W2
  PLASMA_UREA_MMOL_L("Plasma Urea (mmol/L)", FieldPattern.DECIMAL, null), //C8
  PLATELET_COUNT("Platelet count", FieldPattern.DECIMAL, FieldPattern.PLATELET_RANGE), //N8, W8
  PLATELET_COUNT_CELLS_MICROL("Platelet count (cells/microL)", FieldPattern.INTEGER, FieldPattern.PLATELET_RANGE), //C8
  PLATELET_COUNT_CELLS_MICROL_ON_PLAVIX("Platelet count (cells/microL) on Plavix", FieldPattern.INTEGER, FieldPattern.PLATELET_RANGE), //C8
  PRAVASTATIN_PRAVACHOL("Pravastatin (Pravachol)", FieldPattern.YESNONA, null), //W2
  PRIOR_CORONARY_ARTERY_BYPASS_GRAFTING_CABG("Prior Coronary artery bypass grafting (CABG)", FieldPattern.YESNONA, null), //C1
  PRIOR_MI("Prior MI", FieldPattern.YESNONA, null), //C1
  PRIOR_PCI("Prior PCI", FieldPattern.YESNONA, null), //C1
  PRIOR_ANGIOPLASTY("Prior angioplasty", FieldPattern.YESNONA, null), //C1
  PROJECT_SITE("Project site", "^NU|UIC|UofC|DCVA|GWU$", null), //C1, N1, W1
  PROTON_PUMP_INHIBITORS_PPIS("Proton Pump Inhibitors (PPIs)", "([YN123456]|MD)", null), //C2
  PROTON_PUMP_INHIBITORS_PPIS_NAMES("Proton Pump Inhibitors (PPIs)", FieldPattern.ANY, null), //C2
  RED_CELL_COUNT_CELLS_MICROL("Red cell count (cells/microL)", FieldPattern.DECIMAL, FieldPattern.RED_CELL_RANGE), //C8
  RED_CELL_COUNT_CELLS_MICROL_ON_PLAVIX("Red cell count (cells/microL) on Plavix", FieldPattern.DECIMAL, FieldPattern.RED_CELL_RANGE), //C8
  RIFAMPIN("Rifampin", FieldPattern.YESNONA, null), //C2
  RIFAMPIN_OR_RIFAMPICIN("Rifampin or Rifampicin", FieldPattern.YESNONA, null), //W2
  RITONAVIR("Ritonavir", FieldPattern.YESNONA, null), //C2
  ROSUVASTATIN_CRESTOR("Rosuvastatin (Crestor)", FieldPattern.YESNONA, null), //W2
  SNRIS("SNRIs", FieldPattern.YESNONA, null), //C2
  SSRIS("SSRIs", FieldPattern.YESNONA, null), //C2
  STEMI_DURING_FOLLOW_UP("STEMI during follow up", FieldPattern.YESNONA, null), //C3, C4, C5, C6, C7
  SIMVASTATIN_ZOCOR("Simvastatin (Zocor)", FieldPattern.YESNONA, null), //W2
  STABLE_DOSE_REACHED("Stable dose reached?", FieldPattern.YESNONA, null), //W2
  STATINS("Statins", FieldPattern.YESNONA, null), //C2
  STENT_THROMBOSIS_DURING_FOLLOW_UP("Stent Thrombosis during follow up", FieldPattern.YESNONA, null), //C3, C4, C5, C6, C7
  STENT_THROMBOSIS_TIMING("Stent Thrombosis timing", FieldPattern.INTEGER, null), //C3, C4, C5, C6, C7
  STENT_TYPE("Stent type", "[123]|MD", null), //C3, C4, C5, C6, C7
  STUDY_ID_PHARMGKB_ID("Study ID (PharmGKB ID)", "^[Pp][Aa]\\d+", null), //C1, N1, W1
  SULFONAMIDE_ANTIBIOTICS_INCLUDES_SEPTRA_BACTRIM_COTRIM_AND_SULFATRIM("Sulfonamide Antibiotics (includes Septra, Bactrim, Cotrim and Sulfatrim)", FieldPattern.YESNONA, null), //W2
  TARGET_INR("Target INR", FieldPattern.DECIMAL_RANGE, (v) -> {
    Float value = Float.valueOf(v);
    return value >= 1.5 && value <= 3.5;
  }), //W2
  THERAPEUTIC_DOSE_MG_DAY("Therapeutic dose (mg/day)", FieldPattern.DECIMAL, null), //N2
  TIME_INTERVAL_BETWEEN_LOADING_DOSE_AND_VERIFYNOW_PLATELET_AGGREGATION_MEASURES("Time interval between loading dose and VerifyNow platelet aggregation measures", FieldPattern.DECIMAL, null), //C8
  TIME_OF_BLOOD_DRAW("Time of blood draw", FieldPattern.TIME_OF_DAY, null), //N2
  TIME_OF_LAST_DOSE("Time of last dose", FieldPattern.TIME_OF_DAY, null), //N2
  TOTAL_CHOLESTEROL_MG_DL("Total Cholesterol (mg/dL)", FieldPattern.INTEGER, (v) -> {
    Float value = Float.valueOf(v);
    return value >= 80 && value <= 500;
  }), //C8
  TRIGLYCERIDES_MG_DL("Triglycerides (mg/dL)", FieldPattern.INTEGER, (v) -> {
    Float value = Float.valueOf(v);
    return value >= 50 && value <= 500;
  }), //C8
  TYPE_OF_STENT_THROMBOSIS("Type of stent thrombosis", "([123]|MD)", null), //C3, C4, C5, C6, C7
  UNSTABLE_ANGINA("Unstable angina", FieldPattern.YESNONA, null), //C3, C4, C5, C6, C7
  VALVE_REPLACEMENT("Valve Replacement", FieldPattern.YESNONA, null), //W1
  VARIOUS_CHOLESTEROL_MEASUREMENT_TOTAL_LDL_HDL_ETC_REQUIRED("Various cholesterol measurement (total, LDL, HDL, etc.) (Required)", FieldPattern.YESNONA, null), //C8
  VERIFYNOW_ADP_STIMULATED_AGGREGATION_WHILE_ON_MAINTENANCE_DOSE_OF_CLOPIDOGREL_INHIBITION("VerifyNow ADP stimulated Aggregation (while on maintenance dose of Clopidogrel) % Inhibition", FieldPattern.DECIMAL, (v) -> {
    Float value = Float.valueOf(v);
    return value >= 0 && value <= 100;
  }), //C8
  VERIFYNOW_ADP_STIMULATED_AGGREGATION_WHILE_ON_MAINTENANCE_DOSE_OF_CLOPIDOGREL_PRU("VerifyNow ADP stimulated Aggregation (while on maintenance dose of Clopidogrel) PRU", FieldPattern.INTEGER, (v) -> {
    Float value = Float.valueOf(v);
    return value >= 0 && value <= 400;
  }), //C8
  VESSEL_DISEASE_50_STENOSIS("Vessel Disease (> 50% stenosis)", "^([1234]|MD)$", null), //C1
  VORICONAZOLE("Voriconazole", FieldPattern.YESNONA, null), //C2
  WARFARIN("Warfarin", FieldPattern.YESNONA, null), //C2
  WARFARIN_DOSE_MG_WEEK("Warfarin dose (mg/week)", FieldPattern.DECIMAL, (v) -> {
    Float value = Float.valueOf(v);
    return value >= 3 && value <= 84;
  }), //W2
  WEIGHT_KG("Weight (kg)", FieldPattern.DECIMAL, (v) -> {
    Float value = Float.valueOf(v);
    return value >= 34 && value <= 204;
  }), //C1, N1, W1
  
  // Which NOAC Drug used group
  WHICH_NOAC_DRUG_USED_CHOICE_1("Which NOAC drug used? (choice=1)", FieldPattern.CHECKED, null), //N2
  WHICH_NOAC_DRUG_USED_CHOICE_2("Which NOAC drug used? (choice=2)", FieldPattern.CHECKED, null), //N2
  WHICH_NOAC_DRUG_USED_CHOICE_3("Which NOAC drug used? (choice=3)", FieldPattern.CHECKED, null), //N2
  WHICH_NOAC_DRUG_USED_CHOICE_4("Which NOAC drug used? (choice=4)", FieldPattern.CHECKED, null), //N2
  // end group
  
  EGFR_30_ML_MIN_1_73M_2("eGFR < 30 ml/min/1.73m^2", FieldPattern.YESNONA, null), //W3, W4, W5, W6, W7
  EGFR_AT_EMBOLIC_EVENT("eGFR at Embolic event", FieldPattern.DECIMAL, (v) -> {
    Float value = Float.valueOf(v);
    return value >= 5 && value <= 200;
  }), //W3, W4, W5, W6, W7
  VARIOUS_CHOLESTEROL_MEASUREMENT_TOTAL_LDL_HDL_ETC("Various cholesterol measurement (total, LDL, HDL, etc.)", FieldPattern.ANY, null), //C8
  
  // Calculated columns, not from original data source files
  TIME_TO_BLEEDING_EVENT("Time to Bleeding Event (in days)", FieldPattern.DECIMAL, null),
  TIME_TO_MACE("Time to MACE (in days)", FieldPattern.DECIMAL, null),
  TIME_TO_STEMI("Time to first STEMI (in days)", FieldPattern.DECIMAL, null),
  TIME_TO_NSTEMI("Time to first NSTEMI (in days)", FieldPattern.DECIMAL, null),
  TIME_TO_ANGINA("Time to unstable angina (in days)", FieldPattern.DECIMAL, null),
  TIME_TO_THROMB("Time to thrombosis (in days)", FieldPattern.DECIMAL, null),
  TIME_TO_CARD_DEATH("Time to cardiac death (in days)", FieldPattern.DECIMAL, null),
  TIME_TO_MI("Time to MI (in days)", FieldPattern.DECIMAL, null),
  TIME_TO_ACS("Time to first ACS (in days)", FieldPattern.DECIMAL, null),
  TIME_TO_ISC_STROKE("Time to ischemic stroke (in days)", FieldPattern.DECIMAL, null),
  TIME_TO_HEM_STROKE("Time to hmorrhagic stroke (in days)", FieldPattern.DECIMAL, null),
  TIME_TO_DEATH("Time to death (in days)", FieldPattern.DECIMAL, null),
  DURATION_FOLLOWUP("Calculated Duration of follow-up (in days)", FieldPattern.DECIMAL, null),
  TIME_TO_EMBOLIC_EVENT("Time to Embolic Event (in days)", FieldPattern.DECIMAL, null),
  TIME_TO_BLOOD_DRAW("Time to blood draw (in hours)", FieldPattern.DECIMAL, null),
  EMBOLIC_EVENT("Embolic Event", FieldPattern.ANY, null),
  INDICATION_FOR_NOAC_TREATMENT("Indication for NOAC Treatment", FieldPattern.ANY, null),
  WHICH_NOAC_DRUG_USED("Which NOAC Drug Used", FieldPattern.ANY, null),
  INDICATION_FOR_CLOPIDOGREL_TREATMENT("Indication for Clopidogrel Treatment", FieldPattern.ANY, null),
  INDICATION_FOR_WARFARIN_TREATMENT("Indication for Warfarin Treatment", FieldPattern.ANY, null),
  BINNED_AGE("Age at enrollment (with over 89 binning)", FieldPattern.ANY, null),
  // end calculated columns
  ;
  
  private String displayName;
  private Pattern validationPattern = null;
  private Predicate<String> valueRangePredicate = null;

  /**
   * The constructor
   * @param displayName the name of the column as displayed in the input file
   * @param validation a regex String that tests whether this is the right type of value or not
   * @param valueRange a Predicate that says whether hte given value is within an acceptable range
   */
  Field(String displayName, String validation, Predicate<String> valueRange) {
    this.displayName = displayName;
    if (validation != null) {
      this.validationPattern = Pattern.compile(validation);
    }
    if (valueRange != null) {
      this.valueRangePredicate = valueRange;
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
   * The Predicate that determines if the fields value is within range, if null then no check is necessary
   * @return a {@link Predicate} that takes in a String and returns true if the value is within range
   */
  public Predicate<String> getValueRangePredicate() {
    return this.valueRangePredicate;
  }

  /**
   * Does this field not have a validation pattern associated with it and is, thus, not check for validity
   * @return true if this field is not checked for validity
   */
  public boolean isUnvalidated() {
    return this.validationPattern == null;
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
