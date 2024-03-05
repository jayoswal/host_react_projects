package com.backend.deploy.service;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.deploy.utils.UnzipUtility;

@Service
public class BuildService {

	@Autowired
	private GoogleStorageService googleStorageService;

	String downloadDirectoryPath = System.getProperty("user.dir") + File.separatorChar + "repoZip";

	public void downloadAndBuildAndDeploy(String id) throws IOException, GeneralSecurityException {

		if (downloadZipfromDrive(id)) {

			// TODO - unzip the file: downloadFilePath to location:
			String zipFilePath = downloadDirectoryPath + File.separatorChar + id + ".zip";
			UnzipUtility.unzipRepoDir(zipFilePath, id);
		}

	}

	public boolean downloadZipfromDrive(String id) throws IOException, GeneralSecurityException {

		return googleStorageService.downloadZipFromGDrive(id);
	}

	// TODO - unzip it to repoFiles/id

}
