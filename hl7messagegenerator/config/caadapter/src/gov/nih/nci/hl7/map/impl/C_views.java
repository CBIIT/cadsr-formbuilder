/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: C_views.java,v 1.1 2006-10-03 17:38:42 marwahah Exp $
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
 * Class C_views.
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 17:38:42 $
 */
public class C_views implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _c_viewList
     */
    private java.util.Vector _c_viewList;


      //----------------/
     //- Constructors -/
    //----------------/

    public C_views() 
     {
        super();
        _c_viewList = new Vector();
    } //-- gov.nih.nci.hl7.map.impl.C_views()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addC_view
     * 
     * 
     * 
     * @param vC_view
     */
    public void addC_view(gov.nih.nci.hl7.map.impl.C_view vC_view)
        throws java.lang.IndexOutOfBoundsException
    {
        _c_viewList.addElement(vC_view);
    } //-- void addC_view(gov.nih.nci.hl7.map.impl.C_view) 

    /**
     * Method addC_view
     * 
     * 
     * 
     * @param index
     * @param vC_view
     */
    public void addC_view(int index, gov.nih.nci.hl7.map.impl.C_view vC_view)
        throws java.lang.IndexOutOfBoundsException
    {
        _c_viewList.insertElementAt(vC_view, index);
    } //-- void addC_view(int, gov.nih.nci.hl7.map.impl.C_view) 

    /**
     * Method enumerateC_view
     * 
     * 
     * 
     * @return Enumeration
     */
    public java.util.Enumeration enumerateC_view()
    {
        return _c_viewList.elements();
    } //-- java.util.Enumeration enumerateC_view() 

    /**
     * Method getC_view
     * 
     * 
     * 
     * @param index
     * @return C_view
     */
    public gov.nih.nci.hl7.map.impl.C_view getC_view(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _c_viewList.size())) {
            throw new IndexOutOfBoundsException("getC_view: Index value '"+index+"' not in range [0.."+_c_viewList.size()+ "]");
        }
        
        return (gov.nih.nci.hl7.map.impl.C_view) _c_viewList.elementAt(index);
    } //-- gov.nih.nci.hl7.map.impl.C_view getC_view(int) 

    /**
     * Method getC_view
     * 
     * 
     * 
     * @return C_view
     */
    public gov.nih.nci.hl7.map.impl.C_view[] getC_view()
    {
        int size = _c_viewList.size();
        gov.nih.nci.hl7.map.impl.C_view[] mArray = new gov.nih.nci.hl7.map.impl.C_view[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (gov.nih.nci.hl7.map.impl.C_view) _c_viewList.elementAt(index);
        }
        return mArray;
    } //-- gov.nih.nci.hl7.map.impl.C_view[] getC_view() 

    /**
     * Method getC_viewCount
     * 
     * 
     * 
     * @return int
     */
    public int getC_viewCount()
    {
        return _c_viewList.size();
    } //-- int getC_viewCount() 

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
     * Method removeAllC_view
     * 
     */
    public void removeAllC_view()
    {
        _c_viewList.removeAllElements();
    } //-- void removeAllC_view() 

    /**
     * Method removeC_view
     * 
     * 
     * 
     * @param index
     * @return C_view
     */
    public gov.nih.nci.hl7.map.impl.C_view removeC_view(int index)
    {
        java.lang.Object obj = _c_viewList.elementAt(index);
        _c_viewList.removeElementAt(index);
        return (gov.nih.nci.hl7.map.impl.C_view) obj;
    } //-- gov.nih.nci.hl7.map.impl.C_view removeC_view(int) 

    /**
     * Method setC_view
     * 
     * 
     * 
     * @param index
     * @param vC_view
     */
    public void setC_view(int index, gov.nih.nci.hl7.map.impl.C_view vC_view)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _c_viewList.size())) {
            throw new IndexOutOfBoundsException("setC_view: Index value '"+index+"' not in range [0.."+_c_viewList.size()+ "]");
        }
        _c_viewList.setElementAt(vC_view, index);
    } //-- void setC_view(int, gov.nih.nci.hl7.map.impl.C_view) 

    /**
     * Method setC_view
     * 
     * 
     * 
     * @param c_viewArray
     */
    public void setC_view(gov.nih.nci.hl7.map.impl.C_view[] c_viewArray)
    {
        //-- copy array
        _c_viewList.removeAllElements();
        for (int i = 0; i < c_viewArray.length; i++) {
            _c_viewList.addElement(c_viewArray[i]);
        }
    } //-- void setC_view(gov.nih.nci.hl7.map.impl.C_view) 

    /**
     * Method unmarshalC_views
     * 
     * 
     * 
     * @param reader
     * @return C_views
     */
    public static gov.nih.nci.hl7.map.impl.C_views unmarshalC_views(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (gov.nih.nci.hl7.map.impl.C_views) Unmarshaller.unmarshal(gov.nih.nci.hl7.map.impl.C_views.class, reader);
    } //-- gov.nih.nci.hl7.map.impl.C_views unmarshalC_views(java.io.Reader) 

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
