package gov.nih.nci.cadsr.formloader.service;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;

import java.util.List;

public interface CollectionRetrievalService {

	public List<FormCollection> getAllCollectionsByUser(String userName) throws FormLoaderServiceException;
}
