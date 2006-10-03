/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: C_database.java,v 1.1 2006-10-03 17:38:25 marwahah Exp $
 */

package gov.nih.nci.hl7.database.impl;

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
 * Class C_database.
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 17:38:25 $
 */
public class C_database implements java.io.Serializable {


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
     * Field _c_tableList
     */
    private java.util.Vector _c_tableList;


      //----------------/
     //- Constructors -/
    //----------------/

    public C_database() 
     {
        super();
        _c_tableList = new Vector();
    } //-- gov.nih.nci.hl7.database.impl.C_database()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addC_table
     * 
     * 
     * 
     * @param vC_table
     */
    public void addC_table(gov.nih.nci.hl7.database.impl.C_table vC_table)
        throws java.lang.IndexOutOfBoundsException
    {
        _c_tableList.addElement(vC_table);
    } //-- void addC_table(gov.nih.nci.hl7.database.impl.C_table) 

    /**
     * Method addC_table
     * 
     * 
     * 
     * @param index
     * @param vC_table
     */
    public void addC_table(int index, gov.nih.nci.hl7.database.impl.C_table vC_table)
        throws java.lang.IndexOutOfBoundsException
    {
        _c_tableList.insertElementAt(vC_table, index);
    } //-- void addC_table(int, gov.nih.nci.hl7.database.impl.C_table) 

    /**
     * Method enumerateC_table
     * 
     * 
     * 
     * @return Enumeration
     */
    public java.util.Enumeration enumerateC_table()
    {
        return _c_tableList.elements();
    } //-- java.util.Enumeration enumerateC_table() 

    /**
     * Method getC_table
     * 
     * 
     * 
     * @param index
     * @return C_table
     */
    public gov.nih.nci.hl7.database.impl.C_table getC_table(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _c_tableList.size())) {
            throw new IndexOutOfBoundsException("getC_table: Index value '"+index+"' not in range [0.."+_c_tableList.size()+ "]");
        }
        
        return (gov.nih.nci.hl7.database.impl.C_table) _c_tableList.elementAt(index);
    } //-- gov.nih.nci.hl7.database.impl.C_table getC_table(int) 

    /**
     * Method getC_table
     * 
     * 
     * 
     * @return C_table
     */
    public gov.nih.nci.hl7.database.impl.C_table[] getC_table()
    {
        int size = _c_tableList.size();
        gov.nih.nci.hl7.database.impl.C_table[] mArray = new gov.nih.nci.hl7.database.impl.C_table[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (gov.nih.nci.hl7.database.impl.C_table) _c_tableList.elementAt(index);
        }
        return mArray;
    } //-- gov.nih.nci.hl7.database.impl.C_table[] getC_table() 

    /**
     * Method getC_tableCount
     * 
     * 
     * 
     * @return int
     */
    public int getC_tableCount()
    {
        return _c_tableList.size();
    } //-- int getC_tableCount() 

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
     * Method removeAllC_table
     * 
     */
    public void removeAllC_table()
    {
        _c_tableList.removeAllElements();
    } //-- void removeAllC_table() 

    /**
     * Method removeC_table
     * 
     * 
     * 
     * @param index
     * @return C_table
     */
    public gov.nih.nci.hl7.database.impl.C_table removeC_table(int index)
    {
        java.lang.Object obj = _c_tableList.elementAt(index);
        _c_tableList.removeElementAt(index);
        return (gov.nih.nci.hl7.database.impl.C_table) obj;
    } //-- gov.nih.nci.hl7.database.impl.C_table removeC_table(int) 

    /**
     * Method setC_table
     * 
     * 
     * 
     * @param index
     * @param vC_table
     */
    public void setC_table(int index, gov.nih.nci.hl7.database.impl.C_table vC_table)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _c_tableList.size())) {
            throw new IndexOutOfBoundsException("setC_table: Index value '"+index+"' not in range [0.."+_c_tableList.size()+ "]");
        }
        _c_tableList.setElementAt(vC_table, index);
    } //-- void setC_table(int, gov.nih.nci.hl7.database.impl.C_table) 

    /**
     * Method setC_table
     * 
     * 
     * 
     * @param c_tableArray
     */
    public void setC_table(gov.nih.nci.hl7.database.impl.C_table[] c_tableArray)
    {
        //-- copy array
        _c_tableList.removeAllElements();
        for (int i = 0; i < c_tableArray.length; i++) {
            _c_tableList.addElement(c_tableArray[i]);
        }
    } //-- void setC_table(gov.nih.nci.hl7.database.impl.C_table) 

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
     * Method unmarshalC_database
     * 
     * 
     * 
     * @param reader
     * @return C_database
     */
    public static gov.nih.nci.hl7.database.impl.C_database unmarshalC_database(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (gov.nih.nci.hl7.database.impl.C_database) Unmarshaller.unmarshal(gov.nih.nci.hl7.database.impl.C_database.class, reader);
    } //-- gov.nih.nci.hl7.database.impl.C_database unmarshalC_database(java.io.Reader) 

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
