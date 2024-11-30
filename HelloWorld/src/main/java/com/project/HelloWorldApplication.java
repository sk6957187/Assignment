package com.project;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.controller.HelloWorldResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//public class HelloWorldApplication extends Application<HelloWorldConfiguration> {
//    private static final Logger logger = LoggerFactory.getLogger(HelloWorldApplication.class);
//
//    @Override
//    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
//        super.initialize(bootstrap);
//        bootstrap.addBundle(new ViewBundle<>());
//        bootstrap.addBundle(new AssetsBundle("/assets.libs/", "/assets.libs"));
//    }
//
//    @Override
//    public void run( HelloWorldConfiguration configuration, Environment environment) {
//        logger.info("Application started: {}",configuration);
//        HelloWorldResource resource = new HelloWorldResource(configuration);
//        environment.jersey().register(resource);
//    }
//    public static void main(String[] args) throws Exception {
//        new HelloWorldApplication().run(args);
//    }
//}

@JsonIgnoreProperties(ignoreUnknown = true)

public class HelloWorldApplication extends Application<HelloWorldConfiguration> {

    final static Logger logger = LoggerFactory.getLogger(HelloWorldApplication.class);



    @Override
    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
        super.initialize(bootstrap);
        bootstrap.addBundle(new ViewBundle<>());
        bootstrap.addBundle(new AssetsBundle("/assets/", "/assets"));
    }


    @Override
    public void run(HelloWorldConfiguration configuration, Environment environment) {
        logger.info("Application run success.");
        logger.info("Registering REST resources: {}", configuration);
        environment.jersey().register(new HelloWorldResource(configuration));
    }
    public static void main(String[] args) throws Exception {
        new HelloWorldApplication().run(args);
    }
}
