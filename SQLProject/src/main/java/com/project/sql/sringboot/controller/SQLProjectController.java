package com.project.sql.sringboot.controller;

import com.project.sql.sringboot.model.Student;
import com.project.sql.sringboot.service.SqlService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/")
public class SQLProjectController {
    private static final Logger logger = LoggerFactory.getLogger(SQLProjectController.class);
    private final List<Student> students = new ArrayList<>();
    SqlService sqlService = new SqlService();

    @GetMapping("/sql")
    public List<Map<String, Object>> showData() {
        System.out.println("Fetching student data...");
        return sqlService.getData();
    }

    @PostMapping("/sql/add-data")
    public ResponseEntity<String> updateRecord(@RequestBody List<Map<String, Object>> rowDataList) {
        String str = "OK";
        int i = 0;
        if (!rowDataList.isEmpty()) {
            for (Map<String, Object> row : rowDataList) {
                logger.info("{} Received row: {}", i++, row);
            }
            str = sqlService.UpdateData(rowDataList);
        }
        return ResponseEntity.ok(str);
    }

}
