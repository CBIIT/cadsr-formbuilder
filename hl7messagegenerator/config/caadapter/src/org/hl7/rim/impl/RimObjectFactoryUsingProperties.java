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

package org.hl7.rim.impl;

import java.io.InputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.URL;

import org.hl7.rim.RimObject;
import org.hl7.rim.RimObjectFactory;
import org.hl7.util.FactoryException;

public class RimObjectFactoryUsingProperties extends RimObjectFactory
{
  //-------------------------------------------------------------------------
  protected static final Logger LOGGER =
    Logger.getLogger("org.hl7.factories");

  //-------------------------------------------------------------------------
  private Properties props_ = new Properties();
  private HashMap<String, RimObject> cache_ = new HashMap<String, RimObject>();

  //-------------------------------------------------------------------------
  public RimObjectFactoryUsingProperties() throws FactoryException
  {
    InputStream fi = null;
    try
    {
      fi = Thread.currentThread().getContextClassLoader().getResourceAsStream("rim-map.properties");
      props_.load(fi);
    }
    catch (IOException ex)
    {
      throw new FactoryException("Factory RimObjectFactoryUsingProperties cannot " +
        "be created", ex);
    }
    finally
    {
      if (fi != null) try { fi.close(); } catch (IOException ignore) {}
    }
  }

  //-------------------------------------------------------------------------
  public RimObject createRimObject(String name)
    throws FactoryException
  {
    LOGGER.finest("Creating RIM object: " + name);

    synchronized (cache_)
    {
      RimObject rimObject = cache_.get(name);

      if (rimObject == null)
      {
        String clsName = (String) props_.get(name);
        try
        {
          rimObject = (RimObject)Class.forName(clsName).newInstance();
        }
        catch (Exception ex)
        {
          throw new FactoryException("RIM object " + name + " cannot be created",
            ex);
        }
        cache_.put(name, rimObject);
      }

			return (RimObject)rimObject.clone();
    }
  }
}
