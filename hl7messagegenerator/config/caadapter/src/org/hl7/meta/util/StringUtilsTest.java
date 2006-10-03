/* Copyright 2002 Health Level Seven, Inc. All Rights Reserved. 
 * 
 * This software is the proprietary information of Health Level Seven, Inc. 
 * Use is subject to license terms. 
 */
package org.hl7.meta.util;

import java.util.Arrays;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

/**
 * A test suite that exercises {@link StringUtils StringUtils} class.
 * 
 * @author Skirmantas Kligys (skirmis@hotmail.com)
 */
public class StringUtilsTest extends TestCase
{
  //-------------------------------------------------------------------------
  /**
   * Constructor for <code>StringUtilsTest</code>.
   * 
   * @param name  the test name
   */
  public StringUtilsTest(String name)
  {
    super(name);
  }

  //-------------------------------------------------------------------------
  /**
   * Constructs a test suite from testXXX methods found in this class.
   * 
   * @return  the test suite
   */
  public static Test suite()
  {
    TestSuite suite = new TestSuite(StringUtilsTest.class);
    suite.setName("String utilities tests");
    return suite;
  }

  //-------------------------------------------------------------------------
  /**
   * Tests that method
   * {@link StringUtils#split(String, char) StringUtils.split()}
   * works as intended.
   */
  public void testSplit()
  {
    String[] result = StringUtils.split(null, ',');
    assertNull("split(null) should return null", result);
    
    result = StringUtils.split("", ',');
    assertTrue("split(\"\") should return {\"\"}", Arrays.equals(result,
      new String[] { "" }));
    
    result = StringUtils.split("1,2,3.5", ',');
    assertTrue("split(\"1,2,3.5\") should return {\"1\", \"2\", \"3.5\"}",
      Arrays.equals(result, new String[] { "1", "2", "3.5" }));
    
    result = StringUtils.split("a, b ,cde ", ',');
    assertTrue("split(\"a, b ,cde \") should return {\"a\", \"b\", \"cde\"}",
      Arrays.equals(result, new String[] { "a", "b", "cde" }));
  }

  //-------------------------------------------------------------------------
  /**
   * Tests that method
   * {@link StringUtils#join(String[], String) StringUtils.join()}
   * works as intended.
   */
  public void testJoin()
  {
    String result = StringUtils.join(null, ", ");
    assertNull("join(null) should return null", result);
    
    result = StringUtils.join(new String[] {}, ", ");
    assertEquals("join({\"\"})", "", result);
    
    result = StringUtils.join(new String[] {"a", "b", "cde"}, ", ");
    assertEquals("join({\"a\", \"b\", \"cde\"})", "a, b, cde", result);
  }

  //-------------------------------------------------------------------------
  /**
   * Tests that method
   * {@link StringUtils#isAllCaps(String) StringUtils.isAllCaps()}
   * works as intended.
   */
  public void testIsAllCaps()
  {
    assertEquals("isAllCaps(null)", false, StringUtils.isAllCaps(null));
    assertEquals("isAllCaps(\"\")", false, StringUtils.isAllCaps(""));
    assertEquals("isAllCaps(\" \")", true, StringUtils.isAllCaps(" "));  // ???
    assertEquals("isAllCaps(\"ABC\")", true, StringUtils.isAllCaps("ABC"));
    assertEquals("isAllCaps(\"abc\")", false, StringUtils.isAllCaps("abc"));
    assertEquals("isAllCaps(\"Abc\")", false, StringUtils.isAllCaps("Abc"));
  }

  //-------------------------------------------------------------------------
  /**
   * Tests that method
   * {@link StringUtils#matchesCmetHmdPattern(String)
   *   StringUtils.matchesCmetHmdPattern()}
   * works as intended.
   */
  public void testMatchesCmetHmdPattern()
  {
    assertEquals("matchesCmetHmdPattern(null)", false,
      StringUtils.matchesCmetHmdPattern(null));

    assertEquals("matchesCmetHmdPattern(\"\")", false, 
      StringUtils.matchesCmetHmdPattern(""));

    assertEquals("matchesCmetHmdPattern(\"COCT_HD020304\")", true, 
      StringUtils.matchesCmetHmdPattern("COCT_HD020304"));

    assertEquals("matchesCmetHmdPattern(\"COCT_HD020304abc\")", false, 
      StringUtils.matchesCmetHmdPattern("COCT_HD020304abc"));

    assertEquals("matchesCmetHmdPattern(\"coct_hd020304\")", false, 
      StringUtils.matchesCmetHmdPattern("coct_hd020304"));

    assertEquals("matchesCmetHmdPattern(\"COCT_RM020304\")", true, 
      StringUtils.matchesCmetHmdPattern("COCT_RM020304"));

    assertEquals("matchesCmetHmdPattern(\"COCT_MT020304\")", false, 
      StringUtils.matchesCmetHmdPattern("COCT_MT020304"));

    assertEquals("matchesCmetHmdPattern(\"SET<COCT_RM020304>\")", true, 
      StringUtils.matchesCmetHmdPattern("SET<COCT_RM020304>"));

    assertEquals("matchesCmetHmdPattern(\"SET<COCT_MT020304>\")", false, 
      StringUtils.matchesCmetHmdPattern("SET<COCT_MT020304>"));
  }

