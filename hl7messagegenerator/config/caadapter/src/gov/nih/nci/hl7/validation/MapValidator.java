/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/validation/MapValidator.java,v 1.1 2006-10-03 17:38:44 marwahah Exp $
 *
 * ******************************************************************
 * COPYRIGHT NOTICE
 * ******************************************************************
 *
 * The caAdapter Software License, Version 1.3
 * Copyright Notice.
 * 
 * Copyright 2006 SAIC. This software was developed in conjunction with the National Cancer Institute. To the extent government employees are co-authors, any rights in such works are subject to Title 17 of the United States Code, section 105. 
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met: 
 * 
 * 1. Redistributions of source code must retain the Copyright Notice above, this list of conditions, and the disclaimer of Article 3, below. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. 
 * 
 * 2. The end-user documentation included with the redistribution, if any, must include the following acknowledgment:
 * 
 * 
 * "This product includes software developed by the SAIC and the National Cancer Institute."
 * 
 * 
 * If no such end-user documentation is to be included, this acknowledgment shall appear in the software itself, wherever such third-party acknowledgments normally appear. 
 * 
 * 3. The names "The National Cancer Institute", "NCI" and "SAIC" must not be used to endorse or promote products derived from this software. 
 * 
 * 4. This license does not authorize the incorporation of this software into any third party proprietary programs. This license does not authorize the recipient to use any trademarks owned by either NCI or SAIC-Frederick. 
 * 
 * 5. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE UNITED STATES GOVERNMENT, THE NATIONAL CANCER INSTITUTE, SAIC, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * <!-- LICENSE_TEXT_END -->
 */


package gov.nih.nci.hl7.validation;

import gov.nih.nci.hl7.clone.meta.CloneAttributeMeta;
import gov.nih.nci.hl7.clone.meta.CloneDatatypeFieldMeta;
import gov.nih.nci.hl7.clone.meta.CloneMeta;
import gov.nih.nci.hl7.clone.meta.HL7V3Meta;
import gov.nih.nci.hl7.common.MetaObject;
import gov.nih.nci.hl7.csv.meta.CSVFieldMeta;
import gov.nih.nci.hl7.csv.meta.CSVMeta;
import gov.nih.nci.hl7.csv.meta.CSVSegmentMeta;
import gov.nih.nci.hl7.function.FunctionMeta;
import gov.nih.nci.hl7.function.ParameterMeta;
import gov.nih.nci.hl7.map.Map;
import gov.nih.nci.hl7.map.MapProcessorHelper;
import gov.nih.nci.hl7.map.Mapping;
import gov.nih.nci.hl7.map.MappingException;
import gov.nih.nci.hl7.map.components.BaseComponent;
import gov.nih.nci.hl7.map.components.FunctionComponent;
import gov.nih.nci.hl7.util.Config;
import gov.nih.nci.hl7.util.MessageResources;

import java.util.HashSet;
import java.util.List;

/**
 * Used to validate mapping information (map files).
 *
 * @author OWNER: Matthew Giordano
 * @author LAST UPDATE $Author: marwahah $
 * @version $Revision: 1.1 $
 * @since caAdapter v1.2
 */

public class MapValidator extends Validator {
    private static final String LOGID = "$RCSfile: MapValidator.java,v $";
    public static String RCSID = "$Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/validation/MapValidator.java,v 1.1 2006-10-03 17:38:44 marwahah Exp $";
    MapProcessorHelper helper = null;

    public MapValidator(Mapping object) {
        super(object);
        helper = new MapProcessorHelper((Mapping) toBeValidatedObject);
    }

    public ValidatorResults validate() {
        ValidatorResults validatorResults = new ValidatorResults();
        validatorResults.addValidatorResults(mapRule10());
        if (validatorResults.hasFatal()) {
            return validatorResults;
        }
        validatorResults.addValidatorResults(mapRule11());
        if (validatorResults.hasFatal()) {
            return validatorResults;
        }

        validatorResults.addValidatorResults(mapRule3());
        validatorResults.addValidatorResults(mapRule4());
        validatorResults.addValidatorResults(mapRule5());
        validatorResults.addValidatorResults(mapRule6());
        validatorResults.addValidatorResults(mapRule7());
        validatorResults.addValidatorResults(mapRule8());
        validatorResults.addValidatorResults(mapRule9());
        validatorResults.addValidatorResults(mapRule12());
        validatorResults.addValidatorResults(mapRule13());

        return validatorResults;
    }

