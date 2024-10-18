package com.project.model;

public class WeatherDTO {
    private double temp;
    private double feelsLike;
    private String condition;
    private long timestamp;
    public WeatherDTO(double temp, double feelsLike, String condition, long timestamp) {
        this.temp = temp;
        this.feelsLike = feelsLike;
        this.condition = condition;
        this.timestamp = timestamp;
    }
    public void setTemp(double temp) {
        this.temp = temp;
    }
    public void setFeelsLike(double feelsLike) {
        this.feelsLike = feelsLike;
    }
    public void setCondition(String condition) {
        this.condition = condition;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    public double getTemp() {
        return temp;
    }
    public double getFeelsLike() {
        return feelsLike;
    }

    public String getCondition() {
        return condition;
    }
    public long getTimestamp() {
        return timestamp;
    }
}
