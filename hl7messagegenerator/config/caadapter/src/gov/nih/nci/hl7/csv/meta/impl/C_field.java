/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: C_field.java,v 1.1 2006-10-03 17:38:25 marwahah Exp $
 */

package gov.nih.nci.hl7.csv.meta.impl;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Class C_field.
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 17:38:25 $
 */
public class C_field implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _column
     */
    private int _column;

    /**
     * keeps track of state for field: _column
     */
    private boolean _has_column;

    /**
     * Field _name
     */
    private java.lang.Object _name;

    /**
     * Field _uuid
     */
    private java.lang.String _uuid;


      //----------------/
     //- Constructors -/
    //----------------/

    public C_field() 
     {
        super();
    } //-- gov.nih.nci.hl7.csv.meta.impl.C_field()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method deleteColumn
     * 
     */
    public void deleteColumn()
    {
        this._has_column= false;
    } //-- void deleteColumn() 

    /**
     * Returns the value of field 'column'.
     * 
     * @return int
     * @return the value of field 'column'.
     */
    public int getColumn()
    {
        return this._column;
    } //-- int getColumn() 

    /**
     * Returns the value of field 'name'.
     * 
     * @return Object
     * @return the value of field 'name'.
     */
    public java.lang.Object getName()
    {
        return this._name;
    } //-- java.lang.Object getName() 

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
     * Method hasColumn
     * 
     * 
     * 
     * @return boolean
     */
    public boolean hasColumn()
    {
        return this._has_column;
    } //-- boolean hasColumn() 

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
     * Sets the value of field 'column'.
     * 
     * @param column the value of field 'column'.
     */
    public void setColumn(int column)
    {
        this._column = column;
        this._has_column = true;
    } //-- void setColumn(int) 

    /**
     * Sets the value of field 'name'.
     * 
     * @param name the value of field 'name'.
     */
    public void setName(java.lang.Object name)
    {
        this._name = name;
    } //-- void setName(java.lang.Object) 

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
     * Method unmarshalC_field
     * 
     * 
     * 
     * @param reader
     * @return C_field
     */
    public static gov.nih.nci.hl7.csv.meta.impl.C_field unmarshalC_field(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (gov.nih.nci.hl7.csv.meta.impl.C_field) Unmarshaller.unmarshal(gov.nih.nci.hl7.csv.meta.impl.C_field.class, reader);
    } //-- gov.nih.nci.hl7.csv.meta.impl.C_field unmarshalC_field(java.io.Reader) 

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