    /**
     * source field referenced in map doesn't exist in source metadata
     *
     * @return ValidatorResults
     */
    public ValidatorResults mapRule3() {
        ValidatorResults validatorResults = new ValidatorResults();
        return validatorResults;
    }

    /**
     * destination field referenced in map doesn't exist in destination metadata
     *
     * @return ValidatorResults
     */
    public ValidatorResults mapRule4() {
        ValidatorResults validatorResults = new ValidatorResults();
        return validatorResults;
    }

    /**
     * mandatory HMD element has neither a mapping in map nor an HL7-defined
     * or user-defined default value in HSM
     *
     * @return * @return ValidatorResults
     */
    public ValidatorResults mapRule5() {
        HL7V3Meta hl7V3Meta = (HL7V3Meta) ((Mapping) toBeValidatedObject).getTargetComponent().getMeta();
        String cloneUUID = ((Mapping) toBeValidatedObject).getTargetComponent().getUUID();

        CloneMeta rootCloneMeta = hl7V3Meta.getRootCloneMeta();
        ValidatorResults validatorResults = processClone(rootCloneMeta, cloneUUID);
        return validatorResults;
    }

    /* used by mapRule5() */
    private ValidatorResults processClone(CloneMeta cloneMeta, String cloneUUID) {
        ValidatorResults validatorResults = new ValidatorResults();
        boolean optional = cloneMeta.getCardinality()==null || cloneMeta.getCardinality().getMin() == 0;
        List<Map> allmaps = helper.findMapsToThisClonesDatatypefields(cloneMeta, cloneUUID, true);
        // if it's required OR if it has childmaps - it needs to be inspected!
        if (!optional || allmaps.size() > 0) {
            //System.out.println("C-" + cloneMeta.getLinage());
            // process attributes.
            List<CloneAttributeMeta> cloneAttributeMeta = cloneMeta.getAttributes();
            for (int i = 0; i < cloneAttributeMeta.size(); i++) {
                validatorResults.addValidatorResults(processAttribute(cloneAttributeMeta.get(i),cloneUUID));
            }
            // process child clones.
            List<CloneMeta> childCloneMetas = cloneMeta.getChildClones();
            for (int i = 0; i < childCloneMetas.size(); i++) {
                validatorResults.addValidatorResults(processClone(childCloneMetas.get(i), cloneUUID));
            }
        }
        return validatorResults;
    }

    /* used by mapRule5() */
    private ValidatorResults processAttribute(CloneAttributeMeta cloneAttributeMeta, String cloneUUID) {
        ValidatorResults validatorResults = new ValidatorResults();
        boolean optional = cloneAttributeMeta.getCardinality()==null || cloneAttributeMeta.getCardinality().getMin() == 0;
        List<Map> maps = helper.findMapsToThisAttributesDatatypefields(cloneAttributeMeta, cloneUUID, true);
        // required OR if it has childmaps - it needs to be inspected!
        if (!optional || maps.size() > 0) {
            //System.out.println("A-" + cloneAttributeMeta.getName());
            // process child attributes.
            List<CloneAttributeMeta> cloneAttributeMetas = cloneAttributeMeta.getChildAttributes();
            for (int i = 0; i < cloneAttributeMetas.size(); i++) {
                validatorResults.addValidatorResults(processAttribute(cloneAttributeMetas.get(i), cloneUUID));
            }
            // process datatypefields.
            List<CloneDatatypeFieldMeta> cloneDatatypeFieldMetas= cloneAttributeMeta.getDatatypeFields();
            for (int i = 0; i < cloneDatatypeFieldMetas.size(); i++) {
                validatorResults.addValidatorResults(processDatatypeField(cloneDatatypeFieldMetas.get(i),cloneUUID));
            }
        }
        return validatorResults;
    }

