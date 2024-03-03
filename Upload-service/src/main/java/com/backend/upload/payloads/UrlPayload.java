package com.backend.upload.payloads;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
public class UrlPayload {
	private String Url;

	public String getUrl() {
		return this.Url;
	}

	public void setUrl(String url) {
		this.Url = url;
	}

	public UrlPayload(String url) {
		super();
		this.Url = url;
	}

	public UrlPayload() {
		super();
	}

}
