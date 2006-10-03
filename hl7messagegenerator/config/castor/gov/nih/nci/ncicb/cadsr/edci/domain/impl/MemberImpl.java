/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: MemberImpl.java,v 1.1 2006-10-03 19:42:06 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;

/**
 * A Group member, specified by a reference to the Data Element's
 * definition.
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:06 $
 */
public class MemberImpl implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The GUID of the referenced Data Element
     */
    private java.lang.Object _dataElementGUID;


      //----------------/
     //- Constructors -/
    //----------------/

    public MemberImpl() 
     {
        super();
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.MemberImpl()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'dataElementGUID'. The field
     * 'dataElementGUID' has the following description: The GUID of
     * the referenced Data Element
     * 
     * @return Object
     * @return the value of field 'dataElementGUID'.
     */
    public java.lang.Object getDataElementGUID()
    {
        return this._dataElementGUID;
    } //-- java.lang.Object getDataElementGUID() 

    /**
     * Sets the value of field 'dataElementGUID'. The field
     * 'dataElementGUID' has the following description: The GUID of
     * the referenced Data Element
     * 
     * @param dataElementGUID the value of field 'dataElementGUID'.
     */
    public void setDataElementGUID(java.lang.Object dataElementGUID)
    {
        this._dataElementGUID = dataElementGUID;
    } //-- void setDataElementGUID(java.lang.Object) 

}
