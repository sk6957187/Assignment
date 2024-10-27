package com.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.obj.OracleSqlConfig;
import io.dropwizard.Configuration;

@JsonIgnoreProperties(ignoreUnknown = true)

public class RuleEngineConfiguration extends Configuration {
	private OracleSqlConfig oracleSqlConfig;

	public OracleSqlConfig getOracleSqlConfig() {
        return oracleSqlConfig;
    }
    public void setOracleSqlConfig(OracleSqlConfig oracleSqlConfig) {
        this.oracleSqlConfig = oracleSqlConfig;
    }
    @Override
    public String toString(){
    	return "RuleEngineConfiguration{"+
    			"oracleSqlConfig" + oracleSqlConfig+
    			'}';
    }
}
