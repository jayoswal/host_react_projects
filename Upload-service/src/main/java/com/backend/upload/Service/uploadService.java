package com.backend.upload.Service;

import java.io.File;
import java.net.URL;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.springframework.stereotype.Service;

import com.backend.upload.payloads.ApiResponse;

@Service
public class uploadService {
	
public ApiResponse createService(String url, File directory) throws InvalidRemoteException, TransportException, GitAPIException
{
	
	Git git = Git.cloneRepository()
			  .setURI(url)
			  .setDirectory(directory)
			  .call();
	System.out.println(url);
	return new ApiResponse("Success!", true);
	
}

// .setCredentialsProvider( new UsernamePasswordCredentialsProvider( "user", "password" ))
}
