/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: AnnotationKind.java,v 1.1 2006-10-03 19:42:06 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Identifies the kinds of annotations that can be referencedUML:
 * Used to reference a tagged item
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:06 $
 */
public class AnnotationKind implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The Definition type
     */
    public static final int DEFINITION_TYPE = 0;

    /**
     * The instance of the Definition type
     */
    public static final AnnotationKind DEFINITION = new AnnotationKind(DEFINITION_TYPE, "Definition");

    /**
     * The Description type
     */
    public static final int DESCRIPTION_TYPE = 1;

    /**
     * The instance of the Description type
     */
    public static final AnnotationKind DESCRIPTION = new AnnotationKind(DESCRIPTION_TYPE, "Description");

    /**
     * The UsageNotes type
     */
    public static final int USAGENOTES_TYPE = 2;

    /**
     * The instance of the UsageNotes type
     */
    public static final AnnotationKind USAGENOTES = new AnnotationKind(USAGENOTES_TYPE, "UsageNotes");

    /**
     * The Rationale type
     */
    public static final int RATIONALE_TYPE = 3;

    /**
     * The instance of the Rationale type
     */
    public static final AnnotationKind RATIONALE = new AnnotationKind(RATIONALE_TYPE, "Rationale");

    /**
     * The Requirements type
     */
    public static final int REQUIREMENTS_TYPE = 4;

    /**
     * The instance of the Requirements type
     */
    public static final AnnotationKind REQUIREMENTS = new AnnotationKind(REQUIREMENTS_TYPE, "Requirements");

    /**
     * The Walkthrough type
     */
    public static final int WALKTHROUGH_TYPE = 5;

    /**
     * The instance of the Walkthrough type
     */
    public static final AnnotationKind WALKTHROUGH = new AnnotationKind(WALKTHROUGH_TYPE, "Walkthrough");

    /**
     * The Appendix type
     */
    public static final int APPENDIX_TYPE = 6;

    /**
     * The instance of the Appendix type
     */
    public static final AnnotationKind APPENDIX = new AnnotationKind(APPENDIX_TYPE, "Appendix");

    /**
     * The OtherAnnotation type
     */
    public static final int OTHERANNOTATION_TYPE = 7;

    /**
     * The instance of the OtherAnnotation type
     */
    public static final AnnotationKind OTHERANNOTATION = new AnnotationKind(OTHERANNOTATION_TYPE, "OtherAnnotation");

    /**
     * The Mapping type
     */
    public static final int MAPPING_TYPE = 8;

    /**
     * The instance of the Mapping type
     */
    public static final AnnotationKind MAPPING = new AnnotationKind(MAPPING_TYPE, "Mapping");

    /**
     * The Constraint type
     */
    public static final int CONSTRAINT_TYPE = 9;

    /**
     * The instance of the Constraint type
     */
    public static final AnnotationKind CONSTRAINT = new AnnotationKind(CONSTRAINT_TYPE, "Constraint");

    /**
     * The OpenIssue type
     */
    public static final int OPENISSUE_TYPE = 10;

    /**
     * The instance of the OpenIssue type
     */
    public static final AnnotationKind OPENISSUE = new AnnotationKind(OPENISSUE_TYPE, "OpenIssue");

    /**
     * The DesignComment type
     */
    public static final int DESIGNCOMMENT_TYPE = 11;

    /**
     * The instance of the DesignComment type
     */
    public static final AnnotationKind DESIGNCOMMENT = new AnnotationKind(DESIGNCOMMENT_TYPE, "DesignComment");

    /**
     * The StaticExample type
     */
    public static final int STATICEXAMPLE_TYPE = 12;

    /**
     * The instance of the StaticExample type
     */
    public static final AnnotationKind STATICEXAMPLE = new AnnotationKind(STATICEXAMPLE_TYPE, "StaticExample");

    /**
     * The BallotComment type
     */
    public static final int BALLOTCOMMENT_TYPE = 13;

    /**
     * The instance of the BallotComment type
     */
    public static final AnnotationKind BALLOTCOMMENT = new AnnotationKind(BALLOTCOMMENT_TYPE, "BallotComment");

    /**
     * The ChangeRequest type
     */
    public static final int CHANGEREQUEST_TYPE = 14;

    /**
     * The instance of the ChangeRequest type
     */
    public static final AnnotationKind CHANGEREQUEST = new AnnotationKind(CHANGEREQUEST_TYPE, "ChangeRequest");

    /**
     * The DeprecationInfo type
     */
    public static final int DEPRECATIONINFO_TYPE = 15;

    /**
     * The instance of the DeprecationInfo type
     */
    public static final AnnotationKind DEPRECATIONINFO = new AnnotationKind(DEPRECATIONINFO_TYPE, "DeprecationInfo");

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

    private AnnotationKind(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.AnnotationKind(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * AnnotationKind
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
     * Returns the type of this AnnotationKind
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
        members.put("Definition", DEFINITION);
        members.put("Description", DESCRIPTION);
        members.put("UsageNotes", USAGENOTES);
        members.put("Rationale", RATIONALE);
        members.put("Requirements", REQUIREMENTS);
        members.put("Walkthrough", WALKTHROUGH);
        members.put("Appendix", APPENDIX);
        members.put("OtherAnnotation", OTHERANNOTATION);
        members.put("Mapping", MAPPING);
        members.put("Constraint", CONSTRAINT);
        members.put("OpenIssue", OPENISSUE);
        members.put("DesignComment", DESIGNCOMMENT);
        members.put("StaticExample", STATICEXAMPLE);
        members.put("BallotComment", BALLOTCOMMENT);
        members.put("ChangeRequest", CHANGEREQUEST);
        members.put("DeprecationInfo", DEPRECATIONINFO);
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
     * Returns the String representation of this AnnotationKind
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
     * Returns a new AnnotationKind based on the given String
     * value.
     * 
     * @param string
     * @return AnnotationKind
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.AnnotationKind valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid AnnotationKind";
            throw new IllegalArgumentException(err);
        }
        return (AnnotationKind) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.AnnotationKind valueOf(java.lang.String) 

}
