package gov.nih.nci.cadsr.formloader.repository;

import gov.nih.nci.ncicb.cadsr.common.dto.DataElementTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.QuestionTransferObject;
import gov.nih.nci.ncicb.cadsr.common.resource.FormV2;
import gov.nih.nci.ncicb.cadsr.common.resource.PermissibleValueV2;
import gov.nih.nci.ncicb.cadsr.common.resource.ReferenceDocument;

import java.util.List;

public interface FormLoaderRepository {
	public List<FormV2> getFormsForPublicIDs(List<String> pubicIDList);
	public List<QuestionTransferObject> getQuestionsByPublicId(String publicId);
	public List<DataElementTransferObject> getCDEByPublicId(String cdePublicId);
	public List<ReferenceDocument> getReferenceDocsForQuestionCde(String cdePublicId, String cdeVersion);
	public List<PermissibleValueV2> getValueDomainPermissibleValuesByVdId(String vdSeqId);

}
