/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: C_link.java,v 1.1 2006-10-03 17:38:42 marwahah Exp $
 */

package gov.nih.nci.hl7.map.impl;

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
 * Class C_link.
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 17:38:42 $
 */
public class C_link implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _uuid
     */
    private java.lang.String _uuid;

    /**
     * Field _c_linkpointerList
     */
    private java.util.Vector _c_linkpointerList;


      //----------------/
     //- Constructors -/
    //----------------/

    public C_link() 
     {
        super();
        _c_linkpointerList = new Vector();
    } //-- gov.nih.nci.hl7.map.impl.C_link()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addC_linkpointer
     * 
     * 
     * 
     * @param vC_linkpointer
     */
    public void addC_linkpointer(gov.nih.nci.hl7.map.impl.C_linkpointer vC_linkpointer)
        throws java.lang.IndexOutOfBoundsException
    {
        _c_linkpointerList.addElement(vC_linkpointer);
    } //-- void addC_linkpointer(gov.nih.nci.hl7.map.impl.C_linkpointer) 

    /**
     * Method addC_linkpointer
     * 
     * 
     * 
     * @param index
     * @param vC_linkpointer
     */
    public void addC_linkpointer(int index, gov.nih.nci.hl7.map.impl.C_linkpointer vC_linkpointer)
        throws java.lang.IndexOutOfBoundsException
    {
        _c_linkpointerList.insertElementAt(vC_linkpointer, index);
    } //-- void addC_linkpointer(int, gov.nih.nci.hl7.map.impl.C_linkpointer) 

    /**
     * Method enumerateC_linkpointer
     * 
     * 
     * 
     * @return Enumeration
     */
    public java.util.Enumeration enumerateC_linkpointer()
    {
        return _c_linkpointerList.elements();
    } //-- java.util.Enumeration enumerateC_linkpointer() 

    /**
     * Method getC_linkpointer
     * 
     * 
     * 
     * @param index
     * @return C_linkpointer
     */
    public gov.nih.nci.hl7.map.impl.C_linkpointer getC_linkpointer(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _c_linkpointerList.size())) {
            throw new IndexOutOfBoundsException("getC_linkpointer: Index value '"+index+"' not in range [0.."+_c_linkpointerList.size()+ "]");
        }
        
        return (gov.nih.nci.hl7.map.impl.C_linkpointer) _c_linkpointerList.elementAt(index);
    } //-- gov.nih.nci.hl7.map.impl.C_linkpointer getC_linkpointer(int) 

    /**
     * Method getC_linkpointer
     * 
     * 
     * 
     * @return C_linkpointer
     */
    public gov.nih.nci.hl7.map.impl.C_linkpointer[] getC_linkpointer()
    {
        int size = _c_linkpointerList.size();
        gov.nih.nci.hl7.map.impl.C_linkpointer[] mArray = new gov.nih.nci.hl7.map.impl.C_linkpointer[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (gov.nih.nci.hl7.map.impl.C_linkpointer) _c_linkpointerList.elementAt(index);
        }
        return mArray;
    } //-- gov.nih.nci.hl7.map.impl.C_linkpointer[] getC_linkpointer() 

    /**
     * Method getC_linkpointerCount
     * 
     * 
     * 
     * @return int
     */
    public int getC_linkpointerCount()
    {
        return _c_linkpointerList.size();
    } //-- int getC_linkpointerCount() 

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
     * Method removeAllC_linkpointer
     * 
     */
    public void removeAllC_linkpointer()
    {
        _c_linkpointerList.removeAllElements();
    } //-- void removeAllC_linkpointer() 

    /**
     * Method removeC_linkpointer
     * 
     * 
     * 
     * @param index
     * @return C_linkpointer
     */
    public gov.nih.nci.hl7.map.impl.C_linkpointer removeC_linkpointer(int index)
    {
        java.lang.Object obj = _c_linkpointerList.elementAt(index);
        _c_linkpointerList.removeElementAt(index);
        return (gov.nih.nci.hl7.map.impl.C_linkpointer) obj;
    } //-- gov.nih.nci.hl7.map.impl.C_linkpointer removeC_linkpointer(int) 

    /**
     * Method setC_linkpointer
     * 
     * 
     * 
     * @param index
     * @param vC_linkpointer
     */
    public void setC_linkpointer(int index, gov.nih.nci.hl7.map.impl.C_linkpointer vC_linkpointer)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _c_linkpointerList.size())) {
            throw new IndexOutOfBoundsException("setC_linkpointer: Index value '"+index+"' not in range [0.."+_c_linkpointerList.size()+ "]");
        }
        _c_linkpointerList.setElementAt(vC_linkpointer, index);
    } //-- void setC_linkpointer(int, gov.nih.nci.hl7.map.impl.C_linkpointer) 

    /**
     * Method setC_linkpointer
     * 
     * 
     * 
     * @param c_linkpointerArray
     */
    public void setC_linkpointer(gov.nih.nci.hl7.map.impl.C_linkpointer[] c_linkpointerArray)
    {
        //-- copy array
        _c_linkpointerList.removeAllElements();
        for (int i = 0; i < c_linkpointerArray.length; i++) {
            _c_linkpointerList.addElement(c_linkpointerArray[i]);
        }
    } //-- void setC_linkpointer(gov.nih.nci.hl7.map.impl.C_linkpointer) 

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
     * Method unmarshalC_link
     * 
     * 
     * 
     * @param reader
     * @return C_link
     */
    public static gov.nih.nci.hl7.map.impl.C_link unmarshalC_link(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (gov.nih.nci.hl7.map.impl.C_link) Unmarshaller.unmarshal(gov.nih.nci.hl7.map.impl.C_link.class, reader);
    } //-- gov.nih.nci.hl7.map.impl.C_link unmarshalC_link(java.io.Reader) 

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
