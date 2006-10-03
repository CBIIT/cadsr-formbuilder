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

import org.hl7.meta.*;
import org.hl7.xml.XmlItsUtil;

import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

/**
 * An implementation of metadata for an HL7 v3 clone class attribute.
 * 
 * @author Skirmantas Kligys (skirmis@hotmail.com)
 */
public class AttributeImpl
      extends FeatureImpl implements Attribute
    {
      //-------------------------------------------------------------------------
      /**
       * Datatype of this attribute.
       */
      private Datatype datatype_;

      /**
       * Vocabulary domains for this attribute.
       */
      private String[] domains_;

  /**
   * Fixed values for this attribute.
   */
  private String[] fixedValues_;

  /**
   * Default value for this attribute.
   */
  private String defaultValue_;

  /**
   * Coding strength for this attribute.
   */
  private CodingStrength codingStrength_;

  /**
   * Structural attribute flag.
   */
  private boolean structural_;

    /**
     * extra attribute flag.
     */
  private boolean extra_;
  //-------------------------------------------------------------------------
  /**
   * Constructor.
   */
  public AttributeImpl(CloneClass parent)
  {
    super(parent);
  }

  //-------------------------------------------------------------------------
  /**
   * Returns the datatype of this attribute.
   * 
   * @return the datatype
   */
  public Datatype getDatatype()
  {
    return datatype_;
  }

  //-------------------------------------------------------------------------
  /**
   * Returns the vocabulary domains for this attribute.
   * 
   * @return the vocabulary domains
   */
  public String[] getDomains()
  {
    return domains_;
  }

  //-------------------------------------------------------------------------
  /**
   * Returns the fixed values for this attribute.
   * 
   * @return the fixed values
   */
  public String[] getFixedValues()
  {
    return fixedValues_;
  }

  //-------------------------------------------------------------------------
  /**
   * Returns the default value for this attribute.
   * For Meta Obeject parsed by HMD, should call MetaUtils.getDefaultValue(Attribute attribute).
   * HMD does not provide the default Value like Mif 
   * 
   * @return the default values
   */
  public String getDefaultValue()
  {
    return defaultValue_;
  }

  //-------------------------------------------------------------------------
  /**
   * Returns the coding strength for this attribute.
   * 
   * @return the coding strength
   */
  public CodingStrength getCodingStrength()
  {
    return codingStrength_;
  }

  //-------------------------------------------------------------------------
  /**
   * Sets the datatype of this attribute.
   * Should be called only from an implementation of
   * {@link org.hl7.meta.MessageTypeLoader MessageTypeLoader}.
   * 
   * @param datatype  the datatype
   */
  /*default*/ void setDatatype(Datatype datatype)
  {
    datatype_ = datatype;
  }

  //-------------------------------------------------------------------------
  /**
   * Sets the vocabulary domains for this attribute.
   * Should be called only from an implementation of
   * {@link org.hl7.meta.MessageTypeLoader MessageTypeLoader}.
   * 
   * @param domains  the vocabulary domains
   */
  /*default*/ void setDomains(String[] domains)
  {
    domains_ = domains;
  }

  //-------------------------------------------------------------------------
  /**
   * Sets the fixed values for this attribute.
   * Should be called only from an implementation of
   * {@link org.hl7.meta.MessageTypeLoader MessageTypeLoader}.
   * 
   * @param fixedValues  the fixed values
   */
  /*default*/ void setFixedValues(String[] fixedValues)
  {
    fixedValues_ = fixedValues;
  }

  //-------------------------------------------------------------------------
  /**
   * Sets the default value for this attribute.
   * Should be called only from an implementation of
   * {@link org.hl7.meta.MessageTypeLoader MessageTypeLoader}.
   * 
   * @param defaultValue  the default values
   */
  void setDefaultValue(String defaultValue)
  {
    defaultValue_ = defaultValue;
  }

  //-------------------------------------------------------------------------
  /**
   * Sets the coding strength for this attribute.
   * Should be called only from an implementation of
   * {@link org.hl7.meta.MessageTypeLoader MessageTypeLoader}.
   * 
   * @param codingStrength  the coding strength
   */
  /*default*/ void setCodingStrength(CodingStrength codingStrength)
  {
    codingStrength_ = codingStrength;
  }

  //-------------------------------------------------------------------------
  /**
   * Returns a string representation of the attribute metadata.
   * 
   * @return  a string representation
   */
  public String toString()
  {
    return getName() + ": " + getDatatype().getFullName() +
      " // " + getCardinality();
  }

  //-------------------------------------------------------------------------
  /**
   * Sets the name of the RIM class where the feature was originally
   * defined.
   * Should be called only from an implementation of
   * {@link org.hl7.meta.MessageTypeLoader MessageTypeLoader}.
   *
   * @param rimClass  the RIM class name
   */
  /*default*/ void setRimClass(String rimClass)
  {
    super.setRimClass(rimClass);
    if (getRimPropertyName() != null && getRimClass() != null)
    {
      structural_ = isStructuralInternal();
      extra_ = XmlItsUtil.isAdditionalAttribute(getRimPropertyName());
    }
  }


  //-------------------------------------------------------------------------
  /**
   * Sets the name of the feature inside the RIM class.  This may be the
   * attribute name or the association name.
   * Should be called only from an implementation of
   * {@link org.hl7.meta.MessageTypeLoader MessageTypeLoader}.
   *
   * @param rimName the RIM feature name
   */
  /*default*/ void setRimPropertyName(String rimName)
  {
    super.setRimPropertyName(rimName);
    if (getRimPropertyName() != null && getRimClass() != null)
    {
      structural_ = isStructuralInternal();
    }
  }


    //-------------------------------------------------------------------------
    public boolean isStructural()
    {
      return structural_;
    }

    /**
   * Returns <code>true</code> if this attribute is defined as Extra attribute
   * in XML ITS (that means in XML instance it is represented by an XML
   * attribute instead of an XML element.
   *
   * @return the structural attribute flag
   */
    public boolean isExtra()
    {
      return extra_;
    }

  //-------------------------------------------------------------------------
  /**
   * TODO: should not be hardcoded?
   */
  private static final Set<String> STRUCTURAL_ATTRIBUTES =
    new HashSet<String>(Arrays.asList(new String[]
    {
       "templateId","typeId", "realmCode", "nullFlavour",  // InfrastructureRoot
      "moodCode", "classCode", "negationInd", "levelCode", // Act
      "typeCode", "inversionInd", "contextControlCode", "contextConductionInd", //ActRelationship
      "determinerCode",  // Entity
    }));

  //-------------------------------------------------------------------------
  private boolean isStructuralInternal()
  {
      String key = getRimPropertyName();
    return STRUCTURAL_ATTRIBUTES.contains(key);
  }

//    //-------------------------------------------------------------------------
//  private boolean isExtraInternal()
//    {
//        String key = getRimPropertyName();
//      return XMLITS_EXTRA_ATTRIBUTES.contains(key);
//    }


}
