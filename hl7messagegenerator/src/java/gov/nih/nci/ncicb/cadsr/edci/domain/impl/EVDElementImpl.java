/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.0.2</a>, using an XML
 * Schema.
 * $Id: EVDElementImpl.java,v 1.2 2006-08-30 13:08:28 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import gov.nih.nci.ncicb.cadsr.edci.domain.EVDElement;

import gov.nih.nci.ncicb.cadsr.edci.domain.EVDElementText;

import java.util.Collection;
import java.util.Collections;

/**
 * An element in an Enumerated Value Domain Subset. 
 * 
 * @version $Revision: 1.2 $ $Date: 2006-08-30 13:08:28 $
 */
public class EVDElementImpl implements java.io.Serializable, EVDElement {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * May be a 'code' or a text string such as "1" or "Male" for
     * the database representation of the value meaning identifier.
     */
    private java.lang.String _value;

    /**
     * Language-specific texts associated with a particular value
     * in an enumerated value domain.
     */
    private java.util.ArrayList _EVDElementTextImplList;


      //----------------/
     //- Constructors -/
    //----------------/

    public EVDElementImpl() 
     {
        super();
        _EVDElementTextImplList = new java.util.ArrayList();
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDElementImpl()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addEVDElementTextImpl
     * 
     * 
     * 
     * @param vEVDElementTextImpl
     */
    public void addEVDElementTextImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDElementTextImpl vEVDElementTextImpl)
        throws java.lang.IndexOutOfBoundsException
    {
        _EVDElementTextImplList.add(vEVDElementTextImpl);
    } //-- void addEVDElementTextImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDElementTextImpl) 

    /**
     * Method addEVDElementTextImpl
     * 
     * 
     * 
     * @param index
     * @param vEVDElementTextImpl
     */
    public void addEVDElementTextImpl(int index, gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDElementTextImpl vEVDElementTextImpl)
        throws java.lang.IndexOutOfBoundsException
    {
        _EVDElementTextImplList.add(index, vEVDElementTextImpl);
    } //-- void addEVDElementTextImpl(int, gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDElementTextImpl) 

    /**
     * Method clearEVDElementTextImpl
     * 
     */
    public void clearEVDElementTextImpl()
    {
        _EVDElementTextImplList.clear();
    } //-- void clearEVDElementTextImpl() 

    /**
     * Method enumerateEVDElementTextImpl
     * 
     * 
     * 
     * @return Enumeration
     */
    public java.util.Enumeration enumerateEVDElementTextImpl()
    {
        return Collections.enumeration(_EVDElementTextImplList);
    } //-- java.util.Enumeration enumerateEVDElementTextImpl() 

    /**
     * Method getEVDElementTextImpl
     * 
     * 
     * 
     * @param index
     * @return EVDElementTextImpl
     */
    public gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDElementTextImpl getEVDElementTextImpl(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index >= _EVDElementTextImplList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDElementTextImpl) _EVDElementTextImplList.get(index);
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDElementTextImpl getEVDElementTextImpl(int) 

    /**
     * Method getEVDElementTextImpl
     * 
     * 
     * 
     * @return EVDElementTextImpl
     */
    public gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDElementTextImpl[] getEVDElementTextImpl()
    {
        int size = _EVDElementTextImplList.size();
        gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDElementTextImpl[] mArray = new gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDElementTextImpl[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDElementTextImpl) _EVDElementTextImplList.get(index);
        }
        return mArray;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDElementTextImpl[] getEVDElementTextImpl() 

    /**
     * Method getEVDElementTextImplCount
     * 
     * 
     * 
     * @return int
     */
    public int getEVDElementTextImplCount()
    {
        return _EVDElementTextImplList.size();
    } //-- int getEVDElementTextImplCount() 

    /**
     * Returns the value of field 'value'. The field 'value' has
     * the following description: May be a 'code' or a text string
     * such as "1" or "Male" for the database representation of the
     * value meaning identifier.
     * 
     * @return String
     * @return the value of field 'value'.
     */
    public java.lang.String getValue()
    {
        return this._value;
    } //-- java.lang.String getValue() 

    /**
     * Method removeEVDElementTextImpl
     * 
     * 
     * 
     * @param vEVDElementTextImpl
     * @return boolean
     */
    public boolean removeEVDElementTextImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDElementTextImpl vEVDElementTextImpl)
    {
        boolean removed = _EVDElementTextImplList.remove(vEVDElementTextImpl);
        return removed;
    } //-- boolean removeEVDElementTextImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDElementTextImpl) 

    /**
     * Method setEVDElementTextImpl
     * 
     * 
     * 
     * @param index
     * @param vEVDElementTextImpl
     */
    public void setEVDElementTextImpl(int index, gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDElementTextImpl vEVDElementTextImpl)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index >= _EVDElementTextImplList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _EVDElementTextImplList.set(index, vEVDElementTextImpl);
    } //-- void setEVDElementTextImpl(int, gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDElementTextImpl) 

    /**
     * Method setEVDElementTextImpl
     * 
     * 
     * 
     * @param EVDElementTextImplArray
     */
    public void setEVDElementTextImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDElementTextImpl[] EVDElementTextImplArray)
    {
        //-- copy array
        _EVDElementTextImplList.clear();
        for (int i = 0; i < EVDElementTextImplArray.length; i++) {
            _EVDElementTextImplList.add(EVDElementTextImplArray[i]);
        }
    } //-- void setEVDElementTextImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDElementTextImpl) 

    /**
     * Sets the value of field 'value'. The field 'value' has the
     * following description: May be a 'code' or a text string such
     * as "1" or "Male" for the database representation of the
     * value meaning identifier.
     * 
     * @param value the value of field 'value'.
     */
    public void setValue(java.lang.String value)
    {
        this._value = value;
    } //-- void setValue(java.lang.String) 

    public void addEVDElementText(EVDElementText evdElementText) {
        addEVDElementTextImpl((EVDElementTextImpl)evdElementText);
    }

    public void setEVDElementTextCollection(Collection<EVDElementText> eVDElementTextCollection) {
        clearEVDElementTextImpl();
        for (EVDElementText evdElementText: eVDElementTextCollection) {
            addEVDElementTextImpl((EVDElementTextImpl)evdElementText);
        }
    }

    public Collection<EVDElementText> getEVDElementTextCollection() {
        return _EVDElementTextImplList;
    }
}
