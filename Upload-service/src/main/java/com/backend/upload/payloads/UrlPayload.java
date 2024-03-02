package com.backend.upload.payloads;

public class UrlPayload {
private String Url;

public String getUrl() {
	return Url;
}

public void setUrl(String url) {
	Url = url;
}

public UrlPayload(String url) {
	super();
	Url = url;
}
}
