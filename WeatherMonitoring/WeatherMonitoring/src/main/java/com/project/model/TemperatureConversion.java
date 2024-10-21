package com.project.model;

public class TemperatureConversion {

    public double celliusToKel(double tempInCelcious){
        return tempInCelcious + 273.15;
    }
    public double celliusToFran(double tempInCelcious){
        return (tempInCelcious * 9/5) + 32;
    }
}
