package gov.nih.nci.ncicb.cadsr.dao.impl;

import gov.nih.nci.cadsr.domain.Context;
import gov.nih.nci.cadsr.domain.Designation;
import gov.nih.nci.cadsr.domain.EnumeratedValueDomain;
import gov.nih.nci.cadsr.domain.Form;
import gov.nih.nci.cadsr.domain.FormElement;
import gov.nih.nci.cadsr.domain.Module;
import gov.nih.nci.cadsr.domain.Protocol;
import gov.nih.nci.cadsr.domain.Question;
import gov.nih.nci.cadsr.domain.QuestionConditionComponents;
import gov.nih.nci.cadsr.domain.QuestionRepetition;
import gov.nih.nci.cadsr.domain.ReferenceDocument;
import gov.nih.nci.cadsr.domain.TriggerAction;
import gov.nih.nci.cadsr.domain.ValidValue;
import gov.nih.nci.ncicb.cadsr.constants.EDCIConfiguration;
import gov.nih.nci.ncicb.cadsr.constants.MessageGeneratorConstants;
import gov.nih.nci.ncicb.cadsr.dao.DataAccessException;
import gov.nih.nci.ncicb.cadsr.dao.GlobalDefinitionsDAO;
import gov.nih.nci.ncicb.cadsr.dao.InstrumentDAO;
import gov.nih.nci.ncicb.cadsr.dto.ReferenceDocumentAttachment;
import gov.nih.nci.ncicb.cadsr.edci.domain.*;
import gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDSubsetImpl;
import gov.nih.nci.system.applicationservice.ApplicationService;

import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implements the InstumentDAO interface using the caDSR API.
 */
