package com.project.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.project.Repository.WeatherRepository;
import com.project.WeatherMonitoringConfiguration;
import com.project.model.WeatherDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class WeatherService {
    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);
    private final WeatherRepository weatherRepository;

    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public WeatherDTO getWeather(String city) {
        return weatherRepository.fetchWeather(city);
    }

    public ArrayList<ArrayList<String>> getDailySummary(String city) {
        return weatherRepository.getDailySummary(city);
    }

    public String setAlert(String city, double thresholdTemp) {
        return weatherRepository.setAlert(city, thresholdTemp);
    }
    public double getThresholdTempFromUserInput(HashMap<String, String> userData) {
        double thresholdTemp = 0.0;
        String threshold;
        if (userData != null) {
            threshold = userData.get("threshold");
            if (threshold != null) {
                try {
                    thresholdTemp = Double.parseDouble(threshold);
                } catch (Exception e) {
                    logger.info("Invalid threshold item in userData: {}", userData);
                }
            }
        }
        return thresholdTemp;
    }
    public static WeatherMonitoringConfiguration getAppConfig(String configPath) {
        if (configPath == null) {
            return null;
        }
        WeatherMonitoringConfiguration configuration = null;
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        try {
            configuration = objectMapper.readValue(new File(configPath), WeatherMonitoringConfiguration.class);
        } catch (IOException ioe) {
            logger.info("IOE: for file: " + configPath);
        }
        return configuration;
    }
}
