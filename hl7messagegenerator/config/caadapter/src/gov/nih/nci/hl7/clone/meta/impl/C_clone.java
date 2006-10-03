/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: C_clone.java,v 1.1 2006-10-03 17:38:25 marwahah Exp $
 */

package gov.nih.nci.hl7.clone.meta.impl;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Vector;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Class C_clone.
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 17:38:25 $
 */
public class C_clone implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _choiceGroupName
     */
    private java.lang.String _choiceGroupName;

    /**
     * Field _clonename
     */
    private java.lang.String _clonename;

    /**
     * Field _sortKey
     */
    private int _sortKey;

    /**
     * keeps track of state for field: _sortKey
     */
    private boolean _has_sortKey;

    /**
     * Field _cardinality
     */
    private java.lang.String _cardinality;

    /**
     * Field _rimSource
     */
    private java.lang.String _rimSource;

    /**
     * Field _cmetID
     */
    private java.lang.String _cmetID;

    /**
     * Field _referenceUuid
     */
    private java.lang.String _referenceUuid;

    /**
     * Field _referenceName
     */
    private java.lang.String _referenceName;

    /**
     * Field _isReference
     */
    private boolean _isReference;

    /**
     * keeps track of state for field: _isReference
     */
    private boolean _has_isReference;

    /**
     * Field _uuid
     */
    private java.lang.String _uuid;

    /**
     * Field _multipleSequenceNumber
     */
    private int _multipleSequenceNumber;

    /**
     * keeps track of state for field: _multipleSequenceNumber
     */
    private boolean _has_multipleSequenceNumber;

    /**
     * Field _items
     */
    private java.util.Vector _items;


      //----------------/
     //- Constructors -/
    //----------------/

    public C_clone() 
     {
        super();
        _items = new Vector();
    } //-- gov.nih.nci.hl7.clone.meta.impl.C_clone()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addC_cloneItem
     * 
     * 
     * 
     * @param vC_cloneItem
     */
    public void addC_cloneItem(gov.nih.nci.hl7.clone.meta.impl.C_cloneItem vC_cloneItem)
        throws java.lang.IndexOutOfBoundsException
    {
        _items.addElement(vC_cloneItem);
    } //-- void addC_cloneItem(gov.nih.nci.hl7.clone.meta.impl.C_cloneItem) 

    /**
     * Method addC_cloneItem
     * 
     * 
     * 
     * @param index
     * @param vC_cloneItem
     */
    public void addC_cloneItem(int index, gov.nih.nci.hl7.clone.meta.impl.C_cloneItem vC_cloneItem)
        throws java.lang.IndexOutOfBoundsException
    {
        _items.insertElementAt(vC_cloneItem, index);
    } //-- void addC_cloneItem(int, gov.nih.nci.hl7.clone.meta.impl.C_cloneItem) 

    /**
     * Method deleteIsReference
     * 
     */
    public void deleteIsReference()
    {
        this._has_isReference= false;
    } //-- void deleteIsReference() 

    /**
     * Method deleteMultipleSequenceNumber
     * 
     */
    public void deleteMultipleSequenceNumber()
    {
        this._has_multipleSequenceNumber= false;
    } //-- void deleteMultipleSequenceNumber() 

    /**
     * Method deleteSortKey
     * 
     */
    public void deleteSortKey()
    {
        this._has_sortKey= false;
    } //-- void deleteSortKey() 

    /**
     * Method enumerateC_cloneItem
     * 
     * 
     * 
     * @return Enumeration
     */
    public java.util.Enumeration enumerateC_cloneItem()
    {
        return _items.elements();
    } //-- java.util.Enumeration enumerateC_cloneItem() 

    /**
     * Method getC_cloneItem
     * 
     * 
     * 
     * @param index
     * @return C_cloneItem
     */
    public gov.nih.nci.hl7.clone.meta.impl.C_cloneItem getC_cloneItem(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _items.size())) {
            throw new IndexOutOfBoundsException("getC_cloneItem: Index value '"+index+"' not in range [0.."+_items.size()+ "]");
        }
        
        return (gov.nih.nci.hl7.clone.meta.impl.C_cloneItem) _items.elementAt(index);
    } //-- gov.nih.nci.hl7.clone.meta.impl.C_cloneItem getC_cloneItem(int) 

    /**
     * Method getC_cloneItem
     * 
     * 
     * 
     * @return C_cloneItem
     */
    public gov.nih.nci.hl7.clone.meta.impl.C_cloneItem[] getC_cloneItem()
    {
        int size = _items.size();
        gov.nih.nci.hl7.clone.meta.impl.C_cloneItem[] mArray = new gov.nih.nci.hl7.clone.meta.impl.C_cloneItem[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (gov.nih.nci.hl7.clone.meta.impl.C_cloneItem) _items.elementAt(index);
        }
        return mArray;
    } //-- gov.nih.nci.hl7.clone.meta.impl.C_cloneItem[] getC_cloneItem() 

    /**
     * Method getC_cloneItemCount
     * 
     * 
     * 
     * @return int
     */
    public int getC_cloneItemCount()
    {
        return _items.size();
    } //-- int getC_cloneItemCount() 

    /**
     * Returns the value of field 'cardinality'.
     * 
     * @return String
     * @return the value of field 'cardinality'.
     */
    public java.lang.String getCardinality()
    {
        return this._cardinality;
    } //-- java.lang.String getCardinality() 

    /**
     * Returns the value of field 'choiceGroupName'.
     * 
     * @return String
     * @return the value of field 'choiceGroupName'.
     */
    public java.lang.String getChoiceGroupName()
    {
        return this._choiceGroupName;
    } //-- java.lang.String getChoiceGroupName() 

    /**
     * Returns the value of field 'clonename'.
     * 
     * @return String
     * @return the value of field 'clonename'.
     */
    public java.lang.String getClonename()
    {
        return this._clonename;
    } //-- java.lang.String getClonename() 

    /**
     * Returns the value of field 'cmetID'.
     * 
     * @return String
     * @return the value of field 'cmetID'.
     */
    public java.lang.String getCmetID()
    {
        return this._cmetID;
    } //-- java.lang.String getCmetID() 

    /**
     * Returns the value of field 'isReference'.
     * 
     * @return boolean
     * @return the value of field 'isReference'.
     */
    public boolean getIsReference()
    {
        return this._isReference;
    } //-- boolean getIsReference() 

    /**
     * Returns the value of field 'multipleSequenceNumber'.
     * 
     * @return int
     * @return the value of field 'multipleSequenceNumber'.
     */
    public int getMultipleSequenceNumber()
    {
        return this._multipleSequenceNumber;
    } //-- int getMultipleSequenceNumber() 

    /**
     * Returns the value of field 'referenceName'.
     * 
     * @return String
     * @return the value of field 'referenceName'.
     */
    public java.lang.String getReferenceName()
    {
        return this._referenceName;
    } //-- java.lang.String getReferenceName() 

    /**
     * Returns the value of field 'referenceUuid'.
     * 
     * @return String
     * @return the value of field 'referenceUuid'.
     */
    public java.lang.String getReferenceUuid()
    {
        return this._referenceUuid;
    } //-- java.lang.String getReferenceUuid() 

    /**
     * Returns the value of field 'rimSource'.
     * 
     * @return String
     * @return the value of field 'rimSource'.
     */
    public java.lang.String getRimSource()
    {
        return this._rimSource;
    } //-- java.lang.String getRimSource() 

    /**
     * Returns the value of field 'sortKey'.
     * 
     * @return int
     * @return the value of field 'sortKey'.
     */
    public int getSortKey()
    {
        return this._sortKey;
    } //-- int getSortKey() 

    /**
     * Returns the value of field 'uuid'.
     * 
     * @return String
     * @return the value of field 'uuid'.
     */
    public java.lang.String getUuid()
    {
        return this._uuid;
    } //-- java.lang.String getUuid() 

    /**
     * Method hasIsReference
     * 
     * 
     * 
     * @return boolean
     */
    public boolean hasIsReference()
    {
        return this._has_isReference;
    } //-- boolean hasIsReference() 

    /**
     * Method hasMultipleSequenceNumber
     * 
     * 
     * 
     * @return boolean
     */
    public boolean hasMultipleSequenceNumber()
    {
        return this._has_multipleSequenceNumber;
    } //-- boolean hasMultipleSequenceNumber() 

    /**
     * Method hasSortKey
     * 
     * 
     * 
     * @return boolean
     */
    public boolean hasSortKey()
    {
        return this._has_sortKey;
    } //-- boolean hasSortKey() 

    /**
     * Method isValid
     * 
     * 
     * 
     * @return boolean
     */
    public boolean isValid()
    {
        try {
            validate();
        }
        catch (org.exolab.castor.xml.ValidationException vex) {
            return false;
        }
        return true;
    } //-- boolean isValid() 

    /**
     * Method marshal
     * 
     * 
     * 
     * @param out
     */
    public void marshal(java.io.Writer out)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, out);
    } //-- void marshal(java.io.Writer) 

    /**
     * Method marshal
     * 
     * 
     * 
     * @param handler
     */
    public void marshal(org.xml.sax.ContentHandler handler)
        throws java.io.IOException, org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, handler);
    } //-- void marshal(org.xml.sax.ContentHandler) 

    /**
     * Method removeAllC_cloneItem
     * 
     */
    public void removeAllC_cloneItem()
    {
        _items.removeAllElements();
    } //-- void removeAllC_cloneItem() 

    /**
     * Method removeC_cloneItem
     * 
     * 
     * 
     * @param index
     * @return C_cloneItem
     */
    public gov.nih.nci.hl7.clone.meta.impl.C_cloneItem removeC_cloneItem(int index)
    {
        java.lang.Object obj = _items.elementAt(index);
        _items.removeElementAt(index);
        return (gov.nih.nci.hl7.clone.meta.impl.C_cloneItem) obj;
    } //-- gov.nih.nci.hl7.clone.meta.impl.C_cloneItem removeC_cloneItem(int) 

    /**
     * Method setC_cloneItem
     * 
     * 
     * 
     * @param index
     * @param vC_cloneItem
     */
    public void setC_cloneItem(int index, gov.nih.nci.hl7.clone.meta.impl.C_cloneItem vC_cloneItem)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _items.size())) {
            throw new IndexOutOfBoundsException("setC_cloneItem: Index value '"+index+"' not in range [0.."+_items.size()+ "]");
        }
        _items.setElementAt(vC_cloneItem, index);
    } //-- void setC_cloneItem(int, gov.nih.nci.hl7.clone.meta.impl.C_cloneItem) 

    /**
     * Method setC_cloneItem
     * 
     * 
     * 
     * @param c_cloneItemArray
     */
    public void setC_cloneItem(gov.nih.nci.hl7.clone.meta.impl.C_cloneItem[] c_cloneItemArray)
    {
        //-- copy array
        _items.removeAllElements();
        for (int i = 0; i < c_cloneItemArray.length; i++) {
            _items.addElement(c_cloneItemArray[i]);
        }
    } //-- void setC_cloneItem(gov.nih.nci.hl7.clone.meta.impl.C_cloneItem) 

    /**
     * Sets the value of field 'cardinality'.
     * 
     * @param cardinality the value of field 'cardinality'.
     */
    public void setCardinality(java.lang.String cardinality)
    {
        this._cardinality = cardinality;
    } //-- void setCardinality(java.lang.String) 

    /**
     * Sets the value of field 'choiceGroupName'.
     * 
     * @param choiceGroupName the value of field 'choiceGroupName'.
     */
    public void setChoiceGroupName(java.lang.String choiceGroupName)
    {
        this._choiceGroupName = choiceGroupName;
    } //-- void setChoiceGroupName(java.lang.String) 

    /**
     * Sets the value of field 'clonename'.
     * 
     * @param clonename the value of field 'clonename'.
     */
    public void setClonename(java.lang.String clonename)
    {
        this._clonename = clonename;
    } //-- void setClonename(java.lang.String) 

    /**
     * Sets the value of field 'cmetID'.
     * 
     * @param cmetID the value of field 'cmetID'.
     */
    public void setCmetID(java.lang.String cmetID)
    {
        this._cmetID = cmetID;
    } //-- void setCmetID(java.lang.String) 

    /**
     * Sets the value of field 'isReference'.
     * 
     * @param isReference the value of field 'isReference'.
     */
    public void setIsReference(boolean isReference)
    {
        this._isReference = isReference;
        this._has_isReference = true;
    } //-- void setIsReference(boolean) 

    /**
     * Sets the value of field 'multipleSequenceNumber'.
     * 
     * @param multipleSequenceNumber the value of field
     * 'multipleSequenceNumber'.
     */
    public void setMultipleSequenceNumber(int multipleSequenceNumber)
    {
        this._multipleSequenceNumber = multipleSequenceNumber;
        this._has_multipleSequenceNumber = true;
    } //-- void setMultipleSequenceNumber(int) 

    /**
     * Sets the value of field 'referenceName'.
     * 
     * @param referenceName the value of field 'referenceName'.
     */
    public void setReferenceName(java.lang.String referenceName)
    {
        this._referenceName = referenceName;
    } //-- void setReferenceName(java.lang.String) 

    /**
     * Sets the value of field 'referenceUuid'.
     * 
     * @param referenceUuid the value of field 'referenceUuid'.
     */
    public void setReferenceUuid(java.lang.String referenceUuid)
    {
        this._referenceUuid = referenceUuid;
    } //-- void setReferenceUuid(java.lang.String) 

    /**
     * Sets the value of field 'rimSource'.
     * 
     * @param rimSource the value of field 'rimSource'.
     */
    public void setRimSource(java.lang.String rimSource)
    {
        this._rimSource = rimSource;
    } //-- void setRimSource(java.lang.String) 

    /**
     * Sets the value of field 'sortKey'.
     * 
     * @param sortKey the value of field 'sortKey'.
     */
    public void setSortKey(int sortKey)
    {
        this._sortKey = sortKey;
        this._has_sortKey = true;
    } //-- void setSortKey(int) 

    /**
     * Sets the value of field 'uuid'.
     * 
     * @param uuid the value of field 'uuid'.
     */
    public void setUuid(java.lang.String uuid)
    {
        this._uuid = uuid;
    } //-- void setUuid(java.lang.String) 

    /**
     * Method unmarshalC_clone
     * 
     * 
     * 
     * @param reader
     * @return C_clone
     */
    public static gov.nih.nci.hl7.clone.meta.impl.C_clone unmarshalC_clone(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (gov.nih.nci.hl7.clone.meta.impl.C_clone) Unmarshaller.unmarshal(gov.nih.nci.hl7.clone.meta.impl.C_clone.class, reader);
    } //-- gov.nih.nci.hl7.clone.meta.impl.C_clone unmarshalC_clone(java.io.Reader) 

    /**
     * Method validate
     * 
     */
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate() 

}
