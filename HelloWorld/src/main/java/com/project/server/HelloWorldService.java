package com.project.server;

import com.project.HelloWorldConfiguration;
import com.project.comman.AppConstant;

public class HelloWorldService {
    private String message;
    private String appVersion;
    private final HelloWorldConfiguration helloWorldConfiguration;

    public HelloWorldService(HelloWorldConfiguration helloWorldConfiguration) {
        this.helloWorldConfiguration = helloWorldConfiguration;
        this.message = helloWorldConfiguration.getMessage();
        this.appVersion = AppConstant.appVersion;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "HelloWorldService{" +
                "message='" + message + '\'' +
                ", appVersion='" + appVersion + '\'' +
                '}';
    }

    public String hii() {
        return "Hii";
    }
}
