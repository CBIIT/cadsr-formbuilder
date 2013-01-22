package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormConverterUtil;

import gov.nih.nci.ncicb.cadsr.common.CommonNavigationConstants;
import gov.nih.nci.ncicb.cadsr.common.formbuilder.common.FormBuilderConstants;
import gov.nih.nci.ncicb.cadsr.common.formbuilder.struts.common.FormConstants;
import gov.nih.nci.ncicb.cadsr.common.resource.FormV2;
import gov.nih.nci.ncicb.cadsr.common.util.CDEBrowserParams;
import gov.nih.nci.ncicb.cadsr.common.util.logging.Log;
import gov.nih.nci.ncicb.cadsr.common.util.logging.LogFactory;
import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.ServiceDelegateFactory;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.ServiceStartupException;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class FormDownloadXMLAction extends Action {
	private static Log log = LogFactory.getLog(FormDownloadXMLAction.class.getName());


	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		String formIdSeq = (String)request.getParameter(FormConstants.FORM_ID_SEQ);

		FormBuilderServiceDelegate service = getFormBuilderService();
		FormV2 crf = null;

		try {
			crf = service.getFormDetailsV2(formIdSeq);
		} catch (FormBuilderException exp) {
			log.error("Exception getting CRF", exp);
			return mapping.findForward(CommonNavigationConstants.FAILURE);
		}

		String convertedForm = FormConverterUtil.instance().convertFormToV2(crf);  

		CDEBrowserParams params = CDEBrowserParams.getInstance();
		String xmlFilename ="Form"  + crf.getPublicId() + "_v" + crf.getVersion();
		xmlFilename = xmlFilename.replace('/', '_').replace('.', '_');
		xmlFilename = params.getXMLDownloadDir() + xmlFilename + ".xml";

		FileOutputStream fileOut = new FileOutputStream(xmlFilename);
		byte[] xmlBytes = convertedForm.getBytes();
		fileOut.write(xmlBytes);
		fileOut.flush();
		fileOut.close();

		request.setAttribute("fileName", xmlFilename);
		return mapping.findForward("downloadSuccess");
	}

	/**
	 * Gets the ServiceDelegateFactory form the application scope and
	 * instantiates a FormBuilderServiceDelegate from the factory
	 *
	 * @return FormBuilderServiceDelegate
	 *
	 * @throws ServiceStartupException
	 */
	protected FormBuilderServiceDelegate getFormBuilderService()
	throws ServiceStartupException {
		FormBuilderServiceDelegate svcDelegate = null;
		ServiceDelegateFactory svcFactory =
			(ServiceDelegateFactory) servlet.getServletContext().getAttribute(
					FormBuilderConstants.SERVICE_DELEGATE_FACTORY_KEY);
		//svcDelegate = svcFactory.createService();
		svcDelegate = svcFactory.findService();

		return svcDelegate;
	}

}
