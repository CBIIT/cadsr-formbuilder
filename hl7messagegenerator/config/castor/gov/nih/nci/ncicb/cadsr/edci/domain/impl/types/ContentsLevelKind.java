/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: ContentsLevelKind.java,v 1.1 2006-10-03 19:42:06 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Indicates how the element should appear in the table of contents
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:06 $
 */
public class ContentsLevelKind implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The None type
     */
    public static final int NONE_TYPE = 0;

    /**
     * The instance of the None type
     */
    public static final ContentsLevelKind NONE = new ContentsLevelKind(NONE_TYPE, "None");

    /**
     * The Detailed type
     */
    public static final int DETAILED_TYPE = 1;

    /**
     * The instance of the Detailed type
     */
    public static final ContentsLevelKind DETAILED = new ContentsLevelKind(DETAILED_TYPE, "Detailed");

    /**
     * The Summary type
     */
    public static final int SUMMARY_TYPE = 2;

    /**
     * The instance of the Summary type
     */
    public static final ContentsLevelKind SUMMARY = new ContentsLevelKind(SUMMARY_TYPE, "Summary");

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

    private ContentsLevelKind(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.ContentsLevelKind(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * ContentsLevelKind
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
     * Returns the type of this ContentsLevelKind
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
        members.put("None", NONE);
        members.put("Detailed", DETAILED);
        members.put("Summary", SUMMARY);
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
     * Returns the String representation of this ContentsLevelKind
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
     * Returns a new ContentsLevelKind based on the given String
     * value.
     * 
     * @param string
     * @return ContentsLevelKind
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.ContentsLevelKind valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid ContentsLevelKind";
            throw new IllegalArgumentException(err);
        }
        return (ContentsLevelKind) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.ContentsLevelKind valueOf(java.lang.String) 

}
