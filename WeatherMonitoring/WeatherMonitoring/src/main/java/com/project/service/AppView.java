package com.project.service;

import com.project.WeatherMonitoringConfiguration;
import io.dropwizard.views.View;


public class AppView extends View {
    private String baseApi;

    public AppView(String pageName, WeatherMonitoringConfiguration configuration) {
        super(pageName);
        String emptyString = "";
        this.baseApi = configuration.getUiConfig().getUiBaseApi();
        if(baseApi == null){
            this.baseApi = emptyString;
        }
    }

    public String getBaseApi() {
        return baseApi;
    }
}
