/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: SubSectionKind.java,v 1.1 2006-10-03 19:42:17 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Identifies the HL7 'subSection' (business sub-category) to which
 * the package content pertainsUML: The name for a package in the
 * package hierarchy
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:17 $
 */
public class SubSectionKind implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The CO type
     */
    public static final int CO_TYPE = 0;

    /**
     * The instance of the CO type
     */
    public static final SubSectionKind CO = new SubSectionKind(CO_TYPE, "CO");

    /**
     * The FI type
     */
    public static final int FI_TYPE = 1;

    /**
     * The instance of the FI type
     */
    public static final SubSectionKind FI = new SubSectionKind(FI_TYPE, "FI");

    /**
     * The MC type
     */
    public static final int MC_TYPE = 2;

    /**
     * The instance of the MC type
     */
    public static final SubSectionKind MC = new SubSectionKind(MC_TYPE, "MC");

    /**
     * The MF type
     */
    public static final int MF_TYPE = 3;

    /**
     * The instance of the MF type
     */
    public static final SubSectionKind MF = new SubSectionKind(MF_TYPE, "MF");

    /**
     * The PO type
     */
    public static final int PO_TYPE = 4;

    /**
     * The instance of the PO type
     */
    public static final SubSectionKind PO = new SubSectionKind(PO_TYPE, "PO");

    /**
     * The PR type
     */
    public static final int PR_TYPE = 5;

    /**
     * The instance of the PR type
     */
    public static final SubSectionKind PR = new SubSectionKind(PR_TYPE, "PR");

    /**
     * The QU type
     */
    public static final int QU_TYPE = 6;

    /**
     * The instance of the QU type
     */
    public static final SubSectionKind QU = new SubSectionKind(QU_TYPE, "QU");

    /**
     * The RC type
     */
    public static final int RC_TYPE = 7;

    /**
     * The instance of the RC type
     */
    public static final SubSectionKind RC = new SubSectionKind(RC_TYPE, "RC");

    /**
     * The RE type
     */
    public static final int RE_TYPE = 8;

    /**
     * The instance of the RE type
     */
    public static final SubSectionKind RE = new SubSectionKind(RE_TYPE, "RE");

    /**
     * The UU type
     */
    public static final int UU_TYPE = 9;

    /**
     * The instance of the UU type
     */
    public static final SubSectionKind UU = new SubSectionKind(UU_TYPE, "UU");

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

    private SubSectionKind(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.SubSectionKind(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * SubSectionKind
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
     * Returns the type of this SubSectionKind
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
        members.put("CO", CO);
        members.put("FI", FI);
        members.put("MC", MC);
        members.put("MF", MF);
        members.put("PO", PO);
        members.put("PR", PR);
        members.put("QU", QU);
        members.put("RC", RC);
        members.put("RE", RE);
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
     * Returns the String representation of this SubSectionKind
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
     * Returns a new SubSectionKind based on the given String
     * value.
     * 
     * @param string
     * @return SubSectionKind
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.SubSectionKind valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid SubSectionKind";
            throw new IllegalArgumentException(err);
        }
        return (SubSectionKind) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.SubSectionKind valueOf(java.lang.String) 

}
