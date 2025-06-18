package com.project.model;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.view.TableDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonIgnoreProperties(ignoreUnknown = true)

public class OracleSqlConfig {
    private static final Logger lOGGER = LoggerFactory.getLogger(OracleSqlConfig.class);
    private String driver;
    private String url;
    private String username;
    private String password;

    public String getDriver() {
        lOGGER.info("get driver:{}", driver);
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        lOGGER.info("get url: {}", url);
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        lOGGER.info("get name: {}",username);
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        lOGGER.info("get pass: {}",password);
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

