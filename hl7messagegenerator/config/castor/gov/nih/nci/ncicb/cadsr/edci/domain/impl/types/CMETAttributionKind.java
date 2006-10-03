/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: CMETAttributionKind.java,v 1.1 2006-10-03 19:42:06 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Provides the different 'types' of diagrams
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:06 $
 */
public class CMETAttributionKind implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The Abstract type
     */
    public static final int ABSTRACT_TYPE = 0;

    /**
     * The instance of the Abstract type
     */
    public static final CMETAttributionKind ABSTRACT = new CMETAttributionKind(ABSTRACT_TYPE, "Abstract");

    /**
     * The Basic type
     */
    public static final int BASIC_TYPE = 1;

    /**
     * The instance of the Basic type
     */
    public static final CMETAttributionKind BASIC = new CMETAttributionKind(BASIC_TYPE, "Basic");

    /**
     * The Clinical type
     */
    public static final int CLINICAL_TYPE = 2;

    /**
     * The instance of the Clinical type
     */
    public static final CMETAttributionKind CLINICAL = new CMETAttributionKind(CLINICAL_TYPE, "Clinical");

    /**
     * The Contact type
     */
    public static final int CONTACT_TYPE = 3;

    /**
     * The instance of the Contact type
     */
    public static final CMETAttributionKind CONTACT = new CMETAttributionKind(CONTACT_TYPE, "Contact");

    /**
     * The Identified type
     */
    public static final int IDENTIFIED_TYPE = 4;

    /**
     * The instance of the Identified type
     */
    public static final CMETAttributionKind IDENTIFIED = new CMETAttributionKind(IDENTIFIED_TYPE, "Identified");

    /**
     * The Identified/Confirmable type
     */
    public static final int IDENTIFIED_CONFIRMABLE_TYPE = 5;

    /**
     * The instance of the Identified/Confirmable type
     */
    public static final CMETAttributionKind IDENTIFIED_CONFIRMABLE = new CMETAttributionKind(IDENTIFIED_CONFIRMABLE_TYPE, "Identified/Confirmable");

    /**
     * The Minimal type
     */
    public static final int MINIMAL_TYPE = 6;

    /**
     * The instance of the Minimal type
     */
    public static final CMETAttributionKind MINIMAL = new CMETAttributionKind(MINIMAL_TYPE, "Minimal");

    /**
     * The NoText type
     */
    public static final int NOTEXT_TYPE = 7;

    /**
     * The instance of the NoText type
     */
    public static final CMETAttributionKind NOTEXT = new CMETAttributionKind(NOTEXT_TYPE, "NoText");

    /**
     * The Universal type
     */
    public static final int UNIVERSAL_TYPE = 8;

    /**
     * The instance of the Universal type
     */
    public static final CMETAttributionKind UNIVERSAL = new CMETAttributionKind(UNIVERSAL_TYPE, "Universal");

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

    private CMETAttributionKind(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.CMETAttributionKind(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * CMETAttributionKind
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
     * Returns the type of this CMETAttributionKind
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
        members.put("Abstract", ABSTRACT);
        members.put("Basic", BASIC);
        members.put("Clinical", CLINICAL);
        members.put("Contact", CONTACT);
        members.put("Identified", IDENTIFIED);
        members.put("Identified/Confirmable", IDENTIFIED_CONFIRMABLE);
        members.put("Minimal", MINIMAL);
        members.put("NoText", NOTEXT);
        members.put("Universal", UNIVERSAL);
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
     * CMETAttributionKind
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
     * Returns a new CMETAttributionKind based on the given String
     * value.
     * 
     * @param string
     * @return CMETAttributionKind
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.CMETAttributionKind valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid CMETAttributionKind";
            throw new IllegalArgumentException(err);
        }
        return (CMETAttributionKind) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.CMETAttributionKind valueOf(java.lang.String) 

}
