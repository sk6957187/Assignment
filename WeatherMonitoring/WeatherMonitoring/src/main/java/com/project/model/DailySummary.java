package com.project.model;


import java.util.List;

public class DailySummary {
    private double avgTemp;
    private double maxTemp;
    private double minTemp;
    private String dominantCondition;
    private List<WeatherDTO> hourlyData;

    public DailySummary(double avgTemp, double maxTemp, double minTemp, String dominantCondition) {
        this.avgTemp = avgTemp;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.dominantCondition = dominantCondition;
        this.hourlyData = hourlyData;
    }
    public double getAvgTemp() {
        return avgTemp;
    }
    public double getMaxTemp() {
        return maxTemp;
    }
    public double getMinTemp() {

        return minTemp;
    }
    public String getDominantCondition() {
        return dominantCondition;
    }
    public void setAvgTemp(double avgTemp) {
        this.avgTemp = avgTemp;
    }
    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }
    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }
    public void setDominantCondition(String dominantCondition) {
        this.dominantCondition = dominantCondition;
    }
    public void setHourlyData(List<WeatherDTO> hourlyData) {
        this.hourlyData = hourlyData;
    }
    public List<WeatherDTO> getHourlyData() {
        return hourlyData;
    }

    @Override
    public String toString() {
        return "DailySummary{" +
                "avgTemp=" + avgTemp +
                ", maxTemp=" + maxTemp +
                ", minTemp=" + minTemp +
                ", dominantCondition='" + dominantCondition + '\'' +
                ", hourlyData=" + hourlyData +
                '}';
    }
}

