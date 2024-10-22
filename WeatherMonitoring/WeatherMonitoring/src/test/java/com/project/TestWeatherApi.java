package com.project;

import com.project.model.WeatherRepository;
import com.project.service.WeatherService;
import org.junit.Test;

public class TestWeatherApi {
    public WeatherMonitoringConfiguration getAppConfig() {
        String configPath = "A:/workspace/Assignment/WeatherMonitoring/WeatherMonitoring/meta-data/app_env_config_test.yml";
        WeatherMonitoringConfiguration configuration = WeatherService.getAppConfig(configPath);
        return configuration;
    }
    @Test
    public void TestWeatherApi() {
        WeatherMonitoringConfiguration configuration = this.getAppConfig();
        WeatherRepository weatherRepository = new WeatherRepository(configuration.getWeatherApiConfig(),configuration.getOracleSqlConfig());
        weatherRepository.fetchWeather("DElhi");
    }
    @Test
    public void TestWeatherData(){
        WeatherMonitoringConfiguration configuration = this.getAppConfig();
        WeatherRepository weatherRepository = new WeatherRepository(configuration.getWeatherApiConfig(),configuration.getOracleSqlConfig());
        weatherRepository.getDailySummary("DELHI");
    }
    @Test
    public void TestSetAlert(){
        WeatherMonitoringConfiguration configuration = this.getAppConfig();
        WeatherRepository weatherRepository = new WeatherRepository(configuration.getWeatherApiConfig(),configuration.getOracleSqlConfig());
        String rs = weatherRepository.setAlert("Delhi", 21);
        System.out.println(rs);
    }

}
