/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: StaticModelUseKind.java,v 1.1 2006-10-03 19:42:17 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * The allowed uses for a particular static model
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:17 $
 */
public class StaticModelUseKind implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The CommunicationWrapper type
     */
    public static final int COMMUNICATIONWRAPPER_TYPE = 0;

    /**
     * The instance of the CommunicationWrapper type
     */
    public static final StaticModelUseKind COMMUNICATIONWRAPPER = new StaticModelUseKind(COMMUNICATIONWRAPPER_TYPE, "CommunicationWrapper");

    /**
     * The ControlActWrapper type
     */
    public static final int CONTROLACTWRAPPER_TYPE = 1;

    /**
     * The instance of the ControlActWrapper type
     */
    public static final StaticModelUseKind CONTROLACTWRAPPER = new StaticModelUseKind(CONTROLACTWRAPPER_TYPE, "ControlActWrapper");

    /**
     * The SemanticPayload type
     */
    public static final int SEMANTICPAYLOAD_TYPE = 2;

    /**
     * The instance of the SemanticPayload type
     */
    public static final StaticModelUseKind SEMANTICPAYLOAD = new StaticModelUseKind(SEMANTICPAYLOAD_TYPE, "SemanticPayload");

    /**
     * The PresentationStructure type
     */
    public static final int PRESENTATIONSTRUCTURE_TYPE = 3;

    /**
     * The instance of the PresentationStructure type
     */
    public static final StaticModelUseKind PRESENTATIONSTRUCTURE = new StaticModelUseKind(PRESENTATIONSTRUCTURE_TYPE, "PresentationStructure");

    /**
     * The QueryDefinition type
     */
    public static final int QUERYDEFINITION_TYPE = 4;

    /**
     * The instance of the QueryDefinition type
     */
    public static final StaticModelUseKind QUERYDEFINITION = new StaticModelUseKind(QUERYDEFINITION_TYPE, "QueryDefinition");

    /**
     * The CMET type
     */
    public static final int CMET_TYPE = 5;

    /**
     * The instance of the CMET type
     */
    public static final StaticModelUseKind CMET = new StaticModelUseKind(CMET_TYPE, "CMET");

    /**
     * The Template type
     */
    public static final int TEMPLATE_TYPE = 6;

    /**
     * The instance of the Template type
     */
    public static final StaticModelUseKind TEMPLATE = new StaticModelUseKind(TEMPLATE_TYPE, "Template");

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

    private StaticModelUseKind(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.StaticModelUseKind(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * StaticModelUseKind
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
     * Returns the type of this StaticModelUseKind
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
        members.put("CommunicationWrapper", COMMUNICATIONWRAPPER);
        members.put("ControlActWrapper", CONTROLACTWRAPPER);
        members.put("SemanticPayload", SEMANTICPAYLOAD);
        members.put("PresentationStructure", PRESENTATIONSTRUCTURE);
        members.put("QueryDefinition", QUERYDEFINITION);
        members.put("CMET", CMET);
        members.put("Template", TEMPLATE);
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
     * Returns the String representation of this StaticModelUseKind
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
     * Returns a new StaticModelUseKind based on the given String
     * value.
     * 
     * @param string
     * @return StaticModelUseKind
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.StaticModelUseKind valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid StaticModelUseKind";
            throw new IllegalArgumentException(err);
        }
        return (StaticModelUseKind) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.StaticModelUseKind valueOf(java.lang.String) 

}
