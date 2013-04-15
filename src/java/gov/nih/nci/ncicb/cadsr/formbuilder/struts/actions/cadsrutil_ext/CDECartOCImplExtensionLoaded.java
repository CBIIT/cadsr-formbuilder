package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions.cadsrutil_ext;

// This class preloads the forms in CDECartOCImplExtension.
// (There was a performance problem because CDECartOCImplExtension is used as a bean, the jsp that uses the bean calls getForms repeatedly,
//   and forms are dynamically loaded when getForms is called.)

import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;
import gov.nih.nci.ncicb.cadsr.objectCart.CDECart;
import gov.nih.nci.objectCart.client.ObjectCartClient;
import java.io.Serializable;
import java.util.Collection;
import gov.nih.nci.ncicb.cadsr.common.util.logging.Log;
import gov.nih.nci.ncicb.cadsr.common.util.logging.LogFactory;


public class CDECartOCImplExtensionLoaded extends CDECartOCImplExtension implements CDECart, Serializable  {

	private static Log log = LogFactory.getLog(CDECartOCImplExtensionLoaded.class.getName());

	protected Collection forms;
	
	public CDECartOCImplExtensionLoaded(ObjectCartClient client, String uid, String cName, FormBuilderServiceDelegate formBuilderServiceDelegate) {
		super(client, uid, cName, formBuilderServiceDelegate);
		
		forms = super.getForms();
	}

	public Collection getForms() {
		return forms;
	}	

}
