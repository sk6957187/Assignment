package com.project.model;

public class Alert {
    private String city;
    private double thresholdTemp;

    public Alert(String city, double thresholdTemp) {
        this.city = city;
        this.thresholdTemp = thresholdTemp;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setThresholdTemp(double thresholdTemp) {
        this.thresholdTemp = thresholdTemp;
    }
    public String getCity() {
        return city;
    }
    public double getThresholdTemp() {
        return thresholdTemp;
    }
    @Override
    public String toString() {
        return "Alert{" +
                "city='" + city + '\'' +
                ", thresholdTemp=" + thresholdTemp +
                '}';
    }
}

