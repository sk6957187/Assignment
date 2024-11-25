package com.project.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;

public class TableData {
    private static final Logger lOGGER = LoggerFactory.getLogger(TableData.class);

    public Connection sqlConn() {
        Connection con = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@Sumit11:1521:xe";
            String username = "System";
            String password = "tiger";
            con = DriverManager.getConnection(url, username, password);
            if (con != null) {
                System.out.println("Connection established..");
            } else {
                System.out.println("Connection error..");
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error during sql: " + e.getMessage());
        }
        return con;
    }

    public ArrayList<ArrayList<String>> sqlData() {
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con = sqlConn();
            if (con != null) {
                stmt = con.createStatement();
                rs = stmt.executeQuery("select * from daily_report");
                while (rs.next()) {
                    ArrayList<String> row = new ArrayList<>();
                    row.add(String.valueOf(rs.getInt("SNO")));
                    row.add(rs.getString("START_DATE"));
                    row.add(rs.getString("USERID"));
                    row.add(rs.getString("SUB"));
                    row.add(rs.getString("TOPIC"));
                    row.add(rs.getString("TOPIC_DETAILS"));
                    row.add(rs.getString("COMPLETED"));
                    row.add(String.valueOf(rs.getDate("ADDED_DATE")));
                    row.add(String.valueOf(rs.getTimestamp("UPDATE_TIME")));
                    data.add(row);
//                    System.out.println(row);
                }
            } else {
                lOGGER.info("Connection error..");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    public String addRecordSql(ArrayList<String> addData){
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = sqlConn();
            String sql = "INSERT INTO daily_report (START_DATE, USERID, SUB, TOPIC, TOPIC_DETAILS, COMPLETED) VALUES (?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, addData.get(0));               // START_DATE
            pstmt.setString(2, addData.get(1));               // USERID
            pstmt.setString(3, addData.get(2));               // SUB
            pstmt.setString(4, addData.get(3));               // TOPIC
            pstmt.setString(5, addData.get(4));               // TOPIC_DETAILS
            pstmt.setString(6, addData.get(5));               // COMPLETED (assuming YES/NO, adjust as needed)
            int rowsInserted = pstmt.executeUpdate();
            pstmt = conn.prepareStatement("commit");
            pstmt.executeUpdate();
            if (rowsInserted > 0) {
                return "Record added successfully!";
            } else {
                return "Failed to add record.";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public String updateSql(ArrayList<String> rowData) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try{
            conn = sqlConn();
            if (conn != null){

                String stmt = "UPDATE daily_report " +
                        "SET START_DATE = ?, USERID = ?, SUB = ?, TOPIC = ?, " +
                        "TOPIC_DETAILS = ?, COMPLETED = ? " +
                        "WHERE SNO = ?";
                pstmt = conn.prepareStatement(stmt);
                pstmt.setString(1, rowData.get(1)); // START_DATE
                pstmt.setString(2, rowData.get(2)); // USERID
                pstmt.setString(3, rowData.get(3)); // SUB
                pstmt.setString(4, rowData.get(4)); // TOPIC
                pstmt.setString(5, rowData.get(5)); // TOPIC_DETAILS
                pstmt.setString(6, rowData.get(6)); // COMPLETED
                pstmt.setInt(7, Integer.parseInt(rowData.get(0)));
                int rowsUpdated = pstmt.executeUpdate();
                pstmt = conn.prepareStatement("commit");
                pstmt.executeUpdate();
                if (rowsUpdated > 0) {
                    return "Update successful!";
                } else {
                    return "No rows updated. Check the SNO value.";
                }
            } else {
                lOGGER.info("Connection error");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return "OK";
    }
}
