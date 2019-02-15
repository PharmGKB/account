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
  SITE("^(NU|UIC|UofC|DCVA|GWU)$", true, false),
  GENDER("^[MF]$", true, false),
  DOB(Constants.DATE_PATTERN, true, false),
  ENROLLMENT(Constants.DATE_PATTERN, true, false),
  AGE("^\\d+$", true, false),
  HEIGHT(Constants.DECIMAL_PATTERN, true, false),
  WEIGHT(Constants.DECIMAL_PATTERN, true, false),
  BMI(Constants.DECIMAL_PATTERN, true, false),
  NOTES(Constants.ANY_PATTERN, false, true),
  COMORBIDITIES(Constants.ANY_PATTERN, true, false),
  DIABETES("^[012]$", false, false),
  CHF(Constants.YESNO_PATTERN, false, false),
  HYPERTENSION(Constants.YESNO_PATTERN, false, false),
  HYPERCHOLESTEROLEMIA(Constants.YESNO_PATTERN, false, false),
  SMOKER_CURRENT(Constants.YESNO_PATTERN, false, false),
  SMOKER_FORMER(Constants.YESNO_PATTERN, false, false),
  SMOKER_YEARS("^\\d+$", false, true),
  ALCOHOL("^[01234]$", false, false),
  INDICATION_CAD(Constants.CHECKED_PATTERN, true, false),
  INDICATION_PAD(Constants.CHECKED_PATTERN, true, false),
  INDICATION_IS(Constants.CHECKED_PATTERN, true, false),
  INDICATION_ACS(Constants.CHECKED_PATTERN, true, false),
  INDICATION_OTHER(Constants.CHECKED_PATTERN, true, false),
  INDICATION_FOR_PCI("^[1234]$", false, true),
  PRIOR_PCI(Constants.YESNONA_PATTERN, false, false),
  CARDIOGENIC_SHOCK(Constants.YESNONA_PATTERN, false, false),
  PRIOR_MI(Constants.YESNONA_PATTERN, false, false),
  PRIOR_CABG(Constants.YESNONA_PATTERN, false, false),
  PRIOR_ANGIOPLASTY(Constants.YESNONA_PATTERN, false, false),
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
    if (value.equals(Constants.MISSING_DATA) && !m_required) {
      return true;
    }
    return m_validationPattern.matcher(value).matches();
  }
  
  boolean isRequired() {
    return m_required;
  }
}

class Constants {
  static final String DATE_PATTERN = "^\\d{1,2}\\/\\d{1,2}\\/\\d{2}$";
  static final String ANY_PATTERN = "^.*$";
  static final String YESNO_PATTERN = "^[YN]$";
  static final String YESNONA_PATTERN = "^([YN]|NA)$";
  static final String CHECKED_PATTERN = "^(Checked|Unchecked)$";
  static final String DECIMAL_PATTERN = "^\\d+(\\.\\d+)?$";
  static final String MISSING_DATA = "MD";
}
