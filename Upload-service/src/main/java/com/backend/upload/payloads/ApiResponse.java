package com.backend.upload.payloads;

public class ApiResponse {
	private String Response;
	private boolean status;

	public String getResponse() {
		return this.Response;
	}

	public void setResponse(String response) {
		this.Response = response;
	}

	public boolean isStatus() {
		return this.status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public ApiResponse(String response, boolean status) {
		super();
		this.Response = response;
		this.status = status;
	}
}
