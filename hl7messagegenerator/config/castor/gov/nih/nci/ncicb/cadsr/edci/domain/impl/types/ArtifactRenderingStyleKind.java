/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: ArtifactRenderingStyleKind.java,v 1.1 2006-10-03 19:42:06 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Defines the types of renderings that can occur for artifactsUML:
 * Used to reference a tagged item
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:06 $
 */
public class ArtifactRenderingStyleKind implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The GraphicalDetailed type
     */
    public static final int GRAPHICALDETAILED_TYPE = 0;

    /**
     * The instance of the GraphicalDetailed type
     */
    public static final ArtifactRenderingStyleKind GRAPHICALDETAILED = new ArtifactRenderingStyleKind(GRAPHICALDETAILED_TYPE, "GraphicalDetailed");

    /**
     * The GraphicalSummary type
     */
    public static final int GRAPHICALSUMMARY_TYPE = 1;

    /**
     * The instance of the GraphicalSummary type
     */
    public static final ArtifactRenderingStyleKind GRAPHICALSUMMARY = new ArtifactRenderingStyleKind(GRAPHICALSUMMARY_TYPE, "GraphicalSummary");

    /**
     * The TableView type
     */
    public static final int TABLEVIEW_TYPE = 2;

    /**
     * The instance of the TableView type
     */
    public static final ArtifactRenderingStyleKind TABLEVIEW = new ArtifactRenderingStyleKind(TABLEVIEW_TYPE, "TableView");

    /**
     * The ExcelView type
     */
    public static final int EXCELVIEW_TYPE = 3;

    /**
     * The instance of the ExcelView type
     */
    public static final ArtifactRenderingStyleKind EXCELVIEW = new ArtifactRenderingStyleKind(EXCELVIEW_TYPE, "ExcelView");

    /**
     * The TextView type
     */
    public static final int TEXTVIEW_TYPE = 4;

    /**
     * The instance of the TextView type
     */
    public static final ArtifactRenderingStyleKind TEXTVIEW = new ArtifactRenderingStyleKind(TEXTVIEW_TYPE, "TextView");

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

    private ArtifactRenderingStyleKind(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.ArtifactRenderingStyleKind(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * ArtifactRenderingStyleKind
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
     * Returns the type of this ArtifactRenderingStyleKind
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
        members.put("GraphicalDetailed", GRAPHICALDETAILED);
        members.put("GraphicalSummary", GRAPHICALSUMMARY);
        members.put("TableView", TABLEVIEW);
        members.put("ExcelView", EXCELVIEW);
        members.put("TextView", TEXTVIEW);
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
     * ArtifactRenderingStyleKind
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
     * Returns a new ArtifactRenderingStyleKind based on the given
     * String value.
     * 
     * @param string
     * @return ArtifactRenderingStyleKind
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.ArtifactRenderingStyleKind valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid ArtifactRenderingStyleKind";
            throw new IllegalArgumentException(err);
        }
        return (ArtifactRenderingStyleKind) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.ArtifactRenderingStyleKind valueOf(java.lang.String) 

}
