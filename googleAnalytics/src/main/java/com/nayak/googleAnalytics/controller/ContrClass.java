package com.nayak.googleAnalytics.controller;

import com.nayak.googleAnalytics.configuration.Config;
import com.nayak.googleAnalytics.service.ServiceClass;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/google-analytics")
public class ContrClass {

    private final ServiceClass serviceClass;
    private final Config configClass;

    public ContrClass(ServiceClass serviceClass, Config config) {
        this.serviceClass = serviceClass;
        this.configClass = config;
    }

    @GetMapping
    public String home(Model model){
        model.addAttribute("gaId", configClass.getGooglrAnalyticsMeasurementId());
        model.addAttribute("message", "Welcome to my Google analytics home page");
        return "index";
    }

    @GetMapping("/readme")
    public String readmePage(Model model){
        model.addAttribute("gaId", configClass.getGooglrAnalyticsMeasurementId());
        model.addAttribute("readmeFile", serviceClass.readmePage());
        return "readme";
    }

    @GetMapping("/bnn")
    @ResponseBody
    public String hh(){
        return "This is a plain text response.";
    }
}
