/**
 * A collection of RegEx Pattern strings that can be used to validate CSV fields 
 *
 * @author Ryan Whaley
 */
class FieldPattern {
  static final String DATE = "^\\d{1,2}\\/\\d{1,2}\\/\\d{2}$";
  static final String ANY = "^.*$";
  static final String YESNO = "^[YN]$";
  static final String YESNONA = "^([YN]|NA)$";
  static final String CHECKED = "^(Checked|Unchecked)$";
  static final String DECIMAL = "^\\d+(\\.\\d+)?$";
  static final String MISSING_DATA = "MD";
}
