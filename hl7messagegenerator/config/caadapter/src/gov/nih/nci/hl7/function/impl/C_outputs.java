/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: C_outputs.java,v 1.1 2006-10-03 17:38:26 marwahah Exp $
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
 * Class C_outputs.
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 17:38:26 $
 */
public class C_outputs implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _c_datapointList
     */
    private java.util.Vector _c_datapointList;


      //----------------/
     //- Constructors -/
    //----------------/

    public C_outputs() 
     {
        super();
        _c_datapointList = new Vector();
    } //-- gov.nih.nci.hl7.function.impl.C_outputs()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addC_datapoint
     * 
     * 
     * 
     * @param vC_datapoint
     */
    public void addC_datapoint(gov.nih.nci.hl7.function.impl.C_datapoint vC_datapoint)
        throws java.lang.IndexOutOfBoundsException
    {
        _c_datapointList.addElement(vC_datapoint);
    } //-- void addC_datapoint(gov.nih.nci.hl7.function.impl.C_datapoint) 

    /**
     * Method addC_datapoint
     * 
     * 
     * 
     * @param index
     * @param vC_datapoint
     */
    public void addC_datapoint(int index, gov.nih.nci.hl7.function.impl.C_datapoint vC_datapoint)
        throws java.lang.IndexOutOfBoundsException
    {
        _c_datapointList.insertElementAt(vC_datapoint, index);
    } //-- void addC_datapoint(int, gov.nih.nci.hl7.function.impl.C_datapoint) 

    /**
     * Method enumerateC_datapoint
     * 
     * 
     * 
     * @return Enumeration
     */
    public java.util.Enumeration enumerateC_datapoint()
    {
        return _c_datapointList.elements();
    } //-- java.util.Enumeration enumerateC_datapoint() 

    /**
     * Method getC_datapoint
     * 
     * 
     * 
     * @param index
     * @return C_datapoint
     */
    public gov.nih.nci.hl7.function.impl.C_datapoint getC_datapoint(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _c_datapointList.size())) {
            throw new IndexOutOfBoundsException("getC_datapoint: Index value '"+index+"' not in range [0.."+_c_datapointList.size()+ "]");
        }
        
        return (gov.nih.nci.hl7.function.impl.C_datapoint) _c_datapointList.elementAt(index);
    } //-- gov.nih.nci.hl7.function.impl.C_datapoint getC_datapoint(int) 

    /**
     * Method getC_datapoint
     * 
     * 
     * 
     * @return C_datapoint
     */
    public gov.nih.nci.hl7.function.impl.C_datapoint[] getC_datapoint()
    {
        int size = _c_datapointList.size();
        gov.nih.nci.hl7.function.impl.C_datapoint[] mArray = new gov.nih.nci.hl7.function.impl.C_datapoint[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (gov.nih.nci.hl7.function.impl.C_datapoint) _c_datapointList.elementAt(index);
        }
        return mArray;
    } //-- gov.nih.nci.hl7.function.impl.C_datapoint[] getC_datapoint() 

    /**
     * Method getC_datapointCount
     * 
     * 
     * 
     * @return int
     */
    public int getC_datapointCount()
    {
        return _c_datapointList.size();
    } //-- int getC_datapointCount() 

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
     * Method removeAllC_datapoint
     * 
     */
    public void removeAllC_datapoint()
    {
        _c_datapointList.removeAllElements();
    } //-- void removeAllC_datapoint() 

    /**
     * Method removeC_datapoint
     * 
     * 
     * 
     * @param index
     * @return C_datapoint
     */
    public gov.nih.nci.hl7.function.impl.C_datapoint removeC_datapoint(int index)
    {
        java.lang.Object obj = _c_datapointList.elementAt(index);
        _c_datapointList.removeElementAt(index);
        return (gov.nih.nci.hl7.function.impl.C_datapoint) obj;
    } //-- gov.nih.nci.hl7.function.impl.C_datapoint removeC_datapoint(int) 

    /**
     * Method setC_datapoint
     * 
     * 
     * 
     * @param index
     * @param vC_datapoint
     */
    public void setC_datapoint(int index, gov.nih.nci.hl7.function.impl.C_datapoint vC_datapoint)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _c_datapointList.size())) {
            throw new IndexOutOfBoundsException("setC_datapoint: Index value '"+index+"' not in range [0.."+_c_datapointList.size()+ "]");
        }
        _c_datapointList.setElementAt(vC_datapoint, index);
    } //-- void setC_datapoint(int, gov.nih.nci.hl7.function.impl.C_datapoint) 

    /**
     * Method setC_datapoint
     * 
     * 
     * 
     * @param c_datapointArray
     */
    public void setC_datapoint(gov.nih.nci.hl7.function.impl.C_datapoint[] c_datapointArray)
    {
        //-- copy array
        _c_datapointList.removeAllElements();
        for (int i = 0; i < c_datapointArray.length; i++) {
            _c_datapointList.addElement(c_datapointArray[i]);
        }
    } //-- void setC_datapoint(gov.nih.nci.hl7.function.impl.C_datapoint) 

    /**
     * Method unmarshalC_outputs
     * 
     * 
     * 
     * @param reader
     * @return C_outputs
     */
    public static gov.nih.nci.hl7.function.impl.C_outputs unmarshalC_outputs(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (gov.nih.nci.hl7.function.impl.C_outputs) Unmarshaller.unmarshal(gov.nih.nci.hl7.function.impl.C_outputs.class, reader);
    } //-- gov.nih.nci.hl7.function.impl.C_outputs unmarshalC_outputs(java.io.Reader) 

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
