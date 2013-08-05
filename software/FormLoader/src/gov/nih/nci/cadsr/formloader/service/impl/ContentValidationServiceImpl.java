/*L
 * Copyright Oracle Inc, ScenPro Inc, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
 */

package gov.nih.nci.cadsr.formloader.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormHeader;
import gov.nih.nci.cadsr.formloader.repository.FormLoaderRepository;
import gov.nih.nci.cadsr.formloader.service.ContentValidationService;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.AbstractDAOFactoryV2;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.FormDAO;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCFormDAO;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ServiceLocator;

@Service
public class ContentValidationServiceImpl implements ContentValidationService {
	
	private static Logger logger = Logger.getLogger(ContentValidationServiceImpl.class.getName());
	
	FormLoaderRepository repository;
	
	public ContentValidationServiceImpl() {}
	
	public ContentValidationServiceImpl(FormLoaderRepository repository) {
		this.repository = repository;
	}

	public FormLoaderRepository getRepository() {
		return repository;
	}

	public void setRepository(FormLoaderRepository repository) {
		this.repository = repository;
	}

	@Override
	public FormCollection validateXmlContent(FormCollection aCollection, String xmlPathName) 
			throws FormLoaderServiceException {
		
		
		
		List<FormHeader> formHeaders = aCollection.getForms();
		if (formHeaders == null || formHeaders.size() == 0) {
			logger.error("Input form list is null or empty. Nothing to do");
			return null;
		}
		
		determineLoadType(formHeaders);
		validateQuestions(formHeaders, xmlPathName);
		
		return aCollection;
	}
	
	protected void determineLoadType(List<FormHeader> formHeaders) {
		
		List<String> pidList = new ArrayList<String>();
		
		for (FormHeader form : formHeaders) {
			String publicid = form.getPublicID();
			if (publicid == null || publicid.length() == 0)
				form.setLoadType(FormHeader.LOAD_TYPE_NEW);
			else 
				pidList.add(publicid); //TODO: Could be duplicated ids
										//need handle: 2 forms with same public id but different versions
										//what if: 2 forms with same public id and empty version
		}
		
		HashMap<String, String> existingVersions = null;
		if (pidList.size() > 0) {
			existingVersions = repository.getExistingVersionsForPublicIDs(pidList);
		}
		
	
		determineLoadTypeOnVersions(formHeaders, existingVersions);
	
		/*
		ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(  
	            new String[] {"applicationContext-Common.xml"});  
		
		logger.debug("Bean Definition Count : " + appContext.getBeanDefinitionCount()); 
		DataSource dataSource = (DataSource) appContext.getBean("dataSource");
		
		FormDAO formDAO = daoFactory.getFormDAO();
		*/
		//formDAO.findFormByPrimaryKey("BlaBlaBla");
	}
	
	/**
	 * 
	 * @param formHeaders
	 * @param existingVersions comma delimited versions
	 */
	protected void determineLoadTypeOnVersions(List<FormHeader> formHeaders,
			HashMap<String, String> existingVersions) {

		// for duplicate check
		HashMap<String, String> processed = new HashMap<String, String>();

		for (FormHeader form : formHeaders) {
			String publicid = form.getPublicID();
			String version = form.getVersion();
			
			
			if (!existingVersions.containsKey(publicid)) // invalid public id from xml
				form.setLoadType(FormHeader.LOAD_TYPE_NEW);
			else if (version == null || version.length() == 0) {// invalid version from xml
				form.setLoadType(FormHeader.LOAD_TYPE_NEW_VERSION);
			} else {
				boolean matched = false;
				String versString = existingVersions.get(publicid);
				if (versString != null) {
					String[] versions = versString.split(",");
					for (String dbVersion : versions) {
						if (version.equalsIgnoreCase(dbVersion)) {
							form.setLoadType(FormHeader.LOAD_TYPE_UPDATE_FORM);
							matched = true;
							break;
						}
					}
				}
				if (!matched)
					form.setLoadType(FormHeader.LOAD_TYPE_NEW_VERSION);
			}

			//TODO: requirement not clear yet. candidate for duplicate check
			processed.put(publicid, version);
		}
	}
	
	protected boolean beenProcessed(HashMap<String, String> processed, String currPublicid, String currVersion) {
		if (processed == null || processed.size() == 0)
			return false;
		
		return processed.containsKey(currPublicid);
	
		//TODO: need clarification for duplicate
		/*  
		if (!processed.containsKey(currPublicid))
			return false;
		else {
			
			String processedVers = processed.get(currPublicid);
			if ((processedVers == null || processedVers.length() == 0) && 
					(currVersion == null || currVersion.length() == 0))
				return true;
			else
		
			
		}
		*/
		
	}

	public void validateQuestions(List<FormHeader> formHeaders, String xmlPathName) {
		
	}
}
