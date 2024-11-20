package com.project.service;

import com.project.RuleEngineConfiguration;
import io.dropwizard.views.View;

public class AppView extends View {
    private String uiConfig;

    public AppView(String pageName, RuleEngineConfiguration ruleEngineConfiguration){
        super(pageName);
        String emptyString = "";
        this.uiConfig = ruleEngineConfiguration.getUiConfig().getUiBaseApi();
        if(uiConfig == null){
            this.uiConfig=emptyString;
        }
    }
    public String getUiConfig() {
        return uiConfig;
    }

    public void setUiConfig(String uiConfig) {
        this.uiConfig = uiConfig;
    }

}
