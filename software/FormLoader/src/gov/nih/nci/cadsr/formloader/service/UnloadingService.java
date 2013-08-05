/*L
 * Copyright Oracle Inc, ScenPro Inc, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
 */

package gov.nih.nci.cadsr.formloader.service;

import java.util.List;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormHeader;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;

public interface UnloadingService {
	public List<FormCollection> unloadCollections(List<FormCollection> collections) 
			throws FormLoaderServiceException;
	
	public List<FormHeader> unloadForms(List<FormHeader> forms) throws FormLoaderServiceException;

}
