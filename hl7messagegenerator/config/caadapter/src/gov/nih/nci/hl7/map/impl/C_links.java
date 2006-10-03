/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: C_links.java,v 1.1 2006-10-03 17:38:42 marwahah Exp $
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
 * Class C_links.
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 17:38:42 $
 */
public class C_links implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _c_linkList
     */
    private java.util.Vector _c_linkList;


      //----------------/
     //- Constructors -/
    //----------------/

    public C_links() 
     {
        super();
        _c_linkList = new Vector();
    } //-- gov.nih.nci.hl7.map.impl.C_links()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addC_link
     * 
     * 
     * 
     * @param vC_link
     */
    public void addC_link(gov.nih.nci.hl7.map.impl.C_link vC_link)
        throws java.lang.IndexOutOfBoundsException
    {
        _c_linkList.addElement(vC_link);
    } //-- void addC_link(gov.nih.nci.hl7.map.impl.C_link) 

    /**
     * Method addC_link
     * 
     * 
     * 
     * @param index
     * @param vC_link
     */
    public void addC_link(int index, gov.nih.nci.hl7.map.impl.C_link vC_link)
        throws java.lang.IndexOutOfBoundsException
    {
        _c_linkList.insertElementAt(vC_link, index);
    } //-- void addC_link(int, gov.nih.nci.hl7.map.impl.C_link) 

    /**
     * Method enumerateC_link
     * 
     * 
     * 
     * @return Enumeration
     */
    public java.util.Enumeration enumerateC_link()
    {
        return _c_linkList.elements();
    } //-- java.util.Enumeration enumerateC_link() 

    /**
     * Method getC_link
     * 
     * 
     * 
     * @param index
     * @return C_link
     */
    public gov.nih.nci.hl7.map.impl.C_link getC_link(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _c_linkList.size())) {
            throw new IndexOutOfBoundsException("getC_link: Index value '"+index+"' not in range [0.."+_c_linkList.size()+ "]");
        }
        
        return (gov.nih.nci.hl7.map.impl.C_link) _c_linkList.elementAt(index);
    } //-- gov.nih.nci.hl7.map.impl.C_link getC_link(int) 

    /**
     * Method getC_link
     * 
     * 
     * 
     * @return C_link
     */
    public gov.nih.nci.hl7.map.impl.C_link[] getC_link()
    {
        int size = _c_linkList.size();
        gov.nih.nci.hl7.map.impl.C_link[] mArray = new gov.nih.nci.hl7.map.impl.C_link[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (gov.nih.nci.hl7.map.impl.C_link) _c_linkList.elementAt(index);
        }
        return mArray;
    } //-- gov.nih.nci.hl7.map.impl.C_link[] getC_link() 

    /**
     * Method getC_linkCount
     * 
     * 
     * 
     * @return int
     */
    public int getC_linkCount()
    {
        return _c_linkList.size();
    } //-- int getC_linkCount() 

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
     * Method removeAllC_link
     * 
     */
    public void removeAllC_link()
    {
        _c_linkList.removeAllElements();
    } //-- void removeAllC_link() 

    /**
     * Method removeC_link
     * 
     * 
     * 
     * @param index
     * @return C_link
     */
    public gov.nih.nci.hl7.map.impl.C_link removeC_link(int index)
    {
        java.lang.Object obj = _c_linkList.elementAt(index);
        _c_linkList.removeElementAt(index);
        return (gov.nih.nci.hl7.map.impl.C_link) obj;
    } //-- gov.nih.nci.hl7.map.impl.C_link removeC_link(int) 

    /**
     * Method setC_link
     * 
     * 
     * 
     * @param index
     * @param vC_link
     */
    public void setC_link(int index, gov.nih.nci.hl7.map.impl.C_link vC_link)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _c_linkList.size())) {
            throw new IndexOutOfBoundsException("setC_link: Index value '"+index+"' not in range [0.."+_c_linkList.size()+ "]");
        }
        _c_linkList.setElementAt(vC_link, index);
    } //-- void setC_link(int, gov.nih.nci.hl7.map.impl.C_link) 

    /**
     * Method setC_link
     * 
     * 
     * 
     * @param c_linkArray
     */
    public void setC_link(gov.nih.nci.hl7.map.impl.C_link[] c_linkArray)
    {
        //-- copy array
        _c_linkList.removeAllElements();
        for (int i = 0; i < c_linkArray.length; i++) {
            _c_linkList.addElement(c_linkArray[i]);
        }
    } //-- void setC_link(gov.nih.nci.hl7.map.impl.C_link) 

    /**
     * Method unmarshalC_links
     * 
     * 
     * 
     * @param reader
     * @return C_links
     */
    public static gov.nih.nci.hl7.map.impl.C_links unmarshalC_links(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (gov.nih.nci.hl7.map.impl.C_links) Unmarshaller.unmarshal(gov.nih.nci.hl7.map.impl.C_links.class, reader);
    } //-- gov.nih.nci.hl7.map.impl.C_links unmarshalC_links(java.io.Reader) 

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
