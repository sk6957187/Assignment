package com.nayak.Hellow.World.Spring.controller;

import com.nayak.Hellow.World.Spring.config.DataBaseConfiguration;
import com.nayak.Hellow.World.Spring.config.HelloWorldConfiguration;
import com.nayak.Hellow.World.Spring.common.AppConstant;
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

    public HelloWorldResource(HelloWorldConfiguration configuration, DataBaseConfiguration dbConfig){
        this.dbConfig = dbConfig;
        this.configuration = configuration;
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
        return "app_view";
    }
}
