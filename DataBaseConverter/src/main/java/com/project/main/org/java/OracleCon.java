package com.project.main.org.java;

import java.sql.*;

public class OracleCon {
	private String driver;
	private String url;
	private String username;
	private String password;
	private OracleSqlConfig oracleSqlConfig;

	public OracleCon(OracleSqlConfig oracleSqlConfig) {
		this.oracleSqlConfig = oracleSqlConfig;
	}
	
	public Connection createConnection() {

		this.driver = oracleSqlConfig.getDriver();
		this.url = oracleSqlConfig.getUrl();
		this.password = oracleSqlConfig.getPassword();
		this.username = oracleSqlConfig.getUsername();
		System.out.println(oracleSqlConfig.getDriver());
		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, username, password);
			System.out.println("Connection build..!!");
			return con;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
    
}
