/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: DefinedMappingSourceKind.java,v 1.1 2006-10-03 19:42:07 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Lists 'pre-defined' standards that can be mapped to.
 * (Predefinition helps ensure consistency of spelling,
 * capitalization, etc.)UML: type for an object-typed stereotype
 * tag
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:07 $
 */
public class DefinedMappingSourceKind implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The HL7v2 type
     */
    public static final int HL7V2_TYPE = 0;

    /**
     * The instance of the HL7v2 type
     */
    public static final DefinedMappingSourceKind HL7V2 = new DefinedMappingSourceKind(HL7V2_TYPE, "HL7v2");

    /**
     * The DICOM type
     */
    public static final int DICOM_TYPE = 1;

    /**
     * The instance of the DICOM type
     */
    public static final DefinedMappingSourceKind DICOM = new DefinedMappingSourceKind(DICOM_TYPE, "DICOM");

    /**
     * The Analysis Model type
     */
    public static final int ANALYSIS_MODEL_TYPE = 2;

    /**
     * The instance of the Analysis Model type
     */
    public static final DefinedMappingSourceKind ANALYSIS_MODEL = new DefinedMappingSourceKind(ANALYSIS_MODEL_TYPE, "Analysis Model");

    /**
     * Field _memberTable
     */
    private static java.util.Hashtable _memberTable = init();

    /**
     * Field type
     */
    private int type = -1;

    /**
     * Field stringValue
     */
    private java.lang.String stringValue = null;


      //----------------/
     //- Constructors -/
    //----------------/

    private DefinedMappingSourceKind(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.DefinedMappingSourceKind(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * DefinedMappingSourceKind
     * 
     * @return Enumeration
     */
    public static java.util.Enumeration enumerate()
    {
        return _memberTable.elements();
    } //-- java.util.Enumeration enumerate() 

    /**
     * Method getType
     * 
     * Returns the type of this DefinedMappingSourceKind
     * 
     * @return int
     */
    public int getType()
    {
        return this.type;
    } //-- int getType() 

    /**
     * Method init
     * 
     * 
     * 
     * @return Hashtable
     */
    private static java.util.Hashtable init()
    {
        Hashtable members = new Hashtable();
        members.put("HL7v2", HL7V2);
        members.put("DICOM", DICOM);
        members.put("Analysis Model", ANALYSIS_MODEL);
        return members;
    } //-- java.util.Hashtable init() 

    /**
     * Method readResolve
     * 
     *  will be called during deserialization to replace the
     * deserialized object with the correct constant instance.
     * <br/>
     * 
     * @return Object
     */
    private java.lang.Object readResolve()
    {
        return valueOf(this.stringValue);
    } //-- java.lang.Object readResolve() 

    /**
     * Method toString
     * 
     * Returns the String representation of this
     * DefinedMappingSourceKind
     * 
     * @return String
     */
    public java.lang.String toString()
    {
        return this.stringValue;
    } //-- java.lang.String toString() 

    /**
     * Method valueOf
     * 
     * Returns a new DefinedMappingSourceKind based on the given
     * String value.
     * 
     * @param string
     * @return DefinedMappingSourceKind
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.DefinedMappingSourceKind valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid DefinedMappingSourceKind";
            throw new IllegalArgumentException(err);
        }
        return (DefinedMappingSourceKind) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.DefinedMappingSourceKind valueOf(java.lang.String) 

}
