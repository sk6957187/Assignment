package com.project.main.org.java;

import org.yaml.snakeyaml.Yaml;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

public class DataBaseConverter {
    public static void main(String[] args) {
        AppConfig config = null;

        if (args.length == 0) {
            System.out.println("Please provide the YAML config file path as an argument.");
            return;
        }

        String configPath = args[0];
        System.out.println("Reading config from: " + configPath);

        try (InputStream input = new FileInputStream(configPath)) {
            Yaml yaml = new Yaml();
            config = yaml.loadAs(input, AppConfig.class);
            System.out.println("YAML parsed successfully.");
        } catch (Exception e) {
            System.out.println("Failed to read config: " + e.getMessage());
            return;
        }

        OracleSqlConfig oracleSqlConfig = config.getOracleSqlConfig();
        Converter converter = config.getConverter();
        ExcelConfig excelConfig = config.getExcelConfig();
        CsvConfig csvConfig = config.getCsvConfig();
        System.out.println("oracle: "+oracleSqlConfig.getDriver());
        System.out.println("Converter: "+converter.getValue());
        System.out.println("Excel: "+ excelConfig.getSheetName());
        System.out.println("CSV file: "+csvConfig.getFileName());

        if(1==converter.getValue()){
            Excel excel = new Excel(oracleSqlConfig,excelConfig);
            String str = excel.loadInExcel();
            System.out.println(str);
        } else if (3 == converter.getValue()) {
            CsvFile cssFile = new CsvFile(oracleSqlConfig, csvConfig);
            String str1 = cssFile.loadInCSVfile();
            System.out.println(str1);
        } else if(4 == converter.getValue()){
            CsvFile cssFile = new CsvFile(oracleSqlConfig, csvConfig);
            List<String> str = cssFile.readCSVFile();
            List<String> res = cssFile.filterData(str);
//            System.err.println("total number of records is: "+ res);
            String msg = cssFile.writeFile(res);

        } else {
            System.out.println("wrong input..!!");
        }
    }
}
