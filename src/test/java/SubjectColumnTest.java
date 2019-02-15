import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This JUnit test class verifies that the column validator is working as expected
 *
 * @author Ryan Whaley
 */
public class SubjectColumnTest {
  
  @Test
  public void testValidation() {
    testValues(SubjectColumn.SUBJECT_ID, "PA1", "foo");
    testValues(SubjectColumn.SITE, "UofC", "Stanford");
    testValues(SubjectColumn.GENDER, "F", "OOOOO");
    testValues(SubjectColumn.DOB, "1/2/17", "1/2/201");
    testValues(SubjectColumn.ENROLLMENT, "1/2/17", "1/2/201");
    testValues(SubjectColumn.AGE, "17", "10.1.3");
    testValues(SubjectColumn.HEIGHT, "17", "10.1.3");
    testValues(SubjectColumn.WEIGHT, "17", "10.1.3");
    testValues(SubjectColumn.BMI, "17", "10.1.3");
    
    assertTrue(SubjectColumn.NOTES.validate(null));
    assertTrue(SubjectColumn.NOTES.validate(""));
    assertTrue(SubjectColumn.NOTES.validate("foo"));

    assertTrue(SubjectColumn.COMORBIDITIES.validate("foo"));
    
    testValues(SubjectColumn.DIABETES, "1", "3");
    testValues(SubjectColumn.INDICATION_CAD, "Checked", "foo");
    testValues(SubjectColumn.INDICATION_PAD, "Unchecked", "foo");
    testValues(SubjectColumn.INDICATION_IS, "Checked", "unchecked");
    testValues(SubjectColumn.INDICATION_ACS, "Checked", "foo");
    testValues(SubjectColumn.INDICATION_OTHER, "Checked", "foo");
    testValues(SubjectColumn.INDICATION_FOR_PCI, "2", "9");
    testValues(SubjectColumn.ALCOHOL, "2", "9");
  }
  
  private void testValues(SubjectColumn column, String good, String bad) {
    assertTrue(String.format("%s should work for %s", good, column.name()), column.validate(good));
    assertFalse(String.format("%s should not work for %s", bad, column.name()), column.validate(bad));
    
    if (column.isRequired()) {
      assertFalse(String.format("%s should not allow missing data", column.name()), column.validate(Constants.MISSING_DATA));
    } else {
      assertTrue(String.format("%s should allow missing data", column.name()), column.validate(Constants.MISSING_DATA));
    }
    
    try {
      assertFalse(SubjectColumn.SITE.validate("    "));
      fail(String.format("%s should not validate whitespace", column.name()));
    } catch (IllegalArgumentException ex) {
      // this is fine, nothing to see here
    }
  }
}
