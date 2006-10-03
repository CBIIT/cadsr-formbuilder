/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: VoteCommentKind.java,v 1.1 2006-10-03 19:42:17 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * List of allowed votesUML: Type for a complex tag value
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:17 $
 */
public class VoteCommentKind implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The Affirmative - Typo type
     */
    public static final int AFFIRMATIVE___TYPO_TYPE = 0;

    /**
     * The instance of the Affirmative - Typo type
     */
    public static final VoteCommentKind AFFIRMATIVE___TYPO = new VoteCommentKind(AFFIRMATIVE___TYPO_TYPE, "Affirmative - Typo");

    /**
     * The Affirmative - Suggestion type
     */
    public static final int AFFIRMATIVE___SUGGESTION_TYPE = 1;

    /**
     * The instance of the Affirmative - Suggestion type
     */
    public static final VoteCommentKind AFFIRMATIVE___SUGGESTION = new VoteCommentKind(AFFIRMATIVE___SUGGESTION_TYPE, "Affirmative - Suggestion");

    /**
     * The Affirmative - Question type
     */
    public static final int AFFIRMATIVE___QUESTION_TYPE = 2;

    /**
     * The instance of the Affirmative - Question type
     */
    public static final VoteCommentKind AFFIRMATIVE___QUESTION = new VoteCommentKind(AFFIRMATIVE___QUESTION_TYPE, "Affirmative - Question");

    /**
     * The Negative - Minor type
     */
    public static final int NEGATIVE___MINOR_TYPE = 3;

    /**
     * The instance of the Negative - Minor type
     */
    public static final VoteCommentKind NEGATIVE___MINOR = new VoteCommentKind(NEGATIVE___MINOR_TYPE, "Negative - Minor");

    /**
     * The Negative - Major type
     */
    public static final int NEGATIVE___MAJOR_TYPE = 4;

    /**
     * The instance of the Negative - Major type
     */
    public static final VoteCommentKind NEGATIVE___MAJOR = new VoteCommentKind(NEGATIVE___MAJOR_TYPE, "Negative - Major");

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

    private VoteCommentKind(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.VoteCommentKind(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * VoteCommentKind
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
     * Returns the type of this VoteCommentKind
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
        members.put("Affirmative - Typo", AFFIRMATIVE___TYPO);
        members.put("Affirmative - Suggestion", AFFIRMATIVE___SUGGESTION);
        members.put("Affirmative - Question", AFFIRMATIVE___QUESTION);
        members.put("Negative - Minor", NEGATIVE___MINOR);
        members.put("Negative - Major", NEGATIVE___MAJOR);
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
     * Returns the String representation of this VoteCommentKind
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
     * Returns a new VoteCommentKind based on the given String
     * value.
     * 
     * @param string
     * @return VoteCommentKind
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.VoteCommentKind valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid VoteCommentKind";
            throw new IllegalArgumentException(err);
        }
        return (VoteCommentKind) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.VoteCommentKind valueOf(java.lang.String) 

}
