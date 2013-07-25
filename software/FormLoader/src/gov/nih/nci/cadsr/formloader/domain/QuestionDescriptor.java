package gov.nih.nci.cadsr.formloader.domain;

import java.util.ArrayList;
import java.util.List;

public class QuestionDescriptor {
	
	String questionSeqId;
	String publicId;
	String version;
	
	String questionText;
	
	String cdeSeqId;
	String cdePublicId;
	String cdeVersion;
	
	List<ValidValue> validValues = new ArrayList<ValidValue>();
	String defaultValue;
	String instruction;
	
	boolean skip = false;
	List<String> messages = new ArrayList<String>();
	
	public void addMessage(String msg) {
		messages.add(msg);
	}
	
	public String getQuestionSeqId() {
		return questionSeqId;
	}

	public void setQuestionSeqId(String questionSeqId) {
		this.questionSeqId = questionSeqId;
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



	public String getQuestionText() {
		return questionText;
	}



	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}



	public String getCdeSeqId() {
		return cdeSeqId;
	}



	public void setCdeSeqId(String cdeSeqId) {
		this.cdeSeqId = cdeSeqId;
	}



	public String getCdePublicId() {
		return cdePublicId;
	}



	public void setCdePublicId(String cdePublicId) {
		this.cdePublicId = cdePublicId;
	}



	public String getCdeVersion() {
		return cdeVersion;
	}



	public void setCdeVersion(String cdeVersion) {
		this.cdeVersion = cdeVersion;
	}



	public List<ValidValue> getValidValues() {
		return validValues;
	}



	public void setValidValues(List<ValidValue> validValues) {
		this.validValues = validValues;
	}



	public String getDefaultValue() {
		return defaultValue;
	}



	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}



	public String getInstruction() {
		return instruction;
	}



	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public void addInstruction(String newInstruction) {
		if (instruction == null)
			instruction = newInstruction;
		else
			instruction += ";" + newInstruction;
	}

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}



	public class ValidValue {
		String value;
		String meaningText;
		boolean skip = false;
		
		public boolean isSkip() {
			return skip;
		}
		public void setSkip(boolean skip) {
			this.skip = skip;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getMeaningText() {
			return meaningText;
		}
		public void setMeaningText(String valueMeaning) {
			this.meaningText = valueMeaning;
		}
		
	}

}