    /* used by mapRule5() */
    private ValidatorResults processDatatypeField(CloneDatatypeFieldMeta cloneDatatypeFieldMeta, String cloneUUID) {
        ValidatorResults validatorResults = new ValidatorResults();
        // if required, see if it has 1)a map 2)a userdefault or 3) a hl7 default.
        // if not --> create a validation error message.
        boolean optional = cloneDatatypeFieldMeta.getCardinality()==null || cloneDatatypeFieldMeta.getCardinality().getMin() == 0;
        if(!optional){
            List<Map> maps = helper.findMapsAssociatedWithMetaObject(cloneUUID,cloneDatatypeFieldMeta.getUUID(),Config.MAP_COMPONENT_TARGET_TYPE);
            if(maps.size()==0){
                String userdefault = cloneDatatypeFieldMeta.getUserDefaultValue();
                String hl7default = cloneDatatypeFieldMeta.getHL7DefaultValue();
                if(userdefault==null || "".equalsIgnoreCase(userdefault)){
                    if(hl7default==null || "".equalsIgnoreCase(hl7default)){
                        //System.out.println("D-" + cloneDatatypeFieldMeta.getName());
                        String linage = getLinage(cloneDatatypeFieldMeta);
                        Message msg = MessageResources.getMessage("MAP5", new Object[]{linage});
                        validatorResults.addValidatorResult(new ValidatorResult(ValidatorResult.Level.ERROR, msg));
                    }
                }
            }
        }
        return validatorResults;
    }

    /* used by mapRule5() */
    private String getLinage(CloneDatatypeFieldMeta cloneDatatypeFieldMeta){
        String linage = cloneDatatypeFieldMeta.getName();
        MetaObject parentMetaObject = cloneDatatypeFieldMeta.getParentMeta();
        while(parentMetaObject!=null){
            linage = parentMetaObject.getName() + "."+ linage;
            if(parentMetaObject instanceof CloneAttributeMeta){
                parentMetaObject = ((CloneAttributeMeta)parentMetaObject).getParentMeta();
            }else if(parentMetaObject instanceof CloneMeta){
                parentMetaObject = ((CloneMeta)parentMetaObject).getParentMeta();
            }else{
                parentMetaObject = null;
                System.out.println("ERROR??");
            }
        }

        return linage;
    }

    /**
     * input parameter for function in map has no mapping and no constant defined
     *
     * @return ValidatorResults
     */
    public ValidatorResults mapRule6() {
        ValidatorResults validatorResults = new ValidatorResults();
        List<FunctionComponent> functionComponents = ((Mapping) toBeValidatedObject).getFunctionComponent();
        // itereate the functions.
        for (int i = 0; i < functionComponents.size(); i++) {
            FunctionComponent functionComponent = functionComponents.get(i);
            FunctionMeta functionMeta = functionComponent.getMeta();
            List<ParameterMeta> parameterMetas = functionMeta.getInputDefinitionList();
            // iterate the paremeters
            for (int j = 0; j < parameterMetas.size(); j++) {
                ParameterMeta parameterMeta = parameterMetas.get(j);
                List<Map> maps = helper.findMapsAssociatedWithMetaObject(functionComponent.getUUID(), parameterMeta.getUUID(), Config.MAP_COMPONENT_TARGET_TYPE);
                // does this paremeter have a map?
                if (maps.size() == 0) {
                    Message msg = MessageResources.getMessage("MAP6", new Object[]{functionMeta.getName(), parameterMeta.getName()});
                    validatorResults.addValidatorResult(new ValidatorResult(ValidatorResult.Level.ERROR, msg));
                }
            }
        }
        return validatorResults;
    }

    /**
     * output parameter for function in map has no mapping and no constant defined
     *
     * @return ValidatorResults
     */
    public ValidatorResults mapRule7() {
        ValidatorResults validatorResults = new ValidatorResults();
        List<FunctionComponent> functionComponents = ((Mapping) toBeValidatedObject).getFunctionComponent();
        // itereate the functions.
        for (int i = 0; i < functionComponents.size(); i++) {
            FunctionComponent functionComponent = functionComponents.get(i);
            FunctionMeta functionMeta = functionComponent.getMeta();
            List<ParameterMeta> parameterMetas = functionMeta.getOuputDefinitionList();
            // iterate the paremeters
            for (int j = 0; j < parameterMetas.size(); j++) {
                ParameterMeta parameterMeta = parameterMetas.get(j);
                List<Map> maps = helper.findMapsAssociatedWithMetaObject(functionComponent.getUUID(), parameterMeta.getUUID(), Config.MAP_COMPONENT_SOURCE_TYPE);
                // does this paremeter have a map?
                if (maps.size() == 0) {
                    Message msg = MessageResources.getMessage("MAP7", new Object[]{functionMeta.getName(), parameterMeta.getName()});
                    validatorResults.addValidatorResult(new ValidatorResult(ValidatorResult.Level.ERROR, msg));
                }
            }
        }
        return validatorResults;
    }

