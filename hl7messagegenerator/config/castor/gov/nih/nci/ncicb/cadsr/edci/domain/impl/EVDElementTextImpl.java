/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: EVDElementTextImpl.java,v 1.1 2006-10-03 19:42:06 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;

/**
 * Language-specific texts associated with a particular value in an
 * enumerated value domain.
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:06 $
 */
public class EVDElementTextImpl implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * language in which the text is expressed. Language is
     * specified using an ISO standard designation.
     */
    private java.lang.String _language;

    /**
     * The entry's Value, as represented in text.
     */
    private java.lang.String _valueMeaning;

    /**
     * The description of the value meaning identifier.
     */
    private java.lang.String _valueMeaningDescription;

    /**
     * Optional text containing additional information about the
     * meaning of this Value. If the receiving application supports
     * it, this text would be displayed on demand when the checkbox
     * for the value element has focus.
     */
    private java.lang.String _helpText;


      //----------------/
     //- Constructors -/
    //----------------/

    public EVDElementTextImpl() 
     {
        super();
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDElementTextImpl()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'helpText'. The field 'helpText'
     * has the following description: Optional text containing
     * additional information about the meaning of this Value. If
     * the receiving application supports it, this text would be
     * displayed on demand when the checkbox for the value element
     * has focus.
     * 
     * @return String
     * @return the value of field 'helpText'.
     */
    public java.lang.String getHelpText()
    {
        return this._helpText;
    } //-- java.lang.String getHelpText() 

    /**
     * Returns the value of field 'language'. The field 'language'
     * has the following description: language in which the text is
     * expressed. Language is specified using an ISO standard
     * designation.
     * 
     * @return String
     * @return the value of field 'language'.
     */
    public java.lang.String getLanguage()
    {
        return this._language;
    } //-- java.lang.String getLanguage() 

    /**
     * Returns the value of field 'valueMeaning'. The field
     * 'valueMeaning' has the following description: The entry's
     * Value, as represented in text.
     * 
     * @return String
     * @return the value of field 'valueMeaning'.
     */
    public java.lang.String getValueMeaning()
    {
        return this._valueMeaning;
    } //-- java.lang.String getValueMeaning() 

    /**
     * Returns the value of field 'valueMeaningDescription'. The
     * field 'valueMeaningDescription' has the following
     * description: The description of the value meaning
     * identifier.
     * 
     * @return String
     * @return the value of field 'valueMeaningDescription'.
     */
    public java.lang.String getValueMeaningDescription()
    {
        return this._valueMeaningDescription;
    } //-- java.lang.String getValueMeaningDescription() 

    /**
     * Sets the value of field 'helpText'. The field 'helpText' has
     * the following description: Optional text containing
     * additional information about the meaning of this Value. If
     * the receiving application supports it, this text would be
     * displayed on demand when the checkbox for the value element
     * has focus.
     * 
     * @param helpText the value of field 'helpText'.
     */
    public void setHelpText(java.lang.String helpText)
    {
        this._helpText = helpText;
    } //-- void setHelpText(java.lang.String) 

    /**
     * Sets the value of field 'language'. The field 'language' has
     * the following description: language in which the text is
     * expressed. Language is specified using an ISO standard
     * designation.
     * 
     * @param language the value of field 'language'.
     */
    public void setLanguage(java.lang.String language)
    {
        this._language = language;
    } //-- void setLanguage(java.lang.String) 

    /**
     * Sets the value of field 'valueMeaning'. The field
     * 'valueMeaning' has the following description: The entry's
     * Value, as represented in text.
     * 
     * @param valueMeaning the value of field 'valueMeaning'.
     */
    public void setValueMeaning(java.lang.String valueMeaning)
    {
        this._valueMeaning = valueMeaning;
    } //-- void setValueMeaning(java.lang.String) 

    /**
     * Sets the value of field 'valueMeaningDescription'. The field
     * 'valueMeaningDescription' has the following description: The
     * description of the value meaning identifier.
     * 
     * @param valueMeaningDescription the value of field
     * 'valueMeaningDescription'.
     */
    public void setValueMeaningDescription(java.lang.String valueMeaningDescription)
    {
        this._valueMeaningDescription = valueMeaningDescription;
    } //-- void setValueMeaningDescription(java.lang.String) 

}
