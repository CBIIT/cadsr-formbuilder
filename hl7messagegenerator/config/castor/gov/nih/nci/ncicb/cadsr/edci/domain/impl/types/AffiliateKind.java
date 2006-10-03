/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: AffiliateKind.java,v 1.1 2006-10-03 19:42:06 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * The list of HL7 affiliates.
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:06 $
 */
public class AffiliateKind implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The AR type
     */
    public static final int AR_TYPE = 0;

    /**
     * The instance of the AR type
     */
    public static final AffiliateKind AR = new AffiliateKind(AR_TYPE, "AR");

    /**
     * The AU type
     */
    public static final int AU_TYPE = 1;

    /**
     * The instance of the AU type
     */
    public static final AffiliateKind AU = new AffiliateKind(AU_TYPE, "AU");

    /**
     * The BR type
     */
    public static final int BR_TYPE = 2;

    /**
     * The instance of the BR type
     */
    public static final AffiliateKind BR = new AffiliateKind(BR_TYPE, "BR");

    /**
     * The CA type
     */
    public static final int CA_TYPE = 3;

    /**
     * The instance of the CA type
     */
    public static final AffiliateKind CA = new AffiliateKind(CA_TYPE, "CA");

    /**
     * The CN type
     */
    public static final int CN_TYPE = 4;

    /**
     * The instance of the CN type
     */
    public static final AffiliateKind CN = new AffiliateKind(CN_TYPE, "CN");

    /**
     * The HR type
     */
    public static final int HR_TYPE = 5;

    /**
     * The instance of the HR type
     */
    public static final AffiliateKind HR = new AffiliateKind(HR_TYPE, "HR");

    /**
     * The DK type
     */
    public static final int DK_TYPE = 6;

    /**
     * The instance of the DK type
     */
    public static final AffiliateKind DK = new AffiliateKind(DK_TYPE, "DK");

    /**
     * The FI type
     */
    public static final int FI_TYPE = 7;

    /**
     * The instance of the FI type
     */
    public static final AffiliateKind FI = new AffiliateKind(FI_TYPE, "FI");

    /**
     * The DE type
     */
    public static final int DE_TYPE = 8;

    /**
     * The instance of the DE type
     */
    public static final AffiliateKind DE = new AffiliateKind(DE_TYPE, "DE");

    /**
     * The IN type
     */
    public static final int IN_TYPE = 9;

    /**
     * The instance of the IN type
     */
    public static final AffiliateKind IN = new AffiliateKind(IN_TYPE, "IN");

    /**
     * The JP type
     */
    public static final int JP_TYPE = 10;

    /**
     * The instance of the JP type
     */
    public static final AffiliateKind JP = new AffiliateKind(JP_TYPE, "JP");

    /**
     * The KR type
     */
    public static final int KR_TYPE = 11;

    /**
     * The instance of the KR type
     */
    public static final AffiliateKind KR = new AffiliateKind(KR_TYPE, "KR");

    /**
     * The LT type
     */
    public static final int LT_TYPE = 12;

    /**
     * The instance of the LT type
     */
    public static final AffiliateKind LT = new AffiliateKind(LT_TYPE, "LT");

    /**
     * The MX type
     */
    public static final int MX_TYPE = 13;

    /**
     * The instance of the MX type
     */
    public static final AffiliateKind MX = new AffiliateKind(MX_TYPE, "MX");

    /**
     * The NE type
     */
    public static final int NE_TYPE = 14;

    /**
     * The instance of the NE type
     */
    public static final AffiliateKind NE = new AffiliateKind(NE_TYPE, "NE");

    /**
     * The NZ type
     */
    public static final int NZ_TYPE = 15;

    /**
     * The instance of the NZ type
     */
    public static final AffiliateKind NZ = new AffiliateKind(NZ_TYPE, "NZ");

    /**
     * The SOA type
     */
    public static final int SOA_TYPE = 16;

    /**
     * The instance of the SOA type
     */
    public static final AffiliateKind SOA = new AffiliateKind(SOA_TYPE, "SOA");

    /**
     * The CH type
     */
    public static final int CH_TYPE = 17;

    /**
     * The instance of the CH type
     */
    public static final AffiliateKind CH = new AffiliateKind(CH_TYPE, "CH");

    /**
     * The TW type
     */
    public static final int TW_TYPE = 18;

    /**
     * The instance of the TW type
     */
    public static final AffiliateKind TW = new AffiliateKind(TW_TYPE, "TW");

    /**
     * The UK type
     */
    public static final int UK_TYPE = 19;

    /**
     * The instance of the UK type
     */
    public static final AffiliateKind UK = new AffiliateKind(UK_TYPE, "UK");

    /**
     * The US type
     */
    public static final int US_TYPE = 20;

    /**
     * The instance of the US type
     */
    public static final AffiliateKind US = new AffiliateKind(US_TYPE, "US");

    /**
     * The UV type
     */
    public static final int UV_TYPE = 21;

    /**
     * The instance of the UV type
     */
    public static final AffiliateKind UV = new AffiliateKind(UV_TYPE, "UV");

    /**
     * The ZZ type
     */
    public static final int ZZ_TYPE = 22;

    /**
     * The instance of the ZZ type
     */
    public static final AffiliateKind ZZ = new AffiliateKind(ZZ_TYPE, "ZZ");

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

    private AffiliateKind(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.AffiliateKind(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * AffiliateKind
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
     * Returns the type of this AffiliateKind
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
        members.put("AR", AR);
        members.put("AU", AU);
        members.put("BR", BR);
        members.put("CA", CA);
        members.put("CN", CN);
        members.put("HR", HR);
        members.put("DK", DK);
        members.put("FI", FI);
        members.put("DE", DE);
        members.put("IN", IN);
        members.put("JP", JP);
        members.put("KR", KR);
        members.put("LT", LT);
        members.put("MX", MX);
        members.put("NE", NE);
        members.put("NZ", NZ);
        members.put("SOA", SOA);
        members.put("CH", CH);
        members.put("TW", TW);
        members.put("UK", UK);
        members.put("US", US);
        members.put("UV", UV);
        members.put("ZZ", ZZ);
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
     * Returns the String representation of this AffiliateKind
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
     * Returns a new AffiliateKind based on the given String value.
     * 
     * @param string
     * @return AffiliateKind
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.AffiliateKind valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid AffiliateKind";
            throw new IllegalArgumentException(err);
        }
        return (AffiliateKind) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.AffiliateKind valueOf(java.lang.String) 

}
