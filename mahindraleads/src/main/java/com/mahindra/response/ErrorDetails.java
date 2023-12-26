package com.mahindra.response;

import java.util.List;

public class ErrorDetails {
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	private List<String> messages;

	public ErrorDetails(String code, List<String> messages) {
		this.code = code;
		this.messages = messages;
	}
}
