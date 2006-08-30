/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.0.2</a>, using an XML
 * Schema.
 * $Id: EVDSubsetImpl.java,v 1.2 2006-08-30 13:08:28 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import gov.nih.nci.ncicb.cadsr.edci.domain.EVDSubset;

import gov.nih.nci.ncicb.cadsr.edci.domain.ElementInSubset;

import java.util.Collection;
import java.util.Collections;

/**
 * A subset of the enumerated elements (EVDElements) in the Value
 * Domain. 
 * 
 * @version $Revision: 1.2 $ $Date: 2006-08-30 13:08:28 $
 */
public class EVDSubsetImpl implements java.io.Serializable, EVDSubset {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Identifier of this subset of the EnumeratedValueDomain.
     */
    private java.lang.String _subsetId;

    /**
     * Name for the subset.
     */
    private java.lang.String _name;

    /**
     * The source of the label to be presented for the RadioButton
     * corresponding to elements of this subset, when the
     * Enumerated Value Domain is presented as a set of
     * RadioButtons. Values for this attribute are {Value |
     * ValueMeaning | ValueMeaningDescription }. Default is Value.
     */
    private java.lang.String _radioButtonLabel;

    /**
     * The value and ordinal position of a constituent of a Subset.
     */
    private java.util.ArrayList _elementInSubsetImplList;


      //----------------/
     //- Constructors -/
    //----------------/

    public EVDSubsetImpl() 
     {
        super();
        _elementInSubsetImplList = new java.util.ArrayList();
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDSubsetImpl()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addElementInSubsetImpl
     * 
     * 
     * 
     * @param vElementInSubsetImpl
     */
    public void addElementInSubsetImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.ElementInSubsetImpl vElementInSubsetImpl)
        throws java.lang.IndexOutOfBoundsException
    {
        _elementInSubsetImplList.add(vElementInSubsetImpl);
    } //-- void addElementInSubsetImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.ElementInSubsetImpl) 

    /**
     * Method addElementInSubsetImpl
     * 
     * 
     * 
     * @param index
     * @param vElementInSubsetImpl
     */
    public void addElementInSubsetImpl(int index, gov.nih.nci.ncicb.cadsr.edci.domain.impl.ElementInSubsetImpl vElementInSubsetImpl)
        throws java.lang.IndexOutOfBoundsException
    {
        _elementInSubsetImplList.add(index, vElementInSubsetImpl);
    } //-- void addElementInSubsetImpl(int, gov.nih.nci.ncicb.cadsr.edci.domain.impl.ElementInSubsetImpl) 

    /**
     * Method clearElementInSubsetImpl
     * 
     */
    public void clearElementInSubsetImpl()
    {
        _elementInSubsetImplList.clear();
    } //-- void clearElementInSubsetImpl() 

    /**
     * Method enumerateElementInSubsetImpl
     * 
     * 
     * 
     * @return Enumeration
     */
    public java.util.Enumeration enumerateElementInSubsetImpl()
    {
        return Collections.enumeration(_elementInSubsetImplList);
    } //-- java.util.Enumeration enumerateElementInSubsetImpl() 

