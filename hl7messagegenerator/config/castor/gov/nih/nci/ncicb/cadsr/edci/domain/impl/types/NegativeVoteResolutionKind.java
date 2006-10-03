/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: NegativeVoteResolutionKind.java,v 1.1 2006-10-03 19:42:17 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * List of allowed resolutions to a formal ballot (based on HL7
 * bylaws)UML: Type for a complex tag value
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:17 $
 */
public class NegativeVoteResolutionKind implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The Unresolved type
     */
    public static final int UNRESOLVED_TYPE = 0;

    /**
     * The instance of the Unresolved type
     */
    public static final NegativeVoteResolutionKind UNRESOLVED = new NegativeVoteResolutionKind(UNRESOLVED_TYPE, "Unresolved");

    /**
     * The Non-Substantive Proposed type
     */
    public static final int NON_SUBSTANTIVE_PROPOSED_TYPE = 1;

    /**
     * The instance of the Non-Substantive Proposed type
     */
    public static final NegativeVoteResolutionKind NON_SUBSTANTIVE_PROPOSED = new NegativeVoteResolutionKind(NON_SUBSTANTIVE_PROPOSED_TYPE, "Non-Substantive Proposed");

    /**
     * The Non-Substantive Voted type
     */
    public static final int NON_SUBSTANTIVE_VOTED_TYPE = 2;

    /**
     * The instance of the Non-Substantive Voted type
     */
    public static final NegativeVoteResolutionKind NON_SUBSTANTIVE_VOTED = new NegativeVoteResolutionKind(NON_SUBSTANTIVE_VOTED_TYPE, "Non-Substantive Voted");

    /**
     * The Not Related Proposed type
     */
    public static final int NOT_RELATED_PROPOSED_TYPE = 3;

    /**
     * The instance of the Not Related Proposed type
     */
    public static final NegativeVoteResolutionKind NOT_RELATED_PROPOSED = new NegativeVoteResolutionKind(NOT_RELATED_PROPOSED_TYPE, "Not Related Proposed");

    /**
     * The Not Related Voted type
     */
    public static final int NOT_RELATED_VOTED_TYPE = 4;

    /**
     * The instance of the Not Related Voted type
     */
    public static final NegativeVoteResolutionKind NOT_RELATED_VOTED = new NegativeVoteResolutionKind(NOT_RELATED_VOTED_TYPE, "Not Related Voted");

    /**
     * The Previously Considered type
     */
    public static final int PREVIOUSLY_CONSIDERED_TYPE = 5;

    /**
     * The instance of the Previously Considered type
     */
    public static final NegativeVoteResolutionKind PREVIOUSLY_CONSIDERED = new NegativeVoteResolutionKind(PREVIOUSLY_CONSIDERED_TYPE, "Previously Considered");

    /**
     * The Retracted type
     */
    public static final int RETRACTED_TYPE = 6;

    /**
     * The instance of the Retracted type
     */
    public static final NegativeVoteResolutionKind RETRACTED = new NegativeVoteResolutionKind(RETRACTED_TYPE, "Retracted");

    /**
     * The Withdrawn type
     */
    public static final int WITHDRAWN_TYPE = 7;

    /**
     * The instance of the Withdrawn type
     */
    public static final NegativeVoteResolutionKind WITHDRAWN = new NegativeVoteResolutionKind(WITHDRAWN_TYPE, "Withdrawn");

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

    private NegativeVoteResolutionKind(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.NegativeVoteResolutionKind(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * NegativeVoteResolutionKind
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
     * Returns the type of this NegativeVoteResolutionKind
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
        members.put("Unresolved", UNRESOLVED);
        members.put("Non-Substantive Proposed", NON_SUBSTANTIVE_PROPOSED);
        members.put("Non-Substantive Voted", NON_SUBSTANTIVE_VOTED);
        members.put("Not Related Proposed", NOT_RELATED_PROPOSED);
        members.put("Not Related Voted", NOT_RELATED_VOTED);
        members.put("Previously Considered", PREVIOUSLY_CONSIDERED);
        members.put("Retracted", RETRACTED);
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
     * Returns the String representation of this
     * NegativeVoteResolutionKind
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
     * Returns a new NegativeVoteResolutionKind based on the given
     * String value.
     * 
     * @param string
     * @return NegativeVoteResolutionKind
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.NegativeVoteResolutionKind valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid NegativeVoteResolutionKind";
            throw new IllegalArgumentException(err);
        }
        return (NegativeVoteResolutionKind) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.NegativeVoteResolutionKind valueOf(java.lang.String) 

}
