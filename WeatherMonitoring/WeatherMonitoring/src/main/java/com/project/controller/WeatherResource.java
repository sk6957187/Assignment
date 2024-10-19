package com.project.controller;

import com.project.model.WeatherDTO;
import com.project.service.WeatherService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("/api/weather")
@Produces(MediaType.APPLICATION_JSON)
public class WeatherResource {

    private final WeatherService weatherService;

    public WeatherResource(WeatherService weatherService) {
        this.weatherService = weatherService;
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
    public String setAlert(@QueryParam("city") String city, @QueryParam("threshold") double thresholdTemp) {
        city = city.toUpperCase();
        return weatherService.setAlert(city, thresholdTemp);
    }
}
