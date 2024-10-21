package com.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.obj.WeatherApiConfig;
import io.dropwizard.Configuration;

@JsonIgnoreProperties(ignoreUnknown = true)

public class WeatherMonitoringConfiguration extends Configuration {
    private WeatherApiConfig weatherApiConfig;

    public WeatherApiConfig getWeatherApiConfig() {
        return weatherApiConfig;
    }

    public void setWeatherApiConfig(WeatherApiConfig weatherApiConfig) {
        this.weatherApiConfig = weatherApiConfig;
    }

    @Override
    public String toString() {
        return "WeatherMonitoringConfiguration{" +
                "weatherApiConfig=" + weatherApiConfig +
                '}';
    }
}