    /**
     * Destination field has both a mapping and a user-defined default value
     *
     * @return ValidatorResults
     */
    public ValidatorResults mapRule8() {
        ValidatorResults validatorResults = new ValidatorResults();
        return validatorResults;
    }

    /**
     * Source field not mapped to anything
     *
     * @return ValidatorResults
     */
    public ValidatorResults mapRule9() {
        ValidatorResults validatorResults = new ValidatorResults();
        BaseComponent sourceComponent = ((Mapping) toBeValidatedObject).getSourceComponent();
        CSVMeta m = (CSVMeta) sourceComponent.getMeta();

        List<CSVFieldMeta> csvFieldMetas = helper.findAllCsvFields(m.getRootSegment(), true);
        for (int i = 0; i < csvFieldMetas.size(); i++) {
            CSVFieldMeta csvFieldMeta = csvFieldMetas.get(i);
            List<Map> maps = helper.findMapsAssociatedWithMetaObject(sourceComponent.getUUID(), csvFieldMeta.getUUID(), sourceComponent.getType());
            if (maps.size() == 0) {
                Message msg = MessageResources.getMessage("MAP9", new Object[]{csvFieldMeta.getSegmentName(), csvFieldMeta.getName()});
                validatorResults.addValidatorResult(new ValidatorResult(ValidatorResult.Level.WARNING, msg));
            }
        }
        return validatorResults;
    }

    public ValidatorResults mapRule10() {
        ValidatorResults validatorResults = new ValidatorResults();
        if (((Mapping) toBeValidatedObject).getSourceComponent() == null) {
            Message msg = MessageResources.getMessage("MAP10", new Object[]{"Source"});
            validatorResults.addValidatorResult(new ValidatorResult(ValidatorResult.Level.FATAL, msg));
        }
        if (((Mapping) toBeValidatedObject).getTargetComponent() == null) {
            Message msg = MessageResources.getMessage("MAP10", new Object[]{"Target"});
            validatorResults.addValidatorResult(new ValidatorResult(ValidatorResult.Level.FATAL, msg));
        }
        return validatorResults;
    }


    public ValidatorResults mapRule11() {
        ValidatorResults validatorResults = new ValidatorResults();
        Mapping mapping = (Mapping) toBeValidatedObject;

        int mapSize = mapping.getMaps().size();
        if (mapSize == 0) {
            Message msg = MessageResources.getMessage("MAP11", new Object[]{"empty mapfile"});
            validatorResults.addValidatorResult(new ValidatorResult(ValidatorResult.Level.FATAL, msg));
        }
        return validatorResults;
    }

    /**
     * Core attributes cannot have maps.
     *
     * @return the validatorResults
     */
    public ValidatorResults mapRule12() {
        ValidatorResults validatorResults = new ValidatorResults();
        List<Map> allMaps = ((Mapping) toBeValidatedObject).getMaps();
        for (int i = 0; i < allMaps.size(); i++) {
            Map map = allMaps.get(i);
            MetaObject targetMetaObject = map.getTargetMapElement().getMetaObject();
            validatorResults.addValidatorResults(MapLinkValidator.isMetaObjectMappable(targetMetaObject));
        }

        return validatorResults;
    }

    public ValidatorResults mapRule13() {
        ValidatorResults validatorResults = new ValidatorResults();
        Mapping mapping = (Mapping) toBeValidatedObject;
        List<FunctionComponent> functionComponents = mapping.getFunctionComponent();
        for (int i = 0; i < functionComponents.size(); i++) {
            FunctionComponent functionComponent = functionComponents.get(i);
            try {
                List<CSVSegmentMeta> csvSegmentMetas = null;
                csvSegmentMetas = helper.findAllMappedSegments(functionComponent.getMeta(), functionComponent.getUUID());

                // get rid of duplicates.
                HashSet<CSVSegmentMeta> set = new HashSet<CSVSegmentMeta>(csvSegmentMetas);
                if (set.size() > 1) {
                    Message msg = MessageResources.getMessage("MAP13", new Object[]{functionComponent.getMeta().getName()});
                    validatorResults.addValidatorResult(new ValidatorResult(ValidatorResult.Level.WARNING, msg));
                }
            } catch (MappingException e) {
                Message msg = MessageResources.getMessage("GEN0", new Object[]{e.getMessage()});
                validatorResults.addValidatorResult(new ValidatorResult(ValidatorResult.Level.ERROR, msg));
            }
        }

        return validatorResults;
    }
}
