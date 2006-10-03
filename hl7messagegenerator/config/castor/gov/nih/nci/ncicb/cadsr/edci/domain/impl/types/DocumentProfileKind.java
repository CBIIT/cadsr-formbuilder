/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: DocumentProfileKind.java,v 1.1 2006-10-03 19:42:17 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Indicates the scope of the document profile
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:17 $
 */
public class DocumentProfileKind implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The Create type
     */
    public static final int CREATE_TYPE = 0;

    /**
     * The instance of the Create type
     */
    public static final DocumentProfileKind CREATE = new DocumentProfileKind(CREATE_TYPE, "Create");

    /**
     * The Read type
     */
    public static final int READ_TYPE = 1;

    /**
     * The instance of the Read type
     */
    public static final DocumentProfileKind READ = new DocumentProfileKind(READ_TYPE, "Read");

    /**
     * The Both type
     */
    public static final int BOTH_TYPE = 2;

    /**
     * The instance of the Both type
     */
    public static final DocumentProfileKind BOTH = new DocumentProfileKind(BOTH_TYPE, "Both");

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

    private DocumentProfileKind(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.DocumentProfileKind(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * DocumentProfileKind
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
     * Returns the type of this DocumentProfileKind
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
        members.put("Create", CREATE);
        members.put("Read", READ);
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
     * DocumentProfileKind
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
     * Returns a new DocumentProfileKind based on the given String
     * value.
     * 
     * @param string
     * @return DocumentProfileKind
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.DocumentProfileKind valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid DocumentProfileKind";
            throw new IllegalArgumentException(err);
        }
        return (DocumentProfileKind) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.DocumentProfileKind valueOf(java.lang.String) 

}
