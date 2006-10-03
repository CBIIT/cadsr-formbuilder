/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: C_components.java,v 1.1 2006-10-03 17:38:42 marwahah Exp $
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
 * Class C_components.
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 17:38:42 $
 */
public class C_components implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _c_componentList
     */
    private java.util.Vector _c_componentList;


      //----------------/
     //- Constructors -/
    //----------------/

    public C_components() 
     {
        super();
        _c_componentList = new Vector();
    } //-- gov.nih.nci.hl7.map.impl.C_components()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addC_component
     * 
     * 
     * 
     * @param vC_component
     */
    public void addC_component(gov.nih.nci.hl7.map.impl.C_component vC_component)
        throws java.lang.IndexOutOfBoundsException
    {
        _c_componentList.addElement(vC_component);
    } //-- void addC_component(gov.nih.nci.hl7.map.impl.C_component) 

    /**
     * Method addC_component
     * 
     * 
     * 
     * @param index
     * @param vC_component
     */
    public void addC_component(int index, gov.nih.nci.hl7.map.impl.C_component vC_component)
        throws java.lang.IndexOutOfBoundsException
    {
        _c_componentList.insertElementAt(vC_component, index);
    } //-- void addC_component(int, gov.nih.nci.hl7.map.impl.C_component) 

    /**
     * Method enumerateC_component
     * 
     * 
     * 
     * @return Enumeration
     */
    public java.util.Enumeration enumerateC_component()
    {
        return _c_componentList.elements();
    } //-- java.util.Enumeration enumerateC_component() 

    /**
     * Method getC_component
     * 
     * 
     * 
     * @param index
     * @return C_component
     */
    public gov.nih.nci.hl7.map.impl.C_component getC_component(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _c_componentList.size())) {
            throw new IndexOutOfBoundsException("getC_component: Index value '"+index+"' not in range [0.."+_c_componentList.size()+ "]");
        }
        
        return (gov.nih.nci.hl7.map.impl.C_component) _c_componentList.elementAt(index);
    } //-- gov.nih.nci.hl7.map.impl.C_component getC_component(int) 

    /**
     * Method getC_component
     * 
     * 
     * 
     * @return C_component
     */
    public gov.nih.nci.hl7.map.impl.C_component[] getC_component()
    {
        int size = _c_componentList.size();
        gov.nih.nci.hl7.map.impl.C_component[] mArray = new gov.nih.nci.hl7.map.impl.C_component[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (gov.nih.nci.hl7.map.impl.C_component) _c_componentList.elementAt(index);
        }
        return mArray;
    } //-- gov.nih.nci.hl7.map.impl.C_component[] getC_component() 

    /**
     * Method getC_componentCount
     * 
     * 
     * 
     * @return int
     */
    public int getC_componentCount()
    {
        return _c_componentList.size();
    } //-- int getC_componentCount() 

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
     * Method removeAllC_component
     * 
     */
    public void removeAllC_component()
    {
        _c_componentList.removeAllElements();
    } //-- void removeAllC_component() 

    /**
     * Method removeC_component
     * 
     * 
     * 
     * @param index
     * @return C_component
     */
    public gov.nih.nci.hl7.map.impl.C_component removeC_component(int index)
    {
        java.lang.Object obj = _c_componentList.elementAt(index);
        _c_componentList.removeElementAt(index);
        return (gov.nih.nci.hl7.map.impl.C_component) obj;
    } //-- gov.nih.nci.hl7.map.impl.C_component removeC_component(int) 

    /**
     * Method setC_component
     * 
     * 
     * 
     * @param index
     * @param vC_component
     */
    public void setC_component(int index, gov.nih.nci.hl7.map.impl.C_component vC_component)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _c_componentList.size())) {
            throw new IndexOutOfBoundsException("setC_component: Index value '"+index+"' not in range [0.."+_c_componentList.size()+ "]");
        }
        _c_componentList.setElementAt(vC_component, index);
    } //-- void setC_component(int, gov.nih.nci.hl7.map.impl.C_component) 

    /**
     * Method setC_component
     * 
     * 
     * 
     * @param c_componentArray
     */
    public void setC_component(gov.nih.nci.hl7.map.impl.C_component[] c_componentArray)
    {
        //-- copy array
        _c_componentList.removeAllElements();
        for (int i = 0; i < c_componentArray.length; i++) {
            _c_componentList.addElement(c_componentArray[i]);
        }
    } //-- void setC_component(gov.nih.nci.hl7.map.impl.C_component) 

    /**
     * Method unmarshalC_components
     * 
     * 
     * 
     * @param reader
     * @return C_components
     */
    public static gov.nih.nci.hl7.map.impl.C_components unmarshalC_components(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (gov.nih.nci.hl7.map.impl.C_components) Unmarshaller.unmarshal(gov.nih.nci.hl7.map.impl.C_components.class, reader);
    } //-- gov.nih.nci.hl7.map.impl.C_components unmarshalC_components(java.io.Reader) 

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
