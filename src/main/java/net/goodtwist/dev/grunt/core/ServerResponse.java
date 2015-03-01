package net.goodtwist.dev.grunt.core;

import java.util.List;

public class ServerResponse {
	
	private Object content;
	private boolean success;
	private List<String> errorMessages;

	public ServerResponse(){
		
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<String> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}
}
