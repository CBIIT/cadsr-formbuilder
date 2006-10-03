/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: C_function.java,v 1.1 2006-10-03 17:38:26 marwahah Exp $
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
 * Class C_function.
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 17:38:26 $
 */
public class C_function implements java.io.Serializable {


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
     * Field _c_inputs
     */
    private gov.nih.nci.hl7.function.impl.C_inputs _c_inputs;

    /**
     * Field _c_outputs
     */
    private gov.nih.nci.hl7.function.impl.C_outputs _c_outputs;

    /**
     * Field _c_implementation
     */
    private gov.nih.nci.hl7.function.impl.C_implementation _c_implementation;


      //----------------/
     //- Constructors -/
    //----------------/

    public C_function() 
     {
        super();
    } //-- gov.nih.nci.hl7.function.impl.C_function()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'c_implementation'.
     * 
     * @return C_implementation
     * @return the value of field 'c_implementation'.
     */
    public gov.nih.nci.hl7.function.impl.C_implementation getC_implementation()
    {
        return this._c_implementation;
    } //-- gov.nih.nci.hl7.function.impl.C_implementation getC_implementation() 

    /**
     * Returns the value of field 'c_inputs'.
     * 
     * @return C_inputs
     * @return the value of field 'c_inputs'.
     */
    public gov.nih.nci.hl7.function.impl.C_inputs getC_inputs()
    {
        return this._c_inputs;
    } //-- gov.nih.nci.hl7.function.impl.C_inputs getC_inputs() 

    /**
     * Returns the value of field 'c_outputs'.
     * 
     * @return C_outputs
     * @return the value of field 'c_outputs'.
     */
    public gov.nih.nci.hl7.function.impl.C_outputs getC_outputs()
    {
        return this._c_outputs;
    } //-- gov.nih.nci.hl7.function.impl.C_outputs getC_outputs() 

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
     * Sets the value of field 'c_implementation'.
     * 
     * @param c_implementation the value of field 'c_implementation'
     */
    public void setC_implementation(gov.nih.nci.hl7.function.impl.C_implementation c_implementation)
    {
        this._c_implementation = c_implementation;
    } //-- void setC_implementation(gov.nih.nci.hl7.function.impl.C_implementation) 

    /**
     * Sets the value of field 'c_inputs'.
     * 
     * @param c_inputs the value of field 'c_inputs'.
     */
    public void setC_inputs(gov.nih.nci.hl7.function.impl.C_inputs c_inputs)
    {
        this._c_inputs = c_inputs;
    } //-- void setC_inputs(gov.nih.nci.hl7.function.impl.C_inputs) 

    /**
     * Sets the value of field 'c_outputs'.
     * 
     * @param c_outputs the value of field 'c_outputs'.
     */
    public void setC_outputs(gov.nih.nci.hl7.function.impl.C_outputs c_outputs)
    {
        this._c_outputs = c_outputs;
    } //-- void setC_outputs(gov.nih.nci.hl7.function.impl.C_outputs) 

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
     * Method unmarshalC_function
     * 
     * 
     * 
     * @param reader
     * @return C_function
     */
    public static gov.nih.nci.hl7.function.impl.C_function unmarshalC_function(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (gov.nih.nci.hl7.function.impl.C_function) Unmarshaller.unmarshal(gov.nih.nci.hl7.function.impl.C_function.class, reader);
    } //-- gov.nih.nci.hl7.function.impl.C_function unmarshalC_function(java.io.Reader) 

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
