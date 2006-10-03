/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: C_segment.java,v 1.1 2006-10-03 17:38:25 marwahah Exp $
 */

package gov.nih.nci.hl7.csv.meta.impl;

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
 * Class C_segment.
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 17:38:25 $
 */
public class C_segment implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _name
     */
    private java.lang.String _name;

    /**
     * Field _uuid
     */
    private java.lang.String _uuid;

    /**
     * Field _items
     */
    private java.util.Vector _items;


      //----------------/
     //- Constructors -/
    //----------------/

    public C_segment() 
     {
        super();
        _items = new Vector();
    } //-- gov.nih.nci.hl7.csv.meta.impl.C_segment()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addC_segmentItem
     * 
     * 
     * 
     * @param vC_segmentItem
     */
    public void addC_segmentItem(gov.nih.nci.hl7.csv.meta.impl.C_segmentItem vC_segmentItem)
        throws java.lang.IndexOutOfBoundsException
    {
        _items.addElement(vC_segmentItem);
    } //-- void addC_segmentItem(gov.nih.nci.hl7.csv.meta.impl.C_segmentItem) 

    /**
     * Method addC_segmentItem
     * 
     * 
     * 
     * @param index
     * @param vC_segmentItem
     */
    public void addC_segmentItem(int index, gov.nih.nci.hl7.csv.meta.impl.C_segmentItem vC_segmentItem)
        throws java.lang.IndexOutOfBoundsException
    {
        _items.insertElementAt(vC_segmentItem, index);
    } //-- void addC_segmentItem(int, gov.nih.nci.hl7.csv.meta.impl.C_segmentItem) 

    /**
     * Method enumerateC_segmentItem
     * 
     * 
     * 
     * @return Enumeration
     */
    public java.util.Enumeration enumerateC_segmentItem()
    {
        return _items.elements();
    } //-- java.util.Enumeration enumerateC_segmentItem() 

    /**
     * Method getC_segmentItem
     * 
     * 
     * 
     * @param index
     * @return C_segmentItem
     */
    public gov.nih.nci.hl7.csv.meta.impl.C_segmentItem getC_segmentItem(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _items.size())) {
            throw new IndexOutOfBoundsException("getC_segmentItem: Index value '"+index+"' not in range [0.."+_items.size()+ "]");
        }
        
        return (gov.nih.nci.hl7.csv.meta.impl.C_segmentItem) _items.elementAt(index);
    } //-- gov.nih.nci.hl7.csv.meta.impl.C_segmentItem getC_segmentItem(int) 

    /**
     * Method getC_segmentItem
     * 
     * 
     * 
     * @return C_segmentItem
     */
    public gov.nih.nci.hl7.csv.meta.impl.C_segmentItem[] getC_segmentItem()
    {
        int size = _items.size();
        gov.nih.nci.hl7.csv.meta.impl.C_segmentItem[] mArray = new gov.nih.nci.hl7.csv.meta.impl.C_segmentItem[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (gov.nih.nci.hl7.csv.meta.impl.C_segmentItem) _items.elementAt(index);
        }
        return mArray;
    } //-- gov.nih.nci.hl7.csv.meta.impl.C_segmentItem[] getC_segmentItem() 

    /**
     * Method getC_segmentItemCount
     * 
     * 
     * 
     * @return int
     */
    public int getC_segmentItemCount()
    {
        return _items.size();
    } //-- int getC_segmentItemCount() 

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
     * Method removeAllC_segmentItem
     * 
     */
    public void removeAllC_segmentItem()
    {
        _items.removeAllElements();
    } //-- void removeAllC_segmentItem() 

    /**
     * Method removeC_segmentItem
     * 
     * 
     * 
     * @param index
     * @return C_segmentItem
     */
    public gov.nih.nci.hl7.csv.meta.impl.C_segmentItem removeC_segmentItem(int index)
    {
        java.lang.Object obj = _items.elementAt(index);
        _items.removeElementAt(index);
        return (gov.nih.nci.hl7.csv.meta.impl.C_segmentItem) obj;
    } //-- gov.nih.nci.hl7.csv.meta.impl.C_segmentItem removeC_segmentItem(int) 

    /**
     * Method setC_segmentItem
     * 
     * 
     * 
     * @param index
     * @param vC_segmentItem
     */
    public void setC_segmentItem(int index, gov.nih.nci.hl7.csv.meta.impl.C_segmentItem vC_segmentItem)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _items.size())) {
            throw new IndexOutOfBoundsException("setC_segmentItem: Index value '"+index+"' not in range [0.."+_items.size()+ "]");
        }
        _items.setElementAt(vC_segmentItem, index);
    } //-- void setC_segmentItem(int, gov.nih.nci.hl7.csv.meta.impl.C_segmentItem) 

    /**
     * Method setC_segmentItem
     * 
     * 
     * 
     * @param c_segmentItemArray
     */
    public void setC_segmentItem(gov.nih.nci.hl7.csv.meta.impl.C_segmentItem[] c_segmentItemArray)
    {
        //-- copy array
        _items.removeAllElements();
        for (int i = 0; i < c_segmentItemArray.length; i++) {
            _items.addElement(c_segmentItemArray[i]);
        }
    } //-- void setC_segmentItem(gov.nih.nci.hl7.csv.meta.impl.C_segmentItem) 

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
     * Sets the value of field 'uuid'.
     * 
     * @param uuid the value of field 'uuid'.
     */
    public void setUuid(java.lang.String uuid)
    {
        this._uuid = uuid;
    } //-- void setUuid(java.lang.String) 

    /**
     * Method unmarshalC_segment
     * 
     * 
     * 
     * @param reader
     * @return C_segment
     */
    public static gov.nih.nci.hl7.csv.meta.impl.C_segment unmarshalC_segment(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (gov.nih.nci.hl7.csv.meta.impl.C_segment) Unmarshaller.unmarshal(gov.nih.nci.hl7.csv.meta.impl.C_segment.class, reader);
    } //-- gov.nih.nci.hl7.csv.meta.impl.C_segment unmarshalC_segment(java.io.Reader) 

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
