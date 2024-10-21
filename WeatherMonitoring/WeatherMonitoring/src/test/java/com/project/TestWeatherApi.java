package com.project;

import com.project.Repository.WeatherRepository;
import com.project.model.TemperatureConversion;
import com.project.service.WeatherService;
import org.junit.Test;
import static org.junit.Assert.*;

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
        weatherRepository.getDailySummary("Gaya");
    }
    @Test
    public void TestSetAlert(){
        WeatherRepository weatherRepository = new WeatherRepository(this.getAppConfig().getWeatherApiConfig());
        String rs = weatherRepository.setAlert("Delhi", 21);
        System.out.println(rs);
    }
    @Test
    public void testTemperatureConversion(){
        /*
        TemperatureConversion temperatureConversion = new TemperatureConversion();
        double tempInCelcious =
        double rs = temperatureConversion.celliusToKel(tempInCelcious);
        System.out.println("Temp in kel:- "+rs+"k");

        rs = temperatureConversion.celliusToFran(tempInCelcious);
        System.out.println("Temp in Fahrenheit: "+ rs+"°F");
        */
    }
}
