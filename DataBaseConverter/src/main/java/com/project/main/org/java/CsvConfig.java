package com.project.main.org.java;

import java.util.List;

public class CsvConfig {
    private List<String> headers;
    private String fileName;
    private String sheetName;
    private String writeFileName;
    private String mobNum;

    public String getMobNum() {
        return mobNum;
    }

    public void setMobNum(String mobNum) {
        this.mobNum = mobNum;
    }

    public String getWriteFileName() {
        return writeFileName;
    }

    public void setWriteFileName(String writeFileName) {
        this.writeFileName = writeFileName;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }
}
