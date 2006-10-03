/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: C_datatypeField.java,v 1.1 2006-10-03 17:38:25 marwahah Exp $
 */

package gov.nih.nci.hl7.clone.meta.impl;

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
 * Class C_datatypeField.
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 17:38:25 $
 */
public class C_datatypeField implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _hl7Default
     */
    private java.lang.String _hl7Default;

    /**
     * Field _name
     */
    private java.lang.String _name;

    /**
     * Field _userDefault
     */
    private java.lang.String _userDefault;

    /**
     * Field _uuid
     */
    private java.lang.String _uuid;

    /**
     * Field _cardinality
     */
    private java.lang.String _cardinality;


      //----------------/
     //- Constructors -/
    //----------------/

    public C_datatypeField() 
     {
        super();
    } //-- gov.nih.nci.hl7.clone.meta.impl.C_datatypeField()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'cardinality'.
     * 
     * @return String
     * @return the value of field 'cardinality'.
     */
    public java.lang.String getCardinality()
    {
        return this._cardinality;
    } //-- java.lang.String getCardinality() 

    /**
     * Returns the value of field 'hl7Default'.
     * 
     * @return String
     * @return the value of field 'hl7Default'.
     */
    public java.lang.String getHl7Default()
    {
        return this._hl7Default;
    } //-- java.lang.String getHl7Default() 

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
     * Returns the value of field 'userDefault'.
     * 
     * @return String
     * @return the value of field 'userDefault'.
     */
    public java.lang.String getUserDefault()
    {
        return this._userDefault;
    } //-- java.lang.String getUserDefault() 

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
     * Sets the value of field 'cardinality'.
     * 
     * @param cardinality the value of field 'cardinality'.
     */
    public void setCardinality(java.lang.String cardinality)
    {
        this._cardinality = cardinality;
    } //-- void setCardinality(java.lang.String) 

    /**
     * Sets the value of field 'hl7Default'.
     * 
     * @param hl7Default the value of field 'hl7Default'.
     */
    public void setHl7Default(java.lang.String hl7Default)
    {
        this._hl7Default = hl7Default;
    } //-- void setHl7Default(java.lang.String) 

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
     * Sets the value of field 'userDefault'.
     * 
     * @param userDefault the value of field 'userDefault'.
     */
    public void setUserDefault(java.lang.String userDefault)
    {
        this._userDefault = userDefault;
    } //-- void setUserDefault(java.lang.String) 

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
     * Method unmarshalC_datatypeField
     * 
     * 
     * 
     * @param reader
     * @return C_datatypeField
     */
    public static gov.nih.nci.hl7.clone.meta.impl.C_datatypeField unmarshalC_datatypeField(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (gov.nih.nci.hl7.clone.meta.impl.C_datatypeField) Unmarshaller.unmarshal(gov.nih.nci.hl7.clone.meta.impl.C_datatypeField.class, reader);
    } //-- gov.nih.nci.hl7.clone.meta.impl.C_datatypeField unmarshalC_datatypeField(java.io.Reader) 

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
