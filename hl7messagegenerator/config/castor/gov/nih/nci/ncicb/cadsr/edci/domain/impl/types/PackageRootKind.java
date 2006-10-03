/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: PackageRootKind.java,v 1.1 2006-10-03 19:42:17 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Identifies the primary 'purpose' of the packaged contentUML: The
 * name for the initial set of packages within the 'HL7v3' package
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:17 $
 */
public class PackageRootKind implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The DEFN type
     */
    public static final int DEFN_TYPE = 0;

    /**
     * The instance of the DEFN type
     */
    public static final PackageRootKind DEFN = new PackageRootKind(DEFN_TYPE, "DEFN");

    /**
     * The BAL type
     */
    public static final int BAL_TYPE = 1;

    /**
     * The instance of the BAL type
     */
    public static final PackageRootKind BAL = new PackageRootKind(BAL_TYPE, "BAL");

    /**
     * The PUB type
     */
    public static final int PUB_TYPE = 2;

    /**
     * The instance of the PUB type
     */
    public static final PackageRootKind PUB = new PackageRootKind(PUB_TYPE, "PUB");

    /**
     * The PROF type
     */
    public static final int PROF_TYPE = 3;

    /**
     * The instance of the PROF type
     */
    public static final PackageRootKind PROF = new PackageRootKind(PROF_TYPE, "PROF");

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

    private PackageRootKind(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.PackageRootKind(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * PackageRootKind
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
     * Returns the type of this PackageRootKind
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
        members.put("DEFN", DEFN);
        members.put("BAL", BAL);
        members.put("PUB", PUB);
        members.put("PROF", PROF);
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
     * Returns the String representation of this PackageRootKind
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
     * Returns a new PackageRootKind based on the given String
     * value.
     * 
     * @param string
     * @return PackageRootKind
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.PackageRootKind valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid PackageRootKind";
            throw new IllegalArgumentException(err);
        }
        return (PackageRootKind) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.PackageRootKind valueOf(java.lang.String) 

}
