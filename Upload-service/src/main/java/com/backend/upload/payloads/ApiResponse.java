package com.backend.upload.payloads;

public class ApiResponse {
private String Response;
private boolean status;
public String getResponse() {
	return Response;
}
public void setResponse(String response) {
	Response = response;
}
public boolean isStatus() {
	return status;
}
public void setStatus(boolean status) {
	this.status = status;
}

public ApiResponse(String response, boolean status) {
	super();
	Response = response;
	this.status = status;
}
}
