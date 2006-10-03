/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: DataElementConceptImpl.java,v 1.1 2006-10-03 19:42:06 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;

/**
 * A Data Element Concept is the abstract statement of a concept of
 * interest in a clinical trial, independent of its database
 * representation, which has been registered in a Metadata
 * registry.
 * 
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:06 $
 */
public class DataElementConceptImpl implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The name of this Data Element Concept.
     */
    private java.lang.String _name;

    /**
     * The globally-unique identifier for this object.
     */
    private java.lang.String _GUID;

    /**
     * The definition of this Data element concept.
     */
    private java.lang.String _definition;

    /**
     * Extended text description of the concept; may include a
     * statement of the clinical dimension measured by this concept.
     */
    private java.lang.String _description;


      //----------------/
     //- Constructors -/
    //----------------/

    public DataElementConceptImpl() 
     {
        super();
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementConceptImpl()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'definition'. The field
     * 'definition' has the following description: The definition
     * of this Data element concept.
     * 
     * @return String
     * @return the value of field 'definition'.
     */
    public java.lang.String getDefinition()
    {
        return this._definition;
    } //-- java.lang.String getDefinition() 

    /**
     * Returns the value of field 'description'. The field
     * 'description' has the following description: Extended text
     * description of the concept; may include a statement of the
     * clinical dimension measured by this concept.
     * 
     * @return String
     * @return the value of field 'description'.
     */
    public java.lang.String getDescription()
    {
        return this._description;
    } //-- java.lang.String getDescription() 

    /**
     * Returns the value of field 'GUID'. The field 'GUID' has the
     * following description: The globally-unique identifier for
     * this object.
     * 
     * @return String
     * @return the value of field 'GUID'.
     */
    public java.lang.String getGUID()
    {
        return this._GUID;
    } //-- java.lang.String getGUID() 

    /**
     * Returns the value of field 'name'. The field 'name' has the
     * following description: The name of this Data Element
     * Concept.
     * 
     * @return String
     * @return the value of field 'name'.
     */
    public java.lang.String getName()
    {
        return this._name;
    } //-- java.lang.String getName() 

    /**
     * Sets the value of field 'definition'. The field 'definition'
     * has the following description: The definition of this Data
     * element concept.
     * 
     * @param definition the value of field 'definition'.
     */
    public void setDefinition(java.lang.String definition)
    {
        this._definition = definition;
    } //-- void setDefinition(java.lang.String) 

    /**
     * Sets the value of field 'description'. The field
     * 'description' has the following description: Extended text
     * description of the concept; may include a statement of the
     * clinical dimension measured by this concept.
     * 
     * @param description the value of field 'description'.
     */
    public void setDescription(java.lang.String description)
    {
        this._description = description;
    } //-- void setDescription(java.lang.String) 

    /**
     * Sets the value of field 'GUID'. The field 'GUID' has the
     * following description: The globally-unique identifier for
     * this object.
     * 
     * @param GUID the value of field 'GUID'.
     */
    public void setGUID(java.lang.String GUID)
    {
        this._GUID = GUID;
    } //-- void setGUID(java.lang.String) 

    /**
     * Sets the value of field 'name'. The field 'name' has the
     * following description: The name of this Data Element
     * Concept.
     * 
     * @param name the value of field 'name'.
     */
    public void setName(java.lang.String name)
    {
        this._name = name;
    } //-- void setName(java.lang.String) 

}
