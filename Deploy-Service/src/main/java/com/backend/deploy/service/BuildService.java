package com.backend.deploy.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.backend.deploy.utils.UnzipUtility;

@Service
public class BuildService {

	@Autowired
	private AwsS3Service amazon;

	String downloadDirectoryPath = System.getProperty("user.dir") + File.separatorChar + "zipRepo";

	public void downloadAndBuildAndDeploy(String id) throws IOException {

		if (true) {

			// TODO - unzip the file: downloadFilePath to location:
			String zipFilePath = downloadDirectoryPath + File.separatorChar + id + ".zip";
			UnzipUtility.unzipRepoDir(zipFilePath, id);
		}

	}

	// TODO - Download the zip from aws to locally repoZip/id.zip
	public boolean downloadZip(String id) throws IOException {

		// Assuming this.amazon.getS3client() returns an instance of AmazonS3Client

		String originalPath = "zipRepo" + File.separatorChar + id + ".zip";

		S3Object s3Object = this.amazon.getS3client().getObject(this.amazon.getBucketName(), originalPath);
		S3ObjectInputStream inputStream = s3Object.getObjectContent();

		// Specify the path where you want to save the downloaded zip file

		// Create the directory if it doesn't exist
		Files.createDirectories(Paths.get(downloadDirectoryPath));

		// Specify the file path where you want to save the downloaded zip file

		String downloadFilePath = downloadDirectoryPath + File.separatorChar + id + ".zip";
		Files.copy(inputStream, Paths.get(downloadFilePath), StandardCopyOption.REPLACE_EXISTING);

		inputStream.close();
//		System.out.println("Zip Downloaded: " + downloadFilePath);

		// TODO -return false in try catch block
		return true;

	}

	// TODO - unzip it to repoFiles/id

}
