/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: C_mapping.java,v 1.1 2006-10-03 17:38:42 marwahah Exp $
 */

package gov.nih.nci.hl7.map.impl;

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
 * Class C_mapping.
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 17:38:42 $
 */
public class C_mapping implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _version
     */
    private java.math.BigDecimal _version;

    /**
     * Field _c_components
     */
    private gov.nih.nci.hl7.map.impl.C_components _c_components;

    /**
     * Field _c_links
     */
    private gov.nih.nci.hl7.map.impl.C_links _c_links;

    /**
     * Field _c_views
     */
    private gov.nih.nci.hl7.map.impl.C_views _c_views;


      //----------------/
     //- Constructors -/
    //----------------/

    public C_mapping() 
     {
        super();
    } //-- gov.nih.nci.hl7.map.impl.C_mapping()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'c_components'.
     * 
     * @return C_components
     * @return the value of field 'c_components'.
     */
    public gov.nih.nci.hl7.map.impl.C_components getC_components()
    {
        return this._c_components;
    } //-- gov.nih.nci.hl7.map.impl.C_components getC_components() 

    /**
     * Returns the value of field 'c_links'.
     * 
     * @return C_links
     * @return the value of field 'c_links'.
     */
    public gov.nih.nci.hl7.map.impl.C_links getC_links()
    {
        return this._c_links;
    } //-- gov.nih.nci.hl7.map.impl.C_links getC_links() 

    /**
     * Returns the value of field 'c_views'.
     * 
     * @return C_views
     * @return the value of field 'c_views'.
     */
    public gov.nih.nci.hl7.map.impl.C_views getC_views()
    {
        return this._c_views;
    } //-- gov.nih.nci.hl7.map.impl.C_views getC_views() 

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
     * Sets the value of field 'c_components'.
     * 
     * @param c_components the value of field 'c_components'.
     */
    public void setC_components(gov.nih.nci.hl7.map.impl.C_components c_components)
    {
        this._c_components = c_components;
    } //-- void setC_components(gov.nih.nci.hl7.map.impl.C_components) 

    /**
     * Sets the value of field 'c_links'.
     * 
     * @param c_links the value of field 'c_links'.
     */
    public void setC_links(gov.nih.nci.hl7.map.impl.C_links c_links)
    {
        this._c_links = c_links;
    } //-- void setC_links(gov.nih.nci.hl7.map.impl.C_links) 

    /**
     * Sets the value of field 'c_views'.
     * 
     * @param c_views the value of field 'c_views'.
     */
    public void setC_views(gov.nih.nci.hl7.map.impl.C_views c_views)
    {
        this._c_views = c_views;
    } //-- void setC_views(gov.nih.nci.hl7.map.impl.C_views) 

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
     * Method unmarshalC_mapping
     * 
     * 
     * 
     * @param reader
     * @return C_mapping
     */
    public static gov.nih.nci.hl7.map.impl.C_mapping unmarshalC_mapping(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (gov.nih.nci.hl7.map.impl.C_mapping) Unmarshaller.unmarshal(gov.nih.nci.hl7.map.impl.C_mapping.class, reader);
    } //-- gov.nih.nci.hl7.map.impl.C_mapping unmarshalC_mapping(java.io.Reader) 

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
