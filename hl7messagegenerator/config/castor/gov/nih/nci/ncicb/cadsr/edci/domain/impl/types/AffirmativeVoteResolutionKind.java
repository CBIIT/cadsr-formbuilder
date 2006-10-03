/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: AffirmativeVoteResolutionKind.java,v 1.1 2006-10-03 19:42:06 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Class AffirmativeVoteResolutionKind.
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:06 $
 */
public class AffirmativeVoteResolutionKind implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The Affirmative-Incorporated type
     */
    public static final int AFFIRMATIVE_INCORPORATED_TYPE = 0;

    /**
     * The instance of the Affirmative-Incorporated type
     */
    public static final AffirmativeVoteResolutionKind AFFIRMATIVE_INCORPORATED = new AffirmativeVoteResolutionKind(AFFIRMATIVE_INCORPORATED_TYPE, "Affirmative-Incorporated");

    /**
     * The Affirmative-Rejected type
     */
    public static final int AFFIRMATIVE_REJECTED_TYPE = 1;

    /**
     * The instance of the Affirmative-Rejected type
     */
    public static final AffirmativeVoteResolutionKind AFFIRMATIVE_REJECTED = new AffirmativeVoteResolutionKind(AFFIRMATIVE_REJECTED_TYPE, "Affirmative-Rejected");

    /**
     * The Affirmative-Deferred type
     */
    public static final int AFFIRMATIVE_DEFERRED_TYPE = 2;

    /**
     * The instance of the Affirmative-Deferred type
     */
    public static final AffirmativeVoteResolutionKind AFFIRMATIVE_DEFERRED = new AffirmativeVoteResolutionKind(AFFIRMATIVE_DEFERRED_TYPE, "Affirmative-Deferred");

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

    private AffirmativeVoteResolutionKind(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.AffirmativeVoteResolutionKind(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * AffirmativeVoteResolutionKind
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
     * Returns the type of this AffirmativeVoteResolutionKind
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
        members.put("Affirmative-Incorporated", AFFIRMATIVE_INCORPORATED);
        members.put("Affirmative-Rejected", AFFIRMATIVE_REJECTED);
        members.put("Affirmative-Deferred", AFFIRMATIVE_DEFERRED);
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
     * AffirmativeVoteResolutionKind
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
     * Returns a new AffirmativeVoteResolutionKind based on the
     * given String value.
     * 
     * @param string
     * @return AffirmativeVoteResolutionKind
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.AffirmativeVoteResolutionKind valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid AffirmativeVoteResolutionKind";
            throw new IllegalArgumentException(err);
        }
        return (AffirmativeVoteResolutionKind) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.AffirmativeVoteResolutionKind valueOf(java.lang.String) 

}
