package gov.nih.nci.cadsr.formloader.domain;

import java.util.List;

public class FormStatus {
	
	protected int type;
	protected List<String> messages;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public List<String> getMessages() {
		return messages;
	}
	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

}
