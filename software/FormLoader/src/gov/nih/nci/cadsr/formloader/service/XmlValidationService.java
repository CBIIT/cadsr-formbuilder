/*L
 * Copyright Oracle Inc, ScenPro Inc, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
 */

package gov.nih.nci.cadsr.formloader.service;

import gov.nih.nci.cadsr.formloader.domain.FormHeader;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;

import java.util.List;

public interface XmlValidationService {
	
	public List<FormHeader> validateXml(String xmlPathName) 
			throws FormLoaderServiceException;

}
