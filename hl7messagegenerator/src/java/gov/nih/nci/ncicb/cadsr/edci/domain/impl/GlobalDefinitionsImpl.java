/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.0.2</a>, using an XML
 * Schema.
 * $Id: GlobalDefinitionsImpl.java,v 1.2 2006-08-30 13:08:28 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import gov.nih.nci.ncicb.cadsr.edci.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.edci.domain.DataElementConcept;
import gov.nih.nci.ncicb.cadsr.edci.domain.DataElementGroup;
import gov.nih.nci.ncicb.cadsr.edci.domain.GlobalDefinitions;

import gov.nih.nci.ncicb.cadsr.edci.domain.ValueDomain;

import java.util.Collection;
import java.util.Collections;

/**
 * Definitions of objects that are used across multiple Instruments
 * 
 * @version $Revision: 1.2 $ $Date: 2006-08-30 13:08:28 $
 */
public class GlobalDefinitionsImpl implements java.io.Serializable, GlobalDefinitions {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * This class represents all value domains, enumerated or not.
     * A ValueDomain is a set of accepted representations for the
     * intended meanings of a DataElementConcept. 
     */
    private java.util.ArrayList _valueDomainImplList;

    /**
     * A Data Element Concept is the abstract statement of a
     * concept of interest in a clinical trial, independent of its
     * database representation, which has been registered in a
     * Metadata registry.
     */
    private java.util.ArrayList _dataElementConceptImplList;

    /**
     * A Data Element is a Data Element Concept constrained by a
     * particular Value Domain, and given a name.
     */
    private java.util.ArrayList _dataElementImplList;

    /**
     * Each DataElementGroup defines a set of DataElements that
     * constitute a globally defined Group. DataElementGroups are
     * the starting point for the composition of DCI-specific
     * GroupDefs.
     */
    private java.util.ArrayList _dataElementGroupImplList;


      //----------------/
     //- Constructors -/
    //----------------/

    public GlobalDefinitionsImpl() 
     {
        super();
        _valueDomainImplList = new java.util.ArrayList();
        _dataElementConceptImplList = new java.util.ArrayList();
        _dataElementImplList = new java.util.ArrayList();
        _dataElementGroupImplList = new java.util.ArrayList();
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.GlobalDefinitionsImpl()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addDataElementConceptImpl
     * 
     * 
     * 
     * @param vDataElementConceptImpl
     */
    public void addDataElementConceptImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementConceptImpl vDataElementConceptImpl)
        throws java.lang.IndexOutOfBoundsException
    {
        _dataElementConceptImplList.add(vDataElementConceptImpl);
    } //-- void addDataElementConceptImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementConceptImpl) 

    /**
     * Method addDataElementConceptImpl
     * 
     * 
     * 
     * @param index
     * @param vDataElementConceptImpl
     */
    public void addDataElementConceptImpl(int index, gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementConceptImpl vDataElementConceptImpl)
        throws java.lang.IndexOutOfBoundsException
    {
        _dataElementConceptImplList.add(index, vDataElementConceptImpl);
    } //-- void addDataElementConceptImpl(int, gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementConceptImpl) 

