/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: PackageKind.java,v 1.1 2006-10-03 19:42:17 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Identifies the 'level' associated with a particular packageUML:
 * stored in a tag on Package stereotypes
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:17 $
 */
public class PackageKind implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The root type
     */
    public static final int ROOT_TYPE = 0;

    /**
     * The instance of the root type
     */
    public static final PackageKind ROOT = new PackageKind(ROOT_TYPE, "root");

    /**
     * The section type
     */
    public static final int SECTION_TYPE = 1;

    /**
     * The instance of the section type
     */
    public static final PackageKind SECTION = new PackageKind(SECTION_TYPE, "section");

    /**
     * The subSection type
     */
    public static final int SUBSECTION_TYPE = 2;

    /**
     * The instance of the subSection type
     */
    public static final PackageKind SUBSECTION = new PackageKind(SUBSECTION_TYPE, "subSection");

    /**
     * The domain type
     */
    public static final int DOMAIN_TYPE = 3;

    /**
     * The instance of the domain type
     */
    public static final PackageKind DOMAIN = new PackageKind(DOMAIN_TYPE, "domain");

    /**
     * The realmNamespace type
     */
    public static final int REALMNAMESPACE_TYPE = 4;

    /**
     * The instance of the realmNamespace type
     */
    public static final PackageKind REALMNAMESPACE = new PackageKind(REALMNAMESPACE_TYPE, "realmNamespace");

    /**
     * The version type
     */
    public static final int VERSION_TYPE = 5;

    /**
     * The instance of the version type
     */
    public static final PackageKind VERSION = new PackageKind(VERSION_TYPE, "version");

    /**
     * The artifact type
     */
    public static final int ARTIFACT_TYPE = 6;

    /**
     * The instance of the artifact type
     */
    public static final PackageKind ARTIFACT = new PackageKind(ARTIFACT_TYPE, "artifact");

    /**
     * The subArtifact type
     */
    public static final int SUBARTIFACT_TYPE = 7;

    /**
     * The instance of the subArtifact type
     */
    public static final PackageKind SUBARTIFACT = new PackageKind(SUBARTIFACT_TYPE, "subArtifact");

    /**
     * The name type
     */
    public static final int NAME_TYPE = 8;

    /**
     * The instance of the name type
     */
    public static final PackageKind NAME = new PackageKind(NAME_TYPE, "name");

    /**
     * The id type
     */
    public static final int ID_TYPE = 9;

    /**
     * The instance of the id type
     */
    public static final PackageKind ID = new PackageKind(ID_TYPE, "id");

    /**
     * The creatorId type
     */
    public static final int CREATORID_TYPE = 10;

    /**
     * The instance of the creatorId type
     */
    public static final PackageKind CREATORID = new PackageKind(CREATORID_TYPE, "creatorId");

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

    private PackageKind(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.PackageKind(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * PackageKind
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
     * Returns the type of this PackageKind
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
        members.put("root", ROOT);
        members.put("section", SECTION);
        members.put("subSection", SUBSECTION);
        members.put("domain", DOMAIN);
        members.put("realmNamespace", REALMNAMESPACE);
        members.put("version", VERSION);
        members.put("artifact", ARTIFACT);
        members.put("subArtifact", SUBARTIFACT);
        members.put("name", NAME);
        members.put("id", ID);
        members.put("creatorId", CREATORID);
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
     * Returns the String representation of this PackageKind
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
     * Returns a new PackageKind based on the given String value.
     * 
     * @param string
     * @return PackageKind
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.PackageKind valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid PackageKind";
            throw new IllegalArgumentException(err);
        }
        return (PackageKind) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.PackageKind valueOf(java.lang.String) 

}