    /**
     * Method getElementInSubsetImpl
     * 
     * 
     * 
     * @param index
     * @return ElementInSubsetImpl
     */
    public gov.nih.nci.ncicb.cadsr.edci.domain.impl.ElementInSubsetImpl getElementInSubsetImpl(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index >= _elementInSubsetImplList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (gov.nih.nci.ncicb.cadsr.edci.domain.impl.ElementInSubsetImpl) _elementInSubsetImplList.get(index);
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.ElementInSubsetImpl getElementInSubsetImpl(int) 

    /**
     * Method getElementInSubsetImpl
     * 
     * 
     * 
     * @return ElementInSubsetImpl
     */
    public gov.nih.nci.ncicb.cadsr.edci.domain.impl.ElementInSubsetImpl[] getElementInSubsetImpl()
    {
        int size = _elementInSubsetImplList.size();
        gov.nih.nci.ncicb.cadsr.edci.domain.impl.ElementInSubsetImpl[] mArray = new gov.nih.nci.ncicb.cadsr.edci.domain.impl.ElementInSubsetImpl[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (gov.nih.nci.ncicb.cadsr.edci.domain.impl.ElementInSubsetImpl) _elementInSubsetImplList.get(index);
        }
        return mArray;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.ElementInSubsetImpl[] getElementInSubsetImpl() 

    /**
     * Method getElementInSubsetImplCount
     * 
     * 
     * 
     * @return int
     */
    public int getElementInSubsetImplCount()
    {
        return _elementInSubsetImplList.size();
    } //-- int getElementInSubsetImplCount() 

    /**
     * Returns the value of field 'name'. The field 'name' has the
     * following description: Name for the subset.
     * 
     * @return String
     * @return the value of field 'name'.
     */
    public java.lang.String getName()
    {
        return this._name;
    } //-- java.lang.String getName() 

    /**
     * Returns the value of field 'radioButtonLabel'. The field
     * 'radioButtonLabel' has the following description: The source
     * of the label to be presented for the RadioButton
     * corresponding to elements of this subset, when the
     * Enumerated Value Domain is presented as a set of
     * RadioButtons. Values for this attribute are {Value |
     * ValueMeaning | ValueMeaningDescription }. Default is Value.
     * 
     * @return String
     * @return the value of field 'radioButtonLabel'.
     */
    public java.lang.String getRadioButtonLabel()
    {
        return this._radioButtonLabel;
    } //-- java.lang.String getRadioButtonLabel() 

    /**
     * Returns the value of field 'subsetId'. The field 'subsetId'
     * has the following description: Identifier of this subset of
     * the EnumeratedValueDomain.
     * 
     * @return String
     * @return the value of field 'subsetId'.
     */
    public java.lang.String getSubsetId()
    {
        return this._subsetId;
    } //-- java.lang.String getSubsetId() 

    /**
     * Method removeElementInSubsetImpl
     * 
     * 
     * 
     * @param vElementInSubsetImpl
     * @return boolean
     */
    public boolean removeElementInSubsetImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.ElementInSubsetImpl vElementInSubsetImpl)
    {
        boolean removed = _elementInSubsetImplList.remove(vElementInSubsetImpl);
        return removed;
    } //-- boolean removeElementInSubsetImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.ElementInSubsetImpl) 

    /**
     * Method setElementInSubsetImpl
     * 
     * 
     * 
     * @param index
     * @param vElementInSubsetImpl
     */
    public void setElementInSubsetImpl(int index, gov.nih.nci.ncicb.cadsr.edci.domain.impl.ElementInSubsetImpl vElementInSubsetImpl)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index >= _elementInSubsetImplList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _elementInSubsetImplList.set(index, vElementInSubsetImpl);
    } //-- void setElementInSubsetImpl(int, gov.nih.nci.ncicb.cadsr.edci.domain.impl.ElementInSubsetImpl) 

    /**
     * Method setElementInSubsetImpl
     * 
     * 
     * 
     * @param elementInSubsetImplArray
     */
    public void setElementInSubsetImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.ElementInSubsetImpl[] elementInSubsetImplArray)
    {
        //-- copy array
        _elementInSubsetImplList.clear();
        for (int i = 0; i < elementInSubsetImplArray.length; i++) {
            _elementInSubsetImplList.add(elementInSubsetImplArray[i]);
        }
    } //-- void setElementInSubsetImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.ElementInSubsetImpl) 

    /**
     * Sets the value of field 'name'. The field 'name' has the
     * following description: Name for the subset.
     * 
     * @param name the value of field 'name'.
     */
    public void setName(java.lang.String name)
    {
        this._name = name;
    } //-- void setName(java.lang.String) 

    /**
     * Sets the value of field 'radioButtonLabel'. The field
     * 'radioButtonLabel' has the following description: The source
     * of the label to be presented for the RadioButton
     * corresponding to elements of this subset, when the
     * Enumerated Value Domain is presented as a set of
     * RadioButtons. Values for this attribute are {Value |
     * ValueMeaning | ValueMeaningDescription }. Default is Value.
     * 
     * @param radioButtonLabel the value of field 'radioButtonLabel'
     */
    public void setRadioButtonLabel(java.lang.String radioButtonLabel)
    {
        this._radioButtonLabel = radioButtonLabel;
    } //-- void setRadioButtonLabel(java.lang.String) 

    /**
     * Sets the value of field 'subsetId'. The field 'subsetId' has
     * the following description: Identifier of this subset of the
     * EnumeratedValueDomain.
     * 
     * @param subsetId the value of field 'subsetId'.
     */
    public void setSubsetId(java.lang.String subsetId)
    {
        this._subsetId = subsetId;
    } //-- void setSubsetId(java.lang.String) 

    public void setBaseSubsetFlag(boolean isBaseSubsetFlag) {
    }

    public boolean isBaseSubsetFlag() {
        return false;
    }

    public void addElementInSubset(ElementInSubset elementsInSubset) {
       addElementInSubsetImpl((ElementInSubsetImpl)elementsInSubset);
    }

    public void setElementInSubsetCollection(Collection<ElementInSubset> elementsInSubsetCollection) {
        clearElementInSubsetImpl();
        for (ElementInSubset elementInSubset: elementsInSubsetCollection) {
            addElementInSubsetImpl((ElementInSubsetImpl)elementInSubset);
        }
    }

    public Collection<ElementInSubset> getElementInSubsetCollection() {
        return _elementInSubsetImplList;
    }
}
