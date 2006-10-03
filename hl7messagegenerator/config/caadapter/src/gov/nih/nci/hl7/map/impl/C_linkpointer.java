/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: C_linkpointer.java,v 1.1 2006-10-03 17:38:42 marwahah Exp $
 */

package gov.nih.nci.hl7.map.impl;

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
 * Class C_linkpointer.
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 17:38:42 $
 */
public class C_linkpointer implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _componentUuid
     */
    private java.lang.String _componentUuid;

    /**
     * Field _dataUuid
     */
    private java.lang.String _dataUuid;


      //----------------/
     //- Constructors -/
    //----------------/

    public C_linkpointer() 
     {
        super();
    } //-- gov.nih.nci.hl7.map.impl.C_linkpointer()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'componentUuid'.
     * 
     * @return String
     * @return the value of field 'componentUuid'.
     */
    public java.lang.String getComponentUuid()
    {
        return this._componentUuid;
    } //-- java.lang.String getComponentUuid() 

    /**
     * Returns the value of field 'dataUuid'.
     * 
     * @return String
     * @return the value of field 'dataUuid'.
     */
    public java.lang.String getDataUuid()
    {
        return this._dataUuid;
    } //-- java.lang.String getDataUuid() 

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
     * Sets the value of field 'componentUuid'.
     * 
     * @param componentUuid the value of field 'componentUuid'.
     */
    public void setComponentUuid(java.lang.String componentUuid)
    {
        this._componentUuid = componentUuid;
    } //-- void setComponentUuid(java.lang.String) 

    /**
     * Sets the value of field 'dataUuid'.
     * 
     * @param dataUuid the value of field 'dataUuid'.
     */
    public void setDataUuid(java.lang.String dataUuid)
    {
        this._dataUuid = dataUuid;
    } //-- void setDataUuid(java.lang.String) 

    /**
     * Method unmarshalC_linkpointer
     * 
     * 
     * 
     * @param reader
     * @return C_linkpointer
     */
    public static gov.nih.nci.hl7.map.impl.C_linkpointer unmarshalC_linkpointer(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (gov.nih.nci.hl7.map.impl.C_linkpointer) Unmarshaller.unmarshal(gov.nih.nci.hl7.map.impl.C_linkpointer.class, reader);
    } //-- gov.nih.nci.hl7.map.impl.C_linkpointer unmarshalC_linkpointer(java.io.Reader) 

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
