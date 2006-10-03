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
import org.hl7.meta.MessageType;
import org.hl7.meta.MessageTypeLoader;

import java.util.HashMap;

/**
 * An implementation of a CMET metadata cache.  It loads CMET metadata on
 * the first demand and then caches it in memory for efficient subsequent
 * access.
 *
 * Should be used only from an implementation of
 * {@link org.hl7.meta.MessageTypeLoader MessageTypeLoader}.
 *
 * To do: cache expiration policy; the way it is now, the cache will grow
 * until it finally contains metadata for all CMETs ever used.
 *
 * @author Skirmantas Kligys
 */
public class CmetCacheImpl
{
  //-------------------------------------------------------------------------
  /**
   * The associated message type loader used for the initial loading.
   */
  private final MessageTypeLoader loader_;

  /**
   * The cache hashmap.
   */
  private final HashMap<String, MessageType> CACHE =
    new HashMap<String, MessageType>();

  //-------------------------------------------------------------------------
  /**
   * Constructor.
   *
   * @param loader  the associated message type loader
   */
  /*default*/ CmetCacheImpl(MessageTypeLoader loader)
  {
    if (loader == null) throw new NullPointerException();
    loader_ = loader;
  }

  //-------------------------------------------------------------------------
  /**
   * Returns the reference to the CMET message type metadata.  Will try to
   * load the metadata on the first call with given ID; later will just
   * return metadata cached in memory.
   *
   * @param id  the message type ID
   * @param needClone  if clone the message type or not
   * @return the message type metadata
   * @throws LoaderException  if loading fails
   */
  /*default*/ MessageType getCmet(String id, boolean needClone) throws LoaderException
  {
    MessageType mt = CACHE.get(id);

    if (mt == null)
    {
      mt = loader_.loadCmet(id);
      CACHE.put(id, mt);
    }


      MessageType messageType = null;
      try
      {
          if (needClone)
          {
              messageType = (MessageType)((MessageTypeImpl)mt).clone();
          }
          else
          {
              messageType = mt;     
          }
      }
      catch (CloneNotSupportedException e)
      {
          e.printStackTrace();  
      }

      return messageType;

      // no cache anymore
//      return loader_.loadCmet(id);
 }
}
