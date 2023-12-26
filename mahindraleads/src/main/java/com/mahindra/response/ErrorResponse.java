package com.mahindra.response;

public class ErrorResponse {
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ErrorDetails getErrorResponse() {
		return errorResponse;
	}

	public void setErrorResponse(ErrorDetails errorResponse) {
		this.errorResponse = errorResponse;
	}

	private ErrorDetails errorResponse;

	public ErrorResponse(String status, ErrorDetails errorResponse) {
		this.status = status;
		this.errorResponse = errorResponse;
	}
}
