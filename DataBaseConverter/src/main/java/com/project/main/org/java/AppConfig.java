package com.project.main.org.java;

public class AppConfig {
    private OracleSqlConfig oracleSqlConfig;
    private Converter converter;
    private ExcelConfig excelConfig;
    private CsvConfig csvConfig;

    public CsvConfig getCsvConfig() {
        return csvConfig;
    }

    public void setCsvConfig(CsvConfig csvConfig) {
        this.csvConfig = csvConfig;
    }

    public ExcelConfig getExcelConfig() {
        return excelConfig;
    }

    public void setExcelConfig(ExcelConfig excelConfig) {
        this.excelConfig = excelConfig;
    }

    public Converter getConverter() {
        return converter;
    }

    public void setConverter(Converter converter) {
        this.converter = converter;
    }

    public OracleSqlConfig getOracleSqlConfig() {
        return oracleSqlConfig;
    }

    public void setOracleSqlConfig(OracleSqlConfig oracleSqlConfig) {
        this.oracleSqlConfig = oracleSqlConfig;
    }
}

