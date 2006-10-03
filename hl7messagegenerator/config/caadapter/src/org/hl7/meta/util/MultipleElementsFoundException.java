/* Copyright 2002 Health Level Seven, Inc. All Rights Reserved. 
 * 
 * This software is the proprietary information of Health Level Seven, Inc. 
 * Use is subject to license terms. 
 */
package org.hl7.meta.util;

/**
 * An exception that is thrown by methods in
 * {@link JdomUtils JdomUtils}
 * when too many child elements are found.
 * 
 * @author Skirmantas Kligys (skirmis@hotmail.com)
 */
public class MultipleElementsFoundException extends Exception
{
  //-------------------------------------------------------------------------
  /**
   * Constructor.
   * 
   * @param path  XPath to the location where the error occured
   */
  public MultipleElementsFoundException(String path)
  {
    super(path);
  }
}
