/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: C_attribute.java,v 1.1 2006-10-03 17:38:25 marwahah Exp $
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
 * Class C_attribute.
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 17:38:25 $
 */
public class C_attribute implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _uuid
     */
    private java.lang.String _uuid;

    /**
     * Field _name
     */
    private java.lang.String _name;

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
     * Field _datatype
     */
    private java.lang.String _datatype;

    /**
     * Field _isMandatory
     */
    private boolean _isMandatory;

    /**
     * keeps track of state for field: _isMandatory
     */
    private boolean _has_isMandatory;

    /**
     * Field _conformance
     */
    private java.lang.String _conformance;

    /**
     * Field _codingStrength
     */
    private java.lang.String _codingStrength;

    /**
     * Field _domainName
     */
    private java.lang.String _domainName;

    /**
     * Field _hl7Default
     */
    private java.lang.String _hl7Default;

    /**
     * Field _rimSource
     */
    private java.lang.String _rimSource;

    /**
     * Field _subClass
     */
    private java.lang.String _subClass;

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

    public C_attribute() 
     {
        super();
        _items = new Vector();
    } //-- gov.nih.nci.hl7.clone.meta.impl.C_attribute()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addC_attributeItem
     * 
     * 
     * 
     * @param vC_attributeItem
     */
    public void addC_attributeItem(gov.nih.nci.hl7.clone.meta.impl.C_attributeItem vC_attributeItem)
        throws java.lang.IndexOutOfBoundsException
    {
        _items.addElement(vC_attributeItem);
    } //-- void addC_attributeItem(gov.nih.nci.hl7.clone.meta.impl.C_attributeItem) 

    /**
     * Method addC_attributeItem
     * 
     * 
     * 
     * @param index
     * @param vC_attributeItem
     */
    public void addC_attributeItem(int index, gov.nih.nci.hl7.clone.meta.impl.C_attributeItem vC_attributeItem)
        throws java.lang.IndexOutOfBoundsException
    {
        _items.insertElementAt(vC_attributeItem, index);
    } //-- void addC_attributeItem(int, gov.nih.nci.hl7.clone.meta.impl.C_attributeItem) 

    /**
     * Method deleteIsMandatory
     * 
     */
    public void deleteIsMandatory()
    {
        this._has_isMandatory= false;
    } //-- void deleteIsMandatory() 

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
     * Method enumerateC_attributeItem
     * 
     * 
     * 
     * @return Enumeration
     */
    public java.util.Enumeration enumerateC_attributeItem()
    {
        return _items.elements();
    } //-- java.util.Enumeration enumerateC_attributeItem() 

    /**
     * Method getC_attributeItem
     * 
     * 
     * 
     * @param index
     * @return C_attributeItem
     */
    public gov.nih.nci.hl7.clone.meta.impl.C_attributeItem getC_attributeItem(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _items.size())) {
            throw new IndexOutOfBoundsException("getC_attributeItem: Index value '"+index+"' not in range [0.."+_items.size()+ "]");
        }
        
        return (gov.nih.nci.hl7.clone.meta.impl.C_attributeItem) _items.elementAt(index);
    } //-- gov.nih.nci.hl7.clone.meta.impl.C_attributeItem getC_attributeItem(int) 

    /**
     * Method getC_attributeItem
     * 
     * 
     * 
     * @return C_attributeItem
     */
    public gov.nih.nci.hl7.clone.meta.impl.C_attributeItem[] getC_attributeItem()
    {
        int size = _items.size();
        gov.nih.nci.hl7.clone.meta.impl.C_attributeItem[] mArray = new gov.nih.nci.hl7.clone.meta.impl.C_attributeItem[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (gov.nih.nci.hl7.clone.meta.impl.C_attributeItem) _items.elementAt(index);
        }
        return mArray;
    } //-- gov.nih.nci.hl7.clone.meta.impl.C_attributeItem[] getC_attributeItem() 

    /**
     * Method getC_attributeItemCount
     * 
     * 
     * 
     * @return int
     */
    public int getC_attributeItemCount()
    {
        return _items.size();
    } //-- int getC_attributeItemCount() 

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
     * Returns the value of field 'codingStrength'.
     * 
     * @return String
     * @return the value of field 'codingStrength'.
     */
    public java.lang.String getCodingStrength()
    {
        return this._codingStrength;
    } //-- java.lang.String getCodingStrength() 

    /**
     * Returns the value of field 'conformance'.
     * 
     * @return String
     * @return the value of field 'conformance'.
     */
    public java.lang.String getConformance()
    {
        return this._conformance;
    } //-- java.lang.String getConformance() 

    /**
     * Returns the value of field 'datatype'.
     * 
     * @return String
     * @return the value of field 'datatype'.
     */
    public java.lang.String getDatatype()
    {
        return this._datatype;
    } //-- java.lang.String getDatatype() 

    /**
     * Returns the value of field 'domainName'.
     * 
     * @return String
     * @return the value of field 'domainName'.
     */
    public java.lang.String getDomainName()
    {
        return this._domainName;
    } //-- java.lang.String getDomainName() 

    /**
     * Returns the value of field 'hl7Default'.
     * 
     * @return String
     * @return the value of field 'hl7Default'.
     */
    public java.lang.String getHl7Default()
    {
        return this._hl7Default;
    } //-- java.lang.String getHl7Default() 

    /**
     * Returns the value of field 'isMandatory'.
     * 
     * @return boolean
     * @return the value of field 'isMandatory'.
     */
    public boolean getIsMandatory()
    {
        return this._isMandatory;
    } //-- boolean getIsMandatory() 

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
     * Returns the value of field 'name'.
     * 
     * @return String
     * @return the value of field 'name'.
     */
    public java.lang.String getName()
    {
        return this._name;
    } //-- java.lang.String getName() 

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
     * Returns the value of field 'subClass'.
     * 
     * @return String
     * @return the value of field 'subClass'.
     */
    public java.lang.String getSubClass()
    {
        return this._subClass;
    } //-- java.lang.String getSubClass() 

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
     * Method hasIsMandatory
     * 
     * 
     * 
     * @return boolean
     */
    public boolean hasIsMandatory()
    {
        return this._has_isMandatory;
    } //-- boolean hasIsMandatory() 

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
     * Method removeAllC_attributeItem
     * 
     */
    public void removeAllC_attributeItem()
    {
        _items.removeAllElements();
    } //-- void removeAllC_attributeItem() 

    /**
     * Method removeC_attributeItem
     * 
     * 
     * 
     * @param index
     * @return C_attributeItem
     */
    public gov.nih.nci.hl7.clone.meta.impl.C_attributeItem removeC_attributeItem(int index)
    {
        java.lang.Object obj = _items.elementAt(index);
        _items.removeElementAt(index);
        return (gov.nih.nci.hl7.clone.meta.impl.C_attributeItem) obj;
    } //-- gov.nih.nci.hl7.clone.meta.impl.C_attributeItem removeC_attributeItem(int) 

    /**
     * Method setC_attributeItem
     * 
     * 
     * 
     * @param index
     * @param vC_attributeItem
     */
    public void setC_attributeItem(int index, gov.nih.nci.hl7.clone.meta.impl.C_attributeItem vC_attributeItem)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _items.size())) {
            throw new IndexOutOfBoundsException("setC_attributeItem: Index value '"+index+"' not in range [0.."+_items.size()+ "]");
        }
        _items.setElementAt(vC_attributeItem, index);
    } //-- void setC_attributeItem(int, gov.nih.nci.hl7.clone.meta.impl.C_attributeItem) 

    /**
     * Method setC_attributeItem
     * 
     * 
     * 
     * @param c_attributeItemArray
     */
    public void setC_attributeItem(gov.nih.nci.hl7.clone.meta.impl.C_attributeItem[] c_attributeItemArray)
    {
        //-- copy array
        _items.removeAllElements();
        for (int i = 0; i < c_attributeItemArray.length; i++) {
            _items.addElement(c_attributeItemArray[i]);
        }
    } //-- void setC_attributeItem(gov.nih.nci.hl7.clone.meta.impl.C_attributeItem) 

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
     * Sets the value of field 'codingStrength'.
     * 
     * @param codingStrength the value of field 'codingStrength'.
     */
    public void setCodingStrength(java.lang.String codingStrength)
    {
        this._codingStrength = codingStrength;
    } //-- void setCodingStrength(java.lang.String) 

    /**
     * Sets the value of field 'conformance'.
     * 
     * @param conformance the value of field 'conformance'.
     */
    public void setConformance(java.lang.String conformance)
    {
        this._conformance = conformance;
    } //-- void setConformance(java.lang.String) 

    /**
     * Sets the value of field 'datatype'.
     * 
     * @param datatype the value of field 'datatype'.
     */
    public void setDatatype(java.lang.String datatype)
    {
        this._datatype = datatype;
    } //-- void setDatatype(java.lang.String) 

    /**
     * Sets the value of field 'domainName'.
     * 
     * @param domainName the value of field 'domainName'.
     */
    public void setDomainName(java.lang.String domainName)
    {
        this._domainName = domainName;
    } //-- void setDomainName(java.lang.String) 

    /**
     * Sets the value of field 'hl7Default'.
     * 
     * @param hl7Default the value of field 'hl7Default'.
     */
    public void setHl7Default(java.lang.String hl7Default)
    {
        this._hl7Default = hl7Default;
    } //-- void setHl7Default(java.lang.String) 

    /**
     * Sets the value of field 'isMandatory'.
     * 
     * @param isMandatory the value of field 'isMandatory'.
     */
    public void setIsMandatory(boolean isMandatory)
    {
        this._isMandatory = isMandatory;
        this._has_isMandatory = true;
    } //-- void setIsMandatory(boolean) 

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
     * Sets the value of field 'name'.
     * 
     * @param name the value of field 'name'.
     */
    public void setName(java.lang.String name)
    {
        this._name = name;
    } //-- void setName(java.lang.String) 

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
     * Sets the value of field 'subClass'.
     * 
     * @param subClass the value of field 'subClass'.
     */
    public void setSubClass(java.lang.String subClass)
    {
        this._subClass = subClass;
    } //-- void setSubClass(java.lang.String) 

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
     * Method unmarshalC_attribute
     * 
     * 
     * 
     * @param reader
     * @return C_attribute
     */
    public static gov.nih.nci.hl7.clone.meta.impl.C_attribute unmarshalC_attribute(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (gov.nih.nci.hl7.clone.meta.impl.C_attribute) Unmarshaller.unmarshal(gov.nih.nci.hl7.clone.meta.impl.C_attribute.class, reader);
    } //-- gov.nih.nci.hl7.clone.meta.impl.C_attribute unmarshalC_attribute(java.io.Reader) 

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
