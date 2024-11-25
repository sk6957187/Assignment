package com.project.service;

import com.project.DailyReportApplication;
import com.project.view.TableData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class DailyReportService {
    private static final Logger lOGGER = LoggerFactory.getLogger(DailyReportApplication.class);
    TableData tableData = new TableData();
    public DailyReportService() {}
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
