package com.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.model.OracleSqlConfig;
import com.project.model.UiConfig;
import io.dropwizard.Configuration;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DailyReportConfiguration extends Configuration {
    private OracleSqlConfig oracleSqlConfig;
    private UiConfig uiConfig;

    public OracleSqlConfig getOracleSqlConfig() {
        return oracleSqlConfig;
    }

    public void setOracleSqlConfig(OracleSqlConfig oracleSqlConfig) {
        this.oracleSqlConfig = oracleSqlConfig;
    }

    public UiConfig getUiConfig() {
        return uiConfig;
    }

    public void setUiConfig(UiConfig uiConfig) {
        this.uiConfig = uiConfig;
    }

    @Override
    public String toString() {
        return "DailyReportConfiguration{" +
                "oracleSqlConfig=" + oracleSqlConfig +
                ", uiConfig=" + uiConfig +
                '}';
    }
}
