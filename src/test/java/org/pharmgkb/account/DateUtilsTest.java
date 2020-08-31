package org.pharmgkb.account;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static org.junit.Assert.*;

/**
 * Test the {@link DateUtils} methods for validity
 *
 * @author Ryan Whaley
 */
public class DateUtilsTest {
  
  @Test
  public void testParseDateTime() {
    LocalDateTime dateTime1 = DateUtils.parseDateTime("01/2/2019", "2:00 pm");
    assertNotNull(dateTime1);
    
    assertEquals("2019-01-02", dateTime1.format(DateTimeFormatter.ISO_LOCAL_DATE));
    assertEquals("14:00:00", dateTime1.format(DateTimeFormatter.ISO_LOCAL_TIME));

    try {
      DateUtils.parseDateTime("foo", "bar");
      fail("this should not be a valid date-time");
    } catch (DateTimeParseException ex) {
      // ignore since this is what we expect
    }
  }
  
  @Test
  public void testDiff() {
    LocalDateTime fromDt = DateUtils.parseDateTime("01/2/2019", "2:00 pm");
    LocalDateTime toDt   = DateUtils.parseDateTime("01/2/2019", "4:38 pm");
    LocalDateTime muchLaterDt   = DateUtils.parseDateTime("1/9/2019", "10:11 am");
    LocalDateTime wholeHourDt   = DateUtils.parseDateTime("1/2/2019", "3:00 pm");
    
    String diff = DateUtils.diff(fromDt, toDt);
    assertEquals("2.63", diff);
    
    diff = DateUtils.diff(toDt, fromDt);
    assertEquals("-2.63", diff);
    
    diff = DateUtils.diff(fromDt, muchLaterDt);
    assertEquals("164.18", diff);
    
    diff = DateUtils.diff(fromDt, wholeHourDt);
    assertEquals("1.00", diff);
  }
}
