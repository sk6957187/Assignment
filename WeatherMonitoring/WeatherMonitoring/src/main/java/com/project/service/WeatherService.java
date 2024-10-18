package com.project.service;


import com.project.Repository.WeatherRepository;
import com.project.model.WeatherDTO;
import java.util.ArrayList;

public class WeatherService {

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
}
