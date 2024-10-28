package com.project.controller;

import com.project.HelloWorldConfiguration;
import com.project.server.HelloWorldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/hello-world")
@Produces(MediaType.TEXT_PLAIN)
public class HelloWorldResource {
    private static final Logger logger = LoggerFactory.getLogger(HelloWorldResource.class);

    private final HelloWorldService helloWorldService;

    public HelloWorldResource(HelloWorldConfiguration helloWorldConfiguration) {
        this.helloWorldService= new HelloWorldService(helloWorldConfiguration);
    }

    @GET
    @Path("/app/view")
    @Produces(MediaType.APPLICATION_JSON)
    public Response AppView() {
        String message = helloWorldService.getMessage();
        String appVersion = helloWorldService.getAppVersion();
        logger.info("Show final msg: {}, appVersion: {}", message, appVersion);

        String jsonResponse = "{\"message\":\"" + message + "\", \"appVersion\":\"" + appVersion + "\"}";

        return Response.ok()
                .entity(jsonResponse.toString())
                .build();
    }
    @GET
    @Path("{default: .*}")
    public String sayHii(){
        String res = helloWorldService.hii();
        logger.info("Show final msg in sayHii: {}",res);
        return res;
    }
}

