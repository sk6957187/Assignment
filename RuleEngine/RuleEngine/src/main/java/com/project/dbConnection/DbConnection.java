package com.project.dbConnection;

import com.project.RuleEngineConfiguration;
import com.project.obj.OracleSqlConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;

import static com.project.service.RuleEngineService.getOracleSqlConfig;

public class DbConnection {
    private final Logger logger = LoggerFactory.getLogger(DbConnection.class);
    private final String driver;// = "oracle.jdbc.driver.OracleDriver";
    private final String url;// = "jdbc:oracle:thin:@Sumit11:1521:xe";
    private final String user;// = "system";
    private final String pass;// = "tiger";


    public DbConnection(OracleSqlConfig oracleSqlConfig) {
       this.driver = oracleSqlConfig.getDriver();
       this.url = oracleSqlConfig.getUrl();
       this.user = oracleSqlConfig.getUsername();
       this.pass = oracleSqlConfig.getPassword();
    }


    public Connection createConn() {
        Connection conn = null;
        try {
            String driver = this.driver;
            String url = this.url;
            String user = this.user;
            String pass = this.pass;
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, pass);
            logger.info("Connection established!");
        } catch (Exception e) {
            logger.info("Connection failed.");
            e.printStackTrace();
        }
        return conn;
    }

    public boolean insertRuleInTable(String ruleName, String ruleDetail) throws SQLException {
        if(ruleName == null || ruleDetail == null || ruleName.isEmpty() || ruleDetail.isEmpty()){
            logger.info("Invalid ruleName or ruleDetail: {}, {}", ruleName,ruleDetail);
            return false;
        }
        Connection conn = createConn();
        if (conn == null) {
            logger.info("Failed to make connection!");
            return false;
        }
        try {
            String query = "Insert INTO ruletable (RULE, DETAILS) values (UPPER(?), UPPER(?))";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, ruleName);
            stmt.setString(2, ruleDetail);
            stmt.executeUpdate();
            stmt = conn.prepareStatement("COMMIT");
            stmt.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean combineRule(String ruleName, String combineRule) throws SQLException {
        if(ruleName == null || combineRule == null || ruleName.isEmpty() || combineRule.isEmpty()){
            logger.info("Invalid ruleName or combineRule: {}, {}", ruleName,combineRule);
            return false;
        }
        Connection conn = createConn();
        try {
            String query = "Insert INTO ruleTable (RULE, combineRule) values (UPPER(?), UPPER(?))";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, ruleName);
            stmt.setString(2, combineRule);
            stmt.executeUpdate();
            stmt = conn.prepareStatement("COMMIT");
            stmt.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String fetchedValue(String name){
        String res=null;
        if(name == null){
            return res;
        }
        Connection conn = createConn();
        try {
            String query = "select DETAILS from RULETABLE where RULE= upper(?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                res = rs.getString("DETAILS");
                logger.info("Fetched data: {},{}", name,res);
            }
        }catch (Exception e){
            e.printStackTrace();
            return res;
        }
        return res;
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
