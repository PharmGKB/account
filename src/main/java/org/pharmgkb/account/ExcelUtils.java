package org.pharmgkb.account;

import java.util.ArrayList;
import java.util.List;

/**
 * Methods to help with Excel-related tasks
 *
 * @author Ryan Whaley
 */
public class ExcelUtils {

  /**
   * Takes an integer and gives the equivalent Excel column name. For example, 1 gives "A", 2 gives "B", 27 gives "AA"
   * @param columnNumber a 1-based column number
   * @return a string column identifier like "CA"
   */
  public static String getExcelColumnName(int columnNumber)
  {
    int dividend = columnNumber;
    List<String> names = new ArrayList<>();

    while (dividend > 0)
    {
      int modulo = (dividend - 1) % 26;
      names.add(0, String.valueOf((char)(modulo+65)));
      dividend = (dividend - modulo) / 26;
    }

    return String.join("", names);
  }
}
