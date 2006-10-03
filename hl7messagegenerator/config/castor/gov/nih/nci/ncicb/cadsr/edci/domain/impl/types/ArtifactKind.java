/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: ArtifactKind.java,v 1.1 2006-10-03 19:42:06 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Identifies the kind of artifacts that can be packagedUML: The
 * name for a package in the package hierarchy
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:06 $
 */
public class ArtifactKind implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The CMET type
     */
    public static final int CMET_TYPE = 0;

    /**
     * The instance of the CMET type
     */
    public static final ArtifactKind CMET = new ArtifactKind(CMET_TYPE, "CMET");

    /**
     * The STUB type
     */
    public static final int STUB_TYPE = 1;

    /**
     * The instance of the STUB type
     */
    public static final ArtifactKind STUB = new ArtifactKind(STUB_TYPE, "STUB");

    /**
     * The RIM type
     */
    public static final int RIM_TYPE = 2;

    /**
     * The instance of the RIM type
     */
    public static final ArtifactKind RIM = new ArtifactKind(RIM_TYPE, "RIM");

    /**
     * The DIM type
     */
    public static final int DIM_TYPE = 3;

    /**
     * The instance of the DIM type
     */
    public static final ArtifactKind DIM = new ArtifactKind(DIM_TYPE, "DIM");

    /**
     * The CIM type
     */
    public static final int CIM_TYPE = 4;

    /**
     * The instance of the CIM type
     */
    public static final ArtifactKind CIM = new ArtifactKind(CIM_TYPE, "CIM");

    /**
     * The LIM type
     */
    public static final int LIM_TYPE = 5;

    /**
     * The instance of the LIM type
     */
    public static final ArtifactKind LIM = new ArtifactKind(LIM_TYPE, "LIM");

    /**
     * The DAM type
     */
    public static final int DAM_TYPE = 6;

    /**
     * The instance of the DAM type
     */
    public static final ArtifactKind DAM = new ArtifactKind(DAM_TYPE, "DAM");

    /**
     * The TP type
     */
    public static final int TP_TYPE = 7;

    /**
     * The instance of the TP type
     */
    public static final ArtifactKind TP = new ArtifactKind(TP_TYPE, "TP");

    /**
     * The DT type
     */
    public static final int DT_TYPE = 8;

    /**
     * The instance of the DT type
     */
    public static final ArtifactKind DT = new ArtifactKind(DT_TYPE, "DT");

    /**
     * The ITS type
     */
    public static final int ITS_TYPE = 9;

    /**
     * The instance of the ITS type
     */
    public static final ArtifactKind ITS = new ArtifactKind(ITS_TYPE, "ITS");

    /**
     * The DC type
     */
    public static final int DC_TYPE = 10;

    /**
     * The instance of the DC type
     */
    public static final ArtifactKind DC = new ArtifactKind(DC_TYPE, "DC");

    /**
     * The GL type
     */
    public static final int GL_TYPE = 11;

    /**
     * The instance of the GL type
     */
    public static final ArtifactKind GL = new ArtifactKind(GL_TYPE, "GL");

    /**
     * The SB type
     */
    public static final int SB_TYPE = 12;

    /**
     * The instance of the SB type
     */
    public static final ArtifactKind SB = new ArtifactKind(SB_TYPE, "SB");

    /**
     * The VO type
     */
    public static final int VO_TYPE = 13;

    /**
     * The instance of the VO type
     */
    public static final ArtifactKind VO = new ArtifactKind(VO_TYPE, "VO");

    /**
     * The AR type
     */
    public static final int AR_TYPE = 14;

    /**
     * The instance of the AR type
     */
    public static final ArtifactKind AR = new ArtifactKind(AR_TYPE, "AR");

    /**
     * The TE type
     */
    public static final int TE_TYPE = 15;

    /**
     * The instance of the TE type
     */
    public static final ArtifactKind TE = new ArtifactKind(TE_TYPE, "TE");

    /**
     * The IN type
     */
    public static final int IN_TYPE = 16;

    /**
     * The instance of the IN type
     */
    public static final ArtifactKind IN = new ArtifactKind(IN_TYPE, "IN");

    /**
     * The DMIM-deprecated type
     */
    public static final int DMIM_DEPRECATED_TYPE = 17;

    /**
     * The instance of the DMIM-deprecated type
     */
    public static final ArtifactKind DMIM_DEPRECATED = new ArtifactKind(DMIM_DEPRECATED_TYPE, "DMIM-deprecated");

    /**
     * The RM-deprecated type
     */
    public static final int RM_DEPRECATED_TYPE = 18;

    /**
     * The instance of the RM-deprecated type
     */
    public static final ArtifactKind RM_DEPRECATED = new ArtifactKind(RM_DEPRECATED_TYPE, "RM-deprecated");

    /**
     * The HD-deprecated type
     */
    public static final int HD_DEPRECATED_TYPE = 19;

    /**
     * The instance of the HD-deprecated type
     */
    public static final ArtifactKind HD_DEPRECATED = new ArtifactKind(HD_DEPRECATED_TYPE, "HD-deprecated");

    /**
     * The MT-deprecated type
     */
    public static final int MT_DEPRECATED_TYPE = 20;

    /**
     * The instance of the MT-deprecated type
     */
    public static final ArtifactKind MT_DEPRECATED = new ArtifactKind(MT_DEPRECATED_TYPE, "MT-deprecated");

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

    private ArtifactKind(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.ArtifactKind(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * ArtifactKind
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
     * Returns the type of this ArtifactKind
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
        members.put("CMET", CMET);
        members.put("STUB", STUB);
        members.put("RIM", RIM);
        members.put("DIM", DIM);
        members.put("CIM", CIM);
        members.put("LIM", LIM);
        members.put("DAM", DAM);
        members.put("TP", TP);
        members.put("DT", DT);
        members.put("ITS", ITS);
        members.put("DC", DC);
        members.put("GL", GL);
        members.put("SB", SB);
        members.put("VO", VO);
        members.put("AR", AR);
        members.put("TE", TE);
        members.put("IN", IN);
        members.put("DMIM-deprecated", DMIM_DEPRECATED);
        members.put("RM-deprecated", RM_DEPRECATED);
        members.put("HD-deprecated", HD_DEPRECATED);
        members.put("MT-deprecated", MT_DEPRECATED);
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
     * Returns the String representation of this ArtifactKind
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
     * Returns a new ArtifactKind based on the given String value.
     * 
     * @param string
     * @return ArtifactKind
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.ArtifactKind valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid ArtifactKind";
            throw new IllegalArgumentException(err);
        }
        return (ArtifactKind) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.ArtifactKind valueOf(java.lang.String) 

}
