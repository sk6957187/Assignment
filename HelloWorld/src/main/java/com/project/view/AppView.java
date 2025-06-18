package com.project.view;

import com.project.HelloWorldConfiguration;
import com.project.comman.AppConstant;
import io.dropwizard.views.View;

public class AppView extends View {
    private String appVersion;
    private String userMessage;

    public AppView(String pageName, HelloWorldConfiguration configuration) {
        super(pageName);
        this.appVersion = AppConstant.appVersion;
        this.userMessage = configuration.getUserMessage();
        if(this.userMessage == null){
            this.userMessage = AppConstant.emptyString;
        }
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }
}
