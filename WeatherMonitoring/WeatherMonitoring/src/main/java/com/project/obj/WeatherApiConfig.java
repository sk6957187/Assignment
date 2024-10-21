package com.project.obj;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class WeatherApiConfig {
    private String apiKey;
    private String baseUrl;
    private String driver;
    private String db_url;
    private String user;
    private String pass;

    public String getDb_url() {
        return db_url;
    }

    public void setDb_url(String db_url) {
        this.db_url = db_url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getDriver() {
        return driver;
    }
    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public String toString() {
        return "WeatherApiConfig{" +
                "apiKey='" + apiKey + '\'' +
                ", baseUrl='" + baseUrl + '\'' +
                ", driver='" + driver + '\'' +
                ", db_url='" + db_url + '\'' +
                ", user='" + user + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }

}
