package com.nayak.DataBaseConverterWeb.service;

import com.nayak.DataBaseConverterWeb.config.Configration;
import com.nayak.DataBaseConverterWeb.entity.InputStreamMultipartFile;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URL;
import java.io.IOException;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ReadFile {
    @Autowired
    private Configration configration;

    public List<Map<String, Object>> readExcel(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Uploaded file is empty or null");
        }

        String fileName = file.getOriginalFilename();
        if (fileName == null || (!fileName.endsWith(".xlsx") && !fileName.endsWith(".xls"))) {
            throw new RuntimeException("Invalid file type: must be .xlsx or .xls");
        }

        try {
            InputStream is = file.getInputStream();
            Workbook workbook = fileName.endsWith(".xlsx") ? new XSSFWorkbook(is) : new HSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);
            List<Map<String, Object>> data = new ArrayList<>();
            Row header = sheet.getRow(0);
            if (header == null){
                return data;
            }
            List<String> columns = new ArrayList<>();
            for (Cell cell : header) {
                columns.add(cell.toString());
            }
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Map<String, Object> rowData = new LinkedHashMap<>();
                for (int j = 0; j < columns.size(); j++) {
                    Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    rowData.put(columns.get(j), getCellValue(cell));
                }
                data.add(rowData);
            }
            return data;
        } catch (Exception e) {
            throw new RuntimeException("Failed to read Excel file: " + e.getMessage(), e);
        }
    }


    private Object getCellValue(Cell cell) {
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> DateUtil.isCellDateFormatted(cell) ? cell.getDateCellValue() : cell.getNumericCellValue();
            case BOOLEAN -> cell.getBooleanCellValue();
            case FORMULA -> cell.getCellFormula();
            default -> "";
        };
    }

    public String saveFile(MultipartFile file){
        String destination = configration.getFileDestination();
        String fileName = file.getOriginalFilename();
        String filePath = null;
        if (fileName == null || fileName.isEmpty()) {
            throw new RuntimeException("Invalid file name.");
        }
        fileName = fileName.replaceAll("[\\\\/:*?\"<>|]", "_");
        try {
            File dir = new File(destination);
            if(!dir.exists()){
                dir.mkdir();
            }
            String timestamp = new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date());
            String newFileName = timestamp + "_" + fileName;
            File destFile = new File(dir, newFileName);

            if (destFile.exists()) {
                String baseName = fileName.contains(".")
                        ? fileName.substring(0, fileName.lastIndexOf('.'))
                        : fileName;
                String extension = fileName.contains(".")
                        ? fileName.substring(fileName.lastIndexOf('.'))
                        : "";

                String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
                newFileName = timestamp + "_" + uuid + "_" + baseName + extension;
                destFile = new File(dir, newFileName);
            }

            file.transferTo(destFile);
            filePath =  destFile.getAbsolutePath();

        } catch (IOException e) {
            throw new RuntimeException("Failed to save file: " + e.getMessage(), e);
        }
        return filePath;
    }

    public MultipartFile downloadFile(String link) {
        try {
            URL url = new URL(link);
            String fileName = link.substring(link.lastIndexOf('/') + 1);
            try (InputStream inputStream = url.openStream()) {
                return new InputStreamMultipartFile(
                        inputStream,
                        "file", // field name
                        fileName,
                        "application/octet-stream"
                );
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to download file from link: " + e.getMessage(), e);
        }
    }



}
