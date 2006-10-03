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
package org.hl7.meta;

/**
 * An interface to factories that manufacture
 * {@link org.hl7.meta.impl.MessageTypeImpl MessageTypeImpl} instances.
 * Implementations include:
 * <ul>
 *   <li><code>AccessMessageTypeLoader</code> --
 * loads message type metadata from repository Access database</li>
 *   <li><code>JdomMessageTypeLoader</code> --
 * loads metadata from XML extract produced by RoseTree</li>
 *
 * @author Skirmantas Kligys (skirmis@hotmail.com)
 */
public interface MessageTypeLoader
{
  //-------------------------------------------------------------------------
  /**
   * Loads the message type metadata and returns the reference to the
   * metadata object for the message type.  Note that referenced CMETs are
   * not loaded automatically, but only on demand when the corresponding
   * association is actually traversed.
   *
   * @param id  the message type ID
   * @return the message type metadata
   * @throws LoaderException  if loading fails
   */
  public MessageType loadMessageType(String id) throws LoaderException;

  //-------------------------------------------------------------------------
  /**
   * Loads the CMET metadata and returns the reference to the
   * metadata object for the message type.  Note that referenced CMETs are
   * not loaded automatically, but only on demand when the corresponding
   * association is actually traversed.
   *
   * @param id  the message type ID
   * @return the message type metadata
   * @throws LoaderException  if loading fails
   */
  public MessageType loadCmet(String id) throws LoaderException;

  //-------------------------------------------------------------------------
  /**
   * Closes DB connections if they are open.
   */
  public void close();
}
