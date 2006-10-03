/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: DefinedRoleKind.java,v 1.1 2006-10-03 19:42:16 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Lists pre-defined roles that will commonly be used. (Ensures
 * consistency of spelling, capitalization, etc.)UML: Type used in
 * a complex tag value
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:16 $
 */
public class DefinedRoleKind implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The Committee co-chair type
     */
    public static final int COMMITTEE_CO_CHAIR_TYPE = 0;

    /**
     * The instance of the Committee co-chair type
     */
    public static final DefinedRoleKind COMMITTEE_CO_CHAIR = new DefinedRoleKind(COMMITTEE_CO_CHAIR_TYPE, "Committee co-chair");

    /**
     * The Contact type
     */
    public static final int CONTACT_TYPE = 1;

    /**
     * The instance of the Contact type
     */
    public static final DefinedRoleKind CONTACT = new DefinedRoleKind(CONTACT_TYPE, "Contact");

    /**
     * The Editor type
     */
    public static final int EDITOR_TYPE = 2;

    /**
     * The instance of the Editor type
     */
    public static final DefinedRoleKind EDITOR = new DefinedRoleKind(EDITOR_TYPE, "Editor");

    /**
     * The Primary Contributor type
     */
    public static final int PRIMARY_CONTRIBUTOR_TYPE = 3;

    /**
     * The instance of the Primary Contributor type
     */
    public static final DefinedRoleKind PRIMARY_CONTRIBUTOR = new DefinedRoleKind(PRIMARY_CONTRIBUTOR_TYPE, "Primary Contributor");

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

    private DefinedRoleKind(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.DefinedRoleKind(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * DefinedRoleKind
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
     * Returns the type of this DefinedRoleKind
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
        members.put("Committee co-chair", COMMITTEE_CO_CHAIR);
        members.put("Contact", CONTACT);
        members.put("Editor", EDITOR);
        members.put("Primary Contributor", PRIMARY_CONTRIBUTOR);
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
     * Returns the String representation of this DefinedRoleKind
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
     * Returns a new DefinedRoleKind based on the given String
     * value.
     * 
     * @param string
     * @return DefinedRoleKind
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.DefinedRoleKind valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid DefinedRoleKind";
            throw new IllegalArgumentException(err);
        }
        return (DefinedRoleKind) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.DefinedRoleKind valueOf(java.lang.String) 

}
