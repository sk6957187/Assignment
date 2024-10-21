package com.project;

import com.project.Repository.WeatherRepository;
import com.project.controller.WeatherResource;
import com.project.filters.RequestFilter;
import com.project.filters.ResponseFilter;
import com.project.service.WeatherService;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeatherMonitoringApplication extends Application<WeatherMonitoringConfiguration> {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherMonitoringApplication.class);
/*
    @Override
    public void initialize(Bootstrap<WeatherMonitoringConfiguration> bootstrap) {
        super.initialize(bootstrap);
        bootstrap.addBundle(new ViewBundle<>());
        bootstrap.addBundle(new AssetsBundle("/assets/", "/assets"));
    }
*/
    @Override
    public void run(WeatherMonitoringConfiguration configuration, Environment environment) throws Exception {
        LOGGER.info("Registering REST resources");

        environment.jersey().register(new ResponseFilter());
        environment.jersey().register(new RequestFilter());
        environment.jersey().register(new WeatherResource(configuration));
    }
    public static void main(String[] args) throws Exception {
        new WeatherMonitoringApplication().run(args);
    }
}
