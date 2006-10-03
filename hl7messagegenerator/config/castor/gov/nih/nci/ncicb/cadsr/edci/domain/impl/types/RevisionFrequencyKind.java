/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: RevisionFrequencyKind.java,v 1.1 2006-10-03 19:42:17 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Identifies the update frequency expectations for systems which
 * reference a vocabulary domainUML: Type used in a complex tag
 * value
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:17 $
 */
public class RevisionFrequencyKind implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The Edition type
     */
    public static final int EDITION_TYPE = 0;

    /**
     * The instance of the Edition type
     */
    public static final RevisionFrequencyKind EDITION = new RevisionFrequencyKind(EDITION_TYPE, "Edition");

    /**
     * The CodeSystem type
     */
    public static final int CODESYSTEM_TYPE = 1;

    /**
     * The instance of the CodeSystem type
     */
    public static final RevisionFrequencyKind CODESYSTEM = new RevisionFrequencyKind(CODESYSTEM_TYPE, "CodeSystem");

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

    private RevisionFrequencyKind(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.RevisionFrequencyKind(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * RevisionFrequencyKind
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
     * Returns the type of this RevisionFrequencyKind
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
        members.put("Edition", EDITION);
        members.put("CodeSystem", CODESYSTEM);
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
     * RevisionFrequencyKind
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
     * Returns a new RevisionFrequencyKind based on the given
     * String value.
     * 
     * @param string
     * @return RevisionFrequencyKind
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.RevisionFrequencyKind valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid RevisionFrequencyKind";
            throw new IllegalArgumentException(err);
        }
        return (RevisionFrequencyKind) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.RevisionFrequencyKind valueOf(java.lang.String) 

}