public class InstrumentDAOImpl  extends CaDSRApiDAOImpl implements InstrumentDAO{
    private static Logger logger = LogManager.getLogger(InstrumentDAO.class);
    private ReferenceDocumentCreator refDocCreator;
    private RefDocAttachmentCreator refDocAttachmentCreator;
    private QueryRefDocAttachment queryRefDocAttachment;
    private StoreBlob storeBlob;
    private SimpleDateFormat formatter = new SimpleDateFormat(":yyyyMMdd:HH:mm:ss");
    public InstrumentDAOImpl() {
    }
    /**
     * Query and update the Instrument object with the Section data, including 
     * SectionDef and SectionRef.
     * @param instrument
     * @param form
     * @param globalDefinitions
     * @return
     * @throws DataAccessException
     */
    protected Instrument updateSectionData(Instrument instrument,Form form, GlobalDefinitions globalDefinitions) throws DataAccessException
    {
          EDCIConfiguration config = EDCIConfiguration.getInstance();
          //Create the SectionDef
          SectionDef sectionDef = domainObjectFactory.getSectionDef();
          sectionDef.setDescription(form.getPreferredDefinition());
          sectionDef.setGUID(geteDCIGUID(getGUID()));
          //Set the sectionDef ID
          sectionDef.setName(form.getLongName());;

          
          //Create the SectionRef, it refers to the sectionDef
          SectionRef sectionRef = domainObjectFactory.getSectionRef();
          sectionRef.setId(geteDCIGUID(form.getId()));
          sectionRef.setName(form.getLongName());
          sectionRef.setNavigationSequenceNumber(config.getProperty("sectionRefNavigationSequenceNumber"));
          sectionRef.setSectionDef(sectionDef);
     
          //Get groupData
          Map groupData = getGroupData(form, globalDefinitions);
          sectionDef.setGroupDefCollection((Collection<GroupDef>)groupData.get("groupDefs"));   
          sectionRef.setGroupRefCollection((Collection<GroupRef>)groupData.get("groupRefs"));
          
          Collection<SectionDef> sectionDefs = new ArrayList<SectionDef>(1);
          sectionDefs.add(sectionDef);          
          Collection<SectionRef> sectionRefs = new ArrayList<SectionRef>(1);
          sectionRefs.add(sectionRef);
          
          instrument.setSectionDefCollection(sectionDefs);
          instrument.setSectionRefCollection(sectionRefs);
          return instrument;
    }
    /**
     * Query Group data including GroupDef and GroupRef.
     * @param form
     * @param globalDefinitions
     * @return
     * @throws DataAccessException
     */
    protected Map getGroupData(Form form, GlobalDefinitions globalDefinitions) throws DataAccessException {
        Collection<Module> modules = form.getModuleCollection();
        Collection<GroupDef> groupDefs = new ArrayList<GroupDef>(modules.size());
        Collection<GroupRef> groupRefs = new ArrayList<GroupRef>(modules.size());
        
        for (Module module:modules){
          //Create the GroupDef
          GroupDef groupDef = domainObjectFactory.getGroupDef();
          DataElementGroup dataElementGroup = getDataElementGroup(module, globalDefinitions);
          groupDef.setDataElementGroupGUID(dataElementGroup.getGUID());
          groupDef.setDescription(module.getPreferredDefinition());
          groupDef.setName(module.getLongName());
          groupDef.setId(geteDCIGUID(getGUID()));
          Integer maximumQuestionRepeat = module.getMaximumQuestionRepeat();
          if ((maximumQuestionRepeat != null)&&(maximumQuestionRepeat.intValue()>0))
          {
              groupDef.setRepeatingGroupFlag("true");
          }
          else {
              groupDef.setRepeatingGroupFlag("false");
          }
          //Create the GroupRef
          GroupRef groupRef =  domainObjectFactory.getGroupRef();
          groupRef.setGroupDef(groupDef);
          groupRef.setId(module.getId());
          groupRef.setMaximumItemRefRepeats(module.getMaximumQuestionRepeat()==null?null:module.getMaximumQuestionRepeat().toString());
          groupRef.setName(module.getLongName());
          groupRef.setNavigationSequenceNumber(module.getDisplayOrder().toString());
          //
          //Collection<Designation> designations = module.getDesignationCollection();
           Collection<Designation> designations = form.getDesignationCollection();
          Collection<GroupAlias> groupAliases = new ArrayList<GroupAlias>(designations.size());
          for (Designation designation:designations) {
              GroupAlias  groupAlias = domainObjectFactory.getGroupAlias();
              groupAlias.setName(designation.getName());
              groupAlias.setSystem(designation.getType());
              groupAliases.add(groupAlias);
          }
          groupDef.setGroupAliasCollection(groupAliases);
          Map itemData = getItemData(module,globalDefinitions);
          groupDef.setItemDefCollection((Collection<ItemDef>)itemData.get("itemDefs"));
          groupRef.setItemRefCollection((Collection<ItemRef>)itemData.get("itemRefs"));
          groupDefs.add(groupDef);
          groupRefs.add(groupRef);
        }
        Map groupData = new HashMap(2);
        groupData.put("groupDefs", groupDefs);
        groupData.put("groupRefs", groupRefs);
        return groupData;
    }
    /**
     * Query the ItemRef, and ItemDef data using caCORE API. 
     * @param module
     * @param globalDefinitions
     * @return
     * @throws DataAccessException
     */
    
