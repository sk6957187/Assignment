package com.project.sql.sringboot.model;

import com.project.sql.sringboot.controller.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SQLProjectRepository {
    private final Logger logger = LoggerFactory.getLogger(SQLProjectRepository.class);
    private final String driver= "oracle.jdbc.driver.OracleDriver";
    private final String url= "jdbc:oracle:thin:@Sumit11:1521:xe";
    private final String username = "System";
    private final String password= "tiger";

    public String setStuData(){
        return null;
    }

    /*
    NAME, PHOTO, AGE, DOB, PHOTOS, VIDEOS, AUDIO, TEXTFILE, ADDEDTIME, ADDRESS
     */

    public List<Student> getStuData(){
        List<Student> students = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
            String query = "Select * from studentSQL";
            stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if(rs == null){
                logger.info("data not avail..");
            } else {
                while(rs.next()){
                    int sid = rs.getInt("sid");
                    int sroll = rs.getInt("sroll");
                    String sname = rs.getString("sname");
                    String email = rs.getString("email");
                    String photo = rs.getString("photo");
                    students.add(new Student(sid, sname, sroll,  email, photo));
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return students;
    }





}
