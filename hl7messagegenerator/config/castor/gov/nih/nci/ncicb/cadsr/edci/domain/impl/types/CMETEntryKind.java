/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: CMETEntryKind.java,v 1.1 2006-10-03 19:42:06 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Defines how the CMET may be entered
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:06 $
 */
public class CMETEntryKind implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The Act type
     */
    public static final int ACT_TYPE = 0;

    /**
     * The instance of the Act type
     */
    public static final CMETEntryKind ACT = new CMETEntryKind(ACT_TYPE, "Act");

    /**
     * The Entity type
     */
    public static final int ENTITY_TYPE = 1;

    /**
     * The instance of the Entity type
     */
    public static final CMETEntryKind ENTITY = new CMETEntryKind(ENTITY_TYPE, "Entity");

    /**
     * The RolePlayedOrPerformed type
     */
    public static final int ROLEPLAYEDORPERFORMED_TYPE = 2;

    /**
     * The instance of the RolePlayedOrPerformed type
     */
    public static final CMETEntryKind ROLEPLAYEDORPERFORMED = new CMETEntryKind(ROLEPLAYEDORPERFORMED_TYPE, "RolePlayedOrPerformed");

    /**
     * The RoleScopedOrPerformed type
     */
    public static final int ROLESCOPEDORPERFORMED_TYPE = 3;

    /**
     * The instance of the RoleScopedOrPerformed type
     */
    public static final CMETEntryKind ROLESCOPEDORPERFORMED = new CMETEntryKind(ROLESCOPEDORPERFORMED_TYPE, "RoleScopedOrPerformed");

    /**
     * The RolePerformedOnly type
     */
    public static final int ROLEPERFORMEDONLY_TYPE = 4;

    /**
     * The instance of the RolePerformedOnly type
     */
    public static final CMETEntryKind ROLEPERFORMEDONLY = new CMETEntryKind(ROLEPERFORMEDONLY_TYPE, "RolePerformedOnly");

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

    private CMETEntryKind(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.CMETEntryKind(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * CMETEntryKind
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
     * Returns the type of this CMETEntryKind
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
        members.put("Act", ACT);
        members.put("Entity", ENTITY);
        members.put("RolePlayedOrPerformed", ROLEPLAYEDORPERFORMED);
        members.put("RoleScopedOrPerformed", ROLESCOPEDORPERFORMED);
        members.put("RolePerformedOnly", ROLEPERFORMEDONLY);
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
     * Returns the String representation of this CMETEntryKind
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
     * Returns a new CMETEntryKind based on the given String value.
     * 
     * @param string
     * @return CMETEntryKind
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.CMETEntryKind valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid CMETEntryKind";
            throw new IllegalArgumentException(err);
        }
        return (CMETEntryKind) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.CMETEntryKind valueOf(java.lang.String) 

}