    protected Map getItemData(Module module, GlobalDefinitions globalDefinitions) throws DataAccessException {
        Collection<Question> questions = module.getQuestionCollection();
        Collection<ItemDef> itemDefs = new ArrayList<ItemDef>(questions.size());
        Collection<ItemRef> itemRefs = new ArrayList<ItemRef>(questions.size());
        EDCIConfiguration config = EDCIConfiguration.getInstance();
        try 
        {
          for (Question question:questions){
            //Create the ItemDef
            ItemDef itemDef = domainObjectFactory.getItemDef();
            DataElement dataElement = getDataElement(question, globalDefinitions);
            itemDef.setDataElementGUID(geteDCIGUID(dataElement.getGUID()));
            itemDef.setId(geteDCIGUID(getGUID()));
            // isMandatory new for 3.2
            itemDef.setIsMandatoryFlag(question.getIsMandatory());
            itemDef.setNavigationSequenceNumber(question.getDisplayOrder().toString());
            //?Range check based on 3.2
            /**
             * Need more information from Prerna to create RangeCheck.
            Collection<QuestionConditionComponents> qcs = question.getQuestionComponentCollection();
            for (QuestionConditionComponents qcc: qcs) {
                RangeCheck rc = domainObjectFactory.getRangeCheck();
                rc.setComparator(qcc.getOperand());
                rc.setSoftHard(config.getProperty("default.rangeCheck.softHard"));
                rc.setUnitOfMeasure(qcc.getQuestion().getValueDomain().getUOMName());
                qcc.getQuestionCondition().
            }
            */
            //Get the enumeratedValueDomainSubSetId
            if (question.getDataElement().getValueDomain() instanceof EnumeratedValueDomain)
            {
               EVDSubset evdSubset = getEVDSubset(question,globalDefinitions);
               itemDef.setEnumeratedValueDomainSubsetId(evdSubset.getSubsetId());  
            }
            itemDefs.add(itemDef);
            
            
            
            //Create the ItemRef
            ItemRef itemRef = domainObjectFactory.getItemRef();
            itemRef.setId(question.getId());
            itemRef.setIsModifiableFlag(question.getIsEditable());
            itemRef.setItemDef(itemDef);
            itemRef.setNavigationSequenceNumber(question.getDisplayOrder().toString());
            ItemRefPrompt itemRefPrompt = domainObjectFactory.getItemRefPrompt();
            itemRefPrompt.setPrompt(question.getLongName());
            itemRefPrompt.setLanguage(config.getProperty("default.language"));
            Collection<ItemRefPrompt> itemRefPrompts = new ArrayList<ItemRefPrompt>(1);
            itemRefPrompts.add(itemRefPrompt);
            itemRef.setItemRefPromptCollection(itemRefPrompts);
            ValidValue  defaultValidValue = null;
            if ((question.getDefaultValidValueId()== null)||(question.getDefaultValidValueId().equals(""))) {
               itemRef.setDefaultValue(question.getDefaultValue());                
            }
            else {
                //Get the Default Valid Value and set its long name as the default value;
                Collection<ValidValue> validValues = question.getValidValueCollection();
                for(ValidValue validValue: validValues){
                    if (validValue.getId().equals(question.getDefaultValidValueId())) {
                        defaultValidValue = validValue;
                        break;
                    }
                }
                itemRef.setDefaultValue(defaultValidValue.getLongName());
            }
            //Set Instructions
             Collection<gov.nih.nci.cadsr.domain.Instruction> questionInstructions = question.getInstruction();
             Collection<Instruction> instructions = new ArrayList<Instruction>(questionInstructions.size());
             for (gov.nih.nci.cadsr.domain.Instruction caDSRInstruction: questionInstructions){
                Instruction instruction = domainObjectFactory.getInstruction();
                instruction.setInstructionText(caDSRInstruction.getLongName());
                instruction.setLanguage(config.getProperty("default.language"));
                instructions.add(instruction);
             }            
             itemRef.setPersistentInformationCollection(instructions);
            //set ExplicitItemRefRepetition
            Collection<QuestionRepetition> questionRepetitions = question.getQuestionRepetitionCollection();
            Collection<ExplicitItemRefRepetition> explicitItemRefRepetitions = new ArrayList<ExplicitItemRefRepetition>(questionRepetitions.size());
            for (QuestionRepetition questionRepetition: questionRepetitions){
                ExplicitItemRefRepetition explicitItemRefRepetition = domainObjectFactory.getExplicitItemRefRepetition();
                explicitItemRefRepetition.setIsModifiableFlag(questionRepetition.getIsEditable());
                explicitItemRefRepetition.setRepeatSequenceNumber(questionRepetition.getRepeatSequenceNumber().toString());
                if (defaultValidValue != null){
                    explicitItemRefRepetition.setRepeatSpecificDefaultValue(defaultValidValue.getLongName());
                }
                else {
                    explicitItemRefRepetition.setRepeatSpecificDefaultValue(question.getDefaultValue());
                }   
                //Get triggerActions for Repetition
                Collection<TriggerAction> triggerActions = getTriggerActions(questionRepetition);
                explicitItemRefRepetition.setTriggeredActionCollection(getEDCITriggeredActions(triggerActions));
            }
            itemRef.setExplicitItemRefRepetitionCollection(explicitItemRefRepetitions);
            //Get triggeredActions for question
            Collection<TriggerAction> triggerActions = getTriggerActions(question);
            itemRef.setTriggeredActionCollection(getEDCITriggeredActions(triggerActions));
            itemRefs.add(itemRef);
          }
          Map itemData = new HashMap(2);
          itemData.put("itemDefs", itemDefs);
          itemData.put("itemRefs", itemRefs);
          return itemData;
        }
        catch(Exception e){
            logger.error("Error getting item data.", e);
            throw new DataAccessException("Error getting item data.",e);
        }
    }
    /**
     * Returns the DataElementGroup corressponding to a Module from GlobalDefinitions
     * @param module
     * @param globalDefinitions
     * @return
     * @throws DataAccessException
     */
    protected DataElementGroup getDataElementGroup(Module module, GlobalDefinitions globalDefinitions) throws DataAccessException
    {
        String moduleName = module.getLongName();
        DataElementGroup dataElementGroup = null;
        try {
          if ((moduleName != null)&&(!(moduleName.equals(""))))
          {
             Collection<DataElementGroup> dataElementGroups = globalDefinitions.getDataElementGroupCollection(); 
             for (DataElementGroup deg: dataElementGroups){
                 if (moduleName.equals(deg.getName())) {
                     dataElementGroup = deg; break;
                 }
              }
              if (dataElementGroup == null)
              {
                 throw new Exception("Could not find DataElementGroup for module "+moduleName);
              }
           }
        }
        catch(Exception e) {
            logger.error("Error finding DataElementGroup for module "+moduleName);
            throw new DataAccessException("Error finding DataElementGroup for module "+moduleName);
        }
        return dataElementGroup;
    }
    /**
     * Returns the DataElement associated with a Question from GlobalDefinitions
     * @param question
     * @param globalDefinitions
     * @return
     * @throws DataAccessException
     */
    protected DataElement getDataElement(Question question, GlobalDefinitions globalDefinitions) throws DataAccessException{
        String guid = question.getDataElement().getId();
        Collection<DataElement> dataElements = globalDefinitions.getDataElementCollection();
        DataElement searchedDE = domainObjectFactory.getDataElement();
        if (dataElements != null)
        {
           for(DataElement dataElement:dataElements){
              if (dataElement.getGUID().equals(guid)){
                  searchedDE = dataElement;break;
              }
           }
        }
        return searchedDE;
    }  
    /**
     * Returns EVDSubset used by a Question.
     * @param question
     * @param globalDefinitions
     * @return
     * @throws DataAccessException
     */
    protected EVDSubset getEVDSubset(Question question, GlobalDefinitions globalDefinitions ) throws DataAccessException {
        Collection<ValidValue> vvs = question.getValidValueCollection();
        gov.nih.nci.cadsr.domain.ValueDomain cadsrVD = question.getDataElement().getValueDomain();
        EVDSubset searchedEVDSubset = null;
        try 
        {
           //Find the eDCI ValueDomain which matches the caDSR ValueDomain from the Question
           ValueDomain itemVD = null;
           for(ValueDomain valueDomain: globalDefinitions.getValueDomainCollection()) {
              if (valueDomain.getGUID().indexOf(cadsrVD.getId()) >= 0) {
                  itemVD = valueDomain; break;
              }
           }
           //Search for EVDSubset if the ValueDomain is Enumerated
           if ((itemVD != null)&&(itemVD.getIsEnumeratedFlag()))
           {
              Collection<EVDSubset> evdSubsets = itemVD.getEVDSubsetCollection();
               //Sort the ValidValues by displayOrder
               List validValues = new ArrayList(vvs.size());
               for (ValidValue vv:vvs) validValues.add(vv);
               Collections.sort(validValues, new ValidValueComparator());              
              for (EVDSubset evdSubset: evdSubsets) {
                  //If the number of elements are same in the evdSubset and validValues compare each value individually.
                  if (evdSubset.getElementInSubsetCollection().size() == vvs.size()) {
                     //Compare the valid Values individually
                     searchedEVDSubset = evdSubset;
                     List elementsInSubset = Arrays.asList(evdSubset.getElementInSubsetCollection().toArray());
                     //Sort elementsInSubset by sequence number
                     Collections.sort(elementsInSubset, new EISComparator());
                     Iterator ei=elementsInSubset.iterator();
                     //Verify that order of ValidValues is same as order of Elements in EVDSubset
                     for (Iterator i=validValues.iterator(); i.hasNext();) {
                          ValidValue vv = (ValidValue)i.next();
                          ElementInSubset eis = (ElementInSubset)ei.next();
                          if (!(vv.getLongName().equals(eis.getValue()))) {
                              //This is an evdSubset with a different order.
                              searchedEVDSubset = null;break;
                          }
                     }
                      //Break if EVDSubset found
                      if (searchedEVDSubset != null) break;
                  }
              }
           }
        }
        catch(Exception e) {
            logger.error("Error finding EVDSubset for Question "+question.getLongName(),e);
            throw new DataAccessException("Error finding EVDSubset for Question "+question.getLongName(),e);
        }
       return searchedEVDSubset;
    }
    /**
     * Query TriggerActions where a FormElement is the source  using caCORE API
     * @param sourceFormElement
     * @return
     * @throws Exception
     */
    protected Collection<TriggerAction> getTriggerActions(FormElement sourceFormElement) throws Exception {
        Collection<TriggerAction> triggerActions;
        try {
          TriggerAction triggerAction = new TriggerAction();
          FormElement formElement = new FormElement();
          formElement.setId(sourceFormElement.getId());
          triggerAction.setSourceFormElement(formElement);
          triggerActions = appService.search(TriggerAction.class, triggerAction);
          return triggerActions;
        }
        catch (Exception e) {
            logger.error("Error querying TriggerActions from caDSR.",e);
            throw new DataAccessException("Error querying TriggerActions from caDSR.",e);
        }
    }
    /**
     * Generate eDCI TriggeredActions for caDSR TriggerActions
     * @param caDSRTriggerActions
     * @return
     * @throws DataAccessException
     */
    protected Collection<TriggeredAction> getEDCITriggeredActions(Collection<TriggerAction> caDSRTriggerActions) throws DataAccessException {
        try {
            Collection<TriggeredAction> triggeredActions = new ArrayList<TriggeredAction>(caDSRTriggerActions.size());
            for(TriggerAction triggerAction: caDSRTriggerActions) {
                TriggeredAction triggeredAction = domainObjectFactory.getTriggeredAction();
                triggeredAction.setAction(triggerAction.getAction());
                triggeredAction.setCriterionValue(triggerAction.getCriterionValue());
                triggeredAction.setForcedValue(triggerAction.getForcedValue());
                triggeredAction.setTargetId(triggerAction.getTargetFormElement().getId());
                triggeredAction.setTriggerRelationship(triggerAction.getTriggerRelationship());
                triggeredActions.add(triggeredAction);
            }     
            return triggeredActions;
        }
        catch (Exception e) {
            logger.error("Error creating eDCI TriggeredActions.", e);
            throw new DataAccessException("Error creating eDCI TriggeredActions.", e);
        }
    }
   /**
     * Query the Instrument metadata using the caCORE API.
     * @param formIdSeq
     * @param globalDefinitions
     * @return Instrument
     * @throws DataAccessException
     */
    public Instrument getInstrument(String formIdSeq, GlobalDefinitions globalDefinitions) throws DataAccessException {
        Instrument instrument = domainObjectFactory.getInstrument();
        EDCIConfiguration config = EDCIConfiguration.getInstance();
        try {
            Form qForm = queryCaDSRForm(formIdSeq);
            if (qForm.getType().equals(MessageGeneratorConstants.FORM_TYPE_TEMPLATE)) {
                instrument.setIsTemplateDciFlag(new Boolean(true));
            }
            else {
                instrument.setIsTemplateDciFlag(new Boolean(false));
            }
            instrument.setName(qForm.getLongName());
            instrument.setNamespace(config.getProperty("default.namespace"));
            instrument.setDescription(qForm.getPreferredDefinition());
            instrument.setGUID(geteDCIGUID(getGUID()));
            instrument.setCreationTimestamp(qForm.getDateCreated());
            //which protocol to use
            //Get the Instructions
            Collection<gov.nih.nci.cadsr.domain.Instruction> caDSRInstructions = qForm.getInstruction();
            Collection<Instruction> instructions = new ArrayList<Instruction>(caDSRInstructions.size());
            for (gov.nih.nci.cadsr.domain.Instruction caDSRInstruction: caDSRInstructions){
               Instruction instruction = domainObjectFactory.getInstruction();
               instruction.setInstructionText(caDSRInstruction.getLongName());
               instruction.setLanguage(config.getProperty("default.language"));
               instructions.add(instruction);
            }
            instrument.setPersistentInformationCollection(instructions);
            //Get section data
            updateSectionData(instrument, qForm, globalDefinitions);
        }
        catch(Exception e){
            logger.error("Error retrieving Instrument",e);
            throw new DataAccessException("Error retrieving Instrument.",e);
        }
        return instrument;
    }
    
