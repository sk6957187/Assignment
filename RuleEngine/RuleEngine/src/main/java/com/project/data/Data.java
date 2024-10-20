package com.project.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.events.Event;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Data {
    private static final String driver = "oracle.jdbc.driver.OracleDriver";
    private static final String url = "jdbc:oracle:thin:@Sumit11:1521:xe";
    private static final String user = "system";
    private static final String pass = "tiger";
    private static final Logger logger = LoggerFactory.getLogger(Data.class);

    public Connection createConn() {
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, pass);
            logger.info("Connection established!");
        } catch (Exception e) {
            logger.info("Connection failed.");
            e.printStackTrace();
        }
        return conn;
    }
    public void loadData(ArrayList<String> userData) throws SQLException {
        Connection conn = createConn();
        if (conn == null) {
            logger.info("Failed to make connection!");
            return;
        }
        String rule = userData.get(0);
        String details = userData.get(1);
        try {
            String query = "Insert INTO ruletable (RULE, DETAILS) values (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, rule);
            stmt.setString(2, details);
            stmt.executeUpdate();
            stmt = conn.prepareStatement("COMMIT");
            stmt.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<String> checkRule(ArrayList<String> userData) {
        ArrayList<String> summary = new ArrayList<>();
        Connection conn = createConn();
        if (conn == null) {
            System.out.println("Failed to make connection!");
            return null;
        }

        String rule = userData.getFirst();
        System.out.println(rule);
        try {
            String query = "SELECT * FROM ruletable WHERE RULE = ? ";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, rule);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                summary.add(rs.getString("RULE"));
                summary.add(rs.getString("DETAILS"));
                logger.info("Fetched data: {}", summary);
            }

            logger.info("Fetched data: {}", summary);
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return summary;
    }

}
