/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: C_view.java,v 1.1 2006-10-03 17:38:42 marwahah Exp $
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
 * Class C_view.
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 17:38:42 $
 */
public class C_view implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _color
     */
    private java.lang.String _color;

    /**
     * Field _componentUuid
     */
    private java.lang.String _componentUuid;

    /**
     * Field _height
     */
    private int _height;

    /**
     * keeps track of state for field: _height
     */
    private boolean _has_height;

    /**
     * Field _width
     */
    private int _width;

    /**
     * keeps track of state for field: _width
     */
    private boolean _has_width;

    /**
     * Field _x
     */
    private int _x;

    /**
     * keeps track of state for field: _x
     */
    private boolean _has_x;

    /**
     * Field _y
     */
    private int _y;

    /**
     * keeps track of state for field: _y
     */
    private boolean _has_y;


      //----------------/
     //- Constructors -/
    //----------------/

    public C_view() 
     {
        super();
    } //-- gov.nih.nci.hl7.map.impl.C_view()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method deleteHeight
     * 
     */
    public void deleteHeight()
    {
        this._has_height= false;
    } //-- void deleteHeight() 

    /**
     * Method deleteWidth
     * 
     */
    public void deleteWidth()
    {
        this._has_width= false;
    } //-- void deleteWidth() 

    /**
     * Method deleteX
     * 
     */
    public void deleteX()
    {
        this._has_x= false;
    } //-- void deleteX() 

    /**
     * Method deleteY
     * 
     */
    public void deleteY()
    {
        this._has_y= false;
    } //-- void deleteY() 

    /**
     * Returns the value of field 'color'.
     * 
     * @return String
     * @return the value of field 'color'.
     */
    public java.lang.String getColor()
    {
        return this._color;
    } //-- java.lang.String getColor() 

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
     * Returns the value of field 'height'.
     * 
     * @return int
     * @return the value of field 'height'.
     */
    public int getHeight()
    {
        return this._height;
    } //-- int getHeight() 

    /**
     * Returns the value of field 'width'.
     * 
     * @return int
     * @return the value of field 'width'.
     */
    public int getWidth()
    {
        return this._width;
    } //-- int getWidth() 

    /**
     * Returns the value of field 'x'.
     * 
     * @return int
     * @return the value of field 'x'.
     */
    public int getX()
    {
        return this._x;
    } //-- int getX() 

    /**
     * Returns the value of field 'y'.
     * 
     * @return int
     * @return the value of field 'y'.
     */
    public int getY()
    {
        return this._y;
    } //-- int getY() 

    /**
     * Method hasHeight
     * 
     * 
     * 
     * @return boolean
     */
    public boolean hasHeight()
    {
        return this._has_height;
    } //-- boolean hasHeight() 

    /**
     * Method hasWidth
     * 
     * 
     * 
     * @return boolean
     */
    public boolean hasWidth()
    {
        return this._has_width;
    } //-- boolean hasWidth() 

    /**
     * Method hasX
     * 
     * 
     * 
     * @return boolean
     */
    public boolean hasX()
    {
        return this._has_x;
    } //-- boolean hasX() 

    /**
     * Method hasY
     * 
     * 
     * 
     * @return boolean
     */
    public boolean hasY()
    {
        return this._has_y;
    } //-- boolean hasY() 

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
     * Sets the value of field 'color'.
     * 
     * @param color the value of field 'color'.
     */
    public void setColor(java.lang.String color)
    {
        this._color = color;
    } //-- void setColor(java.lang.String) 

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
     * Sets the value of field 'height'.
     * 
     * @param height the value of field 'height'.
     */
    public void setHeight(int height)
    {
        this._height = height;
        this._has_height = true;
    } //-- void setHeight(int) 

    /**
     * Sets the value of field 'width'.
     * 
     * @param width the value of field 'width'.
     */
    public void setWidth(int width)
    {
        this._width = width;
        this._has_width = true;
    } //-- void setWidth(int) 

    /**
     * Sets the value of field 'x'.
     * 
     * @param x the value of field 'x'.
     */
    public void setX(int x)
    {
        this._x = x;
        this._has_x = true;
    } //-- void setX(int) 

    /**
     * Sets the value of field 'y'.
     * 
     * @param y the value of field 'y'.
     */
    public void setY(int y)
    {
        this._y = y;
        this._has_y = true;
    } //-- void setY(int) 

    /**
     * Method unmarshalC_view
     * 
     * 
     * 
     * @param reader
     * @return C_view
     */
    public static gov.nih.nci.hl7.map.impl.C_view unmarshalC_view(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (gov.nih.nci.hl7.map.impl.C_view) Unmarshaller.unmarshal(gov.nih.nci.hl7.map.impl.C_view.class, reader);
    } //-- gov.nih.nci.hl7.map.impl.C_view unmarshalC_view(java.io.Reader) 

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
