package com.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.obj.OracleSqlConfig;
import com.project.obj.UiConfig;
import com.project.obj.WeatherApiConfig;
import io.dropwizard.Configuration;

@JsonIgnoreProperties(ignoreUnknown = true)

public class WeatherMonitoringConfiguration extends Configuration {
    private WeatherApiConfig weatherApiConfig;
    private OracleSqlConfig oracleSqlConfig;
    private UiConfig uiConfig;

    public WeatherApiConfig getWeatherApiConfig() {
        return weatherApiConfig;
    }

    public void setWeatherApiConfig(WeatherApiConfig weatherApiConfig) {
        this.weatherApiConfig = weatherApiConfig;
    }

    public OracleSqlConfig getOracleSqlConfig() {
        return oracleSqlConfig;
    }

    public void setOracleSqlConfig(OracleSqlConfig oracleSqlConfig) {
        this.oracleSqlConfig = oracleSqlConfig;
    }

    public UiConfig getUiConfig() {
        return uiConfig;
    }

    public void setUiConfig(UiConfig uiConfig) {
        this.uiConfig = uiConfig;
    }

    @Override
    public String toString() {
        return "WeatherMonitoringConfiguration{" +
                "weatherApiConfig=" + weatherApiConfig +
                ", oracleSqlConfig=" + oracleSqlConfig +
                ", uiConfig=" + uiConfig +
                '}';
    }
}