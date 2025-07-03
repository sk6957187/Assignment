package com.nayak.DataBaseConverterWeb.service;

import com.nayak.DataBaseConverterWeb.entity.ResponseStructure;
import com.nayak.DataBaseConverterWeb.entity.SQLConn;
import com.nayak.DataBaseConverterWeb.entity.SQLData;
import com.nayak.DataBaseConverterWeb.exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ServiceClass {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ResponseEntity<ResponseStructure<List<Map<String, Object>>>> runSQLQuery(String query) {
        System.err.println("bvnmjkl");
        List<Map<String, Object>> result = jdbcTemplate.queryForList(query);
        ResponseStructure<List<Map<String, Object>>>  str = new ResponseStructure<>();
        if(result != null){
            str.setData( result);
            str.setMsg("get data successfully");
            str.setStatusCode(HttpStatus.OK.value());
            return new ResponseEntity<>(str, HttpStatus.OK);
        } else {
            throw new DataNotFoundException("Not Found");
        }
    }

    public ResponseEntity<ResponseStructure<SQLData>> readDataFromSQL(SQLConn sqlConn) {
        ResponseStructure<SQLData> str = new ResponseStructure<>();
        SQLData data = null;
        if(data != null){
            str.setData(data);
            str.setMsg("get data successfully");
            str.setStatusCode(HttpStatus.OK.value());
            return new ResponseEntity<>(str, HttpStatus.OK);
        } else {
            throw new DataNotFoundException("Not Found");
        }
    }
}
