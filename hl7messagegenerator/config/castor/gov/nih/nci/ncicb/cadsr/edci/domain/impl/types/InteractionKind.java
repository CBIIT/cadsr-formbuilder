/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: InteractionKind.java,v 1.1 2006-10-03 19:42:17 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Identifies the allowed types of interactions
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:17 $
 */
public class InteractionKind implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The Query type
     */
    public static final int QUERY_TYPE = 0;

    /**
     * The instance of the Query type
     */
    public static final InteractionKind QUERY = new InteractionKind(QUERY_TYPE, "Query");

    /**
     * The QueryResponse type
     */
    public static final int QUERYRESPONSE_TYPE = 1;

    /**
     * The instance of the QueryResponse type
     */
    public static final InteractionKind QUERYRESPONSE = new InteractionKind(QUERYRESPONSE_TYPE, "QueryResponse");

    /**
     * The EventNotification type
     */
    public static final int EVENTNOTIFICATION_TYPE = 2;

    /**
     * The instance of the EventNotification type
     */
    public static final InteractionKind EVENTNOTIFICATION = new InteractionKind(EVENTNOTIFICATION_TYPE, "EventNotification");

    /**
     * The RequestForAction type
     */
    public static final int REQUESTFORACTION_TYPE = 3;

    /**
     * The instance of the RequestForAction type
     */
    public static final InteractionKind REQUESTFORACTION = new InteractionKind(REQUESTFORACTION_TYPE, "RequestForAction");

    /**
     * The RequestResponseAccept type
     */
    public static final int REQUESTRESPONSEACCEPT_TYPE = 4;

    /**
     * The instance of the RequestResponseAccept type
     */
    public static final InteractionKind REQUESTRESPONSEACCEPT = new InteractionKind(REQUESTRESPONSEACCEPT_TYPE, "RequestResponseAccept");

    /**
     * The RequestResponseRefuse type
     */
    public static final int REQUESTRESPONSEREFUSE_TYPE = 5;

    /**
     * The instance of the RequestResponseRefuse type
     */
    public static final InteractionKind REQUESTRESPONSEREFUSE = new InteractionKind(REQUESTRESPONSEREFUSE_TYPE, "RequestResponseRefuse");

    /**
     * The UntriggeredNotification type
     */
    public static final int UNTRIGGEREDNOTIFICATION_TYPE = 6;

    /**
     * The instance of the UntriggeredNotification type
     */
    public static final InteractionKind UNTRIGGEREDNOTIFICATION = new InteractionKind(UNTRIGGEREDNOTIFICATION_TYPE, "UntriggeredNotification");

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

    private InteractionKind(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.InteractionKind(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * InteractionKind
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
     * Returns the type of this InteractionKind
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
        members.put("Query", QUERY);
        members.put("QueryResponse", QUERYRESPONSE);
        members.put("EventNotification", EVENTNOTIFICATION);
        members.put("RequestForAction", REQUESTFORACTION);
        members.put("RequestResponseAccept", REQUESTRESPONSEACCEPT);
        members.put("RequestResponseRefuse", REQUESTRESPONSEREFUSE);
        members.put("UntriggeredNotification", UNTRIGGEREDNOTIFICATION);
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
     * Returns the String representation of this InteractionKind
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
     * Returns a new InteractionKind based on the given String
     * value.
     * 
     * @param string
     * @return InteractionKind
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.InteractionKind valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid InteractionKind";
            throw new IllegalArgumentException(err);
        }
        return (InteractionKind) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.InteractionKind valueOf(java.lang.String) 

}
