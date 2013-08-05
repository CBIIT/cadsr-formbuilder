/*L
 * Copyright Oracle Inc, ScenPro Inc, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
 */

package gov.nih.nci.cadsr.formloader.struts2;

import gov.nih.nci.cadsr.formloader.object.FormObj;
import gov.nih.nci.cadsr.formloader.service.LoadingFormMockupService;
import gov.nih.nci.cadsr.formloader.service.MiscMockupService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

public class LoadFormsAction extends ActionSupport implements ServletRequestAware{
		
		private List<String> loadingFormSummary;
		private HttpServletRequest servletRequest;
		    
		
		public String execute() {
			System.out.println("in LoadFormsAction.execute() ... ");
			int count =0;
			loadingFormSummary = new ArrayList<String>();
			List<FormObj> selectedFormsList = (List<FormObj>) servletRequest.getSession().getValue("selectedFormsList");
			
			if (selectedFormsList != null && selectedFormsList.size()>0){
						
				for (int i=0; i<selectedFormsList.size();i++) {
					FormObj form = selectedFormsList.get(i);
						
					String loadingFormSummary = LoadingFormMockupService.getInstance().loadForm(form);								
					this.loadingFormSummary.add(loadingFormSummary);
					
					count++;
				}
				
			}
			System.out.println(this.loadingFormSummary.size()+" forms loaded ...");
			if (count ==0) {
				this.loadingFormSummary.add("No Form selected, please select at least one checkbox");
				
			}

			return SUCCESS;
		}
		
		
		


		public List<String> getLoadingFormSummary() {
			return this.loadingFormSummary;
		
		}
		
		public void setLoadingFormSummary(List<String> summary) {
			this.loadingFormSummary = summary;
		}
		
	
	    public void setServletRequest(HttpServletRequest servletRequest) {
	        this.servletRequest = servletRequest;
	 
	    }
	}
