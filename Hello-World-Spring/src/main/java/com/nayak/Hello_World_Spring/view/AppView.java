package com.nayak.Hello_World_Spring.view;

import com.nayak.Hello_World_Spring.common.AppConstant;
import com.nayak.Hello_World_Spring.config.HelloWorldConfiguration;
import org.springframework.web.servlet.ModelAndView;

public class AppView extends ModelAndView {
    private String appVersion;
    private String userMessage;

    public AppView(String viewName, HelloWorldConfiguration configuration) {
        super(viewName);
        this.appVersion = AppConstant.APP_VERSION;
        this.userMessage = configuration.getUserMessage();
        if (this.userMessage == null) {
            this.userMessage = AppConstant.EMPTY_STRING;
        }
    }

    public String getAppVersion() {
        return appVersion;
    }

    public String getUserMessage() {
        return userMessage;
    }
}
