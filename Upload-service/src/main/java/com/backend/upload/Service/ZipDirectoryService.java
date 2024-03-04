package com.backend.upload.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipOutputStream;

import org.springframework.stereotype.Service;

import com.backend.upload.utils.ZipDirectory;

@Service
public class ZipDirectoryService {

	public boolean zip(String sourceFile, String zipRepoFilesPath) {
		try {

			// Ensure parent directories exist
			File zipFile = new File(zipRepoFilesPath);
			zipFile.getParentFile().mkdirs();

			FileOutputStream fos = new FileOutputStream(zipRepoFilesPath);
			ZipOutputStream zipOut = new ZipOutputStream(fos);

			File fileToZip = new File(sourceFile);
			ZipDirectory.zipFile(fileToZip, fileToZip.getName(), zipOut);

			zipOut.close();
			fos.close();
			return true;
		} catch (FileNotFoundException e) {

			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;

		}

	}

}