    public Form queryCaDSRForm(String idSeq) throws DataAccessException {
        Form form = new Form();
        form.setId(idSeq);
        try {
            List<Form> forms = appService.search(Form.class,form);
            if (forms.size() == 0)throw new DataAccessException("Form not found for "+idSeq);
            Form qForm = forms.get(0);
            return qForm;
        }
        catch (Exception e) {
            logger.error("Error querying form.",e);
            throw new DataAccessException("Error querying form.",e);
        }
    }

    public Form queryCaDSRForm(String publicId, String version) throws DataAccessException {
        Form form = new Form();
        try {
           form.setPublicID(new Long(publicId));
           form.setVersion(new Float(version));
        }
        catch(Exception e) {
            throw new DataAccessException("PublicId should be a valid number and version should be a valid floating point number.");
        }
        try {
            List<Form> forms = appService.search(Form.class,form);
            if (forms.size() == 0) throw new DataAccessException("Form not found for "+publicId+" v"+version);
            Form qForm = forms.get(0);
            return qForm;
        }
        catch (Exception e) {
            logger.error("Error querying form.",e);
            throw new DataAccessException("Error querying form.",e);
        }
    }    
    /**
     * Get the name of the ReferenceDocument used to store the instrument Hl7 
     * message based on current date.
     * @param form
     * @return name of the ReferenceDocument
     * @throws DataAccessException
     */
    protected String getRefDocName(Form form) throws DataAccessException
    {      
       return getRefDocName(form,new Date());
    }
    /**
     * Get the name of the ReferenceDocument used to store the instrument Hl7 
     * message
     * @param form
     * @param createDate
     * @return name of the ReferenceDocument.
     * @throws DataAccessException
     */
    protected String getRefDocName(Form form, Date createDate) throws DataAccessException
    {
       StringBuffer name = new StringBuffer(30);
       name.append(form.getPublicID().toString());
       name.append(":v");
       name.append(form.getVersion());
       name.append(formatter.format(createDate));
       
       return name.toString();
    }    

