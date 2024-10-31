package com.project.service;

import com.project.WeatherMonitoringConfiguration;
import io.dropwizard.views.View;


public class AppView extends View {

    public AppView(String pageName, WeatherMonitoringConfiguration configuration) {
        super(pageName);

    }


}
