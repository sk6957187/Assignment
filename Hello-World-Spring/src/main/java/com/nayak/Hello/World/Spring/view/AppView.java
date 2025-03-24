package com.nayak.Hellow.World.Spring.view;

import com.nayak.Hellow.World.Spring.config.HelloWorldConfiguration;
import com.nayak.Hellow.World.Spring.common.AppConstant;
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
