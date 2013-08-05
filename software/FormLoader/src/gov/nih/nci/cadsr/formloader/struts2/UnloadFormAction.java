/*L
 * Copyright Oracle Inc, ScenPro Inc, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
 */

package gov.nih.nci.cadsr.formloader.struts2;

import gov.nih.nci.cadsr.formloader.service.LoadingFormMockupService;
import gov.nih.nci.cadsr.formloader.service.MiscMockupService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.opensymphony.xwork2.ActionSupport;

public class UnloadFormAction extends ActionSupport{
	private Map<Integer, String> checkboxes;
	private Map<Integer, String> publicIds;
	private Map<Integer, String> versions;
	private List<String> unloadingSummary;
	
	public String execute() {
		System.out.println("in UnloadFormAction.execute() ... ");
		int count =0;
		unloadingSummary = new ArrayList<String>();
		if (checkboxes != null && checkboxes.size()>0){
					
			for (int i=0; i<checkboxes.size();i++) {
				Integer key = new Integer(i);
				if (checkboxes.containsKey(key)) {
					String checkboxValue = checkboxes.get(key).toString();
					if (checkboxValue.equals("true")) {
						String publicId = publicIds.get(key).toString();
						String version = versions.get(key).toString();
						
						String unloadingFormSummary = LoadingFormMockupService.getInstance().unloadForm(publicId, version);								
						this.unloadingSummary.add(unloadingFormSummary);
						
						
						count++;
					}
				}
			}
			
		}
		if (count ==0) {
			this.unloadingSummary.add("No Form selected, please select at least one checkbox");
			
		}

		
		return SUCCESS;
	}
	
	public Map<Integer, String> getPublicIds() {
		return publicIds;
	}
	public void setPublicIds(Map<Integer, String> publicIds) {
		this.publicIds = publicIds;
	}
	public Map<Integer, String> getVersions() {
		return versions;
	}
	public void setVersions(Map<Integer, String> versions) {
		this.versions = versions;
	}
	public Map<Integer, String> getCheckboxes() {
		return checkboxes;
	}
	public void setCheckboxes(Map<Integer, String> checkboxes) {
		this.checkboxes = checkboxes;
	}

	public List<String> getUnloadingSummary() {
		return this.unloadingSummary;
	
	}
	
	public void setUnloadingSummary(List<String> summary) {
		this.unloadingSummary = summary;
	}
}
