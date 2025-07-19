package com.project.main.org.java;

import java.util.ArrayList;
import java.util.List;

public class CsvConfig {
    private List<String> headers;
    private String fileName;
    private String sheetName;
    private String writeFileName;
    private ArrayList<String> searchData;
    private int column;

    public ArrayList<String> getSearchData() {
        return searchData;
    }

    public void setSearchData(ArrayList<String> searchData) {
        this.searchData = searchData;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column-1;
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
