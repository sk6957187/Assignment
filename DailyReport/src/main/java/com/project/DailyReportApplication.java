package com.project;

import com.project.controller.DailyReportController;
import com.project.filter.RequestFilter;
import com.project.filter.ResponseFilter;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DailyReportApplication extends Application<DailyReportConfiguration> {
    private static final Logger lOGGER = LoggerFactory.getLogger(DailyReportApplication.class);

    @Override
    public void initialize(Bootstrap<DailyReportConfiguration> bootstrap) {
        bootstrap.addBundle(new ViewBundle<>());
        bootstrap.addBundle(new AssetsBundle("/assets/", "/assets"));
    }

    @Override
    public void run(DailyReportConfiguration configuration, Environment environment) throws Exception {
        lOGGER.info("Registering REST resources: {}", configuration);
        environment.jersey().register(new ResponseFilter());
        environment.jersey().register(new RequestFilter());
        environment.jersey().register(new DailyReportController());
    }

    public static void main(String[] args) throws Exception {
        new DailyReportApplication().run(args);
    }
}