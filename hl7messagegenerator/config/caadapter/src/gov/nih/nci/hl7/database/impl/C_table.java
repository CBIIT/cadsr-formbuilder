/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: C_table.java,v 1.1 2006-10-03 17:38:25 marwahah Exp $
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
 * Class C_table.
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 17:38:25 $
 */
public class C_table implements java.io.Serializable {


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
     * Field _c_columnList
     */
    private java.util.Vector _c_columnList;

    /**
     * Field _c_foreignKeyList
     */
    private java.util.Vector _c_foreignKeyList;


      //----------------/
     //- Constructors -/
    //----------------/

    public C_table() 
     {
        super();
        _c_columnList = new Vector();
        _c_foreignKeyList = new Vector();
    } //-- gov.nih.nci.hl7.database.impl.C_table()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addC_column
     * 
     * 
     * 
     * @param vC_column
     */
    public void addC_column(gov.nih.nci.hl7.database.impl.C_column vC_column)
        throws java.lang.IndexOutOfBoundsException
    {
        _c_columnList.addElement(vC_column);
    } //-- void addC_column(gov.nih.nci.hl7.database.impl.C_column) 

    /**
     * Method addC_column
     * 
     * 
     * 
     * @param index
     * @param vC_column
     */
    public void addC_column(int index, gov.nih.nci.hl7.database.impl.C_column vC_column)
        throws java.lang.IndexOutOfBoundsException
    {
        _c_columnList.insertElementAt(vC_column, index);
    } //-- void addC_column(int, gov.nih.nci.hl7.database.impl.C_column) 

    /**
     * Method addC_foreignKey
     * 
     * 
     * 
     * @param vC_foreignKey
     */
    public void addC_foreignKey(gov.nih.nci.hl7.database.impl.C_foreignKey vC_foreignKey)
        throws java.lang.IndexOutOfBoundsException
    {
        _c_foreignKeyList.addElement(vC_foreignKey);
    } //-- void addC_foreignKey(gov.nih.nci.hl7.database.impl.C_foreignKey) 

    /**
     * Method addC_foreignKey
     * 
     * 
     * 
     * @param index
     * @param vC_foreignKey
     */
    public void addC_foreignKey(int index, gov.nih.nci.hl7.database.impl.C_foreignKey vC_foreignKey)
        throws java.lang.IndexOutOfBoundsException
    {
        _c_foreignKeyList.insertElementAt(vC_foreignKey, index);
    } //-- void addC_foreignKey(int, gov.nih.nci.hl7.database.impl.C_foreignKey) 

    /**
     * Method enumerateC_column
     * 
     * 
     * 
     * @return Enumeration
     */
    public java.util.Enumeration enumerateC_column()
    {
        return _c_columnList.elements();
    } //-- java.util.Enumeration enumerateC_column() 

    /**
     * Method enumerateC_foreignKey
     * 
     * 
     * 
     * @return Enumeration
     */
    public java.util.Enumeration enumerateC_foreignKey()
    {
        return _c_foreignKeyList.elements();
    } //-- java.util.Enumeration enumerateC_foreignKey() 

