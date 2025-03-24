package com.project.sql.sringboot.service;

import com.project.sql.sringboot.model.Student;

import java.util.List;
import java.util.Map;

public class SqlService {
    SQLProjectRepository sqlProjectRepository = new SQLProjectRepository();

    public String UpdateData(List<Map<String, Object>> rowData) {
        return sqlProjectRepository.addData(rowData);
    }

    public List<Map<String, Object>> getData() {
        return sqlProjectRepository.getStuData();
    }

}
