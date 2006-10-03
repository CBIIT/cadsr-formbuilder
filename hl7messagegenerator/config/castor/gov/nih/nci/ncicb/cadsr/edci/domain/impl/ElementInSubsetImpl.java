/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: ElementInSubsetImpl.java,v 1.1 2006-10-03 19:42:06 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;

/**
 * The value and ordinal position of a constituent of a Subset.
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:06 $
 */
public class ElementInSubsetImpl implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * May be a 'code' or a text string such as "1" or "Male" for
     * the database representation of the value meaning identifier.
     */
    private java.lang.String _value;

    /**
     * The position this Value should take among the all of the
     * Values in this Enumerated Value Domain Subset, when the
     * Subset's values are displayed in a list of values.
     */
    private int _sequenceNumber;

    /**
     * keeps track of state for field: _sequenceNumber
     */
    private boolean _has_sequenceNumber;


      //----------------/
     //- Constructors -/
    //----------------/

    public ElementInSubsetImpl() 
     {
        super();
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.ElementInSubsetImpl()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method deleteSequenceNumber
     * 
     */
    public void deleteSequenceNumber()
    {
        this._has_sequenceNumber= false;
    } //-- void deleteSequenceNumber() 

    /**
     * Returns the value of field 'sequenceNumber'. The field
     * 'sequenceNumber' has the following description: The position
     * this Value should take among the all of the Values in this
     * Enumerated Value Domain Subset, when the Subset's values are
     * displayed in a list of values.
     * 
     * @return int
     * @return the value of field 'sequenceNumber'.
     */
    public int getSequenceNumber()
    {
        return this._sequenceNumber;
    } //-- int getSequenceNumber() 

    /**
     * Returns the value of field 'value'. The field 'value' has
     * the following description: May be a 'code' or a text string
     * such as "1" or "Male" for the database representation of the
     * value meaning identifier.
     * 
     * @return String
     * @return the value of field 'value'.
     */
    public java.lang.String getValue()
    {
        return this._value;
    } //-- java.lang.String getValue() 

    /**
     * Method hasSequenceNumber
     * 
     * 
     * 
     * @return boolean
     */
    public boolean hasSequenceNumber()
    {
        return this._has_sequenceNumber;
    } //-- boolean hasSequenceNumber() 

    /**
     * Sets the value of field 'sequenceNumber'. The field
     * 'sequenceNumber' has the following description: The position
     * this Value should take among the all of the Values in this
     * Enumerated Value Domain Subset, when the Subset's values are
     * displayed in a list of values.
     * 
     * @param sequenceNumber the value of field 'sequenceNumber'.
     */
    public void setSequenceNumber(int sequenceNumber)
    {
        this._sequenceNumber = sequenceNumber;
        this._has_sequenceNumber = true;
    } //-- void setSequenceNumber(int) 

    /**
     * Sets the value of field 'value'. The field 'value' has the
     * following description: May be a 'code' or a text string such
     * as "1" or "Male" for the database representation of the
     * value meaning identifier.
     * 
     * @param value the value of field 'value'.
     */
    public void setValue(java.lang.String value)
    {
        this._value = value;
    } //-- void setValue(java.lang.String) 

}
