package com.nayak.googleAnalytics.service;

import com.nayak.googleAnalytics.configuration.Config;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ServiceClass {

    private final Config config;

    public ServiceClass(Config config) {
        this.config = config;
    }

    public Object readmePage() {
        String filePath = config.getReadmeFilePath();

        try {
            Path path = Path.of(filePath);

            if (!Files.exists(path)) {
                return "Error: README file not found at " + filePath;
            }

            if (!Files.isRegularFile(path)) {
                return "Error: Path exists but is not a regular file: " + filePath;
            }

            return Files.readString(path);

        } catch (IOException e) {
            return "Error reading README file: " + e.getMessage();
        } catch (Exception e) {
            return "Unexpected error: " + e.getMessage();
        }
    }

}
