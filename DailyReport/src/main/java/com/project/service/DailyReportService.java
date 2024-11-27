package com.project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.project.DailyReportApplication;
import com.project.DailyReportConfiguration;
import com.project.view.TableDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DailyReportService {
    private static final Logger lOGGER = LoggerFactory.getLogger(DailyReportApplication.class);
    private final TableDataRepository tableData;

    public DailyReportService(DailyReportConfiguration configuration) {
        this.tableData = new TableDataRepository(configuration.getOracleSqlConfig());
    }

    public static DailyReportConfiguration getAppConfig(String configPath) {
        if (configPath == null || configPath.isEmpty()) {
            lOGGER.error("Config path is null or empty.");
            return null;
        }

        DailyReportConfiguration configuration = null;
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

        try {
            File ref = new File(configPath);
            if (ref.exists() && ref.canRead()) {
                lOGGER.info("Reading configuration file: {}", configPath);
                configuration = objectMapper.readValue(ref, DailyReportConfiguration.class);
                lOGGER.info("Configuration successfully loaded: {}", configuration);
            } else {
                lOGGER.error("Configuration file does not exist or is not readable: {}", configPath);
            }
        } catch (IOException e) {
            lOGGER.error("Error reading configuration file: {}", configPath, e);
        }
        return configuration;
    }



    public ArrayList<ArrayList<String>> getRecord(){
        return tableData.sqlData();
    }

    public String addRecord(ArrayList<String> addData){
        return tableData.addRecordSql(addData);
    }

    public String update(ArrayList<String> rowData) {
        return tableData.updateSql(rowData);
    }
}
