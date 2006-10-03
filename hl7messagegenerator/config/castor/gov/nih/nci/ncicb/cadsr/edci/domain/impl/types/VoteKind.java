/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: VoteKind.java,v 1.1 2006-10-03 19:42:17 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Identifies the allowed types of individual responses within a
 * ballot response.UML: Type used in a complex tag value
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:17 $
 */
public class VoteKind implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The Affirmative type
     */
    public static final int AFFIRMATIVE_TYPE = 0;

    /**
     * The instance of the Affirmative type
     */
    public static final VoteKind AFFIRMATIVE = new VoteKind(AFFIRMATIVE_TYPE, "Affirmative");

    /**
     * The Abstain type
     */
    public static final int ABSTAIN_TYPE = 1;

    /**
     * The instance of the Abstain type
     */
    public static final VoteKind ABSTAIN = new VoteKind(ABSTAIN_TYPE, "Abstain");

    /**
     * The Negative type
     */
    public static final int NEGATIVE_TYPE = 2;

    /**
     * The instance of the Negative type
     */
    public static final VoteKind NEGATIVE = new VoteKind(NEGATIVE_TYPE, "Negative");

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

    private VoteKind(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.VoteKind(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of VoteKind
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
     * Returns the type of this VoteKind
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
        members.put("Affirmative", AFFIRMATIVE);
        members.put("Abstain", ABSTAIN);
        members.put("Negative", NEGATIVE);
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
     * Returns the String representation of this VoteKind
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
     * Returns a new VoteKind based on the given String value.
     * 
     * @param string
     * @return VoteKind
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.VoteKind valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid VoteKind";
            throw new IllegalArgumentException(err);
        }
        return (VoteKind) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.VoteKind valueOf(java.lang.String) 

}