    /**
     * Method addDataElementGroupImpl
     * 
     * 
     * 
     * @param vDataElementGroupImpl
     */
    public void addDataElementGroupImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementGroupImpl vDataElementGroupImpl)
        throws java.lang.IndexOutOfBoundsException
    {
        _dataElementGroupImplList.add(vDataElementGroupImpl);
    } //-- void addDataElementGroupImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementGroupImpl) 

    /**
     * Method addDataElementGroupImpl
     * 
     * 
     * 
     * @param index
     * @param vDataElementGroupImpl
     */
    public void addDataElementGroupImpl(int index, gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementGroupImpl vDataElementGroupImpl)
        throws java.lang.IndexOutOfBoundsException
    {
        _dataElementGroupImplList.add(index, vDataElementGroupImpl);
    } //-- void addDataElementGroupImpl(int, gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementGroupImpl) 

    /**
     * Method addDataElementImpl
     * 
     * 
     * 
     * @param vDataElementImpl
     */
    public void addDataElementImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementImpl vDataElementImpl)
        throws java.lang.IndexOutOfBoundsException
    {
        _dataElementImplList.add(vDataElementImpl);
    } //-- void addDataElementImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementImpl) 

    /**
     * Method addDataElementImpl
     * 
     * 
     * 
     * @param index
     * @param vDataElementImpl
     */
    public void addDataElementImpl(int index, gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementImpl vDataElementImpl)
        throws java.lang.IndexOutOfBoundsException
    {
        _dataElementImplList.add(index, vDataElementImpl);
    } //-- void addDataElementImpl(int, gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementImpl) 

    /**
     * Method addValueDomainImpl
     * 
     * 
     * 
     * @param vValueDomainImpl
     */
    public void addValueDomainImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.ValueDomainImpl vValueDomainImpl)
        throws java.lang.IndexOutOfBoundsException
    {
        _valueDomainImplList.add(vValueDomainImpl);
    } //-- void addValueDomainImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.ValueDomainImpl) 

    /**
     * Method addValueDomainImpl
     * 
     * 
     * 
     * @param index
     * @param vValueDomainImpl
     */
    public void addValueDomainImpl(int index, gov.nih.nci.ncicb.cadsr.edci.domain.impl.ValueDomainImpl vValueDomainImpl)
        throws java.lang.IndexOutOfBoundsException
    {
        _valueDomainImplList.add(index, vValueDomainImpl);
    } //-- void addValueDomainImpl(int, gov.nih.nci.ncicb.cadsr.edci.domain.impl.ValueDomainImpl) 

    /**
     * Method clearDataElementConceptImpl
     * 
     */
    public void clearDataElementConceptImpl()
    {
        _dataElementConceptImplList.clear();
    } //-- void clearDataElementConceptImpl() 

    /**
     * Method clearDataElementGroupImpl
     * 
     */
    public void clearDataElementGroupImpl()
    {
        _dataElementGroupImplList.clear();
    } //-- void clearDataElementGroupImpl() 

    /**
     * Method clearDataElementImpl
     * 
     */
    public void clearDataElementImpl()
    {
        _dataElementImplList.clear();
    } //-- void clearDataElementImpl() 

    /**
     * Method clearValueDomainImpl
     * 
     */
    public void clearValueDomainImpl()
    {
        _valueDomainImplList.clear();
    } //-- void clearValueDomainImpl() 

    /**
     * Method enumerateDataElementConceptImpl
     * 
     * 
     * 
     * @return Enumeration
     */
    public java.util.Enumeration enumerateDataElementConceptImpl()
    {
        return Collections.enumeration(_dataElementConceptImplList);
    } //-- java.util.Enumeration enumerateDataElementConceptImpl() 

    /**
     * Method enumerateDataElementGroupImpl
     * 
     * 
     * 
     * @return Enumeration
     */
    public java.util.Enumeration enumerateDataElementGroupImpl()
    {
        return Collections.enumeration(_dataElementGroupImplList);
    } //-- java.util.Enumeration enumerateDataElementGroupImpl() 

    /**
     * Method enumerateDataElementImpl
     * 
     * 
     * 
     * @return Enumeration
     */
    public java.util.Enumeration enumerateDataElementImpl()
    {
        return Collections.enumeration(_dataElementImplList);
    } //-- java.util.Enumeration enumerateDataElementImpl() 

    /**
     * Method enumerateValueDomainImpl
     * 
     * 
     * 
     * @return Enumeration
     */
    public java.util.Enumeration enumerateValueDomainImpl()
    {
        return Collections.enumeration(_valueDomainImplList);
    } //-- java.util.Enumeration enumerateValueDomainImpl() 

    /**
     * Method getDataElementConceptImpl
     * 
     * 
     * 
     * @param index
     * @return DataElementConceptImpl
     */
    public gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementConceptImpl getDataElementConceptImpl(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index >= _dataElementConceptImplList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementConceptImpl) _dataElementConceptImplList.get(index);
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementConceptImpl getDataElementConceptImpl(int) 

    /**
     * Method getDataElementConceptImpl
     * 
     * 
     * 
     * @return DataElementConceptImpl
     */
    public gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementConceptImpl[] getDataElementConceptImpl()
    {
        int size = _dataElementConceptImplList.size();
        gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementConceptImpl[] mArray = new gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementConceptImpl[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementConceptImpl) _dataElementConceptImplList.get(index);
        }
        return mArray;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementConceptImpl[] getDataElementConceptImpl() 

    /**
     * Method getDataElementConceptImplCount
     * 
     * 
     * 
     * @return int
     */
    public int getDataElementConceptImplCount()
    {
        return _dataElementConceptImplList.size();
    } //-- int getDataElementConceptImplCount() 

    /**
     * Method getDataElementGroupImpl
     * 
     * 
     * 
     * @param index
     * @return DataElementGroupImpl
     */
    public gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementGroupImpl getDataElementGroupImpl(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index >= _dataElementGroupImplList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementGroupImpl) _dataElementGroupImplList.get(index);
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementGroupImpl getDataElementGroupImpl(int) 

    /**
     * Method getDataElementGroupImpl
     * 
     * 
     * 
     * @return DataElementGroupImpl
     */
    public gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementGroupImpl[] getDataElementGroupImpl()
    {
        int size = _dataElementGroupImplList.size();
        gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementGroupImpl[] mArray = new gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementGroupImpl[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementGroupImpl) _dataElementGroupImplList.get(index);
        }
        return mArray;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementGroupImpl[] getDataElementGroupImpl() 

    /**
     * Method getDataElementGroupImplCount
     * 
     * 
     * 
     * @return int
     */
    public int getDataElementGroupImplCount()
    {
        return _dataElementGroupImplList.size();
    } //-- int getDataElementGroupImplCount() 

    /**
     * Method getDataElementImpl
     * 
     * 
     * 
     * @param index
     * @return DataElementImpl
     */
    public gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementImpl getDataElementImpl(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index >= _dataElementImplList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementImpl) _dataElementImplList.get(index);
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementImpl getDataElementImpl(int) 

    /**
     * Method getDataElementImpl
     * 
     * 
     * 
     * @return DataElementImpl
     */
    public gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementImpl[] getDataElementImpl()
    {
        int size = _dataElementImplList.size();
        gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementImpl[] mArray = new gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementImpl[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementImpl) _dataElementImplList.get(index);
        }
        return mArray;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementImpl[] getDataElementImpl() 

    /**
     * Method getDataElementImplCount
     * 
     * 
     * 
     * @return int
     */
    public int getDataElementImplCount()
    {
        return _dataElementImplList.size();
    } //-- int getDataElementImplCount() 

    /**
     * Method getValueDomainImpl
     * 
     * 
     * 
     * @param index
     * @return ValueDomainImpl
     */
    public gov.nih.nci.ncicb.cadsr.edci.domain.impl.ValueDomainImpl getValueDomainImpl(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index >= _valueDomainImplList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (gov.nih.nci.ncicb.cadsr.edci.domain.impl.ValueDomainImpl) _valueDomainImplList.get(index);
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.ValueDomainImpl getValueDomainImpl(int) 

    /**
     * Method getValueDomainImpl
     * 
     * 
     * 
     * @return ValueDomainImpl
     */
    public gov.nih.nci.ncicb.cadsr.edci.domain.impl.ValueDomainImpl[] getValueDomainImpl()
    {
        int size = _valueDomainImplList.size();
        gov.nih.nci.ncicb.cadsr.edci.domain.impl.ValueDomainImpl[] mArray = new gov.nih.nci.ncicb.cadsr.edci.domain.impl.ValueDomainImpl[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (gov.nih.nci.ncicb.cadsr.edci.domain.impl.ValueDomainImpl) _valueDomainImplList.get(index);
        }
        return mArray;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.ValueDomainImpl[] getValueDomainImpl() 

    /**
     * Method getValueDomainImplCount
     * 
     * 
     * 
     * @return int
     */
    public int getValueDomainImplCount()
    {
        return _valueDomainImplList.size();
    } //-- int getValueDomainImplCount() 

    /**
     * Method removeDataElementConceptImpl
     * 
     * 
     * 
     * @param vDataElementConceptImpl
     * @return boolean
     */
    public boolean removeDataElementConceptImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementConceptImpl vDataElementConceptImpl)
    {
        boolean removed = _dataElementConceptImplList.remove(vDataElementConceptImpl);
        return removed;
    } //-- boolean removeDataElementConceptImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementConceptImpl) 

    /**
     * Method removeDataElementGroupImpl
     * 
     * 
     * 
     * @param vDataElementGroupImpl
     * @return boolean
     */
    public boolean removeDataElementGroupImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementGroupImpl vDataElementGroupImpl)
    {
        boolean removed = _dataElementGroupImplList.remove(vDataElementGroupImpl);
        return removed;
    } //-- boolean removeDataElementGroupImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementGroupImpl) 

    /**
     * Method removeDataElementImpl
     * 
     * 
     * 
     * @param vDataElementImpl
     * @return boolean
     */
    public boolean removeDataElementImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementImpl vDataElementImpl)
    {
        boolean removed = _dataElementImplList.remove(vDataElementImpl);
        return removed;
    } //-- boolean removeDataElementImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementImpl) 

    /**
     * Method removeValueDomainImpl
     * 
     * 
     * 
     * @param vValueDomainImpl
     * @return boolean
     */
    public boolean removeValueDomainImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.ValueDomainImpl vValueDomainImpl)
    {
        boolean removed = _valueDomainImplList.remove(vValueDomainImpl);
        return removed;
    } //-- boolean removeValueDomainImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.ValueDomainImpl) 

    /**
     * Method setDataElementConceptImpl
     * 
     * 
     * 
     * @param index
     * @param vDataElementConceptImpl
     */
    public void setDataElementConceptImpl(int index, gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementConceptImpl vDataElementConceptImpl)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index >= _dataElementConceptImplList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _dataElementConceptImplList.set(index, vDataElementConceptImpl);
    } //-- void setDataElementConceptImpl(int, gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementConceptImpl) 

    /**
     * Method setDataElementConceptImpl
     * 
     * 
     * 
     * @param dataElementConceptImplArray
     */
    public void setDataElementConceptImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementConceptImpl[] dataElementConceptImplArray)
    {
        //-- copy array
        _dataElementConceptImplList.clear();
        for (int i = 0; i < dataElementConceptImplArray.length; i++) {
            _dataElementConceptImplList.add(dataElementConceptImplArray[i]);
        }
    } //-- void setDataElementConceptImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementConceptImpl) 

    /**
     * Method setDataElementGroupImpl
     * 
     * 
     * 
     * @param index
     * @param vDataElementGroupImpl
     */
    public void setDataElementGroupImpl(int index, gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementGroupImpl vDataElementGroupImpl)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index >= _dataElementGroupImplList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _dataElementGroupImplList.set(index, vDataElementGroupImpl);
    } //-- void setDataElementGroupImpl(int, gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementGroupImpl) 

    /**
     * Method setDataElementGroupImpl
     * 
     * 
     * 
     * @param dataElementGroupImplArray
     */
    public void setDataElementGroupImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementGroupImpl[] dataElementGroupImplArray)
    {
        //-- copy array
        _dataElementGroupImplList.clear();
        for (int i = 0; i < dataElementGroupImplArray.length; i++) {
            _dataElementGroupImplList.add(dataElementGroupImplArray[i]);
        }
    } //-- void setDataElementGroupImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementGroupImpl) 

    /**
     * Method setDataElementImpl
     * 
     * 
     * 
     * @param index
     * @param vDataElementImpl
     */
    public void setDataElementImpl(int index, gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementImpl vDataElementImpl)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index >= _dataElementImplList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _dataElementImplList.set(index, vDataElementImpl);
    } //-- void setDataElementImpl(int, gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementImpl) 

    /**
     * Method setDataElementImpl
     * 
     * 
     * 
     * @param dataElementImplArray
     */
    public void setDataElementImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementImpl[] dataElementImplArray)
    {
        //-- copy array
        _dataElementImplList.clear();
        for (int i = 0; i < dataElementImplArray.length; i++) {
            _dataElementImplList.add(dataElementImplArray[i]);
        }
    } //-- void setDataElementImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementImpl) 

    /**
     * Method setValueDomainImpl
     * 
     * 
     * 
     * @param index
     * @param vValueDomainImpl
     */
    public void setValueDomainImpl(int index, gov.nih.nci.ncicb.cadsr.edci.domain.impl.ValueDomainImpl vValueDomainImpl)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index >= _valueDomainImplList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _valueDomainImplList.set(index, vValueDomainImpl);
    } //-- void setValueDomainImpl(int, gov.nih.nci.ncicb.cadsr.edci.domain.impl.ValueDomainImpl) 

    /**
     * Method setValueDomainImpl
     * 
     * 
     * 
     * @param valueDomainImplArray
     */
    public void setValueDomainImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.ValueDomainImpl[] valueDomainImplArray)
    {
        //-- copy array
        _valueDomainImplList.clear();
        for (int i = 0; i < valueDomainImplArray.length; i++) {
            _valueDomainImplList.add(valueDomainImplArray[i]);
        }
    } //-- void setValueDomainImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.ValueDomainImpl) 

    public void addValueDomain(ValueDomain valueDomain) {
       addValueDomainImpl((ValueDomainImpl)valueDomain);
    }

    public void setValueDomainCollection(Collection<ValueDomain> valueDomainCollection) {
       clearValueDomainImpl();
       for(ValueDomain valueDomain:valueDomainCollection) {
           addValueDomainImpl((ValueDomainImpl)valueDomain);
       }
    }

    public Collection<ValueDomain> getValueDomainCollection() {
        return _valueDomainImplList;
    }

    public void addDataElementConcept(DataElementConcept dataElementConcept) {
        addDataElementConceptImpl((DataElementConceptImpl)dataElementConcept);
    }

    public void setDataElementConceptCollection(Collection<DataElementConcept> dataElementConceptCollection) {
        clearDataElementConceptImpl();
        for(DataElementConcept dataElementConcept: dataElementConceptCollection) {
            addDataElementConceptImpl((DataElementConceptImpl)dataElementConcept);
        }
    }

    public Collection<DataElementConcept> getDataElementConceptCollection() {
        return _dataElementConceptImplList;
    }

    public void addDataElement(DataElement dataElement) {
       addDataElementImpl((DataElementImpl)dataElement);
    }

    public void setDataElementCollection(Collection<DataElement> dataElementCollection) {
        clearDataElementImpl();
        for(DataElement dataElement: dataElementCollection) {
            addDataElementImpl((DataElementImpl)dataElement);
        }
    }

    public Collection<DataElement> getDataElementCollection() {
        return _dataElementImplList;
    }

    public void addDataElementGroup(DataElementGroup dataElementGroup) {
        addDataElementGroupImpl((DataElementGroupImpl)dataElementGroup);
    }

    public void setDataElementGroupCollection(Collection<DataElementGroup> dataElementGroupCollection) {
        clearDataElementGroupImpl();
        for(DataElementGroup dataElementGroup: dataElementGroupCollection) {
            addDataElementGroupImpl((DataElementGroupImpl)dataElementGroup);
        }
    }

    public Collection<DataElementGroup> getDataElementGroupCollection() {
        return _dataElementGroupImplList;
    }
}
