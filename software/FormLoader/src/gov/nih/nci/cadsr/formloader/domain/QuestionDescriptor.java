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
	
	boolean isEditable;
	boolean isMandatory;
	
	//Needed for loader only
	boolean skip = false;
	List<String> messages = new ArrayList<String>();
	
	public void addMessage(String msg) {
		messages.add(msg);
	}
	
	public List<String> getMessages() {
		return messages;
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
			instruction += "; [FormLoader]:" + newInstruction;
	}

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public boolean isEditable() {
		return isEditable;
	}

	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}
	
	public void setEditable(String val) {
		if (val == null || val.length() == 0)
			setEditable(true); //should default to true?
		
		boolean yesno = (val.equalsIgnoreCase("yes") || val.equalsIgnoreCase("true")) ?
				true : false;
		
		setEditable(yesno);
	}

	public boolean isMandatory() {
		return isMandatory;
	}

	public void setMandatory(boolean isMandatory) {
		this.isMandatory = isMandatory;
	}
	
	public void setMandatory(String val) {
		if (val == null || val.length() == 0)
			setMandatory(true); //should default to true?
		
		boolean yesno = (val.equalsIgnoreCase("yes") || val.equalsIgnoreCase("true")) ?
				true : false;
		
		setMandatory(yesno);
	}





	public class ValidValue {
		String value;
		String meaningText;
		boolean skip = false;
		
		//These will come back from value domain permissible value query
		String vdPermissibleValueSeqid;
		
		//Denise: value meaning public id_quetionpublicid_form_public_id_version_<x> x = 1, 2, 3
		String preferredName; //This happens to be the value meaning's public id
		String perferredDefinition;
		String longName;
		String instruction;
		String description;
		
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
		public String getVdPermissibleValueSeqid() {
			return vdPermissibleValueSeqid;
		}
		public void setVdPermissibleValueSeqid(String vdPermissibleValueSeqid) {
			this.vdPermissibleValueSeqid = vdPermissibleValueSeqid;
		}
		
		public String getPreferredName() {
			return preferredName;
		}
		public void setPreferredName(String preferredName) {
			this.preferredName = preferredName;
		}
		
		/*
		public String getPerferredDefinition() {
			return perferredDefinition;
		}
		public void setPerferredDefinition(String perferredDefinition) {
			this.perferredDefinition = perferredDefinition;
		}
		*/
		/*
		public String getLongName() {
			return longName;
		}
		public void setLongName(String longName) {
			this.longName = longName;
		}
		*/
		public String getInstruction() {
			return instruction;
		}
		public void setInstruction(String instruction) {
			this.instruction = instruction;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		
		
	}

}
