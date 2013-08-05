/*L
 * Copyright Oracle Inc, ScenPro Inc, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
 */

package gov.nih.nci.cadsr.formloader.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import gov.nih.nci.cadsr.formloader.service.impl.ContentValidationServiceImpl;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.AbstractDAOFactoryV2;

public class FormLoaderRepository {
	private static Logger logger = Logger.getLogger(FormLoaderRepository.class.getName());
	
	
	protected AbstractDAOFactoryV2 daoFactory;

	public AbstractDAOFactoryV2 getDaoFactory() {
		return daoFactory;
	}

	public void setDaoFactory(AbstractDAOFactoryV2 daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	public HashMap<String, String> getExistingVersionsForPublicIDs(List<String> pubicIDList) {
		logger.debug("Not implemented");
		return new HashMap<String, String>();
	}
}
