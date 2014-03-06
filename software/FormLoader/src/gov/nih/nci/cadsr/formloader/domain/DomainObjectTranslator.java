package gov.nih.nci.cadsr.formloader.domain;

import gov.nih.nci.cadsr.formloader.repository.FormLoaderRepositoryImpl;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderHelper;
import gov.nih.nci.ncicb.cadsr.common.dto.FormV2TransferObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

public class DomainObjectTranslator {
	
	private static Logger logger = Logger.getLogger(DomainObjectTranslator.class.getName());
	
	/**
	 * A form from database may have 2 rows because each additional protocol will trigger an additional form row added
	 * to database. This method will combine multiple rows for the same form into one form object, with comma separated
	 * protocol names.
	 * @param formdtos
	 * @return
	 */
	public static List<FormDescriptor> translateIntoFormDescriptors(FormCollection aColl, List<FormV2TransferObject> formdtos) {
		
		List<FormDescriptor> forms = new ArrayList<FormDescriptor>();
		
		if (formdtos == null) {
			logger.debug("Form dtos is null. Can't translater list into FormDescriptors");
			return forms;
		}
		
		HashMap<String, FormDescriptor> processedForms = new HashMap<String, FormDescriptor>();
		
		FormDescriptor form = null;
		for (FormV2TransferObject dto : formdtos) {
			if (processedForms.containsKey(dto.getFormIdseq())) {
				form = processedForms.get(dto.getFormIdseq());
				form.setProtocolName(dto.getProtocolLongName());
			}
			else {
				form  = new FormDescriptor();
				form.setFormSeqId(dto.getFormIdseq());
				form.setLongName(dto.getLongName());
				form.setContext(dto.getContextName());
				form.setModifiedBy(dto.getModifiedBy());
				form.setModifiedDate(dto.getDateModified());
				form.setCreatedBy(dto.getCreatedBy());
				form.setProtocolName(dto.getProtocolLongName());
				form.setPublicId(String.valueOf(dto.getPublicId()));
				form.setVersion(FormLoaderHelper.formatVersion(dto.getVersion()));
				form.setType(dto.getFormType());
				form.setWorkflowStatusName(dto.getAslName());
				form.setCollectionSeqid(aColl.getId());
				form.setCollectionName(aColl.getNameWithRepeatIndicator());
				forms.add(form);
				processedForms.put(dto.getFormIdseq(), form);
			}
		}
		
		return forms;
	}

}
