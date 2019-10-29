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
  private static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("M-d-yyyy h:mm a");

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
   * Calculates the hours and minutes difference between two {@link LocalDateTime} objects and outputs the result in 
   * the formate "hours:minutes". This will accommodate negative time frames if the "to" time is before the "from" time.
   * @param fromDt the beginning of the time frame
   * @param toDt the end of the time frame
   * @return a String of the time frame length in the format "hourse:minutes"
   */
  public static String diff(LocalDateTime fromDt, LocalDateTime toDt) {
    Duration duration = Duration.between(fromDt, toDt);
    long secs = duration.getSeconds();
    long mins = Math.abs((secs / 60) % 60);
    double hours = secs > 0 ? Math.floor(secs / (60f * 60f)) : Math.ceil(secs / (60f * 60f));
    
    return String.format("%.0f:%02d", hours, mins);
  }
}
