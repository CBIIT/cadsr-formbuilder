/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: C_group.java,v 1.1 2006-10-03 17:38:26 marwahah Exp $
 */

package gov.nih.nci.hl7.function.impl;

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
 * Class C_group.
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 17:38:26 $
 */
public class C_group implements java.io.Serializable {


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
     * Field _c_functionList
     */
    private java.util.Vector _c_functionList;


      //----------------/
     //- Constructors -/
    //----------------/

    public C_group() 
     {
        super();
        _c_functionList = new Vector();
    } //-- gov.nih.nci.hl7.function.impl.C_group()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addC_function
     * 
     * 
     * 
     * @param vC_function
     */
    public void addC_function(gov.nih.nci.hl7.function.impl.C_function vC_function)
        throws java.lang.IndexOutOfBoundsException
    {
        _c_functionList.addElement(vC_function);
    } //-- void addC_function(gov.nih.nci.hl7.function.impl.C_function) 

    /**
     * Method addC_function
     * 
     * 
     * 
     * @param index
     * @param vC_function
     */
    public void addC_function(int index, gov.nih.nci.hl7.function.impl.C_function vC_function)
        throws java.lang.IndexOutOfBoundsException
    {
        _c_functionList.insertElementAt(vC_function, index);
    } //-- void addC_function(int, gov.nih.nci.hl7.function.impl.C_function) 

    /**
     * Method enumerateC_function
     * 
     * 
     * 
     * @return Enumeration
     */
    public java.util.Enumeration enumerateC_function()
    {
        return _c_functionList.elements();
    } //-- java.util.Enumeration enumerateC_function() 

    /**
     * Method getC_function
     * 
     * 
     * 
     * @param index
     * @return C_function
     */
    public gov.nih.nci.hl7.function.impl.C_function getC_function(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _c_functionList.size())) {
            throw new IndexOutOfBoundsException("getC_function: Index value '"+index+"' not in range [0.."+_c_functionList.size()+ "]");
        }
        
        return (gov.nih.nci.hl7.function.impl.C_function) _c_functionList.elementAt(index);
    } //-- gov.nih.nci.hl7.function.impl.C_function getC_function(int) 

    /**
     * Method getC_function
     * 
     * 
     * 
     * @return C_function
     */
    public gov.nih.nci.hl7.function.impl.C_function[] getC_function()
    {
        int size = _c_functionList.size();
        gov.nih.nci.hl7.function.impl.C_function[] mArray = new gov.nih.nci.hl7.function.impl.C_function[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (gov.nih.nci.hl7.function.impl.C_function) _c_functionList.elementAt(index);
        }
        return mArray;
    } //-- gov.nih.nci.hl7.function.impl.C_function[] getC_function() 

    /**
     * Method getC_functionCount
     * 
     * 
     * 
     * @return int
     */
    public int getC_functionCount()
    {
        return _c_functionList.size();
    } //-- int getC_functionCount() 

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
     * Method removeAllC_function
     * 
     */
    public void removeAllC_function()
    {
        _c_functionList.removeAllElements();
    } //-- void removeAllC_function() 

    /**
     * Method removeC_function
     * 
     * 
     * 
     * @param index
     * @return C_function
     */
    public gov.nih.nci.hl7.function.impl.C_function removeC_function(int index)
    {
        java.lang.Object obj = _c_functionList.elementAt(index);
        _c_functionList.removeElementAt(index);
        return (gov.nih.nci.hl7.function.impl.C_function) obj;
    } //-- gov.nih.nci.hl7.function.impl.C_function removeC_function(int) 

    /**
     * Method setC_function
     * 
     * 
     * 
     * @param index
     * @param vC_function
     */
    public void setC_function(int index, gov.nih.nci.hl7.function.impl.C_function vC_function)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _c_functionList.size())) {
            throw new IndexOutOfBoundsException("setC_function: Index value '"+index+"' not in range [0.."+_c_functionList.size()+ "]");
        }
        _c_functionList.setElementAt(vC_function, index);
    } //-- void setC_function(int, gov.nih.nci.hl7.function.impl.C_function) 

    /**
     * Method setC_function
     * 
     * 
     * 
     * @param c_functionArray
     */
    public void setC_function(gov.nih.nci.hl7.function.impl.C_function[] c_functionArray)
    {
        //-- copy array
        _c_functionList.removeAllElements();
        for (int i = 0; i < c_functionArray.length; i++) {
            _c_functionList.addElement(c_functionArray[i]);
        }
    } //-- void setC_function(gov.nih.nci.hl7.function.impl.C_function) 

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
     * Method unmarshalC_group
     * 
     * 
     * 
     * @param reader
     * @return C_group
     */
    public static gov.nih.nci.hl7.function.impl.C_group unmarshalC_group(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (gov.nih.nci.hl7.function.impl.C_group) Unmarshaller.unmarshal(gov.nih.nci.hl7.function.impl.C_group.class, reader);
    } //-- gov.nih.nci.hl7.function.impl.C_group unmarshalC_group(java.io.Reader) 

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