    /**
     * Method getC_column
     * 
     * 
     * 
     * @param index
     * @return C_column
     */
    public gov.nih.nci.hl7.database.impl.C_column getC_column(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _c_columnList.size())) {
            throw new IndexOutOfBoundsException("getC_column: Index value '"+index+"' not in range [0.."+_c_columnList.size()+ "]");
        }
        
        return (gov.nih.nci.hl7.database.impl.C_column) _c_columnList.elementAt(index);
    } //-- gov.nih.nci.hl7.database.impl.C_column getC_column(int) 

    /**
     * Method getC_column
     * 
     * 
     * 
     * @return C_column
     */
    public gov.nih.nci.hl7.database.impl.C_column[] getC_column()
    {
        int size = _c_columnList.size();
        gov.nih.nci.hl7.database.impl.C_column[] mArray = new gov.nih.nci.hl7.database.impl.C_column[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (gov.nih.nci.hl7.database.impl.C_column) _c_columnList.elementAt(index);
        }
        return mArray;
    } //-- gov.nih.nci.hl7.database.impl.C_column[] getC_column() 

    /**
     * Method getC_columnCount
     * 
     * 
     * 
     * @return int
     */
    public int getC_columnCount()
    {
        return _c_columnList.size();
    } //-- int getC_columnCount() 

    /**
     * Method getC_foreignKey
     * 
     * 
     * 
     * @param index
     * @return C_foreignKey
     */
    public gov.nih.nci.hl7.database.impl.C_foreignKey getC_foreignKey(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _c_foreignKeyList.size())) {
            throw new IndexOutOfBoundsException("getC_foreignKey: Index value '"+index+"' not in range [0.."+_c_foreignKeyList.size()+ "]");
        }
        
        return (gov.nih.nci.hl7.database.impl.C_foreignKey) _c_foreignKeyList.elementAt(index);
    } //-- gov.nih.nci.hl7.database.impl.C_foreignKey getC_foreignKey(int) 

    /**
     * Method getC_foreignKey
     * 
     * 
     * 
     * @return C_foreignKey
     */
    public gov.nih.nci.hl7.database.impl.C_foreignKey[] getC_foreignKey()
    {
        int size = _c_foreignKeyList.size();
        gov.nih.nci.hl7.database.impl.C_foreignKey[] mArray = new gov.nih.nci.hl7.database.impl.C_foreignKey[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (gov.nih.nci.hl7.database.impl.C_foreignKey) _c_foreignKeyList.elementAt(index);
        }
        return mArray;
    } //-- gov.nih.nci.hl7.database.impl.C_foreignKey[] getC_foreignKey() 

    /**
     * Method getC_foreignKeyCount
     * 
     * 
     * 
     * @return int
     */
    public int getC_foreignKeyCount()
    {
        return _c_foreignKeyList.size();
    } //-- int getC_foreignKeyCount() 

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
     * Method removeAllC_column
     * 
     */
    public void removeAllC_column()
    {
        _c_columnList.removeAllElements();
    } //-- void removeAllC_column() 

    /**
     * Method removeAllC_foreignKey
     * 
     */
    public void removeAllC_foreignKey()
    {
        _c_foreignKeyList.removeAllElements();
    } //-- void removeAllC_foreignKey() 

    /**
     * Method removeC_column
     * 
     * 
     * 
     * @param index
     * @return C_column
     */
    public gov.nih.nci.hl7.database.impl.C_column removeC_column(int index)
    {
        java.lang.Object obj = _c_columnList.elementAt(index);
        _c_columnList.removeElementAt(index);
        return (gov.nih.nci.hl7.database.impl.C_column) obj;
    } //-- gov.nih.nci.hl7.database.impl.C_column removeC_column(int) 

    /**
     * Method removeC_foreignKey
     * 
     * 
     * 
     * @param index
     * @return C_foreignKey
     */
    public gov.nih.nci.hl7.database.impl.C_foreignKey removeC_foreignKey(int index)
    {
        java.lang.Object obj = _c_foreignKeyList.elementAt(index);
        _c_foreignKeyList.removeElementAt(index);
        return (gov.nih.nci.hl7.database.impl.C_foreignKey) obj;
    } //-- gov.nih.nci.hl7.database.impl.C_foreignKey removeC_foreignKey(int) 

    /**
     * Method setC_column
     * 
     * 
     * 
     * @param index
     * @param vC_column
     */
    public void setC_column(int index, gov.nih.nci.hl7.database.impl.C_column vC_column)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _c_columnList.size())) {
            throw new IndexOutOfBoundsException("setC_column: Index value '"+index+"' not in range [0.."+_c_columnList.size()+ "]");
        }
        _c_columnList.setElementAt(vC_column, index);
    } //-- void setC_column(int, gov.nih.nci.hl7.database.impl.C_column) 

    /**
     * Method setC_column
     * 
     * 
     * 
     * @param c_columnArray
     */
    public void setC_column(gov.nih.nci.hl7.database.impl.C_column[] c_columnArray)
    {
        //-- copy array
        _c_columnList.removeAllElements();
        for (int i = 0; i < c_columnArray.length; i++) {
            _c_columnList.addElement(c_columnArray[i]);
        }
    } //-- void setC_column(gov.nih.nci.hl7.database.impl.C_column) 

    /**
     * Method setC_foreignKey
     * 
     * 
     * 
     * @param index
     * @param vC_foreignKey
     */
    public void setC_foreignKey(int index, gov.nih.nci.hl7.database.impl.C_foreignKey vC_foreignKey)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _c_foreignKeyList.size())) {
            throw new IndexOutOfBoundsException("setC_foreignKey: Index value '"+index+"' not in range [0.."+_c_foreignKeyList.size()+ "]");
        }
        _c_foreignKeyList.setElementAt(vC_foreignKey, index);
    } //-- void setC_foreignKey(int, gov.nih.nci.hl7.database.impl.C_foreignKey) 

    /**
     * Method setC_foreignKey
     * 
     * 
     * 
     * @param c_foreignKeyArray
     */
    public void setC_foreignKey(gov.nih.nci.hl7.database.impl.C_foreignKey[] c_foreignKeyArray)
    {
        //-- copy array
        _c_foreignKeyList.removeAllElements();
        for (int i = 0; i < c_foreignKeyArray.length; i++) {
            _c_foreignKeyList.addElement(c_foreignKeyArray[i]);
        }
    } //-- void setC_foreignKey(gov.nih.nci.hl7.database.impl.C_foreignKey) 

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
     * Method unmarshalC_table
     * 
     * 
     * 
     * @param reader
     * @return C_table
     */
    public static gov.nih.nci.hl7.database.impl.C_table unmarshalC_table(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (gov.nih.nci.hl7.database.impl.C_table) Unmarshaller.unmarshal(gov.nih.nci.hl7.database.impl.C_table.class, reader);
    } //-- gov.nih.nci.hl7.database.impl.C_table unmarshalC_table(java.io.Reader) 

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
