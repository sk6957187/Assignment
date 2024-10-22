package com.project.controller;

import com.project.WeatherMonitoringConfiguration;
import com.project.model.WeatherDTO;
import com.project.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.HashMap;

@Path("/api/weather")
@Produces(MediaType.APPLICATION_JSON)
public class WeatherResource {
    private static final Logger logger = LoggerFactory.getLogger(WeatherResource.class);

    private final WeatherService weatherService;

    public WeatherResource(WeatherMonitoringConfiguration weatherMonitoringConfiguration) {
        this.weatherService = new WeatherService(weatherMonitoringConfiguration);
    }

    @GET
    @Path("/current/{city}")
    public WeatherDTO getCurrentWeather(@PathParam("city") String city) {
        String ct = city.toUpperCase();
        return weatherService.getWeather(ct);
    }

    @GET
    @Path("/daily-summary/{city}")
    public ArrayList<ArrayList<String>> getDailySummary(@PathParam("city") String city) {
        String ct = city.toUpperCase();
        return weatherService.getDailySummary(ct);
    }

    @POST
    @Path("/set-alert/{city}")
    public String setAlert(@PathParam("city") String city, HashMap<String, String> userData) {
        logger.info("setAlert: City: {}, Threshold: {}", city, userData);
        double thresholdTemp = weatherService.getThresholdTempFromUserInput(userData);
        if (city != null) {
            city = city.toUpperCase();
        }
        return weatherService.setAlert(city, thresholdTemp);
    }
}
