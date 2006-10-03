/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: C_hl7v3meta.java,v 1.1 2006-10-03 17:38:25 marwahah Exp $
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
 * Class C_hl7v3meta.
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 17:38:25 $
 */
public class C_hl7v3meta implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _messageId
     */
    private java.lang.String _messageId;

    /**
     * Field _version
     */
    private java.lang.String _version;

    /**
     * Field _c_clone
     */
    private gov.nih.nci.hl7.clone.meta.impl.C_clone _c_clone;


      //----------------/
     //- Constructors -/
    //----------------/

    public C_hl7v3meta() 
     {
        super();
    } //-- gov.nih.nci.hl7.clone.meta.impl.C_hl7v3meta()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'c_clone'.
     * 
     * @return C_clone
     * @return the value of field 'c_clone'.
     */
    public gov.nih.nci.hl7.clone.meta.impl.C_clone getC_clone()
    {
        return this._c_clone;
    } //-- gov.nih.nci.hl7.clone.meta.impl.C_clone getC_clone() 

    /**
     * Returns the value of field 'messageId'.
     * 
     * @return String
     * @return the value of field 'messageId'.
     */
    public java.lang.String getMessageId()
    {
        return this._messageId;
    } //-- java.lang.String getMessageId() 

    /**
     * Returns the value of field 'version'.
     * 
     * @return String
     * @return the value of field 'version'.
     */
    public java.lang.String getVersion()
    {
        return this._version;
    } //-- java.lang.String getVersion() 

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
     * Sets the value of field 'c_clone'.
     * 
     * @param c_clone the value of field 'c_clone'.
     */
    public void setC_clone(gov.nih.nci.hl7.clone.meta.impl.C_clone c_clone)
    {
        this._c_clone = c_clone;
    } //-- void setC_clone(gov.nih.nci.hl7.clone.meta.impl.C_clone) 

    /**
     * Sets the value of field 'messageId'.
     * 
     * @param messageId the value of field 'messageId'.
     */
    public void setMessageId(java.lang.String messageId)
    {
        this._messageId = messageId;
    } //-- void setMessageId(java.lang.String) 

    /**
     * Sets the value of field 'version'.
     * 
     * @param version the value of field 'version'.
     */
    public void setVersion(java.lang.String version)
    {
        this._version = version;
    } //-- void setVersion(java.lang.String) 

    /**
     * Method unmarshalC_hl7v3meta
     * 
     * 
     * 
     * @param reader
     * @return C_hl7v3meta
     */
    public static gov.nih.nci.hl7.clone.meta.impl.C_hl7v3meta unmarshalC_hl7v3meta(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (gov.nih.nci.hl7.clone.meta.impl.C_hl7v3meta) Unmarshaller.unmarshal(gov.nih.nci.hl7.clone.meta.impl.C_hl7v3meta.class, reader);
    } //-- gov.nih.nci.hl7.clone.meta.impl.C_hl7v3meta unmarshalC_hl7v3meta(java.io.Reader) 

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
