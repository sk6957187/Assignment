package com.project.view;

import com.project.model.OracleSqlConfig;
import com.project.model.UiConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;

public class TableDataRepository {
    private static final Logger lOGGER = LoggerFactory.getLogger(TableDataRepository.class);
    private final String driver;// = "oracle.jdbc.driver.OracleDriver";
    private final String username; //"System";
    private final String pass; //"oracle.jdbc.driver.OracleDriver";
    private final String url; //"tiger";

    public TableDataRepository(OracleSqlConfig oracleSqlConfig){
        driver =    oracleSqlConfig.getDriver();
        username =  oracleSqlConfig.getUsername();
        pass = oracleSqlConfig.getPassword();
        url =  oracleSqlConfig.getUrl();

    }

    public Connection sqlConn() {
        Connection con = null;
        try {
            Class.forName(this.driver);
            con = DriverManager.getConnection(this.url, this.username, this.pass);
            if (con != null) {
                lOGGER.info("Connection established..");
            } else {
                lOGGER.info("Connection error..");
            }
        } catch (SQLException | ClassNotFoundException e) {
            lOGGER.info("Error during sql: " + e.getMessage());
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
                //select * from daily_report where delete='NO' order by sno desc
                rs = stmt.executeQuery("select * from daily_report where deleted = 'NO' order by sno desc");
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
                    row.add(rs.getString("FILE_LINK"));
                    data.add(row);
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
            String sql = "INSERT INTO daily_report (START_DATE, USERID, SUB, TOPIC, TOPIC_DETAILS, COMPLETED, FILE_LINK) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, addData.get(0));               // START_DATE
            pstmt.setString(2, addData.get(1));               // USERID
            pstmt.setString(3, addData.get(2));               // SUB
            pstmt.setString(4, addData.get(3));               // TOPIC
            pstmt.setString(5, addData.get(4));               // TOPIC_DETAILS
            pstmt.setString(6, addData.get(5));               // COMPLETED (assuming YES/NO, adjust as needed)
            pstmt.setString(7,addData.get(6));
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
        if (rowData.size() <= 6 || rowData.get(6) == null) {
            while (rowData.size() <= 6) {
                rowData.add(null);
            }
            rowData.set(6, "NO");
        }
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
    public String deleteSql(int n){
        Connection conn = null;
        PreparedStatement pstmt = null;
        try{
            conn = sqlConn();
            if(conn != null){
                //update daily_report set delete='YES' where sno = ?
                String stmt= "update daily_report set deleted ='YES' where sno = ?";
                pstmt = conn.prepareStatement(stmt);
                pstmt.setInt(1,n);
                int rowsUpdated = pstmt.executeUpdate();
                pstmt = conn.prepareStatement("commit");
                pstmt.executeUpdate();
                if (rowsUpdated > 0) {
                    return "Delete successful!";
                } else {
                    return "No rows updated. Check the SNO value.";
                }
            }
        }  catch (SQLException e){
            e.printStackTrace();
        }
        return "something wrong";
    }
}
