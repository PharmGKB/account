import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import javax.annotation.Nullable;
import java.util.regex.Pattern;

/**
 * This enumerates all the used columns in the spreadsheet with the rules to validate them. Plus, this is the order 
 * that the CSVReader expects to see the columns in while reading the file.
 *
 * @author Ryan Whaley
 */
public enum SubjectColumn {
  
  SUBJECT_ID("^PA\\d+$", true, false),
  SITE("^[12345]$", true, false),
  GENDER("^[MF]$", true, false),
  DOB(FieldPattern.DATE, true, false),
  ENROLLMENT(FieldPattern.DATE, true, false),
  AGE("^\\d+$", true, false),
  HEIGHT(FieldPattern.DECIMAL, true, false),
  WEIGHT(FieldPattern.DECIMAL, true, false),
  BMI(FieldPattern.DECIMAL, true, false),
  NOTES(FieldPattern.ANY, false, true),
  COMORBIDITIES(FieldPattern.ANY, true, false),
  DIABETES("^[012]$", false, false),
  CHF(FieldPattern.YESNO, false, false),
  HYPERTENSION(FieldPattern.YESNO, false, false),
  HYPERCHOLESTEROLEMIA(FieldPattern.YESNO, false, false),
  SMOKER_CURRENT(FieldPattern.YESNO, false, false),
  SMOKER_FORMER(FieldPattern.YESNO, false, false),
  SMOKER_YEARS("^(\\d+|NA)$", false, true),
  ALCOHOL("^[01234]$", false, false),
  INDICATION_CAD(FieldPattern.CHECKED, true, false),
  INDICATION_PAD(FieldPattern.CHECKED, true, false),
  INDICATION_IS(FieldPattern.CHECKED, true, false),
  INDICATION_ACS(FieldPattern.CHECKED, true, false),
  INDICATION_OTHER(FieldPattern.CHECKED, true, false),
  INDICATION_FOR_PCI("^[1234]$", false, true),
  PRIOR_PCI(FieldPattern.YESNONA, false, false),
  CARDIOGENIC_SHOCK(FieldPattern.YESNONA, false, false),
  PRIOR_MI(FieldPattern.YESNONA, false, false),
  PRIOR_CABG(FieldPattern.YESNONA, false, false),
  PRIOR_ANGIOPLASTY(FieldPattern.YESNONA, false, false),
  VESSEL_DISEASE("^[1234]$", false, false),
  COMPLETE("^(Complete|Incomplete)$", false, false),
  ADP_STIM_AGG_PRU("^(\\d+|NA)$", false, false),
  ;

  private Pattern m_validationPattern;
  private boolean m_required;
  private boolean m_emptyAllowed;
  
  SubjectColumn(String validationPattern, boolean required, boolean emptyAllowed) {
    m_validationPattern = Pattern.compile(validationPattern);
    m_required = required;
    m_emptyAllowed = emptyAllowed;
  }
  
  public boolean validate(@Nullable String value) {
    if (m_emptyAllowed && (value == null || Strings.isNullOrEmpty(value))) {
      return true;
    }
    Preconditions.checkNotNull(value);
    Preconditions.checkArgument(!Strings.isNullOrEmpty(value.trim()), "value is blank");
    if (value.equals(FieldPattern.MISSING_DATA) && !m_required) {
      return true;
    }
    return m_validationPattern.matcher(value).matches();
  }
  
  boolean isRequired() {
    return m_required;
  }
}
