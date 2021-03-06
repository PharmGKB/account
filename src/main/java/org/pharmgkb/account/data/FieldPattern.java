package org.pharmgkb.account.data;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A collection of RegEx Pattern strings that can be used to validate CSV fields.
 *
 * The "MD" values are for "missing data".
 * 
 * @author Ryan Whaley
 */
public class FieldPattern {
  // need to accommodate M/D/Y or Y/M/D with either /'s or -'s (or missing data)
  static final String DATE = "^((\\d{1,2}[/-]\\d{1,2}[/-](\\d{2}|\\d{4}))|((\\d{2}|\\d{4})[/-]\\d{1,2}[/-]\\d{1,2})|MD)$";
  static final String ANY = "^.*$";
  static final String YESNONA = "^([YN]|NA|MD)$";
  static final String CHECKED = "^(Checked|Unchecked|MD)$";
  static final String DECIMAL = "^([\\d,]+(\\.\\d+)?|MD)$";
  static final String DECIMAL_RANGE = "^(\\d+(\\.\\d+)?(\\s*-\\s*\\d+(\\.\\d+)?)?|MD)$";
  static final String INTEGER = "^([\\d,]+|MD)$";
  static final String TIME_OF_DAY = "(\\d{1,2}(:\\d{2})?\\s*(am|AM|pm|PM)|\\d{2}:\\d{2}|MD)";
  static final String DAYS = "([\\d,]+|MD)";

  public static final String MISSING_DATA = "MD";
  static final Pattern MD_SYNONYMS = Pattern.compile("(n/a|na|unk|unknown)");
  private static final Pattern DATE_PATTERN = Pattern.compile(DATE);
  
  public static Date parseDate(String value) {
    Matcher m = DATE_PATTERN.matcher(value);
    if (!m.matches()) return null;
    
    try {
      if (m.group(2) != null) {
        return new SimpleDateFormat("MM/dd/yyyy").parse(m.group(1).replaceAll("-", "/"));
      }
      if (m.group(4) != null) {
        return new SimpleDateFormat("yyyy/MM/dd").parse(m.group(4).replaceAll("-", "/"));
      }
      return null;
    } catch (ParseException e) {
      throw new RuntimeException("Error when parsing date value", e);
    }
  }
  
  public static boolean isMissing(String value) {
    String strippedValue = StringUtils.strip(value);
    return StringUtils.isBlank(strippedValue) ||
        strippedValue.equals(MISSING_DATA) ||
        MD_SYNONYMS.matcher(strippedValue.toLowerCase()).matches();
  }
}
