/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: C_csvMetadata.java,v 1.1 2006-10-03 17:38:25 marwahah Exp $
 */

package gov.nih.nci.hl7.csv.meta.impl;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.math.BigDecimal;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Class C_csvMetadata.
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 17:38:25 $
 */
public class C_csvMetadata implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _uuid
     */
    private java.lang.String _uuid;

    /**
     * Field _version
     */
    private java.math.BigDecimal _version;

    /**
     * Field _c_segment
     */
    private gov.nih.nci.hl7.csv.meta.impl.C_segment _c_segment;


      //----------------/
     //- Constructors -/
    //----------------/

    public C_csvMetadata() 
     {
        super();
    } //-- gov.nih.nci.hl7.csv.meta.impl.C_csvMetadata()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'c_segment'.
     * 
     * @return C_segment
     * @return the value of field 'c_segment'.
     */
    public gov.nih.nci.hl7.csv.meta.impl.C_segment getC_segment()
    {
        return this._c_segment;
    } //-- gov.nih.nci.hl7.csv.meta.impl.C_segment getC_segment() 

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
     * Returns the value of field 'version'.
     * 
     * @return BigDecimal
     * @return the value of field 'version'.
     */
    public java.math.BigDecimal getVersion()
    {
        return this._version;
    } //-- java.math.BigDecimal getVersion() 

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
     * Sets the value of field 'c_segment'.
     * 
     * @param c_segment the value of field 'c_segment'.
     */
    public void setC_segment(gov.nih.nci.hl7.csv.meta.impl.C_segment c_segment)
    {
        this._c_segment = c_segment;
    } //-- void setC_segment(gov.nih.nci.hl7.csv.meta.impl.C_segment) 

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
     * Sets the value of field 'version'.
     * 
     * @param version the value of field 'version'.
     */
    public void setVersion(java.math.BigDecimal version)
    {
        this._version = version;
    } //-- void setVersion(java.math.BigDecimal) 

    /**
     * Method unmarshalC_csvMetadata
     * 
     * 
     * 
     * @param reader
     * @return C_csvMetadata
     */
    public static gov.nih.nci.hl7.csv.meta.impl.C_csvMetadata unmarshalC_csvMetadata(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (gov.nih.nci.hl7.csv.meta.impl.C_csvMetadata) Unmarshaller.unmarshal(gov.nih.nci.hl7.csv.meta.impl.C_csvMetadata.class, reader);
    } //-- gov.nih.nci.hl7.csv.meta.impl.C_csvMetadata unmarshalC_csvMetadata(java.io.Reader) 

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
