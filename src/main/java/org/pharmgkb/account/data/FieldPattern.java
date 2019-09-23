package org.pharmgkb.account.data;

import java.util.regex.Pattern;

/**
 * A collection of RegEx Pattern strings that can be used to validate CSV fields 
 *
 * @author Ryan Whaley
 */
class FieldPattern {
  static final String DATE = "^(\\d{1,2}\\/\\d{1,2}\\/(\\d{2}|\\d{4})|MD)$";
  static final String ANY = "^.*$";
  static final String YESNONA = "^([YN]|NA|MD)$";
  static final String CHECKED = "^(Checked|Unchecked|MD)$";
  static final String DECIMAL = "^([\\d,]+(\\.\\d+)?|MD)$";
  static final String DECIMAL_RANGE = "^(\\d+(\\.\\d+)?(\\s*-\\s*\\d+(\\.\\d+)?)?|MD)$";
  static final String INTEGER = "^([\\d,]+|MD)$";
  static final String TIME_OF_DAY = "(\\d{1,2}(:\\d{2})?\\s?(am|AM|pm|PM)|\\d{2}:\\d{2}|MD)";
  static final String DAYS = "([\\d,]+|MD)";

  static final String MISSING_DATA = "MD";
  static final Pattern MD_SYNONYMS = Pattern.compile("(n/a|na|unk|unknown)");
}
