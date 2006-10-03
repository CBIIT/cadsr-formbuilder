/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: SectionKind.java,v 1.1 2006-10-03 19:42:17 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Identifies the HL7 'section' (major business category) to which
 * the package content pertainsUML: The name for a package in the
 * package hierarchy
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:17 $
 */
public class SectionKind implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The AM type
     */
    public static final int AM_TYPE = 0;

    /**
     * The instance of the AM type
     */
    public static final SectionKind AM = new SectionKind(AM_TYPE, "AM");

    /**
     * The HM type
     */
    public static final int HM_TYPE = 1;

    /**
     * The instance of the HM type
     */
    public static final SectionKind HM = new SectionKind(HM_TYPE, "HM");

    /**
     * The IM type
     */
    public static final int IM_TYPE = 2;

    /**
     * The instance of the IM type
     */
    public static final SectionKind IM = new SectionKind(IM_TYPE, "IM");

    /**
     * The UU type
     */
    public static final int UU_TYPE = 3;

    /**
     * The instance of the UU type
     */
    public static final SectionKind UU = new SectionKind(UU_TYPE, "UU");

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

    private SectionKind(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.SectionKind(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * SectionKind
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
     * Returns the type of this SectionKind
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
        members.put("AM", AM);
        members.put("HM", HM);
        members.put("IM", IM);
        members.put("UU", UU);
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
     * Returns the String representation of this SectionKind
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
     * Returns a new SectionKind based on the given String value.
     * 
     * @param string
     * @return SectionKind
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.SectionKind valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid SectionKind";
            throw new IllegalArgumentException(err);
        }
        return (SectionKind) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.SectionKind valueOf(java.lang.String) 

}
