/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: C_attributeItem.java,v 1.1 2006-10-03 17:38:25 marwahah Exp $
 */

package gov.nih.nci.hl7.clone.meta.impl;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class C_attributeItem.
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 17:38:25 $
 */
public class C_attributeItem implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Internal choice value storage
     */
    private java.lang.Object _choiceValue;

    /**
     * Field _c_attribute
     */
    private gov.nih.nci.hl7.clone.meta.impl.C_attribute _c_attribute;

    /**
     * Field _c_datatypeField
     */
    private gov.nih.nci.hl7.clone.meta.impl.C_datatypeField _c_datatypeField;


      //----------------/
     //- Constructors -/
    //----------------/

    public C_attributeItem() 
     {
        super();
    } //-- gov.nih.nci.hl7.clone.meta.impl.C_attributeItem()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'c_attribute'.
     * 
     * @return C_attribute
     * @return the value of field 'c_attribute'.
     */
    public gov.nih.nci.hl7.clone.meta.impl.C_attribute getC_attribute()
    {
        return this._c_attribute;
    } //-- gov.nih.nci.hl7.clone.meta.impl.C_attribute getC_attribute() 

    /**
     * Returns the value of field 'c_datatypeField'.
     * 
     * @return C_datatypeField
     * @return the value of field 'c_datatypeField'.
     */
    public gov.nih.nci.hl7.clone.meta.impl.C_datatypeField getC_datatypeField()
    {
        return this._c_datatypeField;
    } //-- gov.nih.nci.hl7.clone.meta.impl.C_datatypeField getC_datatypeField() 

    /**
     * Returns the value of field 'choiceValue'. The field
     * 'choiceValue' has the following description: Internal choice
     * value storage
     * 
     * @return Object
     * @return the value of field 'choiceValue'.
     */
    public java.lang.Object getChoiceValue()
    {
        return this._choiceValue;
    } //-- java.lang.Object getChoiceValue() 

    /**
     * Sets the value of field 'c_attribute'.
     * 
     * @param c_attribute the value of field 'c_attribute'.
     */
    public void setC_attribute(gov.nih.nci.hl7.clone.meta.impl.C_attribute c_attribute)
    {
        this._c_attribute = c_attribute;
        this._choiceValue = c_attribute;
    } //-- void setC_attribute(gov.nih.nci.hl7.clone.meta.impl.C_attribute) 

    /**
     * Sets the value of field 'c_datatypeField'.
     * 
     * @param c_datatypeField the value of field 'c_datatypeField'.
     */
    public void setC_datatypeField(gov.nih.nci.hl7.clone.meta.impl.C_datatypeField c_datatypeField)
    {
        this._c_datatypeField = c_datatypeField;
        this._choiceValue = c_datatypeField;
    } //-- void setC_datatypeField(gov.nih.nci.hl7.clone.meta.impl.C_datatypeField) 

}
