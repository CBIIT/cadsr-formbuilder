/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: CascadingAnnotationElementKind.java,v 1.1 2006-10-03 19:42:06 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Identifies the kinds of elements against which cascading
 * annotations can be createdUML: Used to reference a tagged item
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:06 $
 */
public class CascadingAnnotationElementKind implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The StaticModel type
     */
    public static final int STATICMODEL_TYPE = 0;

    /**
     * The instance of the StaticModel type
     */
    public static final CascadingAnnotationElementKind STATICMODEL = new CascadingAnnotationElementKind(STATICMODEL_TYPE, "StaticModel");

    /**
     * The Class type
     */
    public static final int CLASS_TYPE = 1;

    /**
     * The instance of the Class type
     */
    public static final CascadingAnnotationElementKind CLASS = new CascadingAnnotationElementKind(CLASS_TYPE, "Class");

    /**
     * The Stub type
     */
    public static final int STUB_TYPE = 2;

    /**
     * The instance of the Stub type
     */
    public static final CascadingAnnotationElementKind STUB = new CascadingAnnotationElementKind(STUB_TYPE, "Stub");

    /**
     * The CMET type
     */
    public static final int CMET_TYPE = 3;

    /**
     * The instance of the CMET type
     */
    public static final CascadingAnnotationElementKind CMET = new CascadingAnnotationElementKind(CMET_TYPE, "CMET");

    /**
     * The EntryPoint type
     */
    public static final int ENTRYPOINT_TYPE = 4;

    /**
     * The instance of the EntryPoint type
     */
    public static final CascadingAnnotationElementKind ENTRYPOINT = new CascadingAnnotationElementKind(ENTRYPOINT_TYPE, "EntryPoint");

    /**
     * The Attribute type
     */
    public static final int ATTRIBUTE_TYPE = 5;

    /**
     * The instance of the Attribute type
     */
    public static final CascadingAnnotationElementKind ATTRIBUTE = new CascadingAnnotationElementKind(ATTRIBUTE_TYPE, "Attribute");

    /**
     * The Association type
     */
    public static final int ASSOCIATION_TYPE = 6;

    /**
     * The instance of the Association type
     */
    public static final CascadingAnnotationElementKind ASSOCIATION = new CascadingAnnotationElementKind(ASSOCIATION_TYPE, "Association");

    /**
     * The AssociationEnd type
     */
    public static final int ASSOCIATIONEND_TYPE = 7;

    /**
     * The instance of the AssociationEnd type
     */
    public static final CascadingAnnotationElementKind ASSOCIATIONEND = new CascadingAnnotationElementKind(ASSOCIATIONEND_TYPE, "AssociationEnd");

    /**
     * The Generalization type
     */
    public static final int GENERALIZATION_TYPE = 8;

    /**
     * The instance of the Generalization type
     */
    public static final CascadingAnnotationElementKind GENERALIZATION = new CascadingAnnotationElementKind(GENERALIZATION_TYPE, "Generalization");

    /**
     * The StateMachine type
     */
    public static final int STATEMACHINE_TYPE = 9;

    /**
     * The instance of the StateMachine type
     */
    public static final CascadingAnnotationElementKind STATEMACHINE = new CascadingAnnotationElementKind(STATEMACHINE_TYPE, "StateMachine");

    /**
     * The State type
     */
    public static final int STATE_TYPE = 10;

    /**
     * The instance of the State type
     */
    public static final CascadingAnnotationElementKind STATE = new CascadingAnnotationElementKind(STATE_TYPE, "State");

    /**
     * The StateTransition type
     */
    public static final int STATETRANSITION_TYPE = 11;

    /**
     * The instance of the StateTransition type
     */
    public static final CascadingAnnotationElementKind STATETRANSITION = new CascadingAnnotationElementKind(STATETRANSITION_TYPE, "StateTransition");

    /**
     * The Datatype type
     */
    public static final int DATATYPE_TYPE = 12;

    /**
     * The instance of the Datatype type
     */
    public static final CascadingAnnotationElementKind DATATYPE = new CascadingAnnotationElementKind(DATATYPE_TYPE, "Datatype");

    /**
     * The DatatypeOperation type
     */
    public static final int DATATYPEOPERATION_TYPE = 13;

    /**
     * The instance of the DatatypeOperation type
     */
    public static final CascadingAnnotationElementKind DATATYPEOPERATION = new CascadingAnnotationElementKind(DATATYPEOPERATION_TYPE, "DatatypeOperation");

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

    private CascadingAnnotationElementKind(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.CascadingAnnotationElementKind(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * CascadingAnnotationElementKind
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
     * Returns the type of this CascadingAnnotationElementKind
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
        members.put("StaticModel", STATICMODEL);
        members.put("Class", CLASS);
        members.put("Stub", STUB);
        members.put("CMET", CMET);
        members.put("EntryPoint", ENTRYPOINT);
        members.put("Attribute", ATTRIBUTE);
        members.put("Association", ASSOCIATION);
        members.put("AssociationEnd", ASSOCIATIONEND);
        members.put("Generalization", GENERALIZATION);
        members.put("StateMachine", STATEMACHINE);
        members.put("State", STATE);
        members.put("StateTransition", STATETRANSITION);
        members.put("Datatype", DATATYPE);
        members.put("DatatypeOperation", DATATYPEOPERATION);
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
     * CascadingAnnotationElementKind
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
     * Returns a new CascadingAnnotationElementKind based on the
     * given String value.
     * 
     * @param string
     * @return CascadingAnnotationElementKind
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.CascadingAnnotationElementKind valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid CascadingAnnotationElementKind";
            throw new IllegalArgumentException(err);
        }
        return (CascadingAnnotationElementKind) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.CascadingAnnotationElementKind valueOf(java.lang.String) 

}
