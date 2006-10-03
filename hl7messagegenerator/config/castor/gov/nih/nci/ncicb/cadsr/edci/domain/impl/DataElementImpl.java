/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: DataElementImpl.java,v 1.1 2006-10-03 19:42:06 marwahah Exp $
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
 * A Data Element is a Data Element Concept constrained by a
 * particular Value Domain, and given a name.
 * 
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:06 $
 */
public class DataElementImpl implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Name of this Data Element.
     */
    private java.lang.String _name;

    /**
     * The globally-unique identifier for this object.
     */
    private java.lang.String _GUID;

    /**
     * The Id of the Data Element Concept that is the basis for
     * this Data Element.
     */
    private java.lang.String _dataElementConceptGUID;

    /**
     * Text definition of the meaning of the Data Element.
     */
    private java.lang.String _definition;

    /**
     * The GUID of the Value Domain that constrains the values that
     * can be set for this Data Element.
     */
    private java.lang.String _valueDomainGUID;

    /**
     * Subset of Value Domain to use for this element (subject to
     * override in ItemDef). Should not be supplied if Value domain
     * is not enumerated.
     */
    private java.lang.String _EVDSubsetId;

    /**
     * An optional prefix for the Name of the DataElementConcept,
     * that designates the namespace within which the Name resides,
     * and within which the Name must be unique. If not provided,
     * the receiving system should treat Names as if their
     * Namespace was "Standard".
     */
    private java.lang.String _namespace;

    /**
     * Optional human-readable text giving additional information
     * about this Item Definition.
     */
    private java.lang.String _description;

    /**
     * Language-specific texts associated with the Data Element.
     */
    private java.util.ArrayList _dataElementTextImplList;

    /**
     * A purpose-specific alternate name for this Data Element
     */
    private java.util.ArrayList _alternateDesignationImplList;


      //----------------/
     //- Constructors -/
    //----------------/

    public DataElementImpl() 
     {
        super();
        _dataElementTextImplList = new ArrayList();
        _alternateDesignationImplList = new ArrayList();
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementImpl()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addAlternateDesignationImpl
     * 
     * 
     * 
     * @param vAlternateDesignationImpl
     */
    public void addAlternateDesignationImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.AlternateDesignationImpl vAlternateDesignationImpl)
        throws java.lang.IndexOutOfBoundsException
    {
        _alternateDesignationImplList.add(vAlternateDesignationImpl);
    } //-- void addAlternateDesignationImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.AlternateDesignationImpl) 

    /**
     * Method addAlternateDesignationImpl
     * 
     * 
     * 
     * @param index
     * @param vAlternateDesignationImpl
     */
    public void addAlternateDesignationImpl(int index, gov.nih.nci.ncicb.cadsr.edci.domain.impl.AlternateDesignationImpl vAlternateDesignationImpl)
        throws java.lang.IndexOutOfBoundsException
    {
        _alternateDesignationImplList.add(index, vAlternateDesignationImpl);
    } //-- void addAlternateDesignationImpl(int, gov.nih.nci.ncicb.cadsr.edci.domain.impl.AlternateDesignationImpl) 

    /**
     * Method addDataElementTextImpl
     * 
     * 
     * 
     * @param vDataElementTextImpl
     */
    public void addDataElementTextImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementTextImpl vDataElementTextImpl)
        throws java.lang.IndexOutOfBoundsException
    {
        _dataElementTextImplList.add(vDataElementTextImpl);
    } //-- void addDataElementTextImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementTextImpl) 

    /**
     * Method addDataElementTextImpl
     * 
     * 
     * 
     * @param index
     * @param vDataElementTextImpl
     */
    public void addDataElementTextImpl(int index, gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementTextImpl vDataElementTextImpl)
        throws java.lang.IndexOutOfBoundsException
    {
        _dataElementTextImplList.add(index, vDataElementTextImpl);
    } //-- void addDataElementTextImpl(int, gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementTextImpl) 

    /**
     * Method clearAlternateDesignationImpl
     * 
     */
    public void clearAlternateDesignationImpl()
    {
        _alternateDesignationImplList.clear();
    } //-- void clearAlternateDesignationImpl() 

    /**
     * Method clearDataElementTextImpl
     * 
     */
    public void clearDataElementTextImpl()
    {
        _dataElementTextImplList.clear();
    } //-- void clearDataElementTextImpl() 

    /**
     * Method enumerateAlternateDesignationImpl
     * 
     * 
     * 
     * @return Enumeration
     */
    public java.util.Enumeration enumerateAlternateDesignationImpl()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_alternateDesignationImplList.iterator());
    } //-- java.util.Enumeration enumerateAlternateDesignationImpl() 

    /**
     * Method enumerateDataElementTextImpl
     * 
     * 
     * 
     * @return Enumeration
     */
    public java.util.Enumeration enumerateDataElementTextImpl()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_dataElementTextImplList.iterator());
    } //-- java.util.Enumeration enumerateDataElementTextImpl() 

    /**
     * Method getAlternateDesignationImpl
     * 
     * 
     * 
     * @param index
     * @return AlternateDesignationImpl
     */
    public gov.nih.nci.ncicb.cadsr.edci.domain.impl.AlternateDesignationImpl getAlternateDesignationImpl(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _alternateDesignationImplList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (gov.nih.nci.ncicb.cadsr.edci.domain.impl.AlternateDesignationImpl) _alternateDesignationImplList.get(index);
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.AlternateDesignationImpl getAlternateDesignationImpl(int) 

    /**
     * Method getAlternateDesignationImpl
     * 
     * 
     * 
     * @return AlternateDesignationImpl
     */
    public gov.nih.nci.ncicb.cadsr.edci.domain.impl.AlternateDesignationImpl[] getAlternateDesignationImpl()
    {
        int size = _alternateDesignationImplList.size();
        gov.nih.nci.ncicb.cadsr.edci.domain.impl.AlternateDesignationImpl[] mArray = new gov.nih.nci.ncicb.cadsr.edci.domain.impl.AlternateDesignationImpl[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (gov.nih.nci.ncicb.cadsr.edci.domain.impl.AlternateDesignationImpl) _alternateDesignationImplList.get(index);
        }
        return mArray;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.AlternateDesignationImpl[] getAlternateDesignationImpl() 

    /**
     * Method getAlternateDesignationImplCount
     * 
     * 
     * 
     * @return int
     */
    public int getAlternateDesignationImplCount()
    {
        return _alternateDesignationImplList.size();
    } //-- int getAlternateDesignationImplCount() 

    /**
     * Returns the value of field 'dataElementConceptGUID'. The
     * field 'dataElementConceptGUID' has the following
     * description: The Id of the Data Element Concept that is the
     * basis for this Data Element.
     * 
     * @return String
     * @return the value of field 'dataElementConceptGUID'.
     */
    public java.lang.String getDataElementConceptGUID()
    {
        return this._dataElementConceptGUID;
    } //-- java.lang.String getDataElementConceptGUID() 

    /**
     * Method getDataElementTextImpl
     * 
     * 
     * 
     * @param index
     * @return DataElementTextImpl
     */
    public gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementTextImpl getDataElementTextImpl(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _dataElementTextImplList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementTextImpl) _dataElementTextImplList.get(index);
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementTextImpl getDataElementTextImpl(int) 

    /**
     * Method getDataElementTextImpl
     * 
     * 
     * 
     * @return DataElementTextImpl
     */
    public gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementTextImpl[] getDataElementTextImpl()
    {
        int size = _dataElementTextImplList.size();
        gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementTextImpl[] mArray = new gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementTextImpl[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementTextImpl) _dataElementTextImplList.get(index);
        }
        return mArray;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementTextImpl[] getDataElementTextImpl() 

    /**
     * Method getDataElementTextImplCount
     * 
     * 
     * 
     * @return int
     */
    public int getDataElementTextImplCount()
    {
        return _dataElementTextImplList.size();
    } //-- int getDataElementTextImplCount() 

    /**
     * Returns the value of field 'definition'. The field
     * 'definition' has the following description: Text definition
     * of the meaning of the Data Element.
     * 
     * @return String
     * @return the value of field 'definition'.
     */
    public java.lang.String getDefinition()
    {
        return this._definition;
    } //-- java.lang.String getDefinition() 

    /**
     * Returns the value of field 'description'. The field
     * 'description' has the following description: Optional
     * human-readable text giving additional information about this
     * Item Definition.
     * 
     * @return String
     * @return the value of field 'description'.
     */
    public java.lang.String getDescription()
    {
        return this._description;
    } //-- java.lang.String getDescription() 

    /**
     * Returns the value of field 'EVDSubsetId'. The field
     * 'EVDSubsetId' has the following description: Subset of Value
     * Domain to use for this element (subject to override in
     * ItemDef). Should not be supplied if Value domain is not
     * enumerated.
     * 
     * @return String
     * @return the value of field 'EVDSubsetId'.
     */
    public java.lang.String getEVDSubsetId()
    {
        return this._EVDSubsetId;
    } //-- java.lang.String getEVDSubsetId() 

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
     * Returns the value of field 'name'. The field 'name' has the
     * following description: Name of this Data Element.
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
     * prefix for the Name of the DataElementConcept, that
     * designates the namespace within which the Name resides, and
     * within which the Name must be unique. If not provided, the
     * receiving system should treat Names as if their Namespace
     * was "Standard".
     * 
     * @return String
     * @return the value of field 'namespace'.
     */
    public java.lang.String getNamespace()
    {
        return this._namespace;
    } //-- java.lang.String getNamespace() 

    /**
     * Returns the value of field 'valueDomainGUID'. The field
     * 'valueDomainGUID' has the following description: The GUID of
     * the Value Domain that constrains the values that can be set
     * for this Data Element.
     * 
     * @return String
     * @return the value of field 'valueDomainGUID'.
     */
    public java.lang.String getValueDomainGUID()
    {
        return this._valueDomainGUID;
    } //-- java.lang.String getValueDomainGUID() 

    /**
     * Method removeAlternateDesignationImpl
     * 
     * 
     * 
     * @param vAlternateDesignationImpl
     * @return boolean
     */
    public boolean removeAlternateDesignationImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.AlternateDesignationImpl vAlternateDesignationImpl)
    {
        boolean removed = _alternateDesignationImplList.remove(vAlternateDesignationImpl);
        return removed;
    } //-- boolean removeAlternateDesignationImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.AlternateDesignationImpl) 

    /**
     * Method removeDataElementTextImpl
     * 
     * 
     * 
     * @param vDataElementTextImpl
     * @return boolean
     */
    public boolean removeDataElementTextImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementTextImpl vDataElementTextImpl)
    {
        boolean removed = _dataElementTextImplList.remove(vDataElementTextImpl);
        return removed;
    } //-- boolean removeDataElementTextImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementTextImpl) 

    /**
     * Method setAlternateDesignationImpl
     * 
     * 
     * 
     * @param index
     * @param vAlternateDesignationImpl
     */
    public void setAlternateDesignationImpl(int index, gov.nih.nci.ncicb.cadsr.edci.domain.impl.AlternateDesignationImpl vAlternateDesignationImpl)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _alternateDesignationImplList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _alternateDesignationImplList.set(index, vAlternateDesignationImpl);
    } //-- void setAlternateDesignationImpl(int, gov.nih.nci.ncicb.cadsr.edci.domain.impl.AlternateDesignationImpl) 

    /**
     * Method setAlternateDesignationImpl
     * 
     * 
     * 
     * @param alternateDesignationImplArray
     */
    public void setAlternateDesignationImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.AlternateDesignationImpl[] alternateDesignationImplArray)
    {
        //-- copy array
        _alternateDesignationImplList.clear();
        for (int i = 0; i < alternateDesignationImplArray.length; i++) {
            _alternateDesignationImplList.add(alternateDesignationImplArray[i]);
        }
    } //-- void setAlternateDesignationImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.AlternateDesignationImpl) 

    /**
     * Sets the value of field 'dataElementConceptGUID'. The field
     * 'dataElementConceptGUID' has the following description: The
     * Id of the Data Element Concept that is the basis for this
     * Data Element.
     * 
     * @param dataElementConceptGUID the value of field
     * 'dataElementConceptGUID'.
     */
    public void setDataElementConceptGUID(java.lang.String dataElementConceptGUID)
    {
        this._dataElementConceptGUID = dataElementConceptGUID;
    } //-- void setDataElementConceptGUID(java.lang.String) 

    /**
     * Method setDataElementTextImpl
     * 
     * 
     * 
     * @param index
     * @param vDataElementTextImpl
     */
    public void setDataElementTextImpl(int index, gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementTextImpl vDataElementTextImpl)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _dataElementTextImplList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _dataElementTextImplList.set(index, vDataElementTextImpl);
    } //-- void setDataElementTextImpl(int, gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementTextImpl) 

    /**
     * Method setDataElementTextImpl
     * 
     * 
     * 
     * @param dataElementTextImplArray
     */
    public void setDataElementTextImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementTextImpl[] dataElementTextImplArray)
    {
        //-- copy array
        _dataElementTextImplList.clear();
        for (int i = 0; i < dataElementTextImplArray.length; i++) {
            _dataElementTextImplList.add(dataElementTextImplArray[i]);
        }
    } //-- void setDataElementTextImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementTextImpl) 

    /**
     * Sets the value of field 'definition'. The field 'definition'
     * has the following description: Text definition of the
     * meaning of the Data Element.
     * 
     * @param definition the value of field 'definition'.
     */
    public void setDefinition(java.lang.String definition)
    {
        this._definition = definition;
    } //-- void setDefinition(java.lang.String) 

    /**
     * Sets the value of field 'description'. The field
     * 'description' has the following description: Optional
     * human-readable text giving additional information about this
     * Item Definition.
     * 
     * @param description the value of field 'description'.
     */
    public void setDescription(java.lang.String description)
    {
        this._description = description;
    } //-- void setDescription(java.lang.String) 

    /**
     * Sets the value of field 'EVDSubsetId'. The field
     * 'EVDSubsetId' has the following description: Subset of Value
     * Domain to use for this element (subject to override in
     * ItemDef). Should not be supplied if Value domain is not
     * enumerated.
     * 
     * @param EVDSubsetId the value of field 'EVDSubsetId'.
     */
    public void setEVDSubsetId(java.lang.String EVDSubsetId)
    {
        this._EVDSubsetId = EVDSubsetId;
    } //-- void setEVDSubsetId(java.lang.String) 

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
     * Sets the value of field 'name'. The field 'name' has the
     * following description: Name of this Data Element.
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
     * Name of the DataElementConcept, that designates the
     * namespace within which the Name resides, and within which
     * the Name must be unique. If not provided, the receiving
     * system should treat Names as if their Namespace was
     * "Standard".
     * 
     * @param namespace the value of field 'namespace'.
     */
    public void setNamespace(java.lang.String namespace)
    {
        this._namespace = namespace;
    } //-- void setNamespace(java.lang.String) 

    /**
     * Sets the value of field 'valueDomainGUID'. The field
     * 'valueDomainGUID' has the following description: The GUID of
     * the Value Domain that constrains the values that can be set
     * for this Data Element.
     * 
     * @param valueDomainGUID the value of field 'valueDomainGUID'.
     */
    public void setValueDomainGUID(java.lang.String valueDomainGUID)
    {
        this._valueDomainGUID = valueDomainGUID;
    } //-- void setValueDomainGUID(java.lang.String) 

}
