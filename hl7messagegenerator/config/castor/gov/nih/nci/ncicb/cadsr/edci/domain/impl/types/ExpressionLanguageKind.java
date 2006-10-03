/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: ExpressionLanguageKind.java,v 1.1 2006-10-03 19:42:17 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Identifies language that constraints can be expressed in.
 * (Excludes OCL, which is the default)UML: Type used by a complex
 * stereotype
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:17 $
 */
public class ExpressionLanguageKind implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The XPath type
     */
    public static final int XPATH_TYPE = 0;

    /**
     * The instance of the XPath type
     */
    public static final ExpressionLanguageKind XPATH = new ExpressionLanguageKind(XPATH_TYPE, "XPath");

    /**
     * The HL7DatatypeDefinitionLanguage type
     */
    public static final int HL7DATATYPEDEFINITIONLANGUAGE_TYPE = 1;

    /**
     * The instance of the HL7DatatypeDefinitionLanguage type
     */
    public static final ExpressionLanguageKind HL7DATATYPEDEFINITIONLANGUAGE = new ExpressionLanguageKind(HL7DATATYPEDEFINITIONLANGUAGE_TYPE, "HL7DatatypeDefinitionLanguage");

    /**
     * The RegularExpression type
     */
    public static final int REGULAREXPRESSION_TYPE = 2;

    /**
     * The instance of the RegularExpression type
     */
    public static final ExpressionLanguageKind REGULAREXPRESSION = new ExpressionLanguageKind(REGULAREXPRESSION_TYPE, "RegularExpression");

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

    private ExpressionLanguageKind(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.ExpressionLanguageKind(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * ExpressionLanguageKind
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
     * Returns the type of this ExpressionLanguageKind
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
        members.put("XPath", XPATH);
        members.put("HL7DatatypeDefinitionLanguage", HL7DATATYPEDEFINITIONLANGUAGE);
        members.put("RegularExpression", REGULAREXPRESSION);
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
     * ExpressionLanguageKind
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
     * Returns a new ExpressionLanguageKind based on the given
     * String value.
     * 
     * @param string
     * @return ExpressionLanguageKind
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.ExpressionLanguageKind valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid ExpressionLanguageKind";
            throw new IllegalArgumentException(err);
        }
        return (ExpressionLanguageKind) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.ExpressionLanguageKind valueOf(java.lang.String) 

}
