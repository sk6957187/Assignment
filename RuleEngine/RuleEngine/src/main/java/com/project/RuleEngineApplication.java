package com.project;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuleEngineApplication extends Application<RuleEngineConfiguration> {
    private static final Logger logger = LoggerFactory.getLogger(RuleEngineApplication.class);
    @Override
    public void initialize(Bootstrap<RuleEngineConfiguration> bootstrap) {
        super.initialize(bootstrap);
        bootstrap.addBundle(new ViewBundle<>());
        bootstrap.addBundle(new AssetsBundle("/assets/", "/assets"));
    }
    @Override
    public void run(RuleEngineConfiguration mvcConfiguration, Environment environment) throws Exception {
        logger.info("Application started.");
    }
    public static void main(String[] args) throws Exception {
        String server = "server";
        new RuleEngineApplication().run(server);
    }
}
