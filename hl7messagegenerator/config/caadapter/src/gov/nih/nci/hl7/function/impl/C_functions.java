/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: C_functions.java,v 1.1 2006-10-03 17:38:26 marwahah Exp $
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
 * Class C_functions.
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 17:38:26 $
 */
public class C_functions implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _c_groupList
     */
    private java.util.Vector _c_groupList;


      //----------------/
     //- Constructors -/
    //----------------/

    public C_functions() 
     {
        super();
        _c_groupList = new Vector();
    } //-- gov.nih.nci.hl7.function.impl.C_functions()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addC_group
     * 
     * 
     * 
     * @param vC_group
     */
    public void addC_group(gov.nih.nci.hl7.function.impl.C_group vC_group)
        throws java.lang.IndexOutOfBoundsException
    {
        _c_groupList.addElement(vC_group);
    } //-- void addC_group(gov.nih.nci.hl7.function.impl.C_group) 

    /**
     * Method addC_group
     * 
     * 
     * 
     * @param index
     * @param vC_group
     */
    public void addC_group(int index, gov.nih.nci.hl7.function.impl.C_group vC_group)
        throws java.lang.IndexOutOfBoundsException
    {
        _c_groupList.insertElementAt(vC_group, index);
    } //-- void addC_group(int, gov.nih.nci.hl7.function.impl.C_group) 

    /**
     * Method enumerateC_group
     * 
     * 
     * 
     * @return Enumeration
     */
    public java.util.Enumeration enumerateC_group()
    {
        return _c_groupList.elements();
    } //-- java.util.Enumeration enumerateC_group() 

    /**
     * Method getC_group
     * 
     * 
     * 
     * @param index
     * @return C_group
     */
    public gov.nih.nci.hl7.function.impl.C_group getC_group(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _c_groupList.size())) {
            throw new IndexOutOfBoundsException("getC_group: Index value '"+index+"' not in range [0.."+_c_groupList.size()+ "]");
        }
        
        return (gov.nih.nci.hl7.function.impl.C_group) _c_groupList.elementAt(index);
    } //-- gov.nih.nci.hl7.function.impl.C_group getC_group(int) 

    /**
     * Method getC_group
     * 
     * 
     * 
     * @return C_group
     */
    public gov.nih.nci.hl7.function.impl.C_group[] getC_group()
    {
        int size = _c_groupList.size();
        gov.nih.nci.hl7.function.impl.C_group[] mArray = new gov.nih.nci.hl7.function.impl.C_group[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (gov.nih.nci.hl7.function.impl.C_group) _c_groupList.elementAt(index);
        }
        return mArray;
    } //-- gov.nih.nci.hl7.function.impl.C_group[] getC_group() 

    /**
     * Method getC_groupCount
     * 
     * 
     * 
     * @return int
     */
    public int getC_groupCount()
    {
        return _c_groupList.size();
    } //-- int getC_groupCount() 

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
     * Method removeAllC_group
     * 
     */
    public void removeAllC_group()
    {
        _c_groupList.removeAllElements();
    } //-- void removeAllC_group() 

    /**
     * Method removeC_group
     * 
     * 
     * 
     * @param index
     * @return C_group
     */
    public gov.nih.nci.hl7.function.impl.C_group removeC_group(int index)
    {
        java.lang.Object obj = _c_groupList.elementAt(index);
        _c_groupList.removeElementAt(index);
        return (gov.nih.nci.hl7.function.impl.C_group) obj;
    } //-- gov.nih.nci.hl7.function.impl.C_group removeC_group(int) 

    /**
     * Method setC_group
     * 
     * 
     * 
     * @param index
     * @param vC_group
     */
    public void setC_group(int index, gov.nih.nci.hl7.function.impl.C_group vC_group)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _c_groupList.size())) {
            throw new IndexOutOfBoundsException("setC_group: Index value '"+index+"' not in range [0.."+_c_groupList.size()+ "]");
        }
        _c_groupList.setElementAt(vC_group, index);
    } //-- void setC_group(int, gov.nih.nci.hl7.function.impl.C_group) 

    /**
     * Method setC_group
     * 
     * 
     * 
     * @param c_groupArray
     */
    public void setC_group(gov.nih.nci.hl7.function.impl.C_group[] c_groupArray)
    {
        //-- copy array
        _c_groupList.removeAllElements();
        for (int i = 0; i < c_groupArray.length; i++) {
            _c_groupList.addElement(c_groupArray[i]);
        }
    } //-- void setC_group(gov.nih.nci.hl7.function.impl.C_group) 

    /**
     * Method unmarshalC_functions
     * 
     * 
     * 
     * @param reader
     * @return C_functions
     */
    public static gov.nih.nci.hl7.function.impl.C_functions unmarshalC_functions(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (gov.nih.nci.hl7.function.impl.C_functions) Unmarshaller.unmarshal(gov.nih.nci.hl7.function.impl.C_functions.class, reader);
    } //-- gov.nih.nci.hl7.function.impl.C_functions unmarshalC_functions(java.io.Reader) 

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
