package org.pharmgkb.account.data;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Predicate;
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
  
  static final Predicate<String> ACETA_DOSE_RANGE = (v) -> {
    Float value = Float.valueOf(v);
    return value >= 325 && value <= 4000;
  };

  static final Predicate<String> AGE_RANGE = (v) -> {
    Float value = Float.valueOf(v);
    return value >= 18;
  };
  
  static final Predicate<String> ASPIRIN_DOSE_RANGE = (v) -> {
    Float value = Float.valueOf(v);
    return value >= 81 && value <= 1500;
  };
  
  static final Predicate<String> HEMATOCRIT_RANGE = (v) -> {
    Float value = Float.valueOf(v);
    return value >= 15 && value <= 55;
  };
  
  static final Predicate<String> HEMOGLOBIN_RANGE = (v) -> {
    Float value = Float.valueOf(v);
    return value >= 5 && value <= 17;
  };
  
  static final Predicate<String> INR_RANGE = (v) -> {
    Float value = Float.valueOf(v);
    return value >= 1 && value <= 7;
  };
  
  static final Predicate<String> NSAID_DOSE_RANGE = (v) -> {
    Float value = Float.valueOf(v);
    return value >= 200 && value <= 3200;
  };
  
  static final Predicate<String> PLATELET_RANGE = (v) -> {
    Float value = Float.valueOf(v.replaceAll(",", ""));
    return value >= 100 && value <= 450;
  };
  
  static final Predicate<String> RED_CELL_RANGE = (v) -> {
    Float value = Float.valueOf(v);
    return value >= 3 && value <= 7;
  };

  static final Predicate<String> WHITE_CELL_RANGE = (v) -> {
    Float value = Float.valueOf(v);
    return value >= 0 && value <= 20;
  };
}
