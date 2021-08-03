package org.pharmgkb.account;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility methods for dealing with DateTime data
 *
 * @author Ryan Whaley
 */
public class DateUtils {
  private static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("M-d-yyyy h:mm[ ]a");

  /**
   * Parses a string represented date and time into one {@link LocalDateTime} object with all the assumptions about how
   * dates and times are formatted in the ACCOuNT data set.
   * @param date a date in the form "month/day/year" that can use either slashes or dashes
   * @param time a time in the form "hours:minutes pm"
   * @return a valid {@link LocalDateTime}
   * @throws java.time.format.DateTimeParseException if the strings can't be parsed
   */
  public static LocalDateTime parseDateTime(String date, String time) {
    return LocalDateTime.parse(
        (date + " " + time)                            // combine date-time into one string
            .replaceAll("/", "-")    // standardize on dashes
            .replaceAll("\\s+", " ") // collapse multiple spaces to just one space
            .toUpperCase(),                            // the am/pm needs to be in upper case, apparently
        DATETIME_FORMAT);
  }

  /**
   * Calculates the hours difference between two {@link LocalDateTime} objects and outputs the result in as a decimal to
   * two significant digits. This will accommodate negative time frames if the "to" time is before the "from" time.
   * @param fromDt the beginning of the time frame
   * @param toDt the end of the time frame
   * @return a String of the time frame length in hours to two decimal places
   */
  public static String diff(LocalDateTime fromDt, LocalDateTime toDt) {
    double hours = Duration.between(fromDt, toDt).getSeconds() / (60f * 60f);
    
    return String.format("%.2f", hours);
  }
}
