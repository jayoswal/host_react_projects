package com.backend.upload.controller;

import java.io.File;
import java.net.URL;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.upload.Service.uploadService;
import com.backend.upload.payloads.ApiResponse;

@RequestMapping
@RestController
public class UploadController {
	@Autowired
	uploadService uploadService;
	
	@PostMapping("/upload")
	ResponseEntity<ApiResponse>createGitRepo(@RequestBody String url) throws InvalidRemoteException, TransportException, GitAPIException
	{
		
		ApiResponse service = uploadService.createService(url, new File(System.getProperty("user.dir")+"/new6"));
		return new ResponseEntity<ApiResponse>(service,HttpStatus.OK);
	}
	
	
	

}