    public String storeInstrumentHL7Message(String formIdSeq, String message, Date createDate, String user) throws DataAccessException {
        try {
             if (refDocCreator == null) {
                 refDocCreator = new ReferenceDocumentCreator(dataSource);
             }
             if (refDocAttachmentCreator == null) {
                 refDocAttachmentCreator = new RefDocAttachmentCreator(dataSource);
             }
             if (storeBlob == null) {
                 storeBlob = new StoreBlob(dataSource);
             }
            if (message == null) {
                 throw new DataAccessException("Instrument HL7 message is null.");
            }             
             Form form = queryCaDSRForm(formIdSeq);
             //Create the ReferenceDocument 
             ReferenceDocument referenceDocument = new ReferenceDocument();
             referenceDocument.setCreatedBy(user);
             referenceDocument.setId(getGUID());
             referenceDocument.setType(INSTRUMENT_REF_DOC_TYPE);
             referenceDocument.setName(getRefDocName(form, createDate));
             referenceDocument.setContext(form.getContext());
             referenceDocument = refDocCreator.createReferenceDocument(referenceDocument, formIdSeq);
             //Create the ReferenceDocumentAttachment
             ReferenceDocumentAttachment refDocAttachment = new ReferenceDocumentAttachment();
             refDocAttachment.setReferenceDocument(referenceDocument);
             refDocAttachment.setMimeType("text/xml");
             refDocAttachment.setCreatedBy(user);
             refDocAttachment.setContentType("BLOB");
             refDocAttachment.setName(getAttachmentName(referenceDocument));
             refDocAttachment.setDocSize(new Long(message.length()));
             refDocAttachment.setAttachment(message);
             refDocAttachmentCreator.createRefDocAttachment(refDocAttachment);
             
             return referenceDocument.getId();
        }
        catch (Exception e) {
            logger.error("Error storing Instrument HL7 message.", e);
            throw new DataAccessException("Error storing Instrument HL7 message.", e);
        }
    }
    /**
     * Gets the attachment name for ReferenceDocument for Instrument HL7 message
     * @param refDocName
     * @return
     */
    protected String getAttachmentName(ReferenceDocument refDoc) {
        return refDoc.getName()+"I";
    }

