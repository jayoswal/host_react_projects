package com.backend.deploy.service;

import org.springframework.stereotype.Service;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.FileList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class GoogleStorageService {

	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	private static final String SERVICE_ACOUNT_KEY_PATH = getPathToGoodleCredentials();

	private static String getPathToGoodleCredentials() {
		String currentDirectory = System.getProperty("user.dir");
		Path filePath = Paths.get(currentDirectory, "cred.json");
		return filePath.toString();
	}

	public boolean downloadZipFromGDrive(String id) {

		try {
			Drive drive = createDriveService();

			// Search for the file by name
			String query = "name='" + id + ".zip" + "' and trashed=false";
			FileList result = drive.files().list().setQ(query).execute();
			if (result.getFiles().isEmpty()) {
				return false;
			}

			String fileId = result.getFiles().get(0).getId(); // Get the ID of the first file with the specified name

			String downloadZipPath = System.getProperty("user.dir") + File.separatorChar + "repoZip"
					+ File.separatorChar + id + ".zip";

			// Create the directory if it doesn't exist
			Files.createDirectories(Paths.get(downloadZipPath).getParent());

			FileOutputStream outputStream = new FileOutputStream(downloadZipPath);
			drive.files().get(fileId).executeMediaAndDownloadTo(outputStream);
			outputStream.close();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

//	public String getfiles() throws IOException, GeneralSecurityException {
//
//		Drive service = createDriveService();
//
//		// Print the names and IDs for up to 10 files.
//		FileList result = service.files().list().setPageSize(10).execute();
//		java.util.List<com.google.api.services.drive.model.File> files = result.getFiles();
//		if (files == null || files.isEmpty()) {
//			return "No files found.";
//		} else {
//			return files.toString();
//		}
//	}

	public String uploadZipToGDrive(File file) throws GeneralSecurityException, IOException {

		try {
			String folderId = "";
			Drive drive = createDriveService();
			com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
			fileMetaData.setName(file.getName());
			fileMetaData.setParents(Collections.singletonList(folderId));
			FileContent mediaContent = new FileContent("application/zip", file);
			com.google.api.services.drive.model.File uploadedFile = drive.files().create(fileMetaData, mediaContent)
					.setFields("id").execute();
			String imageUrl = "https://drive.google.com/uc?export=view&id=" + uploadedFile.getId();
			file.delete();

			String message = "Zip Uploaded: " + imageUrl;
			return message;
		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
		return "failed...!!!";

	}

	private Drive createDriveService() throws GeneralSecurityException, IOException {

		GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream(SERVICE_ACOUNT_KEY_PATH))
				.createScoped(Collections.singleton(DriveScopes.DRIVE));

		return new Drive.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, credential)
				.setApplicationName("Host-React-Projects").build();

	}

}
