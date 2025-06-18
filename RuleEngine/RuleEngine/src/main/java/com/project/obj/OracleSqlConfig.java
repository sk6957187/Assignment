package com.project.obj;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class OracleSqlConfig {
    private String driver;
    private String url;
    private String username;
    private String password;

    public String getDriver() {
        System.out.println("get driver"+driver);
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        System.out.println("get url "+url);
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        System.out.println("get name "+username);
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        System.out.println("get pass: "+ password);
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "OracleSqlConfig{" +
                "driver='" + driver + '\'' +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
