package com.backend.upload.Service;

import java.io.File;
import java.net.URL;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.springframework.stereotype.Service;

import com.backend.upload.payloads.ApiResponse;
import com.backend.upload.payloads.UrlPayload;

@Service
public class uploadService {
	
public ApiResponse createService(UrlPayload url, File directory) throws InvalidRemoteException, TransportException, GitAPIException
{
	String urlString = url.getUrl();
	Git git = Git.cloneRepository()
			  .setURI(urlString)
			  .setDirectory(directory)
			  .call();
	System.out.println(url);
	return new ApiResponse("Success!", true);
	
}

// .setCredentialsProvider( new UsernamePasswordCredentialsProvider( "user", "password" ))
}
