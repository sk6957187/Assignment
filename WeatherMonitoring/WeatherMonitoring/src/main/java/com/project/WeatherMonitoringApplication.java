package com.project;

import com.project.controller.WeatherResource;
import com.project.filters.RequestFilter;
import com.project.filters.ResponseFilter;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeatherMonitoringApplication extends Application<WeatherMonitoringConfiguration> {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherMonitoringApplication.class);

    @Override
    public void initialize(Bootstrap<WeatherMonitoringConfiguration> bootstrap) {
        super.initialize(bootstrap);
        bootstrap.addBundle(new ViewBundle<>());
        bootstrap.addBundle(new AssetsBundle("/assets/", "/assets"));
    }

    @Override
    public void run(WeatherMonitoringConfiguration configuration, Environment environment) throws Exception {
        LOGGER.info("Registering REST resources: {}", configuration);

        environment.jersey().register(new ResponseFilter());
        environment.jersey().register(new RequestFilter());
        environment.jersey().register(new WeatherResource(configuration));
    }
    public static void main(String[] args) throws Exception {
        new WeatherMonitoringApplication().run(args);
    }
}
