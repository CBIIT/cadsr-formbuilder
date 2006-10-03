/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: DataElementGroupImpl.java,v 1.1 2006-10-03 19:42:06 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * Each DataElementGroup defines a set of DataElements that
 * constitute a globally defined Group. DataElementGroups are the
 * starting point for the composition of DCI-specific GroupDefs.
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:06 $
 */
public class DataElementGroupImpl implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Identifier for the Group definition.
     */
    private java.lang.String _name;

    /**
     * The globally-unique identifier for this object.
     */
    private java.lang.String _GUID;

    /**
     * An optional prefix for the Name of the Group, that
     * designates the namespace within which the Name resides, and
     * within which the Name must be unique. If not provided, the
     * receiving system should treat Names as if their Namespace
     * was "Standard".
     */
    private java.lang.String _namespace;

    /**
     * Description of this Data Element Group.
     */
    private java.lang.String _description;

    /**
     * A Group member, specified by a reference to the Data
     * Element's definition.
     */
    private java.util.ArrayList _memberImplList;


      //----------------/
     //- Constructors -/
    //----------------/

    public DataElementGroupImpl() 
     {
        super();
        _memberImplList = new ArrayList();
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementGroupImpl()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addMemberImpl
     * 
     * 
     * 
     * @param vMemberImpl
     */
    public void addMemberImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.MemberImpl vMemberImpl)
        throws java.lang.IndexOutOfBoundsException
    {
        _memberImplList.add(vMemberImpl);
    } //-- void addMemberImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.MemberImpl) 

    /**
     * Method addMemberImpl
     * 
     * 
     * 
     * @param index
     * @param vMemberImpl
     */
    public void addMemberImpl(int index, gov.nih.nci.ncicb.cadsr.edci.domain.impl.MemberImpl vMemberImpl)
        throws java.lang.IndexOutOfBoundsException
    {
        _memberImplList.add(index, vMemberImpl);
    } //-- void addMemberImpl(int, gov.nih.nci.ncicb.cadsr.edci.domain.impl.MemberImpl) 

    /**
     * Method clearMemberImpl
     * 
     */
    public void clearMemberImpl()
    {
        _memberImplList.clear();
    } //-- void clearMemberImpl() 

    /**
     * Method enumerateMemberImpl
     * 
     * 
     * 
     * @return Enumeration
     */
    public java.util.Enumeration enumerateMemberImpl()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_memberImplList.iterator());
    } //-- java.util.Enumeration enumerateMemberImpl() 

    /**
     * Returns the value of field 'description'. The field
     * 'description' has the following description: Description of
     * this Data Element Group.
     * 
     * @return String
     * @return the value of field 'description'.
     */
    public java.lang.String getDescription()
    {
        return this._description;
    } //-- java.lang.String getDescription() 

    /**
     * Returns the value of field 'GUID'. The field 'GUID' has the
     * following description: The globally-unique identifier for
     * this object.
     * 
     * @return String
     * @return the value of field 'GUID'.
     */
    public java.lang.String getGUID()
    {
        return this._GUID;
    } //-- java.lang.String getGUID() 

    /**
     * Method getMemberImpl
     * 
     * 
     * 
     * @param index
     * @return MemberImpl
     */
    public gov.nih.nci.ncicb.cadsr.edci.domain.impl.MemberImpl getMemberImpl(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _memberImplList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (gov.nih.nci.ncicb.cadsr.edci.domain.impl.MemberImpl) _memberImplList.get(index);
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.MemberImpl getMemberImpl(int) 

    /**
     * Method getMemberImpl
     * 
     * 
     * 
     * @return MemberImpl
     */
    public gov.nih.nci.ncicb.cadsr.edci.domain.impl.MemberImpl[] getMemberImpl()
    {
        int size = _memberImplList.size();
        gov.nih.nci.ncicb.cadsr.edci.domain.impl.MemberImpl[] mArray = new gov.nih.nci.ncicb.cadsr.edci.domain.impl.MemberImpl[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (gov.nih.nci.ncicb.cadsr.edci.domain.impl.MemberImpl) _memberImplList.get(index);
        }
        return mArray;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.MemberImpl[] getMemberImpl() 

    /**
     * Method getMemberImplCount
     * 
     * 
     * 
     * @return int
     */
    public int getMemberImplCount()
    {
        return _memberImplList.size();
    } //-- int getMemberImplCount() 

    /**
     * Returns the value of field 'name'. The field 'name' has the
     * following description: Identifier for the Group definition.
     * 
     * @return String
     * @return the value of field 'name'.
     */
    public java.lang.String getName()
    {
        return this._name;
    } //-- java.lang.String getName() 

    /**
     * Returns the value of field 'namespace'. The field
     * 'namespace' has the following description: An optional
     * prefix for the Name of the Group, that designates the
     * namespace within which the Name resides, and within which
     * the Name must be unique. If not provided, the receiving
     * system should treat Names as if their Namespace was
     * "Standard".
     * 
     * @return String
     * @return the value of field 'namespace'.
     */
    public java.lang.String getNamespace()
    {
        return this._namespace;
    } //-- java.lang.String getNamespace() 

    /**
     * Method removeMemberImpl
     * 
     * 
     * 
     * @param vMemberImpl
     * @return boolean
     */
    public boolean removeMemberImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.MemberImpl vMemberImpl)
    {
        boolean removed = _memberImplList.remove(vMemberImpl);
        return removed;
    } //-- boolean removeMemberImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.MemberImpl) 

    /**
     * Sets the value of field 'description'. The field
     * 'description' has the following description: Description of
     * this Data Element Group.
     * 
     * @param description the value of field 'description'.
     */
    public void setDescription(java.lang.String description)
    {
        this._description = description;
    } //-- void setDescription(java.lang.String) 

    /**
     * Sets the value of field 'GUID'. The field 'GUID' has the
     * following description: The globally-unique identifier for
     * this object.
     * 
     * @param GUID the value of field 'GUID'.
     */
    public void setGUID(java.lang.String GUID)
    {
        this._GUID = GUID;
    } //-- void setGUID(java.lang.String) 

    /**
     * Method setMemberImpl
     * 
     * 
     * 
     * @param index
     * @param vMemberImpl
     */
    public void setMemberImpl(int index, gov.nih.nci.ncicb.cadsr.edci.domain.impl.MemberImpl vMemberImpl)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _memberImplList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _memberImplList.set(index, vMemberImpl);
    } //-- void setMemberImpl(int, gov.nih.nci.ncicb.cadsr.edci.domain.impl.MemberImpl) 

    /**
     * Method setMemberImpl
     * 
     * 
     * 
     * @param memberImplArray
     */
    public void setMemberImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.MemberImpl[] memberImplArray)
    {
        //-- copy array
        _memberImplList.clear();
        for (int i = 0; i < memberImplArray.length; i++) {
            _memberImplList.add(memberImplArray[i]);
        }
    } //-- void setMemberImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.MemberImpl) 

    /**
     * Sets the value of field 'name'. The field 'name' has the
     * following description: Identifier for the Group definition.
     * 
     * @param name the value of field 'name'.
     */
    public void setName(java.lang.String name)
    {
        this._name = name;
    } //-- void setName(java.lang.String) 

    /**
     * Sets the value of field 'namespace'. The field 'namespace'
     * has the following description: An optional prefix for the
     * Name of the Group, that designates the namespace within
     * which the Name resides, and within which the Name must be
     * unique. If not provided, the receiving system should treat
     * Names as if their Namespace was "Standard".
     * 
     * @param namespace the value of field 'namespace'.
     */
    public void setNamespace(java.lang.String namespace)
    {
        this._namespace = namespace;
    } //-- void setNamespace(java.lang.String) 

}