    public ReferenceDocumentAttachment queryInstrumentHL7Message(String formIdSeq, Date createDate) throws DataAccessException {
          try {
              if (queryRefDocAttachment == null){
                  queryRefDocAttachment = new QueryRefDocAttachment(dataSource);
              }
              ReferenceDocument rd = new ReferenceDocument();
              rd.setType(INSTRUMENT_REF_DOC_TYPE);
              Form form = queryCaDSRForm(formIdSeq);
              rd.setName(getRefDocName(form,createDate));
              List refDocs = appService.search(ReferenceDocument.class,rd);
              if (refDocs.size() == 0) {
                  throw new DataAccessException("Instrument Message Reference Document not found for form "+formIdSeq+" date "+formatter.format(createDate));
              }
              ReferenceDocument referenceDocument = (ReferenceDocument)refDocs.get(0);
              ReferenceDocumentAttachment rda = queryRefDocAttachment.query(getAttachmentName(referenceDocument));
              rda.setReferenceDocument(referenceDocument);
              
              return rda;
          }
          catch(Exception e) {
              logger.error("Error querying ReferenceDocumentAttachment.",e);
              throw new DataAccessException("Error querying ReferenceDocumentAttachment.",e);
          }
    }
    
    public ReferenceDocumentAttachment queryInstrumentHL7Message(String formIdSeq,String refDocName) throws DataAccessException{
        try {
            if (queryRefDocAttachment == null){
                queryRefDocAttachment = new QueryRefDocAttachment(dataSource);
            }
            ReferenceDocument rd = new ReferenceDocument();
            rd.setName(refDocName);
            rd.setType(INSTRUMENT_REF_DOC_TYPE);
            Form form = new Form();
            form.setId(formIdSeq);
            Collection<ReferenceDocument> rds = new ArrayList(1);
            rds.add(rd);
            form.setReferenceDocumentCollection(rds);
            List refDocs = appService.search(ReferenceDocument.class,rd);
            if (refDocs.size() == 0) {
                throw new DataAccessException("Instrument Message Reference Document not found for "+formIdSeq+ " ref doc "+refDocName);
            }
            ReferenceDocument referenceDocument = (ReferenceDocument)refDocs.get(0);
            ReferenceDocumentAttachment rda = queryRefDocAttachment.query(getAttachmentName(referenceDocument));
            rda.setReferenceDocument(referenceDocument);
            
            return rda;
        }
        catch(Exception e) {
            logger.error("Error querying ReferenceDocumentAttachment.",e);
            throw new DataAccessException("Error querying ReferenceDocumentAttachment.",e);
        }         
    }
    
