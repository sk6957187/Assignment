package com.project.service;

import com.project.DailyReportApplication;
import com.project.view.TableData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class DailyReportService {
    private static final Logger lOGGER = LoggerFactory.getLogger(DailyReportApplication.class);

    public DailyReportService() {}
    public ArrayList<ArrayList<String>> getRecord(){
        TableData tableData = new TableData();
        return tableData.sqlData();
    }

    public String update(ArrayList<String> rowData) {
        TableData tableData = new TableData();
        return tableData.updateSql(rowData);
    }
}
