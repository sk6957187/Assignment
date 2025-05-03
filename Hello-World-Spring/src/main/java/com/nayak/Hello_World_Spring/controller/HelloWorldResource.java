package com.nayak.Hello_World_Spring.controller;


import com.nayak.Hello_World_Spring.common.AppConstant;
import com.nayak.Hello_World_Spring.config.DataBaseConfiguration;
import com.nayak.Hello_World_Spring.config.EmailConfig;
import com.nayak.Hello_World_Spring.config.HelloWorldConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin
@RequestMapping("/hello-world")
public class HelloWorldResource {
    private static final Logger logger = LoggerFactory.getLogger(HelloWorldResource.class);
    private final HelloWorldConfiguration configuration;
    private final DataBaseConfiguration dbConfig;
    private final EmailConfig emailConfig;

    HelloWorldResource(HelloWorldConfiguration configuration, DataBaseConfiguration dbConfig, EmailConfig emailConfig){
        this.dbConfig = dbConfig;
        this.configuration = configuration;
        this.emailConfig = emailConfig;
    }

    @GetMapping
//    @ResponseBody   //When write @ResponseBody then sure return row data without .html page
    public String baseHelloWorld(Model model) {
        model.addAttribute("message", "Welcome to the Hello World API!!");
        return "hello-world";
    }

    @GetMapping("/message")
    @ResponseBody  // This tells Spring to return plain text instead of a view
    public String getUserMessage() {
        String message = configuration.getUserMessage();
        logger.info("Reading message from configuration: {}", message);
        return "Configured Message: " + message;
    }

    @GetMapping("/app/view")
    public String appView(Model model) {
//        model.addAttribute("appVersion", "1.0.0");
        model.addAttribute("appVersion", AppConstant.APP_VERSION);
        model.addAttribute("userMessage", configuration.getUserMessage());
        model.addAttribute("sqlUrl",dbConfig.getUrl());
        String sql = dbConfig.getDriver();
        logger.info("Sql Driver: {}",sql);
        model.addAttribute("sqlDriver",sql);
        String emailId = emailConfig.getEmailId();
        String pass = emailConfig.getPassword();
        logger.info("email: {}",emailId);
        model.addAttribute("email", emailId);
        model.addAttribute("pass",pass);
        return "app_view";
    }
}
