/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: NodeOrientation.java,v 1.1 2006-10-03 19:42:17 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * List of allowed values to reflect the orientation of graphic
 * node elements, including ChoiceBox alignment and the four,
 * flip-orientations for a Role.UML: Type for a complex tag value
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:17 $
 */
public class NodeOrientation implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The Portrait type
     */
    public static final int PORTRAIT_TYPE = 0;

    /**
     * The instance of the Portrait type
     */
    public static final NodeOrientation PORTRAIT = new NodeOrientation(PORTRAIT_TYPE, "Portrait");

    /**
     * The Landscape type
     */
    public static final int LANDSCAPE_TYPE = 1;

    /**
     * The instance of the Landscape type
     */
    public static final NodeOrientation LANDSCAPE = new NodeOrientation(LANDSCAPE_TYPE, "Landscape");

    /**
     * The UpRight type
     */
    public static final int UPRIGHT_TYPE = 2;

    /**
     * The instance of the UpRight type
     */
    public static final NodeOrientation UPRIGHT = new NodeOrientation(UPRIGHT_TYPE, "UpRight");

    /**
     * The UpLeft type
     */
    public static final int UPLEFT_TYPE = 3;

    /**
     * The instance of the UpLeft type
     */
    public static final NodeOrientation UPLEFT = new NodeOrientation(UPLEFT_TYPE, "UpLeft");

    /**
     * The DownRight type
     */
    public static final int DOWNRIGHT_TYPE = 4;

    /**
     * The instance of the DownRight type
     */
    public static final NodeOrientation DOWNRIGHT = new NodeOrientation(DOWNRIGHT_TYPE, "DownRight");

    /**
     * The DownLeft type
     */
    public static final int DOWNLEFT_TYPE = 5;

    /**
     * The instance of the DownLeft type
     */
    public static final NodeOrientation DOWNLEFT = new NodeOrientation(DOWNLEFT_TYPE, "DownLeft");

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

    private NodeOrientation(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.NodeOrientation(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * NodeOrientation
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
     * Returns the type of this NodeOrientation
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
        members.put("Portrait", PORTRAIT);
        members.put("Landscape", LANDSCAPE);
        members.put("UpRight", UPRIGHT);
        members.put("UpLeft", UPLEFT);
        members.put("DownRight", DOWNRIGHT);
        members.put("DownLeft", DOWNLEFT);
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
     * Returns the String representation of this NodeOrientation
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
     * Returns a new NodeOrientation based on the given String
     * value.
     * 
     * @param string
     * @return NodeOrientation
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.NodeOrientation valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid NodeOrientation";
            throw new IllegalArgumentException(err);
        }
        return (NodeOrientation) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.NodeOrientation valueOf(java.lang.String) 

}
