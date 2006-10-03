/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: ParentArtifactKind.java,v 1.1 2006-10-03 19:42:17 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * A list of the types of artifacts that can be referenced
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:17 $
 */
public class ParentArtifactKind implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The datatype type
     */
    public static final int DATATYPE_TYPE = 0;

    /**
     * The instance of the datatype type
     */
    public static final ParentArtifactKind DATATYPE = new ParentArtifactKind(DATATYPE_TYPE, "datatype");

    /**
     * The property type
     */
    public static final int PROPERTY_TYPE = 1;

    /**
     * The instance of the property type
     */
    public static final ParentArtifactKind PROPERTY = new ParentArtifactKind(PROPERTY_TYPE, "property");

    /**
     * The class type
     */
    public static final int CLASS_TYPE = 2;

    /**
     * The instance of the class type
     */
    public static final ParentArtifactKind CLASS = new ParentArtifactKind(CLASS_TYPE, "class");

    /**
     * The attribute type
     */
    public static final int ATTRIBUTE_TYPE = 3;

    /**
     * The instance of the attribute type
     */
    public static final ParentArtifactKind ATTRIBUTE = new ParentArtifactKind(ATTRIBUTE_TYPE, "attribute");

    /**
     * The relationship type
     */
    public static final int RELATIONSHIP_TYPE = 4;

    /**
     * The instance of the relationship type
     */
    public static final ParentArtifactKind RELATIONSHIP = new ParentArtifactKind(RELATIONSHIP_TYPE, "relationship");

    /**
     * The trigger type
     */
    public static final int TRIGGER_TYPE = 5;

    /**
     * The instance of the trigger type
     */
    public static final ParentArtifactKind TRIGGER = new ParentArtifactKind(TRIGGER_TYPE, "trigger");

    /**
     * The appRole type
     */
    public static final int APPROLE_TYPE = 6;

    /**
     * The instance of the appRole type
     */
    public static final ParentArtifactKind APPROLE = new ParentArtifactKind(APPROLE_TYPE, "appRole");

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

    private ParentArtifactKind(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.ParentArtifactKind(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * ParentArtifactKind
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
     * Returns the type of this ParentArtifactKind
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
        members.put("datatype", DATATYPE);
        members.put("property", PROPERTY);
        members.put("class", CLASS);
        members.put("attribute", ATTRIBUTE);
        members.put("relationship", RELATIONSHIP);
        members.put("trigger", TRIGGER);
        members.put("appRole", APPROLE);
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
     * Returns the String representation of this ParentArtifactKind
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
     * Returns a new ParentArtifactKind based on the given String
     * value.
     * 
     * @param string
     * @return ParentArtifactKind
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.ParentArtifactKind valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid ParentArtifactKind";
            throw new IllegalArgumentException(err);
        }
        return (ParentArtifactKind) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.ParentArtifactKind valueOf(java.lang.String) 

}