    public ReferenceDocumentAttachment queryInstrumentHL7Message(String rdIdSeq) throws DataAccessException {
        try {
            if (queryRefDocAttachment == null){
                queryRefDocAttachment = new QueryRefDocAttachment(dataSource);
            }
            ReferenceDocument rd = new ReferenceDocument();
            rd.setId(rdIdSeq);
            List refDocs = appService.search(ReferenceDocument.class,rd);
            if (refDocs.size() == 0) {
                throw new DataAccessException("Instrument Message Reference Document not found for "+rdIdSeq);
            }
            ReferenceDocument referenceDocument = (ReferenceDocument)refDocs.get(0);
            ReferenceDocumentAttachment rda = queryRefDocAttachment.query(getAttachmentName(referenceDocument));
            rda.setReferenceDocument(referenceDocument);
            
            return rda;
        }
        catch(Exception e) {
            logger.error("Error querying ReferenceDocumentAttachment.",e);
            throw new DataAccessException("Error querying ReferenceDocumentAttachment.",e);
        }        
    }

    public Collection<ReferenceDocument> queryFormMessageReferenceDocuments(String publicId, 
                                                                            String version) throws DataAccessException {
        try {
            Form form = queryCaDSRForm(publicId, version);
            ReferenceDocument qRD1 = new ReferenceDocument();
            qRD1.setType(INSTRUMENT_REF_DOC_TYPE);
            ReferenceDocument qRD2 = new ReferenceDocument();
            qRD2.setType(GlobalDefinitionsDAO.GLOBALDEFINITIONS_REF_DOC_TYPE); 
            Collection<ReferenceDocument> qRefDocs = new ArrayList<ReferenceDocument>(2);
            qRefDocs.add(qRD1);
            qRefDocs.add(qRD2);
            Form qForm = new Form();
            qForm.setId(form.getId());
            qForm.setReferenceDocumentCollection(qRefDocs);
            List refDocs = appService.search(ReferenceDocument.class, qForm);
            for (Iterator i = refDocs.iterator(); i.hasNext();) {
                ReferenceDocument rd = (ReferenceDocument)i.next();
                logger.debug(" Id-> "+rd.getId());
                logger.debug(" Type-> "+rd.getType());
            }
            return refDocs;
        }
        catch(Exception e) {
            logger.error("Error querying ReferenceDocument for Form "+publicId+" v"+version);
            throw new DataAccessException("Error querying ReferenceDocument for Form "+publicId+" v"+version);
        }
    }

