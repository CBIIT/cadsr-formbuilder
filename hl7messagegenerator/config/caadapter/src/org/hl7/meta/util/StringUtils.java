/* Copyright 2002 Health Level Seven, Inc. All Rights Reserved.
 *
 * This software is the proprietary information of Health Level Seven, Inc.
 * Use is subject to license terms.
 */
package org.hl7.meta.util;

import java.util.ArrayList;

/**
 * A utility class that contains common functions dealing with strings.
 *
 * @author Skirmantas Kligys (skirmis@hotmail.com)
 */
public class StringUtils
{
  //-------------------------------------------------------------------------
  /**
   * Constructor is private, all methods are static; utility class pattern.
   */
  private StringUtils()
  {
  }

  //-------------------------------------------------------------------------
  /**
   * Splits a String at character <code>separator</code> and trims spaces
   * if present.
   *
   * @param s  incoming string
   * @param separator  separator character
   * @return  an array of tokens found
   */
  public static String[] split(String s, char separator)
  {
    if (s == null) return null;

    ArrayList/*<String>*/ result = new ArrayList/*<String>*/();
    int j = -1;
    for (int i = s.indexOf(separator); i != -1;
      j = i, i = s.indexOf(separator, i + 1))
    {
      result.add(s.substring(j + 1, i).trim());
    }
    result.add(s.substring(j + 1).trim());

    return (String[])result.toArray(new String[result.size()]);
  }

