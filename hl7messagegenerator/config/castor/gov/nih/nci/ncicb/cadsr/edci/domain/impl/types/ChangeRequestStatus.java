/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: ChangeRequestStatus.java,v 1.1 2006-10-03 19:42:06 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Provides the different 'types' of diagrams
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:06 $
 */
public class ChangeRequestStatus implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The Proposed type
     */
    public static final int PROPOSED_TYPE = 0;

    /**
     * The instance of the Proposed type
     */
    public static final ChangeRequestStatus PROPOSED = new ChangeRequestStatus(PROPOSED_TYPE, "Proposed");

    /**
     * The Approved type
     */
    public static final int APPROVED_TYPE = 1;

    /**
     * The instance of the Approved type
     */
    public static final ChangeRequestStatus APPROVED = new ChangeRequestStatus(APPROVED_TYPE, "Approved");

    /**
     * The Rejected type
     */
    public static final int REJECTED_TYPE = 2;

    /**
     * The instance of the Rejected type
     */
    public static final ChangeRequestStatus REJECTED = new ChangeRequestStatus(REJECTED_TYPE, "Rejected");

    /**
     * The Implemented type
     */
    public static final int IMPLEMENTED_TYPE = 3;

    /**
     * The instance of the Implemented type
     */
    public static final ChangeRequestStatus IMPLEMENTED = new ChangeRequestStatus(IMPLEMENTED_TYPE, "Implemented");

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

    private ChangeRequestStatus(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.ChangeRequestStatus(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * ChangeRequestStatus
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
     * Returns the type of this ChangeRequestStatus
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
        members.put("Proposed", PROPOSED);
        members.put("Approved", APPROVED);
        members.put("Rejected", REJECTED);
        members.put("Implemented", IMPLEMENTED);
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
     * ChangeRequestStatus
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
     * Returns a new ChangeRequestStatus based on the given String
     * value.
     * 
     * @param string
     * @return ChangeRequestStatus
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.ChangeRequestStatus valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid ChangeRequestStatus";
            throw new IllegalArgumentException(err);
        }
        return (ChangeRequestStatus) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.ChangeRequestStatus valueOf(java.lang.String) 

}
