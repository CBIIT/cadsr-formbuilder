package gov.nih.nci.cadsr.formloader.domain;

import java.util.ArrayList;
import java.util.List;

public class ModuleDescriptor {
	String moduleSeqId;
	String publicId;
	String version;
	
	List<QuestionDescriptor> questions = new ArrayList<QuestionDescriptor>();

	public String getModuleSeqId() {
		return moduleSeqId;
	}

	public void setModuleSeqId(String moduleSeqId) {
		this.moduleSeqId = moduleSeqId;
	}

	
	public String getPublicId() {
		return publicId;
	}

	public void setPublicId(String publicId) {
		this.publicId = publicId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<QuestionDescriptor> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionDescriptor> questions) {
		this.questions = questions;
	}

	
}
