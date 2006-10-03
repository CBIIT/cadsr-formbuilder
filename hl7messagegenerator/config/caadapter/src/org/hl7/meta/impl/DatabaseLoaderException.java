/* The contents of this file are subject to the Health Level-7 Public
* License Version 1.0 (the "License"); you may not use this file
* except in compliance with the License. You may obtain a copy of the
* License at http://www.hl7.org/HPL/
*
* Software distributed under the License is distributed on an "AS IS"
* basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
* the License for the specific language governing rights and
* limitations under the License.
*
* The Original Code is all this file.
*
* The Initial Developer of the Original Code is .
* Portions created by Initial Developer are Copyright (C) 2002-2004 
* Health Level Seven, Inc. All Rights Reserved.
*
* Contributor(s): 
*/
package org.hl7.meta.impl;

import org.hl7.meta.LoaderException;

/**
 * An application level exception is thrown by database loaders.
 * May wrap a lower level exception inside (such as java.sql.SQLException).
 * 
 * @author Skirmantas Kligys (skirmis@hotmail.com)
 */
public class DatabaseLoaderException extends LoaderException
{
  //-------------------------------------------------------------------------
  /**
   * Constructor for DatabaseLoaderException.
   * 
   * @param message  message
   */
  public DatabaseLoaderException(String message)
  {
    super(message);
  }

  //-------------------------------------------------------------------------
  /**
   * Constructor for DatabaseLoaderException.
   * 
   * @param t  nested exception
   */
  public DatabaseLoaderException(Throwable t)
  {
    super(t);
  }

  //-------------------------------------------------------------------------
  /**
   * Constructor for DatabaseLoaderException.
   * 
   * @param messageType  message type ID
   * @param row          HMD row where error was detected
   * @param message      error message
   */
  public DatabaseLoaderException(String messageType, int row, String message)
  {
    super(messageType + '.' + row + ": " + message);
  }
}
