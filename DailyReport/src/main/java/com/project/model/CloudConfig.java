package com.project.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CloudConfig {
    private static final Logger logger = LoggerFactory.getLogger(CloudConfig.class);
    private  String folderId;
    private String applicationName;
    private String fileDirectory;

    public String getFolderId() {
        logger.info("folderId: {}",folderId);
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    public String getApplicationName() {
        logger.info("Application name: {}", applicationName);
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getFileDirectory() {
        logger.info("File directory: {}", fileDirectory);
        return fileDirectory;
    }

    public void setFileDirectory(String fileDirectry) {
        this.fileDirectory = fileDirectry;
    }
}
