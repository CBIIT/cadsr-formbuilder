/*L
 * Copyright Oracle Inc, ScenPro Inc, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
 */

package gov.nih.nci.cadsr.formloader.service;

import gov.nih.nci.cadsr.formloader.object.FormObj;

public interface LoadingFormServiceI {

	public String unloadForm(String publicId, String version);
	
	public String loadForm(FormObj form) ;
	
}
