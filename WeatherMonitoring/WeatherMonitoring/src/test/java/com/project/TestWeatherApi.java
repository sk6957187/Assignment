package com.project;

import com.project.Repository.WeatherRepository;
import com.project.model.TemperatureConversion;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestWeatherApi {
    @Test
    public void TestWeatherApi() {
        WeatherRepository weatherRepository = new WeatherRepository();
        weatherRepository.fetchWeather("DElhi");
    }
    @Test
    public void TestWeatherData(){
        WeatherRepository weatherRepository = new WeatherRepository();
        weatherRepository.getDailySummary("Gaya");
    }
    @Test
    public void TestSetAlert(){
        WeatherRepository weatherRepository = new WeatherRepository();
        String rs = weatherRepository.setAlert("Delhi", 25);
        System.out.println(rs);
    }
    @Test
    public void testTemperatureConversion(){
        TemperatureConversion temperatureConversion = new TemperatureConversion();
        double rs = temperatureConversion.celliusToFran("Delhi");
        System.out.println(rs);
        rs = temperatureConversion.celliusToFran("Kolkata");
        System.out.println(rs);

    }
}
