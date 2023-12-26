package com.mahindra.response;

public class SuccessResponse {
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	private String data;

	public SuccessResponse(String data) {
		this.status = "success";
		this.data = data;
	}
}