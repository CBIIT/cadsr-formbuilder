package gov.nih.nci.cadsr.formloader.service.impl;

import org.springframework.stereotype.Service;
import java.util.List;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.service.UnloadingService;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;

@Service
public class UnloadingServiceImpl implements UnloadingService {

	@Override
	public List<FormCollection> unloadCollections(
			List<FormCollection> collections) throws FormLoaderServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FormDescriptor> unloadForms(List<FormDescriptor> forms)
			throws FormLoaderServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
