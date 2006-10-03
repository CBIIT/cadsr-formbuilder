/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: DesignCommentTagKind.java,v 1.1 2006-10-03 19:42:17 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Flags allowed for design comments
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:17 $
 */
public class DesignCommentTagKind implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The TODO type
     */
    public static final int TODO_TYPE = 0;

    /**
     * The instance of the TODO type
     */
    public static final DesignCommentTagKind TODO = new DesignCommentTagKind(TODO_TYPE, "TODO");

    /**
     * The FIXME type
     */
    public static final int FIXME_TYPE = 1;

    /**
     * The instance of the FIXME type
     */
    public static final DesignCommentTagKind FIXME = new DesignCommentTagKind(FIXME_TYPE, "FIXME");

    /**
     * The NOTE type
     */
    public static final int NOTE_TYPE = 2;

    /**
     * The instance of the NOTE type
     */
    public static final DesignCommentTagKind NOTE = new DesignCommentTagKind(NOTE_TYPE, "NOTE");

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

    private DesignCommentTagKind(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.DesignCommentTagKind(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * DesignCommentTagKind
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
     * Returns the type of this DesignCommentTagKind
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
        members.put("TODO", TODO);
        members.put("FIXME", FIXME);
        members.put("NOTE", NOTE);
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
     * DesignCommentTagKind
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
     * Returns a new DesignCommentTagKind based on the given String
     * value.
     * 
     * @param string
     * @return DesignCommentTagKind
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.DesignCommentTagKind valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid DesignCommentTagKind";
            throw new IllegalArgumentException(err);
        }
        return (DesignCommentTagKind) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.DesignCommentTagKind valueOf(java.lang.String) 

}
