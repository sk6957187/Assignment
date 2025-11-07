package com.nayak.DataBaseConverterWeb.service;

import com.nayak.DataBaseConverterWeb.entity.ResponseStructure;
import com.nayak.DataBaseConverterWeb.exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ServiceClass {

    @Autowired @Qualifier("mysqlJdbcTemplate")
    private JdbcTemplate mysqlTemplate;

    @Autowired @Qualifier("oracleJdbcTemplate")
    private JdbcTemplate oracleTemplate;

    @Autowired @Qualifier("postgresJdbcTemplate")
    private JdbcTemplate postgresTemplate;


    @Autowired
    private ReadFile readFile;

    public ResponseEntity<ResponseStructure<List<Map<String, Object>>>> runSQLQuery(String query, String database) {
        JdbcTemplate jdbcTemplate = switch (database.toLowerCase()) {
            case "mysql" -> mysqlTemplate;
            case "oraclesql" -> oracleTemplate;
            case "postgresql" -> postgresTemplate;
            default -> throw new IllegalArgumentException("Unsupported database: " + database);
        };
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

    public ResponseEntity<ResponseStructure<String>> UpdateSQL(String dataJson, String tableName) {

        ResponseStructure<String> str = new ResponseStructure<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Map<String, Object>> data = objectMapper.readValue(
                    dataJson,
                    new TypeReference<List<Map<String, Object>>>() {}
            );
            System.err.println("Table Nameeeeeeee: " + tableName);
            if (data.isEmpty()) throw new RuntimeException("No data to insert.");
            System.err.println("Table Name: " + tableName);
            String table = "\"" + tableName.toUpperCase() + "\"";

//            // 1. Check if table exists
//            String checkTableQuery = "SELECT COUNT(*) FROM user_tables WHERE table_name = ?";
//            Integer count = jdbcTemplate.queryForObject(checkTableQuery, new Object[]{tableName.toUpperCase()}, Integer.class);

            // 2. Create table if not exists
//            if (count != null && count == 0) {
//                Map<String, Object> firstRow = data.get(0);
//                StringBuilder createQuery = new StringBuilder("CREATE TABLE " + table + " (");
//                for (Map.Entry<String, Object> entry : firstRow.entrySet()) {
//                    createQuery.append("\"").append(entry.getKey().toUpperCase()).append("\" VARCHAR2(255), ");
//                }
//                createQuery.setLength(createQuery.length() - 2);
//                createQuery.append(")");
//                jdbcTemplate.execute(createQuery.toString());
//            }

            // 3. Insert data
//            for (Map<String, Object> row : data) {
//                List<String> quotedKeys = row.keySet().stream()
//                        .map(k -> "\"" + k.toUpperCase() + "\"")
//                        .toList();
//
//                String columns = String.join(", ", quotedKeys);
//                String placeholders = quotedKeys.stream().map(k -> "?").collect(Collectors.joining(", "));
//
//                String insertQuery = "INSERT INTO " + table + " (" + columns + ") VALUES (" + placeholders + ")";
//                jdbcTemplate.update(insertQuery, row.values().toArray());
//            }

            str.setData("Uploaded to table: " + tableName);
            str.setMsg("Data uploaded successfully");
            str.setStatusCode(HttpStatus.OK.value());
            return new ResponseEntity<>(str, HttpStatus.OK);

        } catch (DataAccessException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse or insert data", e);
        }
    }


    public ResponseEntity<ResponseStructure<List<Map<String, Object>>>> readFile(String link, MultipartFile file) {
        ResponseStructure<List<Map<String, Object>>>  str = new ResponseStructure<>();
        List<Map<String, Object>> result = null;
        if(link != null){
            file = file = readFile.downloadFile(link);
            result = readFile.readExcel(file);
        } else {
            result = readFile.readExcel(file);
        }
        String filePath = readFile.saveFile(file);
        str.setData(result);
        str.setMsg("Data read successfully");
        str.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(str, HttpStatus.OK);
    }
}
