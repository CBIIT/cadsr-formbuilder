/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: SubArtifactKind.java,v 1.1 2006-10-03 19:42:17 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Identifies the kind of sub artifacts that can be packaged within
 * an artifactUML: The name for a package in the package hierarchy
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:17 $
 */
public class SubArtifactKind implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The VD type
     */
    public static final int VD_TYPE = 0;

    /**
     * The instance of the VD type
     */
    public static final SubArtifactKind VD = new SubArtifactKind(VD_TYPE, "VD");

    /**
     * The VC type
     */
    public static final int VC_TYPE = 1;

    /**
     * The instance of the VC type
     */
    public static final SubArtifactKind VC = new SubArtifactKind(VC_TYPE, "VC");

    /**
     * The VS type
     */
    public static final int VS_TYPE = 2;

    /**
     * The instance of the VS type
     */
    public static final SubArtifactKind VS = new SubArtifactKind(VS_TYPE, "VS");

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

    private SubArtifactKind(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.SubArtifactKind(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * SubArtifactKind
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
     * Returns the type of this SubArtifactKind
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
        members.put("VD", VD);
        members.put("VC", VC);
        members.put("VS", VS);
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
     * Returns the String representation of this SubArtifactKind
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
     * Returns a new SubArtifactKind based on the given String
     * value.
     * 
     * @param string
     * @return SubArtifactKind
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.SubArtifactKind valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid SubArtifactKind";
            throw new IllegalArgumentException(err);
        }
        return (SubArtifactKind) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.SubArtifactKind valueOf(java.lang.String) 

}
