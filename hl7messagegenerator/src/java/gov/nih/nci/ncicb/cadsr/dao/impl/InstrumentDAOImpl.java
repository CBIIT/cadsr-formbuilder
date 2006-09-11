package gov.nih.nci.ncicb.cadsr.dao.impl;

import gov.nih.nci.cadsr.domain.Designation;
import gov.nih.nci.cadsr.domain.EnumeratedValueDomain;
import gov.nih.nci.cadsr.domain.Form;
import gov.nih.nci.cadsr.domain.FormElement;
import gov.nih.nci.cadsr.domain.Module;
import gov.nih.nci.cadsr.domain.Protocol;
import gov.nih.nci.cadsr.domain.Question;
import gov.nih.nci.cadsr.domain.QuestionRepetition;
import gov.nih.nci.cadsr.domain.TriggerAction;
import gov.nih.nci.cadsr.domain.ValidValue;
import gov.nih.nci.ncicb.cadsr.constants.EDCIConfiguration;
import gov.nih.nci.ncicb.cadsr.constants.MessageGeneratorConstants;
import gov.nih.nci.ncicb.cadsr.dao.DataAccessException;
import gov.nih.nci.ncicb.cadsr.dao.InstrumentDAO;
import gov.nih.nci.ncicb.cadsr.edci.domain.*;
import gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementGroupImpl;
import gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementImpl;
import gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDSubsetImpl;
import gov.nih.nci.ncicb.cadsr.edci.domain.impl.ExplicitItemRefRepetitionImpl;
import gov.nih.nci.ncicb.cadsr.edci.domain.impl.GroupAliasImpl;
import gov.nih.nci.ncicb.cadsr.edci.domain.impl.GroupDefImpl;
import gov.nih.nci.ncicb.cadsr.edci.domain.impl.GroupRefImpl;
import gov.nih.nci.ncicb.cadsr.edci.domain.impl.InstructionImpl;
import gov.nih.nci.ncicb.cadsr.edci.domain.impl.InstrumentImpl;
import gov.nih.nci.ncicb.cadsr.edci.domain.impl.ItemDefImpl;
import gov.nih.nci.ncicb.cadsr.edci.domain.impl.ItemRefImpl;
import gov.nih.nci.ncicb.cadsr.edci.domain.impl.ItemRefPromptImpl;
import gov.nih.nci.ncicb.cadsr.edci.domain.impl.SectionDefImpl;
import gov.nih.nci.ncicb.cadsr.edci.domain.impl.SectionRefImpl;
import gov.nih.nci.ncicb.cadsr.edci.domain.impl.TriggeredActionImpl;
import gov.nih.nci.system.applicationservice.ApplicationService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
/**
 * Implements the InstumentDAO interface using the caDSR API.
 */
public class InstrumentDAOImpl  extends CaDSRApiDAOImpl implements InstrumentDAO{
    private static Logger logger = LogManager.getLogger(InstrumentDAO.class);
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
          sectionRef.setId(form.getId());
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
          groupDef.setDataElementGroupGUID(geteDCIGUID(dataElementGroup.getGUID()));
          groupDef.setDescription(module.getPreferredDefinition());
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
          
          Collection<Designation> designations = module.getDesignationCollection();
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
            //itemDef.setIsMandatoryFlag(question.isMandatory());
            //This mapping to displayOrder is incorrect
            //itemDef.setOccurenceSn(question.getDisplayOrder().toString());
            //Range check based on 3.2
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
        Collection validValues = question.getValidValueCollection();
        gov.nih.nci.cadsr.domain.ValueDomain cadsrVD = question.getDataElement().getValueDomain();
        EVDSubset searchedEVDSubset = null;
        try 
        {
           ValueDomain itemVD = null;
           for(ValueDomain valueDomain: globalDefinitions.getValueDomainCollection()) {
              if (valueDomain.getGUID().indexOf(cadsrVD.getId()) > 0) {
                  itemVD = valueDomain; break;
              }
           }
           //Search for EVDSubset if the ValueDomain is Enumerated
           if ((itemVD != null)&&(itemVD.getIsEnumeratedFlag()))
           {
              Collection<EVDSubset> evdSubsets = itemVD.getEVDSubsetCollection();
              for (EVDSubset evdSubset: evdSubsets) {
                  if (evdSubset.getElementInSubsetCollection().size() == validValues.size()) {
                     //Compare the valid Values individually
                     searchedEVDSubset = evdSubset; break;
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
          ApplicationService appService = serviceLocator.getCaDSRPublicApiService();
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
        Form form = new Form();
        form.setId(formIdSeq);
        Instrument instrument = domainObjectFactory.getInstrument();
        EDCIConfiguration config = EDCIConfiguration.getInstance();
        try {
            ApplicationService appService = serviceLocator.getCaDSRPublicApiService();
            List<Form> forms = appService.search(Form.class,form);
            Form qForm = forms.get(0);
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

}
