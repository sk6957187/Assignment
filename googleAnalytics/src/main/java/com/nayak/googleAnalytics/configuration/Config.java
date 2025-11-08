package com.nayak.googleAnalytics.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Value("${REACT_APP_GA_MEASUREMENT_ID}")
    private String googlrAnalyticsMeasurementId;

    @Value("${README_FILE_PATH}")
    private String readmeFilePath;

    public String getGooglrAnalyticsMeasurementId() {
        return googlrAnalyticsMeasurementId;
    }

    public String getReadmeFilePath(){
        return readmeFilePath;
    }
}
