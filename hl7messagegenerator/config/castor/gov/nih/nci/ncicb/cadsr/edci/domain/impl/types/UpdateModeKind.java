/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: UpdateModeKind.java,v 1.1 2006-10-03 19:42:17 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Potential update modes
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:17 $
 */
public class UpdateModeKind implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The A type
     */
    public static final int A_TYPE = 0;

    /**
     * The instance of the A type
     */
    public static final UpdateModeKind A = new UpdateModeKind(A_TYPE, "A");

    /**
     * The D type
     */
    public static final int D_TYPE = 1;

    /**
     * The instance of the D type
     */
    public static final UpdateModeKind D = new UpdateModeKind(D_TYPE, "D");

    /**
     * The R type
     */
    public static final int R_TYPE = 2;

    /**
     * The instance of the R type
     */
    public static final UpdateModeKind R = new UpdateModeKind(R_TYPE, "R");

    /**
     * The AR type
     */
    public static final int AR_TYPE = 3;

    /**
     * The instance of the AR type
     */
    public static final UpdateModeKind AR = new UpdateModeKind(AR_TYPE, "AR");

    /**
     * The N type
     */
    public static final int N_TYPE = 4;

    /**
     * The instance of the N type
     */
    public static final UpdateModeKind N = new UpdateModeKind(N_TYPE, "N");

    /**
     * The U type
     */
    public static final int U_TYPE = 5;

    /**
     * The instance of the U type
     */
    public static final UpdateModeKind U = new UpdateModeKind(U_TYPE, "U");

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

    private UpdateModeKind(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.UpdateModeKind(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * UpdateModeKind
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
     * Returns the type of this UpdateModeKind
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
        members.put("A", A);
        members.put("D", D);
        members.put("R", R);
        members.put("AR", AR);
        members.put("N", N);
        members.put("U", U);
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
     * Returns the String representation of this UpdateModeKind
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
     * Returns a new UpdateModeKind based on the given String
     * value.
     * 
     * @param string
     * @return UpdateModeKind
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.UpdateModeKind valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid UpdateModeKind";
            throw new IllegalArgumentException(err);
        }
        return (UpdateModeKind) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.UpdateModeKind valueOf(java.lang.String) 

}
