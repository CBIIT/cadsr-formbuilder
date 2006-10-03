/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: C_datapoint.java,v 1.1 2006-10-03 17:38:26 marwahah Exp $
 */

package gov.nih.nci.hl7.function.impl;

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
 * Class C_datapoint.
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 17:38:26 $
 */
public class C_datapoint implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _datatype
     */
    private java.lang.String _datatype;

    /**
     * Field _name
     */
    private java.lang.String _name;

    /**
     * Field _pos
     */
    private int _pos;

    /**
     * keeps track of state for field: _pos
     */
    private boolean _has_pos;

    /**
     * Field _uuid
     */
    private java.lang.String _uuid;


      //----------------/
     //- Constructors -/
    //----------------/

    public C_datapoint() 
     {
        super();
    } //-- gov.nih.nci.hl7.function.impl.C_datapoint()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method deletePos
     * 
     */
    public void deletePos()
    {
        this._has_pos= false;
    } //-- void deletePos() 

    /**
     * Returns the value of field 'datatype'.
     * 
     * @return String
     * @return the value of field 'datatype'.
     */
    public java.lang.String getDatatype()
    {
        return this._datatype;
    } //-- java.lang.String getDatatype() 

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
     * Returns the value of field 'pos'.
     * 
     * @return int
     * @return the value of field 'pos'.
     */
    public int getPos()
    {
        return this._pos;
    } //-- int getPos() 

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
     * Method hasPos
     * 
     * 
     * 
     * @return boolean
     */
    public boolean hasPos()
    {
        return this._has_pos;
    } //-- boolean hasPos() 

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
     * Sets the value of field 'datatype'.
     * 
     * @param datatype the value of field 'datatype'.
     */
    public void setDatatype(java.lang.String datatype)
    {
        this._datatype = datatype;
    } //-- void setDatatype(java.lang.String) 

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
     * Sets the value of field 'pos'.
     * 
     * @param pos the value of field 'pos'.
     */
    public void setPos(int pos)
    {
        this._pos = pos;
        this._has_pos = true;
    } //-- void setPos(int) 

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
     * Method unmarshalC_datapoint
     * 
     * 
     * 
     * @param reader
     * @return C_datapoint
     */
    public static gov.nih.nci.hl7.function.impl.C_datapoint unmarshalC_datapoint(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (gov.nih.nci.hl7.function.impl.C_datapoint) Unmarshaller.unmarshal(gov.nih.nci.hl7.function.impl.C_datapoint.class, reader);
    } //-- gov.nih.nci.hl7.function.impl.C_datapoint unmarshalC_datapoint(java.io.Reader) 

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