  //-------------------------------------------------------------------------
  /**
   * Joins a string array into one string with <code>separator</code>
   * between elements.
   *
   * @param as  array of tokens to join
   * @param separator  separator character
   * @return  the joined string
   */
  public static String join(String[] as, String separator)
  {
    if (as == null) return null;
    else if (as.length == 1) return as[0];
    else
    {
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < as.length; ++i)
      {
        if (i > 0) sb.append(separator);
        sb.append(as[i]);
      }
      return sb.toString();
    }
  }

  //-------------------------------------------------------------------------
  /**
   * Tests if a string is all capitals or not.
   *
   * @param s  the string to be tested
   * @return  flag indicating if the string is all capitals
   */
  public static boolean isAllCaps(String s)
  {
    if (s == null || s.length() == 0) return false;

    for (int i = 0; i < s.length(); ++i)
    {
      char c = s.charAt(i);
      if (Character.isLowerCase(c)) return false;
    }
    return true;
  }

  //-------------------------------------------------------------------------
  /**
   * Returns <code>true</code> if the string fits the CMET HMD or RMIM ID
   * pattern: <code>COCT_HDdddddd</code> or <code>COCT_RMdddddd</code>,
   * where <code>d</code> is a decimal digit.
   *
   * @param s  string to test
   * @return  flag indicating if the string matches the pattern
   */
  public static boolean matchesCmetHmdPattern(String s)
  {
    s = removeSet(s);
    return (s != null) && (s.length() == 13) &&
      (s.startsWith("COCT_HD") || s.startsWith("COCT_RM")) &&
      (s.charAt(7) >= '0') && (s.charAt(7) <= '9') &&
      (s.charAt(8) >= '0') && (s.charAt(8) <= '9') &&
      (s.charAt(9) >= '0') && (s.charAt(9) <= '9') &&
      (s.charAt(10) >= '0') && (s.charAt(10) <= '9') &&
      (s.charAt(11) >= '0') && (s.charAt(11) <= '9') &&
      (s.charAt(12) >= '0') && (s.charAt(12) <= '9');
  }

  //-------------------------------------------------------------------------
  /**
   * Returns <code>true</code> if the string fits the CMET message type
   * pattern: <code>COCT_HDdddddd</code> or <code>COCT_RMdddddd</code>,
   * where d is a decimal digit.
   *
   * @param s  string to test
   * @return  flag indicating if the string matches the pattern
   */
  public static boolean matchesCmetMessagetypePattern(String s)
  {
    s = removeSet(s);
    return (s != null) && (s.length() == 13) &&
      (s.startsWith("COCT_MT") || s.startsWith("PORR_MT") || s.startsWith("MCAI_MT")) &&
      (s.charAt(7) >= '0') && (s.charAt(7) <= '9') &&
      (s.charAt(8) >= '0') && (s.charAt(8) <= '9') &&
      (s.charAt(9) >= '0') && (s.charAt(9) <= '9') &&
      (s.charAt(10) >= '0') && (s.charAt(10) <= '9') &&
      (s.charAt(11) >= '0') && (s.charAt(11) <= '9') &&
      (s.charAt(12) >= '0') && (s.charAt(12) <= '9');
  }

  //-------------------------------------------------------------------------
  /**
   * Maps a CMET HMD ID of the form <code>COCT_HDdddd00</code> or an RMIM ID
   * of the form <code>COCT_RMdd0000</code> to a message type ID of form
   * <code>COCT_MTdddd01</code>, where <code>d</code> is a decimal digit.
   *
   * @param s  the HMD or RMIM ID to convert
   * @return  conversion result: message type ID
   */
  public static String mapCmetHmdToMessageType(String s)
  {
    if (s == null) return null;
    s = removeSet(s);
    if (matchesCmetMessagetypePattern(s)) return s;
    else if (matchesCmetHmdPattern(s))
    {
      // A workaround for when RMIM IDs are here, and they are not supposed to.
      String workaround = (s.charAt(9) == '0' && s.charAt(10) == '0') ?
        "01" : s.substring(9, 11);
      return s.substring(0, 5) + "MT" + s.substring(7, 9) + workaround +
        "01";
    }
    else return null;
  }

  //-------------------------------------------------------------------------
  /**
   * Maps a CMET message type ID of the form <code>COCT_MTdddddd</code> to
   * an HMD ID of form <code>COCT_HDdddddd</code>,
   * where <code>d</code> is a decimal digit.
   *
   * @param s  the message type ID to convert
   * @return  conversion result: HMD ID
   */
  public static String mapCmetMessagetypeToHmd(String s)
  {
    s = removeSet(s);
    if (!matchesCmetMessagetypePattern(s)) return null;

    return s.substring(0, 5) + "HD" + s.substring(7, 13);
  }

  //-------------------------------------------------------------------------
  /**
   * If the string is of the form <code>SET&lt;...&gt;</code>, removes
   * the set; otherwise returns unchanged string.
   *
   * @param s  the string to process
   * @return  the string with set notation removed
   */
  public static String removeSet(String s)
  {
    if (s == null) return null;
    else if (s.startsWith("SET<") && s.endsWith(">"))
    {
      return s.substring(4, s.length() - 1);
    }
    else return s;
  }

  //-------------------------------------------------------------------------
  /**
   * Trims the suffix form the string if it is present; otherwise does
   * nothing.
   *
   * @param s  string to process
   * @param suffix  suffix to trim
   * @return  source string with suffix trimmed
   */
  public static String trimSuffix(String s, String suffix)
  {
    if (s == null || s.length() == 0 ||
        suffix == null || suffix.length() == 0) return s;

    if (s.endsWith(suffix))
    {
      return s.substring(0, s.length() - suffix.length());
    }
    else return s;
  }

    /**
     * Enforces that the first character is capital, and the remaining are
     * lowercase.
     *
     * @param s  incoming string
     * @return  transformed string
     */
    public static String forceInitialCap(String s)
    {
      if (s == null || s.length() == 0 || checkInitialCap(s)) return s;

      return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    /**
     * Checks that the first character is capital, and the remaining are
     * lowercase.
     *
     * @param s  incoming string
     * @return  <code>true</code> if the check was successful
     */
    protected static boolean checkInitialCap(String s)
    {
      if (s == null || s.length() == 0) return false;
      if (!Character.isUpperCase(s.charAt(0))) return false;

      for (int i = 1; i < s.length(); ++i)
      {
        if (!Character.isUpperCase(s.charAt(i))) return false;
      }
      return true;
    }
}
