package com.project.sql.sringboot.service;

import com.project.sql.sringboot.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class SQLProjectRepository {
    private final Logger logger = LoggerFactory.getLogger(SQLProjectRepository.class);
    private final String driver= "oracle.jdbc.driver.OracleDriver";
    private final String url= "jdbc:oracle:thin:@Sumit11:1521:xe";
    private final String username = "System";
    private final String password= "tiger";

    public String addData(List<Map<String, Object>> rowData) {
        String query = "INSERT INTO studentSQL (SNAME, AGE, DOB, PHOTOS, VIDEOS, AUDIO, TEXTFILE, ADDRESS) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            for (Map<String, Object> row : rowData) {
                stmt.setString(1, (String) row.get("name"));
                stmt.setInt(2, Integer.parseInt(row.get("age").toString()));
                stmt.setDate(3, java.sql.Date.valueOf((String) row.get("dob")));

                // Convert BLOB data from URL or Base64 (Assuming it's coming as Base64 encoded string)
                stmt.setBytes(4, row.get("photos") != null ? row.get("photos").toString().getBytes() : null);
                stmt.setBytes(5, row.get("videos") != null ? row.get("videos").toString().getBytes() : null);
                stmt.setBytes(6, row.get("audio") != null ? row.get("audio").toString().getBytes() : null);

                // Handle CLOB (Assuming textFile content is stored as a String)
                stmt.setString(7, (String) row.get("textFile"));
                stmt.setString(8, (String) row.get("address"));

                stmt.addBatch();
            }

            stmt.executeBatch();
            return "Data inserted successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error inserting data: " + e.getMessage();
        }
    }

    // Utility method to decode Base64 (Modify based on your input format)
    private static byte[] decodeBase64(String base64String) {
        return Base64.getDecoder().decode(base64String);
    }

    private static String encodeBase64(byte[] data) {
        return data != null ? Base64.getEncoder().encodeToString(data) : null;
    }


    /*
    NAME, PHOTO, AGE, DOB, PHOTOS, VIDEOS, AUDIO, TEXTFILE, ADDEDTIME, ADDRESS
     */

    public List<Map<String, Object>> getStuData(){
        List<Map<String, Object>> students = new ArrayList<>();
        String query = "SELECT * FROM studentSQL";

        try {
            Class.forName(driver);
            try (Connection conn = DriverManager.getConnection(url, username, password);
                 PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Map<String, Object> student = new HashMap<>();
                    student.put("sid", rs.getObject("SID") != null ? rs.getInt("SID") : 0);
                    student.put("name", rs.getString("SNAME"));
                    student.put("age", rs.getObject("AGE") != null ? rs.getInt("AGE") : 0);
                    student.put("dob", rs.getDate("DOB"));
                    student.put("photos", rs.getBytes("PHOTOS") != null ? encodeBase64(rs.getBytes("PHOTOS")) : null);
                    student.put("videos", rs.getBytes("VIDEOS") != null ? encodeBase64(rs.getBytes("VIDEOS")) : null);
                    student.put("audio", rs.getBytes("AUDIO") != null ? encodeBase64(rs.getBytes("AUDIO")) : null);
                    student.put("textFile", rs.getString("TEXTFILE"));
                    student.put("addedTime", rs.getTimestamp("ADDEDTIME"));
                    student.put("address", rs.getString("ADDRESS"));

                    students.add(student);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }
}
