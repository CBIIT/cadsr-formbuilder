/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: InteractionProfileKind.java,v 1.1 2006-10-03 19:42:17 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Indicates the scope of the interaction profile
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:17 $
 */
public class InteractionProfileKind implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The R type
     */
    public static final int R_TYPE = 0;

    /**
     * The instance of the R type
     */
    public static final InteractionProfileKind R = new InteractionProfileKind(R_TYPE, "R");

    /**
     * The Send type
     */
    public static final int SEND_TYPE = 1;

    /**
     * The instance of the Send type
     */
    public static final InteractionProfileKind SEND = new InteractionProfileKind(SEND_TYPE, "Send");

    /**
     * The Receive type
     */
    public static final int RECEIVE_TYPE = 2;

    /**
     * The instance of the Receive type
     */
    public static final InteractionProfileKind RECEIVE = new InteractionProfileKind(RECEIVE_TYPE, "Receive");

    /**
     * The Both type
     */
    public static final int BOTH_TYPE = 3;

    /**
     * The instance of the Both type
     */
    public static final InteractionProfileKind BOTH = new InteractionProfileKind(BOTH_TYPE, "Both");

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

    private InteractionProfileKind(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.InteractionProfileKind(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * InteractionProfileKind
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
     * Returns the type of this InteractionProfileKind
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
        members.put("R", R);
        members.put("Send", SEND);
        members.put("Receive", RECEIVE);
        members.put("Both", BOTH);
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
     * InteractionProfileKind
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
     * Returns a new InteractionProfileKind based on the given
     * String value.
     * 
     * @param string
     * @return InteractionProfileKind
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.InteractionProfileKind valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid InteractionProfileKind";
            throw new IllegalArgumentException(err);
        }
        return (InteractionProfileKind) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.InteractionProfileKind valueOf(java.lang.String) 

}
