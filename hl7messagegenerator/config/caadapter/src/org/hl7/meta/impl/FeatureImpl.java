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

/**
 * An abstract class that captures similarities between attributes and
 * associations.
 * 
 * @author Skirmantas Kligys (skirmis@hotmail.com)
 */
public class FeatureImpl implements Feature
{
  //-------------------------------------------------------------------------
  /**
   * The clone class where the feature belongs.
   */
  private CloneClass parent_;

  /**
   * Feature's name, which may be the attribute name, or the short
   * name in case of an association.
   */
  private String name_;

 /**
   * Feature's sort key.
   */
  private int sortKey_;

  /**
   * Feature's cardinality.
   */
  private Cardinality cardinality_;

  /**
   * The name of the RIM class where the feature was originally defined.
   */
  private String rimClass_;

  /**
   * Property Name inside the RIM class.  This may be the
   * attribute name or the association name.
   */
  private String propertyName_;

  /**
   * Flag showing if the feature is mandatory in the clone class.
   */
  private boolean mandatory_;

  /**
   * Conformance field attached to this feature.
   */
  private Conformance conformance_;

  /**
   * Textual constraint note attached to the feature.
   */
  private String constraint_;
  
  //-------------------------------------------------------------------------
  /**
   * Constructor.
   */
  public FeatureImpl(CloneClass parent)
  {
    parent_ = parent;
  }

  //-------------------------------------------------------------------------
  /**
   * Returns the feature's name, which may be the attribute name, or the short
   * name in case of an association.
   * 
   * @return the feature's name
   */
  public String getName()
  {
    return name_;
  }

    public int getSortKey()
    {
        return sortKey_;
    }

  //-------------------------------------------------------------------------
  /**
   * Returns the feature's cardinality.
   * 
   * @return the feature's cardinality
   */
  public Cardinality getCardinality()
  {
    return cardinality_;
  }

  //-------------------------------------------------------------------------
  /**
   * Returns the name of the RIM class where the feature was originally
   * defined.
   * 
   * @return the RIM class name
   */
  public String getRimClass()
  {
    return rimClass_;
  }

  //-------------------------------------------------------------------------
  /**
   * Returns the element name of the feature inside the RIM class.  This may be the
   * attribute name or the association name.
   * 
   * @return the RIM feature name
   */
  public String getRimPropertyName()
  {
    return propertyName_;
  }

  //-------------------------------------------------------------------------
  /**
   * Returns the clone class where the feature belongs.
   * 
   * @return the clone class
   */
  public CloneClass getParent()
  {
    return parent_;
  }

  //-------------------------------------------------------------------------
  /**
   * Returns a flag showing if the feature is mandatory in the clone class.
   * 
   * @return the flag
   */
  public boolean isMandatory()
  {
    return mandatory_;
  }

  //-------------------------------------------------------------------------
  /**
   * Returns the conformance field attached to this feature.
   * 
   * @return the conformance field
   */
  public Conformance getConformance()
  {
    return conformance_;
  }

  //-------------------------------------------------------------------------
  /**
   * Returns the textual constraint note attached to the feature.
   * 
   * @return the constraint note
   */
  public String getConstraint()
  {
    return constraint_;
  }

  //-------------------------------------------------------------------------
  /**
   * Sets the feature's name, which may be the attribute name, or the short
   * name in case of an association.
   * Should be called only from an implementation of
   * {@link org.hl7.meta.MessageTypeLoader MessageTypeLoader}.
   * 
   * @param name the feature's name
   */
  /*default*/ void setName(String name)
  {
    name_ = name;
  }

  void setSortKey(int sortKey)
  {
    sortKey_ = sortKey;
  }
  //-------------------------------------------------------------------------
  /**
   * Sets the feature's cardinality.
   * Should be called only from an implementation of
   * {@link org.hl7.meta.MessageTypeLoader MessageTypeLoader}.
   * 
   * @param cardinality  the feature's cardinality
   */
  /*default*/ void setCardinality(Cardinality cardinality)
  {
    cardinality_ = cardinality;
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
    rimClass_ = rimClass;
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
    propertyName_ = rimName;
  }

  //-------------------------------------------------------------------------
  /**
   * Sets a flag showing if the feature is mandatory in the clone class.
   * Should be called only from an implementation of
   * {@link org.hl7.meta.MessageTypeLoader MessageTypeLoader}.
   * 
   * @param mandatory  the flag
   */
  /*default*/ void setMandatory(boolean mandatory)
  {
    mandatory_ = mandatory;
  }

  //-------------------------------------------------------------------------
  /**
   * Returns the conformance field attached to this feature.
   * Should be called only from an implementation of
   * {@link org.hl7.meta.MessageTypeLoader MessageTypeLoader}.
   * 
   * @param conformance  the conformance field
   */
  /*default*/ void setConformance(Conformance conformance)
  {
    conformance_ = conformance;
  }

  //-------------------------------------------------------------------------
  /**
   * Returns the textual constraint note attached to the feature.
   * Should be called only from an implementation of
   * {@link org.hl7.meta.MessageTypeLoader MessageTypeLoader}.
   * 
   * @param constraint  the constraint note
   */
  /*default*/ void setConstraint(String constraint)
  {
    constraint_ = constraint;
  }

    protected FeatureImpl clone() throws CloneNotSupportedException
    {
        FeatureImpl feature = (FeatureImpl)super.clone();
        return feature;
    }

    /**
     * Pass cloned parent class
     * @param clonedParentCloneClass
     * @return
     * @throws CloneNotSupportedException
     */
    protected FeatureImpl clone(CloneClass clonedParentCloneClass) throws CloneNotSupportedException
    {
        FeatureImpl feature = (FeatureImpl)clone();
        feature.parent_ = clonedParentCloneClass;
        return feature;
    }
}
