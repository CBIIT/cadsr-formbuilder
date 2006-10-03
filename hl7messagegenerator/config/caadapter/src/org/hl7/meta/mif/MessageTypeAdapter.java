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
* The Initial Developer of the Original Code is Gunther Schadow.
* Portions created by Initial Developer are Copyright (C) 2002-2004 
* Health Level Seven, Inc. All Rights Reserved.
*
* Contributor(s): 
*
* $Id: MessageTypeAdapter.java,v 1.1 2006-10-03 17:39:01 marwahah Exp $
*/
package org.hl7.meta.mif;

import hl7OrgV3.mif.AffiliateKind;
import hl7OrgV3.mif.PackageRef;
import org.hl7.meta.CloneClass;
import org.hl7.meta.LoaderException;
import org.hl7.meta.MessageType;
import org.hl7.util.ForwardReferenceTool;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author Gunther Schadow, Peter Hendler
 */
public class MessageTypeAdapter implements MessageType {

  private hl7OrgV3.mif.SerializedStaticModel _mifThing;

  private Map<String,CloneClass> _cloneClassMap = new HashMap<String, CloneClass>();
  private CloneClass _rootClass;

  /*package*/ MessageTypeAdapter(hl7OrgV3.mif.SerializedStaticModel mifThing) throws LoaderException {
    _mifThing = mifThing;
    _rootClass = new CloneClassAdapter(this,_mifThing.getOwnedEntryPoint().getSpecializedClass().getClass1());
  }

  /*package*/ MessageTypeAdapter(String messagetype) throws LoaderException {
    this(mifThing(messagetype));
  }

  private static hl7OrgV3.mif.SerializedStaticModel mifThing(String messagetype) {
    String mifFileName = (String)_config.get(messagetype);
    InputStream input = null;
    try {
      input = Thread.currentThread().getContextClassLoader().getResourceAsStream(mifFileName);
      return hl7OrgV3.mif.SerializedStaticModelDocument
				.Factory.parse(input)
				.getSerializedStaticModel();
    } catch (Exception ex) {
      throw new Error("Could not load MIF file " + mifFileName + ". Be sure it's in classpath. " + ex.getMessage(), ex);
    } finally {
      if (input != null) try { input.close(); } catch(IOException ignore) {}
    }
  }

  public CloneClass lookupCloneClass(String name) throws NoSuchElementException {
    CloneClass result = _cloneClassMap.get(name);
    if(result == null || result instanceof ForwardReferenceTool.Reference)
      throw new NoSuchElementException("class not found: " + name + " " + result);
    return result;
  }

  /*package*/ CloneClass lookupCloneClass(String name, ForwardReferenceTool.Replacer<CloneClass> replacer) {
    return ForwardReferenceTool.get(_cloneClassMap, name, CloneClass.class, replacer);
  }

  /*package*/ CloneClass lookupCloneClass(String name, ForwardReferenceTool.ReplacerFactory<CloneClass> replacerFactory) {
    return ForwardReferenceTool.get(_cloneClassMap, name, CloneClass.class, replacerFactory);
  }

  public String getName() {
    // FIXME: not right?
    return _mifThing.getTitle();
  }

  public String getId() {

      PackageRef packageLocation = _mifThing.getPackageLocation();
      String subSection = packageLocation.getSubSection().toString();
      String domain = packageLocation.getDomain().toString();
      String artifact = packageLocation.getArtifact().toString().substring(0, 2);
      AffiliateKind.Enum realmEnum = packageLocation.getRealm();
      if (realmEnum != null)
      {
          String realm = realmEnum.toString();
      }
      String version = packageLocation.getVersion();

      String id = _mifThing.getName();
      StringBuffer sb = new StringBuffer(subSection).append(domain).append("_").append(artifact).append(id);
      return sb.toString();
  }

  public Set<String> getAllCloneClassNames() {
    return _cloneClassMap.keySet();
  }

  public CloneClass getRootClass() {
    return _rootClass;
  }

  public void addCloneClass(String name, CloneClass cc) {
      ForwardReferenceTool.put(_cloneClassMap, name, cc);
  }

  private static Properties _config = new Properties();
  static {
    InputStream input = null;
    try {
      input = Thread.currentThread().getContextClassLoader().getResourceAsStream("cmet-file.properties");
      _config.load(input);
    } catch (Exception ex) {
      System.err.println("Could not load properties. Be sure it's in classpath. " + ex.getMessage());
    } finally {
      if (input != null) try { input.close(); } catch(IOException ignore) {}
    }
  }
}
