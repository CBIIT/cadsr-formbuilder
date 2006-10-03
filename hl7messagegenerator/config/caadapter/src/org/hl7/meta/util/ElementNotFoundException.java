/* Copyright 2002 Health Level Seven, Inc. All Rights Reserved. 
 * 
 * This software is the proprietary information of Health Level Seven, Inc. 
 * Use is subject to license terms. 
 */
package org.hl7.meta.util;

/**
 * An exception that is thrown by methods in
 * {@link JdomUtils JdomUtils}
 * when the specified child element is not found.
 * 
 * @author Skirmantas Kligys (skirmis@hotmail.com)
 */
public class ElementNotFoundException extends Exception
{
  //-------------------------------------------------------------------------
  /**
   * Constructor.
   * 
   * @param path  XPath to the location where the error occured
   */
  public ElementNotFoundException(String path)
  {
    super(path);
  }
}
