package com.project.controller;

import com.project.WeatherMonitoringConfiguration;
import com.project.model.WeatherDTO;
import com.project.service.AppView;
import com.project.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class WeatherResource {
    private static final Logger logger = LoggerFactory.getLogger(WeatherResource.class);
    private final WeatherService weatherService;
    private final WeatherMonitoringConfiguration weatherMonitoringConfiguration;
    public WeatherResource(WeatherMonitoringConfiguration weatherMonitoringConfiguration) {
        this.weatherMonitoringConfiguration = weatherMonitoringConfiguration;
        this.weatherService = new WeatherService(weatherMonitoringConfiguration);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/app-view")
    public Response appView(@Context HttpServletRequest request) {
        logger.info("Loading app view url");
        return Response.ok( new AppView("app_view.ftl", weatherMonitoringConfiguration)).build();
    }
    @GET
    @Path("/api/weather/base-api")
    public Response getBaseApi() {
        String baseApi = weatherService.getUiBaseApi();
//        String baseApi = "http://localhost:8080";
        return Response.ok(baseApi).build();
    }

    @GET
    @Path("/api/weather/current/{city}")
    public WeatherDTO getCurrentWeather(@PathParam("city") String city) {
        String ct = city.toUpperCase();
        return weatherService.getWeather(ct);
    }

    @GET
    @Path("/api/weather/daily-summary/{city}")
    public ArrayList<ArrayList<String>> getDailySummary(@PathParam("city") String city) {
        String ct = city.toUpperCase();
        return weatherService.getDailySummary(ct);
    }

    @POST
    @Path("/api/weather/set-alert/{city}")
    public ArrayList<String> setAlert(@PathParam("city") String city, HashMap<String, String> userData) {
        logger.info("setAlert: City: {}, Threshold: {}", city, userData);
        double thresholdTemp = weatherService.getThresholdTempFromUserInput(userData);
        if (city != null) {
            city = city.toUpperCase();
        }
        return weatherService.setAlert(city, thresholdTemp);
    }

    @GET
    @Path("/favicon.ico")
    @Produces("image/x-icon")
    public Response LoadFaviconIcon() throws URISyntaxException {
        System.out.println("Favicon loaded");
        System.out.println();
        return Response.seeOther(new URI("/assets/favicon.ico")).build();
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("{default: .*}")
    public Response defaultMethod(@Context HttpServletRequest request) {
        logger.info("Loading default url");
        return Response.ok( new AppView("file_not_found.ftl", weatherMonitoringConfiguration)).build();
    }
}
