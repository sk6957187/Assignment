package com.project.controller;

import com.project.HelloWorldConfiguration;
import com.project.view.AppView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

//@Path("/hello-world")
//@Produces(MediaType.TEXT_PLAIN)
//public class HelloWorldResource {
//    private static final Logger logger = LoggerFactory.getLogger(HelloWorldResource.class);
//
//    private final AppView helloWorldService;
//
//    public HelloWorldResource(HelloWorldConfiguration helloWorldConfiguration) {
//        this.helloWorldService= new AppView(helloWorldConfiguration);
//    }
//
//    @GET
//    @Path("/app/view")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response AppView() {
//        String message = helloWorldService.getMessage();
//        String appVersion = helloWorldService.getAppVersion();
//        logger.info("Show final msg: {}, appVersion: {}", message, appVersion);
//
//        String jsonResponse = "{\"message\":\"" + message + "\", \"appVersion\":\"" + appVersion + "\"}";
//
//        return Response.ok()
//                .entity(jsonResponse.toString())
//                .build();
//    }
//    @GET
//    public String sayHii(){
//        String res = helloWorldService.hii();
//        logger.info("Show final msg in sayHii: {}",res);
//        return res;
//    }
//}
//
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HelloWorldResource {
    final static Logger logger = LoggerFactory.getLogger(HelloWorldResource.class);
    private final HelloWorldConfiguration configuration;
    public HelloWorldResource(HelloWorldConfiguration configuration) {
        this.configuration = configuration;
    }
    @GET
    @Path("/hello-world/app/view")
    @Produces(MediaType.TEXT_HTML)
    public Response appView(@Context HttpServletRequest request) {
        logger.info("Loading/helloWorld/view");
        return Response.ok(new AppView("app_view.ftl", configuration)).build();
    }
    @GET
    @Path("/favicon.ico")
    @Produces("image/x-icon")
    public Response LoadFaviconIcon() throws URISyntaxException {
        System.out.println("favicon loaded");
        return Response.seeOther(new URI("assets/favicon.ico")).build();
    }
    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("{default: .*}")
    public Response defaultMethod(@Context HttpServletRequest request) {
        logger.info("Loading default url");
        return Response.ok(new AppView("file_not_found.ftl", configuration)).build();
    }
}