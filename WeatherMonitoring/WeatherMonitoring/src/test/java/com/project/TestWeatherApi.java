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
        WeatherRepository weatherRepository = new WeatherRepository(this.getAppConfig().getWeatherApiConfig());
        weatherRepository.fetchWeather("DElhi");
    }
    @Test
    public void TestWeatherData(){
        WeatherRepository weatherRepository = new WeatherRepository(this.getAppConfig().getWeatherApiConfig());
        weatherRepository.getDailySummary("DELHI");
    }
    @Test
    public void TestSetAlert(){
        WeatherRepository weatherRepository = new WeatherRepository(this.getAppConfig().getWeatherApiConfig());
        String rs = weatherRepository.setAlert("Delhi", 21);
        System.out.println(rs);
    }

}
