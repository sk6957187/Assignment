package com.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.dropwizard.Configuration;

@JsonIgnoreProperties(ignoreUnknown = true)

public class HelloWorldConfiguration extends Configuration {
    private String message;

    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "HelloWorldConfiguration{" +
                "message='" + message + '\'' +
                '}';
    }
}

