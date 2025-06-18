package com.project;

import com.project.filters.RequestFilter;
import com.project.filters.ResponseFilter;
import com.project.resources.RuleEngineController;
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
    public void run(RuleEngineConfiguration ruleEngineConfiguration, Environment environment) throws Exception {
        logger.info("Application started.");
        RuleEngineController ruleEngineController = new RuleEngineController(ruleEngineConfiguration);
        environment.jersey().register(new RequestFilter());
        environment.jersey().register(new ResponseFilter());
        environment.jersey().register(ruleEngineController);
    }
    public static void main(String[] args) throws Exception {
        new RuleEngineApplication().run(args);
    }
}