    public Collection<ReferenceDocument> queryFormMessageReferenceDocuments(String formIdSeq) throws DataAccessException
    {
        try {
            Form qForm = queryCaDSRForm(formIdSeq);
            Collection<ReferenceDocument> refDocs = qForm.getReferenceDocumentCollection();
            Collection<ReferenceDocument> rds = new ArrayList<ReferenceDocument>(refDocs.size());
            for (ReferenceDocument rd: refDocs) {
                if ((rd.getType().equals(INSTRUMENT_REF_DOC_TYPE))||(rd.getType().equals(GlobalDefinitionsDAO.GLOBALDEFINITIONS_REF_DOC_TYPE))) {
                     rds.add(rd);
                }
                logger.debug(" Id-> "+rd.getId());
                logger.debug(" Type-> "+rd.getType());
                logger.debug(" Name-> "+rd.getName());
            }
            return rds;
        }
        catch(Exception e) {
            logger.error("Error querying ReferenceDocument for Form "+formIdSeq);
            throw new DataAccessException("Error querying ReferenceDocument for Form "+formIdSeq);
        }

    }
    
    /**
     * Comparator for sorting ElementInSubset list by sequenceNumber.
     * Note: this comparator imposes orderings that are inconsistent with equals.
     */
    protected class EISComparator implements Comparator {
        public int compare(Object o1, Object o2) {
            ElementInSubset eis1 = (ElementInSubset)o1;
            ElementInSubset eis2 = (ElementInSubset)o2;
            int i1 = eis1.getSequenceNumber();
            int i2 = eis2.getSequenceNumber();
            int retval = -1;
            if (i1<i2) retval=-1;
            if (i1==i2) retval=0;
            if (i1>i2) retval=1;
            return retval;
        } 
    }
    /**
     * Comparator for sorting ValidValue list by displayOrder.
     * Note: this comparator imposes orderings that are inconsistent with equals.
     */    
    protected class ValidValueComparator implements Comparator {
        public int compare(Object o1, Object o2) {
            ValidValue v1 = (ValidValue)o1;
            ValidValue v2 = (ValidValue)o2;
            Integer i1 = v1.getDisplayOrder();
            Integer i2 = v2.getDisplayOrder();
            int retval = -1;
            if (i1<i2) retval=-1;
            if (i1==i2) retval=0;
            if (i1>i2) retval=1;
            return retval;
        } 
    }    
}
