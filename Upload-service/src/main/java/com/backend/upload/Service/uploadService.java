package com.backend.upload.Service;

import java.io.File;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.upload.payloads.ApiResponse;
import com.backend.upload.payloads.UrlPayload;
import com.backend.upload.utils.generateId;

@Service
public class uploadService {

	@Autowired
	private RedisQueueService redisQueueService;

	public ApiResponse createService(UrlPayload url)
			throws InvalidRemoteException, TransportException, GitAPIException {
		String urlString = url.getUrl();
		String id = generateId.generateRandomId();

		// TODO - App Constants
		File directory = new File(
				System.getProperty("user.dir") + File.separatorChar + "repoFiles" + File.separatorChar + id);
		Git.cloneRepository().setURI(urlString).setDirectory(directory).call();
		// to clone from private repo
		// .setCredentialsProvider( new UsernamePasswordCredentialsProvider( "user",
		// "password" ))

		// TODO -publish to redis queue
		redisQueueService.publish(id);

		return new ApiResponse(id, true);

	}

}
