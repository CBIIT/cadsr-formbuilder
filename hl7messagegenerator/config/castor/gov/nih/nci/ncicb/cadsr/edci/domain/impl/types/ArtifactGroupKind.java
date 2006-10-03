/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: ArtifactGroupKind.java,v 1.1 2006-10-03 19:42:06 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * A list of the types of groups of artifacts that can be
 * referenced
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:06 $
 */
public class ArtifactGroupKind implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The Footnotes type
     */
    public static final int FOOTNOTES_TYPE = 0;

    /**
     * The instance of the Footnotes type
     */
    public static final ArtifactGroupKind FOOTNOTES = new ArtifactGroupKind(FOOTNOTES_TYPE, "Footnotes");

    /**
     * The Packages type
     */
    public static final int PACKAGES_TYPE = 1;

    /**
     * The instance of the Packages type
     */
    public static final ArtifactGroupKind PACKAGES = new ArtifactGroupKind(PACKAGES_TYPE, "Packages");

    /**
     * The Header type
     */
    public static final int HEADER_TYPE = 2;

    /**
     * The instance of the Header type
     */
    public static final ArtifactGroupKind HEADER = new ArtifactGroupKind(HEADER_TYPE, "Header");

    /**
     * The Annotations type
     */
    public static final int ANNOTATIONS_TYPE = 3;

    /**
     * The instance of the Annotations type
     */
    public static final ArtifactGroupKind ANNOTATIONS = new ArtifactGroupKind(ANNOTATIONS_TYPE, "Annotations");

    /**
     * The History type
     */
    public static final int HISTORY_TYPE = 4;

    /**
     * The instance of the History type
     */
    public static final ArtifactGroupKind HISTORY = new ArtifactGroupKind(HISTORY_TYPE, "History");

    /**
     * The Derivation type
     */
    public static final int DERIVATION_TYPE = 5;

    /**
     * The instance of the Derivation type
     */
    public static final ArtifactGroupKind DERIVATION = new ArtifactGroupKind(DERIVATION_TYPE, "Derivation");

    /**
     * The Properties type
     */
    public static final int PROPERTIES_TYPE = 6;

    /**
     * The instance of the Properties type
     */
    public static final ArtifactGroupKind PROPERTIES = new ArtifactGroupKind(PROPERTIES_TYPE, "Properties");

    /**
     * The StaticModels type
     */
    public static final int STATICMODELS_TYPE = 7;

    /**
     * The instance of the StaticModels type
     */
    public static final ArtifactGroupKind STATICMODELS = new ArtifactGroupKind(STATICMODELS_TYPE, "StaticModels");

    /**
     * The SubjectAreas type
     */
    public static final int SUBJECTAREAS_TYPE = 8;

    /**
     * The instance of the SubjectAreas type
     */
    public static final ArtifactGroupKind SUBJECTAREAS = new ArtifactGroupKind(SUBJECTAREAS_TYPE, "SubjectAreas");

    /**
     * The Classes type
     */
    public static final int CLASSES_TYPE = 9;

    /**
     * The instance of the Classes type
     */
    public static final ArtifactGroupKind CLASSES = new ArtifactGroupKind(CLASSES_TYPE, "Classes");

    /**
     * The StateEngines type
     */
    public static final int STATEENGINES_TYPE = 10;

    /**
     * The instance of the StateEngines type
     */
    public static final ArtifactGroupKind STATEENGINES = new ArtifactGroupKind(STATEENGINES_TYPE, "StateEngines");

    /**
     * The States type
     */
    public static final int STATES_TYPE = 11;

    /**
     * The instance of the States type
     */
    public static final ArtifactGroupKind STATES = new ArtifactGroupKind(STATES_TYPE, "States");

    /**
     * The StateTransitions type
     */
    public static final int STATETRANSITIONS_TYPE = 12;

    /**
     * The instance of the StateTransitions type
     */
    public static final ArtifactGroupKind STATETRANSITIONS = new ArtifactGroupKind(STATETRANSITIONS_TYPE, "StateTransitions");

    /**
     * The Associations type
     */
    public static final int ASSOCIATIONS_TYPE = 13;

    /**
     * The instance of the Associations type
     */
    public static final ArtifactGroupKind ASSOCIATIONS = new ArtifactGroupKind(ASSOCIATIONS_TYPE, "Associations");

    /**
     * The Attributes type
     */
    public static final int ATTRIBUTES_TYPE = 14;

    /**
     * The instance of the Attributes type
     */
    public static final ArtifactGroupKind ATTRIBUTES = new ArtifactGroupKind(ATTRIBUTES_TYPE, "Attributes");

    /**
     * The TriggerEvents type
     */
    public static final int TRIGGEREVENTS_TYPE = 15;

    /**
     * The instance of the TriggerEvents type
     */
    public static final ArtifactGroupKind TRIGGEREVENTS = new ArtifactGroupKind(TRIGGEREVENTS_TYPE, "TriggerEvents");

    /**
     * The ApplicationRoles type
     */
    public static final int APPLICATIONROLES_TYPE = 16;

    /**
     * The instance of the ApplicationRoles type
     */
    public static final ArtifactGroupKind APPLICATIONROLES = new ArtifactGroupKind(APPLICATIONROLES_TYPE, "ApplicationRoles");

    /**
     * The Interactions type
     */
    public static final int INTERACTIONS_TYPE = 17;

    /**
     * The instance of the Interactions type
     */
    public static final ArtifactGroupKind INTERACTIONS = new ArtifactGroupKind(INTERACTIONS_TYPE, "Interactions");

    /**
     * The ReceiverResponsibilities type
     */
    public static final int RECEIVERRESPONSIBILITIES_TYPE = 18;

    /**
     * The instance of the ReceiverResponsibilities type
     */
    public static final ArtifactGroupKind RECEIVERRESPONSIBILITIES = new ArtifactGroupKind(RECEIVERRESPONSIBILITIES_TYPE, "ReceiverResponsibilities");

    /**
     * The VocabularyDomains type
     */
    public static final int VOCABULARYDOMAINS_TYPE = 19;

    /**
     * The instance of the VocabularyDomains type
     */
    public static final ArtifactGroupKind VOCABULARYDOMAINS = new ArtifactGroupKind(VOCABULARYDOMAINS_TYPE, "VocabularyDomains");

    /**
     * The VocabularyCodes type
     */
    public static final int VOCABULARYCODES_TYPE = 20;

    /**
     * The instance of the VocabularyCodes type
     */
    public static final ArtifactGroupKind VOCABULARYCODES = new ArtifactGroupKind(VOCABULARYCODES_TYPE, "VocabularyCodes");

    /**
     * The Templates type
     */
    public static final int TEMPLATES_TYPE = 21;

    /**
     * The instance of the Templates type
     */
    public static final ArtifactGroupKind TEMPLATES = new ArtifactGroupKind(TEMPLATES_TYPE, "Templates");

    /**
     * The CommunicationProtocols type
     */
    public static final int COMMUNICATIONPROTOCOLS_TYPE = 22;

    /**
     * The instance of the CommunicationProtocols type
     */
    public static final ArtifactGroupKind COMMUNICATIONPROTOCOLS = new ArtifactGroupKind(COMMUNICATIONPROTOCOLS_TYPE, "CommunicationProtocols");

    /**
     * The ImplementationTechnologySpecifications type
     */
    public static final int IMPLEMENTATIONTECHNOLOGYSPECIFICATIONS_TYPE = 23;

    /**
     * The instance of the ImplementationTechnologySpecifications
     * type
     */
    public static final ArtifactGroupKind IMPLEMENTATIONTECHNOLOGYSPECIFICATIONS = new ArtifactGroupKind(IMPLEMENTATIONTECHNOLOGYSPECIFICATIONS_TYPE, "ImplementationTechnologySpecifications");

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

    private ArtifactGroupKind(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.ArtifactGroupKind(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * ArtifactGroupKind
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
     * Returns the type of this ArtifactGroupKind
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
        members.put("Footnotes", FOOTNOTES);
        members.put("Packages", PACKAGES);
        members.put("Header", HEADER);
        members.put("Annotations", ANNOTATIONS);
        members.put("History", HISTORY);
        members.put("Derivation", DERIVATION);
        members.put("Properties", PROPERTIES);
        members.put("StaticModels", STATICMODELS);
        members.put("SubjectAreas", SUBJECTAREAS);
        members.put("Classes", CLASSES);
        members.put("StateEngines", STATEENGINES);
        members.put("States", STATES);
        members.put("StateTransitions", STATETRANSITIONS);
        members.put("Associations", ASSOCIATIONS);
        members.put("Attributes", ATTRIBUTES);
        members.put("TriggerEvents", TRIGGEREVENTS);
        members.put("ApplicationRoles", APPLICATIONROLES);
        members.put("Interactions", INTERACTIONS);
        members.put("ReceiverResponsibilities", RECEIVERRESPONSIBILITIES);
        members.put("VocabularyDomains", VOCABULARYDOMAINS);
        members.put("VocabularyCodes", VOCABULARYCODES);
        members.put("Templates", TEMPLATES);
        members.put("CommunicationProtocols", COMMUNICATIONPROTOCOLS);
        members.put("ImplementationTechnologySpecifications", IMPLEMENTATIONTECHNOLOGYSPECIFICATIONS);
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
     * Returns the String representation of this ArtifactGroupKind
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
     * Returns a new ArtifactGroupKind based on the given String
     * value.
     * 
     * @param string
     * @return ArtifactGroupKind
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.ArtifactGroupKind valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid ArtifactGroupKind";
            throw new IllegalArgumentException(err);
        }
        return (ArtifactGroupKind) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.ArtifactGroupKind valueOf(java.lang.String) 

}
