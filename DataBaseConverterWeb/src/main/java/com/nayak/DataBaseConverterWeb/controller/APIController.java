package com.nayak.DataBaseConverterWeb.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nayak.DataBaseConverterWeb.entity.ResponseStructure;
import com.nayak.DataBaseConverterWeb.entity.SQLConn;
import com.nayak.DataBaseConverterWeb.entity.SQLData;
import com.nayak.DataBaseConverterWeb.service.ServiceClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/databaseconvert")
public class APIController {
    private static final Logger logger = LoggerFactory.getLogger(APIController.class);



    @Autowired
    private ServiceClass serviceClass;

    @PutMapping("/readmysql")
    public ResponseEntity<ResponseStructure<List<Map<String, Object>>>> readDataFromMySQL(
            @RequestBody Map<String, Object> requestBody) {
        logger.info("Query: {}", requestBody.get("query"));
        logger.info("database: {}", requestBody.get("database"));
        String query = (String) requestBody.get("query");
        String database = (String) requestBody.get("database");

        return serviceClass.runSQLQuery(query, database);
    }

    @PostMapping("/uploadsql")
    public ResponseEntity<ResponseStructure<String>> updateSQL(@RequestBody Map<String, Object> payload) throws JsonProcessingException {
        String tableName = (String) payload.get("tableName");
        ObjectMapper mapper = new ObjectMapper();
        String dataJson = mapper.writeValueAsString(payload.get("data"));
        System.err.println(dataJson);
        return serviceClass.UpdateSQL(dataJson, tableName);
    }

    @PostMapping("/uploadfile")
    public ResponseEntity<ResponseStructure<List<Map<String, Object>>>> readFile(
            @RequestPart(value = "link", required = false) String link,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        logger.info("file: {}", file);
        logger.info("file link: {}", link);
        return serviceClass.readFile(link, file);
    }
}
