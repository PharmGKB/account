package org.pharmgkb.account.data;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This JUnit test class verifies that the column validator is working as expected
 *
 * @author Ryan Whaley
 */
public class FieldTest {
  
  @Test
  public void testValidation() {
    testValues(Field.STUDY_ID_PHARMGKB_ID, "PA1", "pa1");
    testValues(Field.PROJECT_SITE, "UofC", "Stanford");
    testValues(Field.GENDER, "F", "OOOOO");
    testValues(Field.ENROLLMENT_DATE, "1/2/17", "1/2/201");
    testValues(Field.AGE_AT_ENROLLMENT, "17", "10.1.3");
    testValues(Field.HEIGHT_CM, "17", "10.1.3");
    testValues(Field.WEIGHT_KG, "17", "10.1.3");
    testValues(Field.BMI, "17", "10.1.3");
    
    assertTrue(Field.NOTES.validate(null));
    assertTrue(Field.NOTES.validate(""));
    assertTrue(Field.NOTES.validate("foo"));

    assertTrue(Field.LIST_OF_COMORBIDITIES.validate("foo"));
    
    testValues(Field.DIABETES, "1", "3");
    testValues(Field.INDICATION_FOR_CLOPIDOGREL_TREATMENT_CHOICE_1, "Checked", "foo");
    testValues(Field.INDICATION_FOR_PCI, "2", "9");
    testValues(Field.ALCOHOL, "2", "9");
    
    assertTrue(Field.DATE_OF_BIRTH.validate("1/2/17"));
    assertFalse(Field.DATE_OF_BIRTH.validate("1/2/201"));

    assertTrue(Field.ENROLLMENT_DATE.validate("10-24-2019"));
    assertTrue(Field.ENROLLMENT_DATE.validate("2019-10-24"));
    assertFalse(Field.ENROLLMENT_DATE.validate("201-10-24"));
  }
  
  private void testValues(Field column, String good, String bad) {
    assertTrue(String.format("%s should work for %s", good, column.name()), column.validate(good));
    assertFalse(String.format("%s should not work for %s", bad, column.name()), column.validate(bad));
  }
}
