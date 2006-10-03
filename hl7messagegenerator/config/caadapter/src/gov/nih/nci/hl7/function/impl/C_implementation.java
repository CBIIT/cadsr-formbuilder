/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: C_implementation.java,v 1.1 2006-10-03 17:38:26 marwahah Exp $
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
 * Class C_implementation.
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 17:38:26 $
 */
public class C_implementation implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _classname
     */
    private java.lang.Object _classname;

    /**
     * Field _method
     */
    private java.lang.String _method;


      //----------------/
     //- Constructors -/
    //----------------/

    public C_implementation() 
     {
        super();
    } //-- gov.nih.nci.hl7.function.impl.C_implementation()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'classname'.
     * 
     * @return Object
     * @return the value of field 'classname'.
     */
    public java.lang.Object getClassname()
    {
        return this._classname;
    } //-- java.lang.Object getClassname() 

    /**
     * Returns the value of field 'method'.
     * 
     * @return String
     * @return the value of field 'method'.
     */
    public java.lang.String getMethod()
    {
        return this._method;
    } //-- java.lang.String getMethod() 

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
     * Sets the value of field 'classname'.
     * 
     * @param classname the value of field 'classname'.
     */
    public void setClassname(java.lang.Object classname)
    {
        this._classname = classname;
    } //-- void setClassname(java.lang.Object) 

    /**
     * Sets the value of field 'method'.
     * 
     * @param method the value of field 'method'.
     */
    public void setMethod(java.lang.String method)
    {
        this._method = method;
    } //-- void setMethod(java.lang.String) 

    /**
     * Method unmarshalC_implementation
     * 
     * 
     * 
     * @param reader
     * @return C_implementation
     */
    public static gov.nih.nci.hl7.function.impl.C_implementation unmarshalC_implementation(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (gov.nih.nci.hl7.function.impl.C_implementation) Unmarshaller.unmarshal(gov.nih.nci.hl7.function.impl.C_implementation.class, reader);
    } //-- gov.nih.nci.hl7.function.impl.C_implementation unmarshalC_implementation(java.io.Reader) 

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
