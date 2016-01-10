package net.goodtwist.dev.grunt.core;

import java.util.LinkedList;
import java.util.List;

public class ResponseEntity {
	
	private Object content;
	private List<String> errorMessages;

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public List<String> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}
	
	public void addErrorMessage(String newError){
		if (this.errorMessages == null){
			errorMessages = new LinkedList<>();
		}
		this.errorMessages.add(newError);
	}
}
