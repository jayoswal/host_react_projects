package com.backend.upload.Service;

import java.io.File;
import java.io.FileInputStream;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.backend.upload.payloads.ApiResponse;
import com.backend.upload.payloads.UrlPayload;
import com.backend.upload.utils.generateId;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class uploadService {

	@Autowired
	private RedisQueueService redisQueueService;

	@Autowired
	private ZipDirectoryService zipDirectoryService;

	@Autowired
	private AwsS3Service amazon;

	public ApiResponse createService(UrlPayload url)
			throws InvalidRemoteException, TransportException, GitAPIException, IOException {
		String urlString = url.getUrl();
		String id = generateId.generateRandomId();

		// TODO - App Constants
		File directory = new File(
				System.getProperty("user.dir") + File.separatorChar + "repoFiles" + File.separatorChar + id);
		Git.cloneRepository().setURI(urlString).setDirectory(directory).call();
		// to clone from private repo
		// .setCredentialsProvider( new UsernamePasswordCredentialsProvider( "user",
		// "password" ))

		// TODO - zip the directory to /repoZip/id.zip
		File zipRepoFiles = new File(
				System.getProperty("user.dir") + File.separatorChar + "zipRepo" + File.separatorChar + id + ".zip");
		// TODO - find the parent directory where package.json is there ... because code
		// can be inside folder as well
		if (zipDirectoryService.zip(directory.getAbsolutePath(), zipRepoFiles.getAbsolutePath())) {

		}

//		System.out.println(org.apache.http.conn.ssl.SSLConnectionSocketFactory.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		// TODO - push zip to object storage (file: zipRepoFilesPath)
		InputStream inputStream = new FileInputStream(zipRepoFiles);
		String originalPath = "zipRepo" + File.separatorChar + zipRepoFiles.getName();

		final ObjectMetadata md = new ObjectMetadata();
		final AccessControlList acl = new AccessControlList();
		final byte[] buffer = inputStream.readAllBytes();
		final InputStream convertedInputStream = new ByteArrayInputStream(buffer);
		md.setContentLength(buffer.length);
		md.setContentType("application/zip");
		final PutObjectRequest req = new PutObjectRequest(this.amazon.getBucketName(), originalPath,
				convertedInputStream, md);
		acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
//        req.setAccessControlList(acl);
		PutObjectResult res = this.amazon.getS3client().putObject(req);

		// TODO -publish to redis queue
		redisQueueService.publish(id);

		return new ApiResponse(id, true);

	}

}
