package gov.nih.nci.cadsr.formloader.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormHeader;
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
	public List<FormHeader> unloadForms(List<FormHeader> forms)
			throws FormLoaderServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
