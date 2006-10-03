/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: ClassPresentationKind.java,v 1.1 2006-10-03 19:42:06 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * A list of the 'styles' of graphic images that can be associated
 * with a class
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:06 $
 */
public class ClassPresentationKind implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The HL7 type
     */
    public static final int HL7_TYPE = 0;

    /**
     * The instance of the HL7 type
     */
    public static final ClassPresentationKind HL7 = new ClassPresentationKind(HL7_TYPE, "HL7");

    /**
     * The HL7Shadow type
     */
    public static final int HL7SHADOW_TYPE = 1;

    /**
     * The instance of the HL7Shadow type
     */
    public static final ClassPresentationKind HL7SHADOW = new ClassPresentationKind(HL7SHADOW_TYPE, "HL7Shadow");

    /**
     * The UML type
     */
    public static final int UML_TYPE = 2;

    /**
     * The instance of the UML type
     */
    public static final ClassPresentationKind UML = new ClassPresentationKind(UML_TYPE, "UML");

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

    private ClassPresentationKind(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.ClassPresentationKind(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * ClassPresentationKind
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
     * Returns the type of this ClassPresentationKind
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
        members.put("HL7", HL7);
        members.put("HL7Shadow", HL7SHADOW);
        members.put("UML", UML);
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
     * ClassPresentationKind
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
     * Returns a new ClassPresentationKind based on the given
     * String value.
     * 
     * @param string
     * @return ClassPresentationKind
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.ClassPresentationKind valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid ClassPresentationKind";
            throw new IllegalArgumentException(err);
        }
        return (ClassPresentationKind) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.ClassPresentationKind valueOf(java.lang.String) 

}
