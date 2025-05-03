package com.nayak.cloudStorage.dta;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class DataAccess {
    private static final JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
    private static final String serviceAccountKeyPath = getPathToGoogleCredentials();
    private static final String APPLICATION_NAME = "CloudStorageApp";

    private static String getPathToGoogleCredentials() {
        String currentDirectory = System.getProperty("user.dir");
        Path filePath = Paths.get(currentDirectory, "googleKey.json");
        return filePath.toString();
    }
    private Drive createDriveService() throws GeneralSecurityException, IOException {
        GoogleCredential credential = GoogleCredential
                .fromStream(new FileInputStream(serviceAccountKeyPath))
                .createScoped(Collections.singletonList("https://www.googleapis.com/auth/drive"));

        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                jsonFactory,
                credential
        ).setApplicationName(APPLICATION_NAME)
                .build();
    }

    public  String uploadImage(File file) {
        String imageUrl = null;
        try {
            String folderId = "1cWMQFLwrB3xOoFQC2VD-Q6RO4hJEyqc8";
            Drive drive = createDriveService();

            com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
            fileMetaData.setName(file.getName());
            fileMetaData.setParents(Collections.singletonList(folderId));

            FileContent mediaContent = new FileContent("image/jpeg", file);
            com.google.api.services.drive.model.File uploadFile = drive
                    .files()
                    .create(fileMetaData, mediaContent)
                    .setFields("id")
                    .execute();

            imageUrl = "https://drive.google.com/uc?export=view&id=" + uploadFile.getId();
            System.out.println("Image url: " + imageUrl);
        }catch (Exception e){
            return e.getMessage();
        } finally {
            file.delete();
        }
        return imageUrl;
    }
}
