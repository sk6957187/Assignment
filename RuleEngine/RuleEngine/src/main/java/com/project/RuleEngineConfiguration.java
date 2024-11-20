package com.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.obj.OracleSqlConfig;
import com.project.obj.UiConfig;
import io.dropwizard.Configuration;

@JsonIgnoreProperties(ignoreUnknown = true)

public class RuleEngineConfiguration extends Configuration {
	private OracleSqlConfig oracleSqlConfig;
    private UiConfig uiConfig;

	public OracleSqlConfig getOracleSqlConfig() {
        System.out.println(oracleSqlConfig);
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
        return "RuleEngineConfiguration{" +
                "oracleSqlConfig=" + oracleSqlConfig +
                ", uiConfig=" + uiConfig +
                '}';
    }
}
