/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: GlobalDefinitionsImplDescriptor.java,v 1.1 2006-10-03 19:42:06 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.mapping.AccessMode;
import org.exolab.castor.xml.TypeValidator;
import org.exolab.castor.xml.XMLFieldDescriptor;
import org.exolab.castor.xml.validators.*;

/**
 * Class GlobalDefinitionsImplDescriptor.
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:06 $
 */
public class GlobalDefinitionsImplDescriptor extends org.exolab.castor.xml.util.XMLClassDescriptorImpl {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field nsPrefix
     */
    private java.lang.String nsPrefix;

    /**
     * Field nsURI
     */
    private java.lang.String nsURI;

    /**
     * Field xmlName
     */
    private java.lang.String xmlName;

    /**
     * Field identity
     */
    private org.exolab.castor.xml.XMLFieldDescriptor identity;


      //----------------/
     //- Constructors -/
    //----------------/

    public GlobalDefinitionsImplDescriptor() 
     {
        super();
        nsURI = "urn:hl7-org:v3/mif";
        xmlName = "GlobalDefinitions";
        
        //-- set grouping compositor
        setCompositorAsSequence();
        org.exolab.castor.xml.util.XMLFieldDescriptorImpl  desc           = null;
        org.exolab.castor.mapping.FieldHandler             handler        = null;
        org.exolab.castor.xml.FieldValidator               fieldValidator = null;
        //-- initialize attribute descriptors
        
        //-- _activityTime
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(java.util.Date.class, "_activityTime", "activityTime", org.exolab.castor.xml.NodeType.Attribute);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                GlobalDefinitionsImpl target = (GlobalDefinitionsImpl) object;
                return target.getActivityTime();
            }
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    GlobalDefinitionsImpl target = (GlobalDefinitionsImpl) object;
                    target.setActivityTime( (java.util.Date) value);
                }
                catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            public java.lang.Object newInstance( java.lang.Object parent ) {
                return new java.util.Date();
            }
        };
        handler = new org.exolab.castor.xml.handlers.DateFieldHandler(handler);
        desc.setImmutable(true);
        desc.setHandler(handler);
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        
        //-- validation code for: _activityTime
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- initialize element descriptors
        
        //-- _valueDomainImplList
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.ValueDomainImpl.class, "_valueDomainImplList", "ValueDomain", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                GlobalDefinitionsImpl target = (GlobalDefinitionsImpl) object;
                return target.getValueDomainImpl();
            }
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    GlobalDefinitionsImpl target = (GlobalDefinitionsImpl) object;
                    target.addValueDomainImpl( (gov.nih.nci.ncicb.cadsr.edci.domain.impl.ValueDomainImpl) value);
                }
                catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            public java.lang.Object newInstance( java.lang.Object parent ) {
                return new gov.nih.nci.ncicb.cadsr.edci.domain.impl.ValueDomainImpl();
            }
        };
        desc.setHandler(handler);
        desc.setNameSpaceURI("urn:hl7-org:v3/mif");
        desc.setMultivalued(true);
        addFieldDescriptor(desc);
        
        //-- validation code for: _valueDomainImplList
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(0);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _dataElementConceptImplList
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementConceptImpl.class, "_dataElementConceptImplList", "DataElementConcept", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                GlobalDefinitionsImpl target = (GlobalDefinitionsImpl) object;
                return target.getDataElementConceptImpl();
            }
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    GlobalDefinitionsImpl target = (GlobalDefinitionsImpl) object;
                    target.addDataElementConceptImpl( (gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementConceptImpl) value);
                }
                catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            public java.lang.Object newInstance( java.lang.Object parent ) {
                return new gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementConceptImpl();
            }
        };
        desc.setHandler(handler);
        desc.setNameSpaceURI("urn:hl7-org:v3/mif");
        desc.setMultivalued(true);
        addFieldDescriptor(desc);
        
        //-- validation code for: _dataElementConceptImplList
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(0);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _dataElementImplList
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementImpl.class, "_dataElementImplList", "DataElement", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                GlobalDefinitionsImpl target = (GlobalDefinitionsImpl) object;
                return target.getDataElementImpl();
            }
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    GlobalDefinitionsImpl target = (GlobalDefinitionsImpl) object;
                    target.addDataElementImpl( (gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementImpl) value);
                }
                catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            public java.lang.Object newInstance( java.lang.Object parent ) {
                return new gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementImpl();
            }
        };
        desc.setHandler(handler);
        desc.setNameSpaceURI("urn:hl7-org:v3/mif");
        desc.setMultivalued(true);
        addFieldDescriptor(desc);
        
        //-- validation code for: _dataElementImplList
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(0);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _dataElementGroupImplList
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementGroupImpl.class, "_dataElementGroupImplList", "DataElementGroup", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                GlobalDefinitionsImpl target = (GlobalDefinitionsImpl) object;
                return target.getDataElementGroupImpl();
            }
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    GlobalDefinitionsImpl target = (GlobalDefinitionsImpl) object;
                    target.addDataElementGroupImpl( (gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementGroupImpl) value);
                }
                catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            public java.lang.Object newInstance( java.lang.Object parent ) {
                return new gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementGroupImpl();
            }
        };
        desc.setHandler(handler);
        desc.setNameSpaceURI("urn:hl7-org:v3/mif");
        desc.setMultivalued(true);
        addFieldDescriptor(desc);
        
        //-- validation code for: _dataElementGroupImplList
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(0);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.GlobalDefinitionsImplDescriptor()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method getAccessMode
     * 
     * 
     * 
     * @return AccessMode
     */
    public org.exolab.castor.mapping.AccessMode getAccessMode()
    {
        return null;
    } //-- org.exolab.castor.mapping.AccessMode getAccessMode() 

    /**
     * Method getExtends
     * 
     * 
     * 
     * @return ClassDescriptor
     */
    public org.exolab.castor.mapping.ClassDescriptor getExtends()
    {
        return null;
    } //-- org.exolab.castor.mapping.ClassDescriptor getExtends() 

    /**
     * Method getIdentity
     * 
     * 
     * 
     * @return FieldDescriptor
     */
    public org.exolab.castor.mapping.FieldDescriptor getIdentity()
    {
        return identity;
    } //-- org.exolab.castor.mapping.FieldDescriptor getIdentity() 

    /**
     * Method getJavaClass
     * 
     * 
     * 
     * @return Class
     */
    public java.lang.Class getJavaClass()
    {
        return gov.nih.nci.ncicb.cadsr.edci.domain.impl.GlobalDefinitionsImpl.class;
    } //-- java.lang.Class getJavaClass() 

    /**
     * Method getNameSpacePrefix
     * 
     * 
     * 
     * @return String
     */
    public java.lang.String getNameSpacePrefix()
    {
        return nsPrefix;
    } //-- java.lang.String getNameSpacePrefix() 

    /**
     * Method getNameSpaceURI
     * 
     * 
     * 
     * @return String
     */
    public java.lang.String getNameSpaceURI()
    {
        return nsURI;
    } //-- java.lang.String getNameSpaceURI() 

    /**
     * Method getValidator
     * 
     * 
     * 
     * @return TypeValidator
     */
    public org.exolab.castor.xml.TypeValidator getValidator()
    {
        return this;
    } //-- org.exolab.castor.xml.TypeValidator getValidator() 

    /**
     * Method getXMLName
     * 
     * 
     * 
     * @return String
     */
    public java.lang.String getXMLName()
    {
        return xmlName;
    } //-- java.lang.String getXMLName() 

}
