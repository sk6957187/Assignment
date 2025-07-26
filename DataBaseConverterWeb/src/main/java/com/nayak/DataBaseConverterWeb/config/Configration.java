package com.nayak.DataBaseConverterWeb.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.config")
public class Configration {
    private String fileDestination;

    public String getFileDestination() {
        return fileDestination;
    }

    public void setFileDestination(String fileDestination) {
        this.fileDestination = fileDestination;
    }
}
