/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: AlternateDesignationImpl.java,v 1.1 2006-10-03 19:42:06 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;

/**
 * A purpose-specific alternate name for this Data Element
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:06 $
 */
public class AlternateDesignationImpl implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * This attribute defines the intended use of the designation.
     * Type is not constrained by a codelist. Possibilities:
     * abbreviation (meaning that Name is to be treated as an
     * abbreviation for the Data Element, in the specified
     * language); keyword (meaning that Name is is keyword for
     * categorizing the Data Element, in the specified language);
     * SAS (expressing the intent that the Data Element, when
     * represented in SAS, is to be given the name "Name"); SDTM
     * (meaning that the Data Element, when represented in CDISC
     * SDTM, is to be given the name "Name").
     */
    private java.lang.String _type;

    /**
     * The string to be used as the alternate designation, for the
     * specified type of use and language. E.g., if Concept is
     * "DateOfBirth" and Type is SASname, Name might be "DOB", and
     * Language would be "*".
     */
    private java.lang.String _name;

    /**
     * Language for which this Alternate Designation applies.
     * Language should be expressed as a 2-character code, using
     * ISO 639-1 codes. Language can also be the string "*",
     * meaning the designation is not language-specific.
     */
    private java.lang.String _language;


      //----------------/
     //- Constructors -/
    //----------------/

    public AlternateDesignationImpl() 
     {
        super();
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.AlternateDesignationImpl()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'language'. The field 'language'
     * has the following description: Language for which this
     * Alternate Designation applies. Language should be expressed
     * as a 2-character code, using ISO 639-1 codes. Language can
     * also be the string "*", meaning the designation is not
     * language-specific.
     * 
     * @return String
     * @return the value of field 'language'.
     */
    public java.lang.String getLanguage()
    {
        return this._language;
    } //-- java.lang.String getLanguage() 

    /**
     * Returns the value of field 'name'. The field 'name' has the
     * following description: The string to be used as the
     * alternate designation, for the specified type of use and
     * language. E.g., if Concept is "DateOfBirth" and Type is
     * SASname, Name might be "DOB", and Language would be "*".
     * 
     * @return String
     * @return the value of field 'name'.
     */
    public java.lang.String getName()
    {
        return this._name;
    } //-- java.lang.String getName() 

    /**
     * Returns the value of field 'type'. The field 'type' has the
     * following description: This attribute defines the intended
     * use of the designation. Type is not constrained by a
     * codelist. Possibilities: abbreviation (meaning that Name is
     * to be treated as an abbreviation for the Data Element, in
     * the specified language); keyword (meaning that Name is is
     * keyword for categorizing the Data Element, in the specified
     * language); SAS (expressing the intent that the Data Element,
     * when represented in SAS, is to be given the name "Name");
     * SDTM (meaning that the Data Element, when represented in
     * CDISC SDTM, is to be given the name "Name").
     * 
     * @return String
     * @return the value of field 'type'.
     */
    public java.lang.String getType()
    {
        return this._type;
    } //-- java.lang.String getType() 

    /**
     * Sets the value of field 'language'. The field 'language' has
     * the following description: Language for which this Alternate
     * Designation applies. Language should be expressed as a
     * 2-character code, using ISO 639-1 codes. Language can also
     * be the string "*", meaning the designation is not
     * language-specific.
     * 
     * @param language the value of field 'language'.
     */
    public void setLanguage(java.lang.String language)
    {
        this._language = language;
    } //-- void setLanguage(java.lang.String) 

    /**
     * Sets the value of field 'name'. The field 'name' has the
     * following description: The string to be used as the
     * alternate designation, for the specified type of use and
     * language. E.g., if Concept is "DateOfBirth" and Type is
     * SASname, Name might be "DOB", and Language would be "*".
     * 
     * @param name the value of field 'name'.
     */
    public void setName(java.lang.String name)
    {
        this._name = name;
    } //-- void setName(java.lang.String) 

    /**
     * Sets the value of field 'type'. The field 'type' has the
     * following description: This attribute defines the intended
     * use of the designation. Type is not constrained by a
     * codelist. Possibilities: abbreviation (meaning that Name is
     * to be treated as an abbreviation for the Data Element, in
     * the specified language); keyword (meaning that Name is is
     * keyword for categorizing the Data Element, in the specified
     * language); SAS (expressing the intent that the Data Element,
     * when represented in SAS, is to be given the name "Name");
     * SDTM (meaning that the Data Element, when represented in
     * CDISC SDTM, is to be given the name "Name").
     * 
     * @param type the value of field 'type'.
     */
    public void setType(java.lang.String type)
    {
        this._type = type;
    } //-- void setType(java.lang.String) 

}
