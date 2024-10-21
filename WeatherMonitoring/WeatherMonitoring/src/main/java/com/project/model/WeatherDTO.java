package com.project.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeatherDTO {
    private double temp;
    private double feelsLike;
    private String condition;
    private long timestamp;
    private double pressure;
    private double humidity;
    private double seaLevel;
    private double visibility;
    private double windSpeed;
    private static final Logger logger = LoggerFactory.getLogger(WeatherDTO.class);

    public WeatherDTO(double temp, double feelsLike, String condition, long timestamp, double pressure, double humidity, double seaLevel, double visibility, double windSpeed) {

        this.temp = temp;
        this.feelsLike = feelsLike;
        this.condition = condition;
        this.timestamp = timestamp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.seaLevel = seaLevel;
        this.visibility = visibility;
        this.windSpeed = windSpeed;
    }
    public double getTemp() {
        return temp;
    }
    public void setTemp(double temp) {
        logger.info("temp {}",temp);
        this.temp = temp;
    }
    public double getFeelsLike() {
        return feelsLike;
    }
    public void setFeelsLike(double feelsLike) {
        logger.info("feelsLike {}",feelsLike);
        this.feelsLike = feelsLike;
    }
    public String getCondition() {
        return condition;
    }
    public void setCondition(String condition) {
        logger.info("condition {}",condition);
        this.condition = condition;
    }
    public long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(long timestamp) {
        logger.info("setFeelsLike {}",feelsLike);
        this.timestamp = timestamp;
    }
    public double getPressure() {
        return pressure;
    }
    public void setPressure(double pressure) {
        logger.info("pressure {}",pressure);
        this.pressure = pressure;
    }
    public double getHumidity() {
        return humidity;
    }
    public void setHumidity(int humidity) {
        logger.info("humidity {}",humidity);
        this.humidity = humidity;
    }
    public double getSeaLevel() {
        return seaLevel;
    }
    public void setSeaLevel(int seaLevel) {
        logger.info("seaLevel {}",seaLevel);
        this.seaLevel = seaLevel;
    }
    public double getVisibility() {
        return visibility;
    }
    public void setVisibility(int visibility) {
        logger.info("visibility {}",visibility);
        this.visibility = visibility;
    }
    public double getWindSpeed() {
        return windSpeed;
    }
    public void setWindSpeed(double windSpeed) {
        logger.info("windSpeed {}",windSpeed);
        this.windSpeed = windSpeed;
    }
    @Override
    public String toString() {
        return "WeatherDTO{" +
                "temp=" + temp +
                ", feelsLike=" + feelsLike +
                ", condition='" + condition + '\'' +
                ", timestamp=" + timestamp +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", seaLevel=" + seaLevel +
                ", visibility=" + visibility +
                ", windSpeed=" + windSpeed +
                '}';
    }
}
