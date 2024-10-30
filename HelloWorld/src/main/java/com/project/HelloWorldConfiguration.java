package com.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.dropwizard.Configuration;

@JsonIgnoreProperties(ignoreUnknown = true)

public class HelloWorldConfiguration  extends Configuration {
    private String userMessage;

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    @Override
    public String toString() {
        return "SampleConfiguration{" +
                "userMessage='" + userMessage + '\'' +
                '}';
    }
}
