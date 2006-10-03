/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: C_segmentItem.java,v 1.1 2006-10-03 17:38:25 marwahah Exp $
 */

package gov.nih.nci.hl7.csv.meta.impl;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class C_segmentItem.
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 17:38:25 $
 */
public class C_segmentItem implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Internal choice value storage
     */
    private java.lang.Object _choiceValue;

    /**
     * Field _c_segment
     */
    private gov.nih.nci.hl7.csv.meta.impl.C_segment _c_segment;

    /**
     * Field _c_field
     */
    private gov.nih.nci.hl7.csv.meta.impl.C_field _c_field;


      //----------------/
     //- Constructors -/
    //----------------/

    public C_segmentItem() 
     {
        super();
    } //-- gov.nih.nci.hl7.csv.meta.impl.C_segmentItem()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'c_field'.
     * 
     * @return C_field
     * @return the value of field 'c_field'.
     */
    public gov.nih.nci.hl7.csv.meta.impl.C_field getC_field()
    {
        return this._c_field;
    } //-- gov.nih.nci.hl7.csv.meta.impl.C_field getC_field() 

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
     * Sets the value of field 'c_field'.
     * 
     * @param c_field the value of field 'c_field'.
     */
    public void setC_field(gov.nih.nci.hl7.csv.meta.impl.C_field c_field)
    {
        this._c_field = c_field;
        this._choiceValue = c_field;
    } //-- void setC_field(gov.nih.nci.hl7.csv.meta.impl.C_field) 

    /**
     * Sets the value of field 'c_segment'.
     * 
     * @param c_segment the value of field 'c_segment'.
     */
    public void setC_segment(gov.nih.nci.hl7.csv.meta.impl.C_segment c_segment)
    {
        this._c_segment = c_segment;
        this._choiceValue = c_segment;
    } //-- void setC_segment(gov.nih.nci.hl7.csv.meta.impl.C_segment) 

}
