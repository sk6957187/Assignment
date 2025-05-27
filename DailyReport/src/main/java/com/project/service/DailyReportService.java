package com.project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.project.DailyReportApplication;
import com.project.DailyReportConfiguration;
import com.project.view.CloudeDataRepository;
import com.project.view.TableDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DailyReportService {
    private static final Logger lOGGER = LoggerFactory.getLogger(DailyReportApplication.class);
    private final TableDataRepository tableData;
    private final CloudeDataRepository cloudeDataRepository;

    public DailyReportService(DailyReportConfiguration configuration) {
        this.tableData = new TableDataRepository(configuration.getOracleSqlConfig());
        System.out.println(configuration.getCloudConfig());
        this.cloudeDataRepository = new CloudeDataRepository(configuration.getCloudConfig());

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
            configuration = objectMapper.readValue(ref, DailyReportConfiguration.class);
            lOGGER.info("Configuration successfully loaded: {}", configuration);
        } catch (IOException e) {
            lOGGER.error("Error reading configuration file: {}", configPath, e);
        }
        return configuration;
    }



    public ArrayList<ArrayList<String>> getRecord(){
        return tableData.sqlData();
    }

    public String addRecord(ArrayList<String> addData, List<File> mediaFile){
        String mediaUrl="";
        if( ! mediaFile.isEmpty()){
            mediaUrl = cloudeDataRepository.uploadFile(mediaFile);
        }
        System.out.println(mediaUrl);
        addData.add(mediaUrl);
        return tableData.addRecordSql(addData);
    }

    public String update(ArrayList<String> rowData) {
        return tableData.updateSql(rowData);
    }
    public String deleteRec(int n){
        return tableData.deleteSql(n);
    }
}
