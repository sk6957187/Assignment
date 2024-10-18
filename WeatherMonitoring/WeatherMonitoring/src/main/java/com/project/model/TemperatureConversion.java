package com.project.model;

import com.project.Repository.WeatherRepository;

public class TemperatureConversion {
    WeatherRepository weatherRepository = new WeatherRepository();

    public double celliusToKel(String city){
        city = city.toUpperCase();
        double temp = weatherRepository.getTemp(city);
        return temp + 273.15;
    }
    public double celliusToFran(String city){
        city = city.toUpperCase();
        double temp = weatherRepository.getTemp(city);
        return (temp * 9/5) + 32;
    }
}
