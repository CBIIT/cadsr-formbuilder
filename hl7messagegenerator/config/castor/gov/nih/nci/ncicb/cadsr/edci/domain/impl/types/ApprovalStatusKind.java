/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: ApprovalStatusKind.java,v 1.1 2006-10-03 19:42:06 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * List of allowed ballot statusesUML: Type used in a complex tag
 * value
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:06 $
 */
public class ApprovalStatusKind implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The Draft type
     */
    public static final int DRAFT_TYPE = 0;

    /**
     * The instance of the Draft type
     */
    public static final ApprovalStatusKind DRAFT = new ApprovalStatusKind(DRAFT_TYPE, "Draft");

    /**
     * The Non-standard - Available for use type
     */
    public static final int NON_STANDARD___AVAILABLE_FOR_USE_TYPE = 1;

    /**
     * The instance of the Non-standard - Available for use type
     */
    public static final ApprovalStatusKind NON_STANDARD___AVAILABLE_FOR_USE = new ApprovalStatusKind(NON_STANDARD___AVAILABLE_FOR_USE_TYPE, "Non-standard - Available for use");

    /**
     * The Proposal type
     */
    public static final int PROPOSAL_TYPE = 2;

    /**
     * The instance of the Proposal type
     */
    public static final ApprovalStatusKind PROPOSAL = new ApprovalStatusKind(PROPOSAL_TYPE, "Proposal");

    /**
     * The Committee Ballot - Normative type
     */
    public static final int COMMITTEE_BALLOT___NORMATIVE_TYPE = 3;

    /**
     * The instance of the Committee Ballot - Normative type
     */
    public static final ApprovalStatusKind COMMITTEE_BALLOT___NORMATIVE = new ApprovalStatusKind(COMMITTEE_BALLOT___NORMATIVE_TYPE, "Committee Ballot - Normative");

    /**
     * The Committee Ballot - Informative type
     */
    public static final int COMMITTEE_BALLOT___INFORMATIVE_TYPE = 4;

    /**
     * The instance of the Committee Ballot - Informative type
     */
    public static final ApprovalStatusKind COMMITTEE_BALLOT___INFORMATIVE = new ApprovalStatusKind(COMMITTEE_BALLOT___INFORMATIVE_TYPE, "Committee Ballot - Informative");

    /**
     * The Membership Ballot - Normative type
     */
    public static final int MEMBERSHIP_BALLOT___NORMATIVE_TYPE = 5;

    /**
     * The instance of the Membership Ballot - Normative type
     */
    public static final ApprovalStatusKind MEMBERSHIP_BALLOT___NORMATIVE = new ApprovalStatusKind(MEMBERSHIP_BALLOT___NORMATIVE_TYPE, "Membership Ballot - Normative");

    /**
     * The Membership Ballot - Informative type
     */
    public static final int MEMBERSHIP_BALLOT___INFORMATIVE_TYPE = 6;

    /**
     * The instance of the Membership Ballot - Informative type
     */
    public static final ApprovalStatusKind MEMBERSHIP_BALLOT___INFORMATIVE = new ApprovalStatusKind(MEMBERSHIP_BALLOT___INFORMATIVE_TYPE, "Membership Ballot - Informative");

    /**
     * The Membership Ballot - DSTU type
     */
    public static final int MEMBERSHIP_BALLOT___DSTU_TYPE = 7;

    /**
     * The instance of the Membership Ballot - DSTU type
     */
    public static final ApprovalStatusKind MEMBERSHIP_BALLOT___DSTU = new ApprovalStatusKind(MEMBERSHIP_BALLOT___DSTU_TYPE, "Membership Ballot - DSTU");

    /**
     * The Approved Normative Standard type
     */
    public static final int APPROVED_NORMATIVE_STANDARD_TYPE = 8;

    /**
     * The instance of the Approved Normative Standard type
     */
    public static final ApprovalStatusKind APPROVED_NORMATIVE_STANDARD = new ApprovalStatusKind(APPROVED_NORMATIVE_STANDARD_TYPE, "Approved Normative Standard");

    /**
     * The Approved Informative Standard type
     */
    public static final int APPROVED_INFORMATIVE_STANDARD_TYPE = 9;

    /**
     * The instance of the Approved Informative Standard type
     */
    public static final ApprovalStatusKind APPROVED_INFORMATIVE_STANDARD = new ApprovalStatusKind(APPROVED_INFORMATIVE_STANDARD_TYPE, "Approved Informative Standard");

    /**
     * The Approved DSTU type
     */
    public static final int APPROVED_DSTU_TYPE = 10;

    /**
     * The instance of the Approved DSTU type
     */
    public static final ApprovalStatusKind APPROVED_DSTU = new ApprovalStatusKind(APPROVED_DSTU_TYPE, "Approved DSTU");

    /**
     * The Affiliate Ballot - Normative type
     */
    public static final int AFFILIATE_BALLOT___NORMATIVE_TYPE = 11;

    /**
     * The instance of the Affiliate Ballot - Normative type
     */
    public static final ApprovalStatusKind AFFILIATE_BALLOT___NORMATIVE = new ApprovalStatusKind(AFFILIATE_BALLOT___NORMATIVE_TYPE, "Affiliate Ballot - Normative");

    /**
     * The Affiliate Ballot - Informative type
     */
    public static final int AFFILIATE_BALLOT___INFORMATIVE_TYPE = 12;

    /**
     * The instance of the Affiliate Ballot - Informative type
     */
    public static final ApprovalStatusKind AFFILIATE_BALLOT___INFORMATIVE = new ApprovalStatusKind(AFFILIATE_BALLOT___INFORMATIVE_TYPE, "Affiliate Ballot - Informative");

    /**
     * The Reference type
     */
    public static final int REFERENCE_TYPE = 13;

    /**
     * The instance of the Reference type
     */
    public static final ApprovalStatusKind REFERENCE = new ApprovalStatusKind(REFERENCE_TYPE, "Reference");

    /**
     * The Approved Affiliate Normative Standard type
     */
    public static final int APPROVED_AFFILIATE_NORMATIVE_STANDARD_TYPE = 14;

    /**
     * The instance of the Approved Affiliate Normative Standard typ
     */
    public static final ApprovalStatusKind APPROVED_AFFILIATE_NORMATIVE_STANDARD = new ApprovalStatusKind(APPROVED_AFFILIATE_NORMATIVE_STANDARD_TYPE, "Approved Affiliate Normative Standard");

    /**
     * The Approved Affiliate Informative Standard type
     */
    public static final int APPROVED_AFFILIATE_INFORMATIVE_STANDARD_TYPE = 15;

    /**
     * The instance of the Approved Affiliate Informative Standard
     * type
     */
    public static final ApprovalStatusKind APPROVED_AFFILIATE_INFORMATIVE_STANDARD = new ApprovalStatusKind(APPROVED_AFFILIATE_INFORMATIVE_STANDARD_TYPE, "Approved Affiliate Informative Standard");

    /**
     * The Approved Affiliate DSTU type
     */
    public static final int APPROVED_AFFILIATE_DSTU_TYPE = 16;

    /**
     * The instance of the Approved Affiliate DSTU type
     */
    public static final ApprovalStatusKind APPROVED_AFFILIATE_DSTU = new ApprovalStatusKind(APPROVED_AFFILIATE_DSTU_TYPE, "Approved Affiliate DSTU");

    /**
     * The Localized Adaptation type
     */
    public static final int LOCALIZED_ADAPTATION_TYPE = 17;

    /**
     * The instance of the Localized Adaptation type
     */
    public static final ApprovalStatusKind LOCALIZED_ADAPTATION = new ApprovalStatusKind(LOCALIZED_ADAPTATION_TYPE, "Localized Adaptation");

    /**
     * The Withdrawn type
     */
    public static final int WITHDRAWN_TYPE = 18;

    /**
     * The instance of the Withdrawn type
     */
    public static final ApprovalStatusKind WITHDRAWN = new ApprovalStatusKind(WITHDRAWN_TYPE, "Withdrawn");

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

    private ApprovalStatusKind(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.ApprovalStatusKind(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * ApprovalStatusKind
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
     * Returns the type of this ApprovalStatusKind
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
        members.put("Draft", DRAFT);
        members.put("Non-standard - Available for use", NON_STANDARD___AVAILABLE_FOR_USE);
        members.put("Proposal", PROPOSAL);
        members.put("Committee Ballot - Normative", COMMITTEE_BALLOT___NORMATIVE);
        members.put("Committee Ballot - Informative", COMMITTEE_BALLOT___INFORMATIVE);
        members.put("Membership Ballot - Normative", MEMBERSHIP_BALLOT___NORMATIVE);
        members.put("Membership Ballot - Informative", MEMBERSHIP_BALLOT___INFORMATIVE);
        members.put("Membership Ballot - DSTU", MEMBERSHIP_BALLOT___DSTU);
        members.put("Approved Normative Standard", APPROVED_NORMATIVE_STANDARD);
        members.put("Approved Informative Standard", APPROVED_INFORMATIVE_STANDARD);
        members.put("Approved DSTU", APPROVED_DSTU);
        members.put("Affiliate Ballot - Normative", AFFILIATE_BALLOT___NORMATIVE);
        members.put("Affiliate Ballot - Informative", AFFILIATE_BALLOT___INFORMATIVE);
        members.put("Reference", REFERENCE);
        members.put("Approved Affiliate Normative Standard", APPROVED_AFFILIATE_NORMATIVE_STANDARD);
        members.put("Approved Affiliate Informative Standard", APPROVED_AFFILIATE_INFORMATIVE_STANDARD);
        members.put("Approved Affiliate DSTU", APPROVED_AFFILIATE_DSTU);
        members.put("Localized Adaptation", LOCALIZED_ADAPTATION);
        members.put("Withdrawn", WITHDRAWN);
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
     * Returns the String representation of this ApprovalStatusKind
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
     * Returns a new ApprovalStatusKind based on the given String
     * value.
     * 
     * @param string
     * @return ApprovalStatusKind
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.ApprovalStatusKind valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid ApprovalStatusKind";
            throw new IllegalArgumentException(err);
        }
        return (ApprovalStatusKind) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.ApprovalStatusKind valueOf(java.lang.String) 

}