  //-------------------------------------------------------------------------
  /**
   * Tests that method
   * {@link StringUtils#matchesCmetMessagetypePattern(String)
   *   StringUtils.matchesCmetMessagetypePattern()}
   * works as intended.
   */
  public void testMatchesCmetMessagetypePattern()
  {
    assertEquals("matchesCmetMessagetypePattern(null)", false,
      StringUtils.matchesCmetMessagetypePattern(null));

    assertEquals("matchesCmetMessagetypePattern(\"\")", false, 
      StringUtils.matchesCmetMessagetypePattern(""));

    assertEquals("matchesCmetMessagetypePattern(\"COCT_MT020304\")", true, 
      StringUtils.matchesCmetMessagetypePattern("COCT_MT020304"));

    assertEquals("matchesCmetMessagetypePattern(\"COCT_HD020304\")", false, 
      StringUtils.matchesCmetMessagetypePattern("COCT_HD020304"));

    assertEquals("matchesCmetMessagetypePattern(\"COCT_MT020304abc\")", false, 
      StringUtils.matchesCmetMessagetypePattern("COCT_MT020304abc"));

    assertEquals("matchesCmetMessagetypePattern(\"coct_mt020304\")", false, 
      StringUtils.matchesCmetMessagetypePattern("coct_mt020304"));

    assertEquals("matchesCmetMessagetypePattern(\"COCT_RM020304\")", false, 
      StringUtils.matchesCmetMessagetypePattern("COCT_RM020304"));

    assertEquals("matchesCmetMessagetypePattern(\"SET<COCT_MT020304>\")", true, 
      StringUtils.matchesCmetMessagetypePattern("SET<COCT_MT020304>"));

    assertEquals("matchesCmetMessagetypePattern(\"SET<COCT_RM020304>\")", false, 
      StringUtils.matchesCmetMessagetypePattern("SET<COCT_RM020304>"));
  }

  //-------------------------------------------------------------------------
  /**
   * Tests that method
   * {@link StringUtils#mapCmetHmdToMessageType(String)
   *   StringUtils.mapCmetHmdToMessageType()}
   * works as intended.
   */
  public void testMapCmetHmdToMessageType()
  {
    String result = StringUtils.mapCmetHmdToMessageType(null);
    assertNull("mapCmetHmdToMessageType(null) should return null", result);
    
    result = StringUtils.mapCmetHmdToMessageType("COCT_MT010203");
    assertEquals("mapCmetHmdToMessageType(\"COCT_MT010203\")", "COCT_MT010203",
      result);
    
    result = StringUtils.mapCmetHmdToMessageType("COCT_HD010200");
    assertEquals("mapCmetHmdToMessageType(\"COCT_HD010200\")", "COCT_MT010201",
      result);
    
    result = StringUtils.mapCmetHmdToMessageType("COCT_RM090000");
    assertEquals("mapCmetHmdToMessageType(\"COCT_RM090000\")", "COCT_MT090101",
      result);
  }

  //-------------------------------------------------------------------------
  /**
   * Tests that method
   * {@link StringUtils#mapCmetMessagetypeToHmd(String)
   *   StringUtils.mapCmetMessagetypeToHmd()}
   * works as intended.
   */
  public void testMapCmetMessagetypeToHmd()
  {
    String result = StringUtils.mapCmetMessagetypeToHmd(null);
    assertNull("mapCmetMessagetypeToHmd(null) should return null", result);
    
    result = StringUtils.mapCmetMessagetypeToHmd("COCT_HD010200");
    assertNull("mapCmetMessagetypeToHmd(\"COCT_HD010200\") should return null",
      result);
    
    result = StringUtils.mapCmetMessagetypeToHmd("COCT_MT010200abc");
    assertNull("mapCmetMessagetypeToHmd(\"COCT_MT010200abc\") should return null",
      result);
    
    result = StringUtils.mapCmetMessagetypeToHmd("COCT_MT010203");
    assertEquals("mapCmetMessagetypeToHmd(\"COCT_MT010203\")", "COCT_HD010200",
      result);
  }

  //-------------------------------------------------------------------------
  /**
   * Tests that method
   * {@link StringUtils#removeSet(String) StringUtils.removeSet()}
   * works as intended.
   */
  public void testRemoveSet()
  {
    String result = StringUtils.removeSet(null);
    assertNull("removeSet(null) should return null", result);
    
    result = StringUtils.removeSet("abcdef");
    assertEquals("removeSet(\"abcdef\")", "abcdef", result);
    
    result = StringUtils.removeSet("SET<abcdef>");
    assertEquals("removeSet(\"SET<abcdef>\")", "abcdef", result);
  }

  //-----------------------------------------------------------------------
  /**
   * Runs the test suite in console mode.
   * 
   * @param args  command line parameters
   */
  public static void main(String args[])
  {
    TestRunner.run(suite());
  }
}
