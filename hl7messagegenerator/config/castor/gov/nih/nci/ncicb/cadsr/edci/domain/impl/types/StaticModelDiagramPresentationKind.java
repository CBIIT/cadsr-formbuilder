/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: StaticModelDiagramPresentationKind.java,v 1.1 2006-10-03 19:42:17 marwahah Exp $
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
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:17 $
 */
public class StaticModelDiagramPresentationKind implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The UML type
     */
    public static final int UML_TYPE = 0;

    /**
     * The instance of the UML type
     */
    public static final StaticModelDiagramPresentationKind UML = new StaticModelDiagramPresentationKind(UML_TYPE, "UML");

    /**
     * The HL7 type
     */
    public static final int HL7_TYPE = 1;

    /**
     * The instance of the HL7 type
     */
    public static final StaticModelDiagramPresentationKind HL7 = new StaticModelDiagramPresentationKind(HL7_TYPE, "HL7");

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

    private StaticModelDiagramPresentationKind(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.StaticModelDiagramPresentationKind(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * StaticModelDiagramPresentationKind
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
     * Returns the type of this StaticModelDiagramPresentationKind
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
        members.put("UML", UML);
        members.put("HL7", HL7);
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
     * StaticModelDiagramPresentationKind
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
     * Returns a new StaticModelDiagramPresentationKind based on
     * the given String value.
     * 
     * @param string
     * @return StaticModelDiagramPresentationKind
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.StaticModelDiagramPresentationKind valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid StaticModelDiagramPresentationKind";
            throw new IllegalArgumentException(err);
        }
        return (StaticModelDiagramPresentationKind) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.StaticModelDiagramPresentationKind valueOf(java.lang.String) 

}
