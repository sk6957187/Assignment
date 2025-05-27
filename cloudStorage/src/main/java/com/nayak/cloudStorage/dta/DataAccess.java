package com.nayak.cloudStorage.dta;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.nayak.cloudStorage.model.BioData;
import com.nayak.cloudStorage.repositry.CloudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Component
public class DataAccess {
    private static final JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
    private static final String serviceAccountKeyPath = getPathToGoogleCredentials();
    private static final String APPLICATION_NAME = "CloudStorageApp";

    @Autowired
    private CloudRepository cloudRepository;

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

    public String uploadFile(File file) {
        String fileUrl = null;
        try {
            String folderId = "1cWMQFLwrB3xOoFQC2VD-Q6RO4hJEyqc8";
            Drive drive = createDriveService();

            com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
            fileMetaData.setName(file.getName());
            fileMetaData.setParents(Collections.singletonList(folderId));

            // Dynamically detect MIME type
            String mimeType = Files.probeContentType(file.toPath());
            if (mimeType == null) {
                mimeType = "application/octet-stream"; // Fallback for unknown types
            }

            FileContent mediaContent = new FileContent(mimeType, file);
            com.google.api.services.drive.model.File uploadedFile = drive
                    .files()
                    .create(fileMetaData, mediaContent)
                    .setFields("id")
                    .execute();

            if (mimeType != null && (mimeType.startsWith("video/") || mimeType.startsWith("audio/"))) {
                fileUrl = "https://drive.google.com/file/d/" + uploadedFile.getId() + "/preview";
            } else {
                fileUrl = "https://drive.google.com/uc?export=view&id=" + uploadedFile.getId();
            }
            System.out.println(mimeType+" File uploaded URL: " + fileUrl);
        } catch (Exception e) {
            return e.getMessage();
        } finally {
            file.delete(); // Caution: this deletes the uploaded file from your local system
        }
        return fileUrl;
    }

    public List<BioData> getBioData() {
        List<BioData> lb = cloudRepository.findAll();
        return lb;
    }

    public BioData saveBioData(BioData bd) {
        BioData newBd = cloudRepository.save(bd);
        return newBd;
    }
}
