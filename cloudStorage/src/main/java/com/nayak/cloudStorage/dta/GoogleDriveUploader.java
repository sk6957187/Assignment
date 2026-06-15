package com.nayak.cloudStorage.dta;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class GoogleDriveUploader {

    private static final String APPLICATION_NAME = "Daily Report App";
    private static final String CREDENTIALS_FILE_PATH = "credentials1.json"; // service account JSON
    private static final String FOLDER_ID = "11hcK4FOKeAl21QjjRs7xuP-WBF-XTri_";

    // Authorize (equivalent to JWT in Node.js)
    public static Drive authorize() throws IOException, GeneralSecurityException {

        GoogleCredentials credentials = GoogleCredentials
                .fromStream(new FileInputStream(CREDENTIALS_FILE_PATH))
                .createScoped(Collections.singleton(DriveScopes.DRIVE));

        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                new HttpCredentialsAdapter(credentials))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    // Upload file
    public static void uploadFile(Drive driveService) throws IOException {

        File fileMetadata = new File();
        fileMetadata.setName("mydrivetext.txt");
        fileMetadata.setParents(Collections.singletonList(FOLDER_ID));

        java.io.File filePath = new java.io.File("mydrivetext.txt");

        FileContent mediaContent = new FileContent("text/plain", filePath);

        File uploadedFile = driveService.files().create(fileMetadata, mediaContent)
                .setFields("id")
                .execute();

        System.out.println("File uploaded successfully. File ID: " + uploadedFile.getId());
    }

    public static void main(String[] args) {
        try {
            Drive driveService = authorize();
            uploadFile(driveService);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
