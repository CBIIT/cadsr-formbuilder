/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: DerivationRelationshipKind.java,v 1.1 2006-10-03 19:42:17 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * The allowed derivation relationships between two artifacts
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:17 $
 */
public class DerivationRelationshipKind implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The restriction type
     */
    public static final int RESTRICTION_TYPE = 0;

    /**
     * The instance of the restriction type
     */
    public static final DerivationRelationshipKind RESTRICTION = new DerivationRelationshipKind(RESTRICTION_TYPE, "restriction");

    /**
     * The extension type
     */
    public static final int EXTENSION_TYPE = 1;

    /**
     * The instance of the extension type
     */
    public static final DerivationRelationshipKind EXTENSION = new DerivationRelationshipKind(EXTENSION_TYPE, "extension");

    /**
     * The conflicting type
     */
    public static final int CONFLICTING_TYPE = 2;

    /**
     * The instance of the conflicting type
     */
    public static final DerivationRelationshipKind CONFLICTING = new DerivationRelationshipKind(CONFLICTING_TYPE, "conflicting");

    /**
     * The annotated type
     */
    public static final int ANNOTATED_TYPE = 3;

    /**
     * The instance of the annotated type
     */
    public static final DerivationRelationshipKind ANNOTATED = new DerivationRelationshipKind(ANNOTATED_TYPE, "annotated");

    /**
     * The unchanged type
     */
    public static final int UNCHANGED_TYPE = 4;

    /**
     * The instance of the unchanged type
     */
    public static final DerivationRelationshipKind UNCHANGED = new DerivationRelationshipKind(UNCHANGED_TYPE, "unchanged");

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

    private DerivationRelationshipKind(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.DerivationRelationshipKind(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * DerivationRelationshipKind
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
     * Returns the type of this DerivationRelationshipKind
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
        members.put("restriction", RESTRICTION);
        members.put("extension", EXTENSION);
        members.put("conflicting", CONFLICTING);
        members.put("annotated", ANNOTATED);
        members.put("unchanged", UNCHANGED);
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
     * DerivationRelationshipKind
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
     * Returns a new DerivationRelationshipKind based on the given
     * String value.
     * 
     * @param string
     * @return DerivationRelationshipKind
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.DerivationRelationshipKind valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid DerivationRelationshipKind";
            throw new IllegalArgumentException(err);
        }
        return (DerivationRelationshipKind) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.DerivationRelationshipKind valueOf(java.lang.String) 

}
