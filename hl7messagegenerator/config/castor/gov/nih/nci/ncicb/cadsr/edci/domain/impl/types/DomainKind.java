/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: DomainKind.java,v 1.1 2006-10-03 19:42:17 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Identifies the HL7 'domain' (specific content area) to which the
 * package content pertainsUML: The name for a package in the
 * package hierarchy
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:17 $
 */
public class DomainKind implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The DD type
     */
    public static final int DD_TYPE = 0;

    /**
     * The instance of the DD type
     */
    public static final DomainKind DD = new DomainKind(DD_TYPE, "DD");

    /**
     * The CT type
     */
    public static final int CT_TYPE = 1;

    /**
     * The instance of the CT type
     */
    public static final DomainKind CT = new DomainKind(CT_TYPE, "CT");

    /**
     * The MT type
     */
    public static final int MT_TYPE = 2;

    /**
     * The instance of the MT type
     */
    public static final DomainKind MT = new DomainKind(MT_TYPE, "MT");

    /**
     * The AB type
     */
    public static final int AB_TYPE = 3;

    /**
     * The instance of the AB type
     */
    public static final DomainKind AB = new DomainKind(AB_TYPE, "AB");

    /**
     * The CR type
     */
    public static final int CR_TYPE = 4;

    /**
     * The instance of the CR type
     */
    public static final DomainKind CR = new DomainKind(CR_TYPE, "CR");

    /**
     * The AI type
     */
    public static final int AI_TYPE = 5;

    /**
     * The instance of the AI type
     */
    public static final DomainKind AI = new DomainKind(AI_TYPE, "AI");

    /**
     * The CI type
     */
    public static final int CI_TYPE = 6;

    /**
     * The instance of the CI type
     */
    public static final DomainKind CI = new DomainKind(CI_TYPE, "CI");

    /**
     * The MI type
     */
    public static final int MI_TYPE = 7;

    /**
     * The instance of the MI type
     */
    public static final DomainKind MI = new DomainKind(MI_TYPE, "MI");

    /**
     * The BB type
     */
    public static final int BB_TYPE = 8;

    /**
     * The instance of the BB type
     */
    public static final DomainKind BB = new DomainKind(BB_TYPE, "BB");

    /**
     * The CD type
     */
    public static final int CD_TYPE = 9;

    /**
     * The instance of the CD type
     */
    public static final DomainKind CD = new DomainKind(CD_TYPE, "CD");

    /**
     * The CG type
     */
    public static final int CG_TYPE = 10;

    /**
     * The instance of the CG type
     */
    public static final DomainKind CG = new DomainKind(CG_TYPE, "CG");

    /**
     * The IZ type
     */
    public static final int IZ_TYPE = 11;

    /**
     * The instance of the IZ type
     */
    public static final DomainKind IZ = new DomainKind(IZ_TYPE, "IZ");

    /**
     * The LB type
     */
    public static final int LB_TYPE = 12;

    /**
     * The instance of the LB type
     */
    public static final DomainKind LB = new DomainKind(LB_TYPE, "LB");

    /**
     * The ME type
     */
    public static final int ME_TYPE = 13;

    /**
     * The instance of the ME type
     */
    public static final DomainKind ME = new DomainKind(ME_TYPE, "ME");

    /**
     * The RI type
     */
    public static final int RI_TYPE = 14;

    /**
     * The instance of the RI type
     */
    public static final DomainKind RI = new DomainKind(RI_TYPE, "RI");

    /**
     * The RR type
     */
    public static final int RR_TYPE = 15;

    /**
     * The instance of the RR type
     */
    public static final DomainKind RR = new DomainKind(RR_TYPE, "RR");

    /**
     * The RT type
     */
    public static final int RT_TYPE = 16;

    /**
     * The instance of the RT type
     */
    public static final DomainKind RT = new DomainKind(RT_TYPE, "RT");

    /**
     * The RX type
     */
    public static final int RX_TYPE = 17;

    /**
     * The instance of the RX type
     */
    public static final DomainKind RX = new DomainKind(RX_TYPE, "RX");

    /**
     * The TD type
     */
    public static final int TD_TYPE = 18;

    /**
     * The instance of the TD type
     */
    public static final DomainKind TD = new DomainKind(TD_TYPE, "TD");

    /**
     * The PA type
     */
    public static final int PA_TYPE = 19;

    /**
     * The instance of the PA type
     */
    public static final DomainKind PA = new DomainKind(PA_TYPE, "PA");

    /**
     * The PM type
     */
    public static final int PM_TYPE = 20;

    /**
     * The instance of the PM type
     */
    public static final DomainKind PM = new DomainKind(PM_TYPE, "PM");

    /**
     * The SC type
     */
    public static final int SC_TYPE = 21;

    /**
     * The instance of the SC type
     */
    public static final DomainKind SC = new DomainKind(SC_TYPE, "SC");

    /**
     * The QI type
     */
    public static final int QI_TYPE = 22;

    /**
     * The instance of the QI type
     */
    public static final DomainKind QI = new DomainKind(QI_TYPE, "QI");

    /**
     * The MR type
     */
    public static final int MR_TYPE = 23;

    /**
     * The instance of the MR type
     */
    public static final DomainKind MR = new DomainKind(MR_TYPE, "MR");

    /**
     * The PC type
     */
    public static final int PC_TYPE = 24;

    /**
     * The instance of the PC type
     */
    public static final DomainKind PC = new DomainKind(PC_TYPE, "PC");

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

    private DomainKind(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.DomainKind(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * DomainKind
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
     * Returns the type of this DomainKind
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
        members.put("DD", DD);
        members.put("CT", CT);
        members.put("MT", MT);
        members.put("AB", AB);
        members.put("CR", CR);
        members.put("AI", AI);
        members.put("CI", CI);
        members.put("MI", MI);
        members.put("BB", BB);
        members.put("CD", CD);
        members.put("CG", CG);
        members.put("IZ", IZ);
        members.put("LB", LB);
        members.put("ME", ME);
        members.put("RI", RI);
        members.put("RR", RR);
        members.put("RT", RT);
        members.put("RX", RX);
        members.put("TD", TD);
        members.put("PA", PA);
        members.put("PM", PM);
        members.put("SC", SC);
        members.put("QI", QI);
        members.put("MR", MR);
        members.put("PC", PC);
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
     * Returns the String representation of this DomainKind
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
     * Returns a new DomainKind based on the given String value.
     * 
     * @param string
     * @return DomainKind
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.DomainKind valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid DomainKind";
            throw new IllegalArgumentException(err);
        }
        return (DomainKind) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.DomainKind valueOf(java.lang.String) 

}
