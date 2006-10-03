/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: C_component.java,v 1.1 2006-10-03 17:38:42 marwahah Exp $
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
 * Class C_component.
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 17:38:42 $
 */
public class C_component implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _group
     */
    private java.lang.String _group;

    /**
     * Field _kind
     */
    private java.lang.String _kind;

    /**
     * Field _location
     */
    private java.lang.Object _location;

    /**
     * Field _name
     */
    private java.lang.String _name;

    /**
     * Field _type
     */
    private java.lang.String _type;

    /**
     * Field _uuid
     */
    private java.lang.String _uuid;

    /**
     * Field _c_data
     */
    private gov.nih.nci.hl7.map.impl.C_data _c_data;


      //----------------/
     //- Constructors -/
    //----------------/

    public C_component() 
     {
        super();
    } //-- gov.nih.nci.hl7.map.impl.C_component()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'c_data'.
     * 
     * @return C_data
     * @return the value of field 'c_data'.
     */
    public gov.nih.nci.hl7.map.impl.C_data getC_data()
    {
        return this._c_data;
    } //-- gov.nih.nci.hl7.map.impl.C_data getC_data() 

    /**
     * Returns the value of field 'group'.
     * 
     * @return String
     * @return the value of field 'group'.
     */
    public java.lang.String getGroup()
    {
        return this._group;
    } //-- java.lang.String getGroup() 

    /**
     * Returns the value of field 'kind'.
     * 
     * @return String
     * @return the value of field 'kind'.
     */
    public java.lang.String getKind()
    {
        return this._kind;
    } //-- java.lang.String getKind() 

    /**
     * Returns the value of field 'location'.
     * 
     * @return Object
     * @return the value of field 'location'.
     */
    public java.lang.Object getLocation()
    {
        return this._location;
    } //-- java.lang.Object getLocation() 

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
     * Returns the value of field 'type'.
     * 
     * @return String
     * @return the value of field 'type'.
     */
    public java.lang.String getType()
    {
        return this._type;
    } //-- java.lang.String getType() 

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
     * Sets the value of field 'c_data'.
     * 
     * @param c_data the value of field 'c_data'.
     */
    public void setC_data(gov.nih.nci.hl7.map.impl.C_data c_data)
    {
        this._c_data = c_data;
    } //-- void setC_data(gov.nih.nci.hl7.map.impl.C_data) 

    /**
     * Sets the value of field 'group'.
     * 
     * @param group the value of field 'group'.
     */
    public void setGroup(java.lang.String group)
    {
        this._group = group;
    } //-- void setGroup(java.lang.String) 

    /**
     * Sets the value of field 'kind'.
     * 
     * @param kind the value of field 'kind'.
     */
    public void setKind(java.lang.String kind)
    {
        this._kind = kind;
    } //-- void setKind(java.lang.String) 

    /**
     * Sets the value of field 'location'.
     * 
     * @param location the value of field 'location'.
     */
    public void setLocation(java.lang.Object location)
    {
        this._location = location;
    } //-- void setLocation(java.lang.Object) 

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
     * Sets the value of field 'type'.
     * 
     * @param type the value of field 'type'.
     */
    public void setType(java.lang.String type)
    {
        this._type = type;
    } //-- void setType(java.lang.String) 

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
     * Method unmarshalC_component
     * 
     * 
     * 
     * @param reader
     * @return C_component
     */
    public static gov.nih.nci.hl7.map.impl.C_component unmarshalC_component(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (gov.nih.nci.hl7.map.impl.C_component) Unmarshaller.unmarshal(gov.nih.nci.hl7.map.impl.C_component.class, reader);
    } //-- gov.nih.nci.hl7.map.impl.C_component unmarshalC_component(java.io.Reader) 

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
