/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: ValueDomainImpl.java,v 1.1 2006-10-03 19:42:06 marwahah Exp $
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
 * This class represents all value domains, enumerated or not. A
 * ValueDomain is a set of accepted representations for the
 * intended meanings of a DataElementConcept. 
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:06 $
 */
public class ValueDomainImpl implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Anticipated datatype for responses to questions constrained
     * by this Value Domain. Types are: string, integer, float.
     */
    private java.lang.String _datatype;

    /**
     * Text description of the meaning of the Value Domain
     */
    private int _decimalPlaces;

    /**
     * keeps track of state for field: _decimalPlaces
     */
    private boolean _has_decimalPlaces;

    /**
     * Text description of the meaning of the Value Domain
     */
    private java.lang.String _description;

    /**
     * The globally-unique identifier for this object. A
     * globally-unique identifer consists of these parts: a
     * metadata registry identifier that defines a namespace for
     * all objects registered in that registry; an object identifer
     * that is unique for the object within the registry;
     * optionally, a version identifer within the life-cycle of the
     * object.
     */
    private java.lang.String _GUID;

    /**
     * If Yes (default is No), this VD specifies an enumerated set
     * of values. It is not required that an enumerated VD have EVD
     * Elements.
     */
    private boolean _isEnumeratedFlag;

    /**
     * keeps track of state for field: _isEnumeratedFlag
     */
    private boolean _has_isEnumeratedFlag;

    /**
     * The maximum length of values in the value domain
     */
    private int _maximumLength;

    /**
     * keeps track of state for field: _maximumLength
     */
    private boolean _has_maximumLength;

    /**
     * THuman-readable name for the Value Domain in the metadata
     * registry
     */
    private java.lang.String _name;

    /**
     * An optional prefix for the Name of the ValueDomain, that
     * designates the namespace within which the Name resides, and
     * within which the Name must be unique.
     */
    private java.lang.String _namespace;

    /**
     * A descriptor of how SAS is to store ItemRef instance values
     * controlled by this Value Domain
     */
    private java.lang.String _sasFormatName;

    /**
     * The name of the Coding System from which this Enumerated
     * Value Domain is drawn.
     */
    private java.lang.String _sourceCodingSystem;

    /**
     * The globally unique identifier of the Coding System from
     * which this Enumerated Value Domain is drawn.
     */
    private java.lang.String _sourceCodingSystemGUID;

    /**
     * Version of the coding system
     */
    private java.lang.String _sourceCodingSystemVersion;

    /**
     * ID of the domain within the coding system.
     */
    private java.lang.String _sourceDomainId;

    /**
     * An element in an Enumerated Value Domain Subset. 
     */
    private java.util.ArrayList _EVDElementImplList;

    /**
     * A subset of the enumerated elements (EVDElements) in the
     * Value Domain. 
     */
    private java.util.ArrayList _EVDSubsetImplList;


      //----------------/
     //- Constructors -/
    //----------------/

    public ValueDomainImpl() 
     {
        super();
        _EVDElementImplList = new ArrayList();
        _EVDSubsetImplList = new ArrayList();
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.ValueDomainImpl()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addEVDElementImpl
     * 
     * 
     * 
     * @param vEVDElementImpl
     */
    public void addEVDElementImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDElementImpl vEVDElementImpl)
        throws java.lang.IndexOutOfBoundsException
    {
        _EVDElementImplList.add(vEVDElementImpl);
    } //-- void addEVDElementImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDElementImpl) 

    /**
     * Method addEVDElementImpl
     * 
     * 
     * 
     * @param index
     * @param vEVDElementImpl
     */
    public void addEVDElementImpl(int index, gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDElementImpl vEVDElementImpl)
        throws java.lang.IndexOutOfBoundsException
    {
        _EVDElementImplList.add(index, vEVDElementImpl);
    } //-- void addEVDElementImpl(int, gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDElementImpl) 

    /**
     * Method addEVDSubsetImpl
     * 
     * 
     * 
     * @param vEVDSubsetImpl
     */
    public void addEVDSubsetImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDSubsetImpl vEVDSubsetImpl)
        throws java.lang.IndexOutOfBoundsException
    {
        _EVDSubsetImplList.add(vEVDSubsetImpl);
    } //-- void addEVDSubsetImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDSubsetImpl) 

    /**
     * Method addEVDSubsetImpl
     * 
     * 
     * 
     * @param index
     * @param vEVDSubsetImpl
     */
    public void addEVDSubsetImpl(int index, gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDSubsetImpl vEVDSubsetImpl)
        throws java.lang.IndexOutOfBoundsException
    {
        _EVDSubsetImplList.add(index, vEVDSubsetImpl);
    } //-- void addEVDSubsetImpl(int, gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDSubsetImpl) 

    /**
     * Method clearEVDElementImpl
     * 
     */
    public void clearEVDElementImpl()
    {
        _EVDElementImplList.clear();
    } //-- void clearEVDElementImpl() 

    /**
     * Method clearEVDSubsetImpl
     * 
     */
    public void clearEVDSubsetImpl()
    {
        _EVDSubsetImplList.clear();
    } //-- void clearEVDSubsetImpl() 

    /**
     * Method deleteDecimalPlaces
     * 
     */
    public void deleteDecimalPlaces()
    {
        this._has_decimalPlaces= false;
    } //-- void deleteDecimalPlaces() 

    /**
     * Method deleteIsEnumeratedFlag
     * 
     */
    public void deleteIsEnumeratedFlag()
    {
        this._has_isEnumeratedFlag= false;
    } //-- void deleteIsEnumeratedFlag() 

    /**
     * Method deleteMaximumLength
     * 
     */
    public void deleteMaximumLength()
    {
        this._has_maximumLength= false;
    } //-- void deleteMaximumLength() 

    /**
     * Method enumerateEVDElementImpl
     * 
     * 
     * 
     * @return Enumeration
     */
    public java.util.Enumeration enumerateEVDElementImpl()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_EVDElementImplList.iterator());
    } //-- java.util.Enumeration enumerateEVDElementImpl() 

    /**
     * Method enumerateEVDSubsetImpl
     * 
     * 
     * 
     * @return Enumeration
     */
    public java.util.Enumeration enumerateEVDSubsetImpl()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_EVDSubsetImplList.iterator());
    } //-- java.util.Enumeration enumerateEVDSubsetImpl() 

    /**
     * Returns the value of field 'datatype'. The field 'datatype'
     * has the following description: Anticipated datatype for
     * responses to questions constrained by this Value Domain.
     * Types are: string, integer, float.
     * 
     * @return String
     * @return the value of field 'datatype'.
     */
    public java.lang.String getDatatype()
    {
        return this._datatype;
    } //-- java.lang.String getDatatype() 

    /**
     * Returns the value of field 'decimalPlaces'. The field
     * 'decimalPlaces' has the following description: Text
     * description of the meaning of the Value Domain
     * 
     * @return int
     * @return the value of field 'decimalPlaces'.
     */
    public int getDecimalPlaces()
    {
        return this._decimalPlaces;
    } //-- int getDecimalPlaces() 

    /**
     * Returns the value of field 'description'. The field
     * 'description' has the following description: Text
     * description of the meaning of the Value Domain
     * 
     * @return String
     * @return the value of field 'description'.
     */
    public java.lang.String getDescription()
    {
        return this._description;
    } //-- java.lang.String getDescription() 

    /**
     * Method getEVDElementImpl
     * 
     * 
     * 
     * @param index
     * @return EVDElementImpl
     */
    public gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDElementImpl getEVDElementImpl(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _EVDElementImplList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDElementImpl) _EVDElementImplList.get(index);
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDElementImpl getEVDElementImpl(int) 

    /**
     * Method getEVDElementImpl
     * 
     * 
     * 
     * @return EVDElementImpl
     */
    public gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDElementImpl[] getEVDElementImpl()
    {
        int size = _EVDElementImplList.size();
        gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDElementImpl[] mArray = new gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDElementImpl[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDElementImpl) _EVDElementImplList.get(index);
        }
        return mArray;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDElementImpl[] getEVDElementImpl() 

    /**
     * Method getEVDElementImplCount
     * 
     * 
     * 
     * @return int
     */
    public int getEVDElementImplCount()
    {
        return _EVDElementImplList.size();
    } //-- int getEVDElementImplCount() 

    /**
     * Method getEVDSubsetImpl
     * 
     * 
     * 
     * @param index
     * @return EVDSubsetImpl
     */
    public gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDSubsetImpl getEVDSubsetImpl(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _EVDSubsetImplList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDSubsetImpl) _EVDSubsetImplList.get(index);
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDSubsetImpl getEVDSubsetImpl(int) 

    /**
     * Method getEVDSubsetImpl
     * 
     * 
     * 
     * @return EVDSubsetImpl
     */
    public gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDSubsetImpl[] getEVDSubsetImpl()
    {
        int size = _EVDSubsetImplList.size();
        gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDSubsetImpl[] mArray = new gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDSubsetImpl[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDSubsetImpl) _EVDSubsetImplList.get(index);
        }
        return mArray;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDSubsetImpl[] getEVDSubsetImpl() 

    /**
     * Method getEVDSubsetImplCount
     * 
     * 
     * 
     * @return int
     */
    public int getEVDSubsetImplCount()
    {
        return _EVDSubsetImplList.size();
    } //-- int getEVDSubsetImplCount() 

    /**
     * Returns the value of field 'GUID'. The field 'GUID' has the
     * following description: The globally-unique identifier for
     * this object. A globally-unique identifer consists of these
     * parts: a metadata registry identifier that defines a
     * namespace for all objects registered in that registry; an
     * object identifer that is unique for the object within the
     * registry; optionally, a version identifer within the
     * life-cycle of the object.
     * 
     * @return String
     * @return the value of field 'GUID'.
     */
    public java.lang.String getGUID()
    {
        return this._GUID;
    } //-- java.lang.String getGUID() 

    /**
     * Returns the value of field 'isEnumeratedFlag'. The field
     * 'isEnumeratedFlag' has the following description: If Yes
     * (default is No), this VD specifies an enumerated set of
     * values. It is not required that an enumerated VD have EVD
     * Elements.
     * 
     * @return boolean
     * @return the value of field 'isEnumeratedFlag'.
     */
    public boolean getIsEnumeratedFlag()
    {
        return this._isEnumeratedFlag;
    } //-- boolean getIsEnumeratedFlag() 

    /**
     * Returns the value of field 'maximumLength'. The field
     * 'maximumLength' has the following description: The maximum
     * length of values in the value domain
     * 
     * @return int
     * @return the value of field 'maximumLength'.
     */
    public int getMaximumLength()
    {
        return this._maximumLength;
    } //-- int getMaximumLength() 

    /**
     * Returns the value of field 'name'. The field 'name' has the
     * following description: THuman-readable name for the Value
     * Domain in the metadata registry
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
     * prefix for the Name of the ValueDomain, that designates the
     * namespace within which the Name resides, and within which
     * the Name must be unique.
     * 
     * @return String
     * @return the value of field 'namespace'.
     */
    public java.lang.String getNamespace()
    {
        return this._namespace;
    } //-- java.lang.String getNamespace() 

    /**
     * Returns the value of field 'sasFormatName'. The field
     * 'sasFormatName' has the following description: A descriptor
     * of how SAS is to store ItemRef instance values controlled by
     * this Value Domain
     * 
     * @return String
     * @return the value of field 'sasFormatName'.
     */
    public java.lang.String getSasFormatName()
    {
        return this._sasFormatName;
    } //-- java.lang.String getSasFormatName() 

    /**
     * Returns the value of field 'sourceCodingSystem'. The field
     * 'sourceCodingSystem' has the following description: The name
     * of the Coding System from which this Enumerated Value Domain
     * is drawn.
     * 
     * @return String
     * @return the value of field 'sourceCodingSystem'.
     */
    public java.lang.String getSourceCodingSystem()
    {
        return this._sourceCodingSystem;
    } //-- java.lang.String getSourceCodingSystem() 

    /**
     * Returns the value of field 'sourceCodingSystemGUID'. The
     * field 'sourceCodingSystemGUID' has the following
     * description: The globally unique identifier of the Coding
     * System from which this Enumerated Value Domain is drawn.
     * 
     * @return String
     * @return the value of field 'sourceCodingSystemGUID'.
     */
    public java.lang.String getSourceCodingSystemGUID()
    {
        return this._sourceCodingSystemGUID;
    } //-- java.lang.String getSourceCodingSystemGUID() 

    /**
     * Returns the value of field 'sourceCodingSystemVersion'. The
     * field 'sourceCodingSystemVersion' has the following
     * description: Version of the coding system
     * 
     * @return String
     * @return the value of field 'sourceCodingSystemVersion'.
     */
    public java.lang.String getSourceCodingSystemVersion()
    {
        return this._sourceCodingSystemVersion;
    } //-- java.lang.String getSourceCodingSystemVersion() 

    /**
     * Returns the value of field 'sourceDomainId'. The field
     * 'sourceDomainId' has the following description: ID of the
     * domain within the coding system.
     * 
     * @return String
     * @return the value of field 'sourceDomainId'.
     */
    public java.lang.String getSourceDomainId()
    {
        return this._sourceDomainId;
    } //-- java.lang.String getSourceDomainId() 

    /**
     * Method hasDecimalPlaces
     * 
     * 
     * 
     * @return boolean
     */
    public boolean hasDecimalPlaces()
    {
        return this._has_decimalPlaces;
    } //-- boolean hasDecimalPlaces() 

    /**
     * Method hasIsEnumeratedFlag
     * 
     * 
     * 
     * @return boolean
     */
    public boolean hasIsEnumeratedFlag()
    {
        return this._has_isEnumeratedFlag;
    } //-- boolean hasIsEnumeratedFlag() 

    /**
     * Method hasMaximumLength
     * 
     * 
     * 
     * @return boolean
     */
    public boolean hasMaximumLength()
    {
        return this._has_maximumLength;
    } //-- boolean hasMaximumLength() 

    /**
     * Method removeEVDElementImpl
     * 
     * 
     * 
     * @param vEVDElementImpl
     * @return boolean
     */
    public boolean removeEVDElementImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDElementImpl vEVDElementImpl)
    {
        boolean removed = _EVDElementImplList.remove(vEVDElementImpl);
        return removed;
    } //-- boolean removeEVDElementImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDElementImpl) 

    /**
     * Method removeEVDSubsetImpl
     * 
     * 
     * 
     * @param vEVDSubsetImpl
     * @return boolean
     */
    public boolean removeEVDSubsetImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDSubsetImpl vEVDSubsetImpl)
    {
        boolean removed = _EVDSubsetImplList.remove(vEVDSubsetImpl);
        return removed;
    } //-- boolean removeEVDSubsetImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDSubsetImpl) 

    /**
     * Sets the value of field 'datatype'. The field 'datatype' has
     * the following description: Anticipated datatype for
     * responses to questions constrained by this Value Domain.
     * Types are: string, integer, float.
     * 
     * @param datatype the value of field 'datatype'.
     */
    public void setDatatype(java.lang.String datatype)
    {
        this._datatype = datatype;
    } //-- void setDatatype(java.lang.String) 

    /**
     * Sets the value of field 'decimalPlaces'. The field
     * 'decimalPlaces' has the following description: Text
     * description of the meaning of the Value Domain
     * 
     * @param decimalPlaces the value of field 'decimalPlaces'.
     */
    public void setDecimalPlaces(int decimalPlaces)
    {
        this._decimalPlaces = decimalPlaces;
        this._has_decimalPlaces = true;
    } //-- void setDecimalPlaces(int) 

    /**
     * Sets the value of field 'description'. The field
     * 'description' has the following description: Text
     * description of the meaning of the Value Domain
     * 
     * @param description the value of field 'description'.
     */
    public void setDescription(java.lang.String description)
    {
        this._description = description;
    } //-- void setDescription(java.lang.String) 

    /**
     * Method setEVDElementImpl
     * 
     * 
     * 
     * @param index
     * @param vEVDElementImpl
     */
    public void setEVDElementImpl(int index, gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDElementImpl vEVDElementImpl)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _EVDElementImplList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _EVDElementImplList.set(index, vEVDElementImpl);
    } //-- void setEVDElementImpl(int, gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDElementImpl) 

    /**
     * Method setEVDElementImpl
     * 
     * 
     * 
     * @param EVDElementImplArray
     */
    public void setEVDElementImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDElementImpl[] EVDElementImplArray)
    {
        //-- copy array
        _EVDElementImplList.clear();
        for (int i = 0; i < EVDElementImplArray.length; i++) {
            _EVDElementImplList.add(EVDElementImplArray[i]);
        }
    } //-- void setEVDElementImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDElementImpl) 

    /**
     * Method setEVDSubsetImpl
     * 
     * 
     * 
     * @param index
     * @param vEVDSubsetImpl
     */
    public void setEVDSubsetImpl(int index, gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDSubsetImpl vEVDSubsetImpl)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _EVDSubsetImplList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _EVDSubsetImplList.set(index, vEVDSubsetImpl);
    } //-- void setEVDSubsetImpl(int, gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDSubsetImpl) 

    /**
     * Method setEVDSubsetImpl
     * 
     * 
     * 
     * @param EVDSubsetImplArray
     */
    public void setEVDSubsetImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDSubsetImpl[] EVDSubsetImplArray)
    {
        //-- copy array
        _EVDSubsetImplList.clear();
        for (int i = 0; i < EVDSubsetImplArray.length; i++) {
            _EVDSubsetImplList.add(EVDSubsetImplArray[i]);
        }
    } //-- void setEVDSubsetImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDSubsetImpl) 

    /**
     * Sets the value of field 'GUID'. The field 'GUID' has the
     * following description: The globally-unique identifier for
     * this object. A globally-unique identifer consists of these
     * parts: a metadata registry identifier that defines a
     * namespace for all objects registered in that registry; an
     * object identifer that is unique for the object within the
     * registry; optionally, a version identifer within the
     * life-cycle of the object.
     * 
     * @param GUID the value of field 'GUID'.
     */
    public void setGUID(java.lang.String GUID)
    {
        this._GUID = GUID;
    } //-- void setGUID(java.lang.String) 

    /**
     * Sets the value of field 'isEnumeratedFlag'. The field
     * 'isEnumeratedFlag' has the following description: If Yes
     * (default is No), this VD specifies an enumerated set of
     * values. It is not required that an enumerated VD have EVD
     * Elements.
     * 
     * @param isEnumeratedFlag the value of field 'isEnumeratedFlag'
     */
    public void setIsEnumeratedFlag(boolean isEnumeratedFlag)
    {
        this._isEnumeratedFlag = isEnumeratedFlag;
        this._has_isEnumeratedFlag = true;
    } //-- void setIsEnumeratedFlag(boolean) 

    /**
     * Sets the value of field 'maximumLength'. The field
     * 'maximumLength' has the following description: The maximum
     * length of values in the value domain
     * 
     * @param maximumLength the value of field 'maximumLength'.
     */
    public void setMaximumLength(int maximumLength)
    {
        this._maximumLength = maximumLength;
        this._has_maximumLength = true;
    } //-- void setMaximumLength(int) 

    /**
     * Sets the value of field 'name'. The field 'name' has the
     * following description: THuman-readable name for the Value
     * Domain in the metadata registry
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
     * Name of the ValueDomain, that designates the namespace
     * within which the Name resides, and within which the Name
     * must be unique.
     * 
     * @param namespace the value of field 'namespace'.
     */
    public void setNamespace(java.lang.String namespace)
    {
        this._namespace = namespace;
    } //-- void setNamespace(java.lang.String) 

    /**
     * Sets the value of field 'sasFormatName'. The field
     * 'sasFormatName' has the following description: A descriptor
     * of how SAS is to store ItemRef instance values controlled by
     * this Value Domain
     * 
     * @param sasFormatName the value of field 'sasFormatName'.
     */
    public void setSasFormatName(java.lang.String sasFormatName)
    {
        this._sasFormatName = sasFormatName;
    } //-- void setSasFormatName(java.lang.String) 

    /**
     * Sets the value of field 'sourceCodingSystem'. The field
     * 'sourceCodingSystem' has the following description: The name
     * of the Coding System from which this Enumerated Value Domain
     * is drawn.
     * 
     * @param sourceCodingSystem the value of field
     * 'sourceCodingSystem'.
     */
    public void setSourceCodingSystem(java.lang.String sourceCodingSystem)
    {
        this._sourceCodingSystem = sourceCodingSystem;
    } //-- void setSourceCodingSystem(java.lang.String) 

    /**
     * Sets the value of field 'sourceCodingSystemGUID'. The field
     * 'sourceCodingSystemGUID' has the following description: The
     * globally unique identifier of the Coding System from which
     * this Enumerated Value Domain is drawn.
     * 
     * @param sourceCodingSystemGUID the value of field
     * 'sourceCodingSystemGUID'.
     */
    public void setSourceCodingSystemGUID(java.lang.String sourceCodingSystemGUID)
    {
        this._sourceCodingSystemGUID = sourceCodingSystemGUID;
    } //-- void setSourceCodingSystemGUID(java.lang.String) 

    /**
     * Sets the value of field 'sourceCodingSystemVersion'. The
     * field 'sourceCodingSystemVersion' has the following
     * description: Version of the coding system
     * 
     * @param sourceCodingSystemVersion the value of field
     * 'sourceCodingSystemVersion'.
     */
    public void setSourceCodingSystemVersion(java.lang.String sourceCodingSystemVersion)
    {
        this._sourceCodingSystemVersion = sourceCodingSystemVersion;
    } //-- void setSourceCodingSystemVersion(java.lang.String) 

    /**
     * Sets the value of field 'sourceDomainId'. The field
     * 'sourceDomainId' has the following description: ID of the
     * domain within the coding system.
     * 
     * @param sourceDomainId the value of field 'sourceDomainId'.
     */
    public void setSourceDomainId(java.lang.String sourceDomainId)
    {
        this._sourceDomainId = sourceDomainId;
    } //-- void setSourceDomainId(java.lang.String) 

}
