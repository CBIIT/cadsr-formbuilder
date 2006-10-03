/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: C_foreignKey.java,v 1.1 2006-10-03 17:38:25 marwahah Exp $
 */

package gov.nih.nci.hl7.database.impl;

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
 * Class C_foreignKey.
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 17:38:25 $
 */
public class C_foreignKey implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _detail
     */
    private java.lang.String _detail;

    /**
     * Field _formula
     */
    private java.lang.Object _formula;

    /**
     * Field _master
     */
    private java.lang.String _master;

    /**
     * Field _name
     */
    private java.lang.String _name;

    /**
     * Field _uuid
     */
    private java.lang.String _uuid;


      //----------------/
     //- Constructors -/
    //----------------/

    public C_foreignKey() 
     {
        super();
    } //-- gov.nih.nci.hl7.database.impl.C_foreignKey()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'detail'.
     * 
     * @return String
     * @return the value of field 'detail'.
     */
    public java.lang.String getDetail()
    {
        return this._detail;
    } //-- java.lang.String getDetail() 

    /**
     * Returns the value of field 'formula'.
     * 
     * @return Object
     * @return the value of field 'formula'.
     */
    public java.lang.Object getFormula()
    {
        return this._formula;
    } //-- java.lang.Object getFormula() 

    /**
     * Returns the value of field 'master'.
     * 
     * @return String
     * @return the value of field 'master'.
     */
    public java.lang.String getMaster()
    {
        return this._master;
    } //-- java.lang.String getMaster() 

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
     * Sets the value of field 'detail'.
     * 
     * @param detail the value of field 'detail'.
     */
    public void setDetail(java.lang.String detail)
    {
        this._detail = detail;
    } //-- void setDetail(java.lang.String) 

    /**
     * Sets the value of field 'formula'.
     * 
     * @param formula the value of field 'formula'.
     */
    public void setFormula(java.lang.Object formula)
    {
        this._formula = formula;
    } //-- void setFormula(java.lang.Object) 

    /**
     * Sets the value of field 'master'.
     * 
     * @param master the value of field 'master'.
     */
    public void setMaster(java.lang.String master)
    {
        this._master = master;
    } //-- void setMaster(java.lang.String) 

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
     * Method unmarshalC_foreignKey
     * 
     * 
     * 
     * @param reader
     * @return C_foreignKey
     */
    public static gov.nih.nci.hl7.database.impl.C_foreignKey unmarshalC_foreignKey(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (gov.nih.nci.hl7.database.impl.C_foreignKey) Unmarshaller.unmarshal(gov.nih.nci.hl7.database.impl.C_foreignKey.class, reader);
    } //-- gov.nih.nci.hl7.database.impl.C_foreignKey unmarshalC_foreignKey(java.io.Reader) 

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
