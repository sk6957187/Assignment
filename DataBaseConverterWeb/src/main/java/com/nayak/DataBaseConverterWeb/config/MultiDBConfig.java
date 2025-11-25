package com.nayak.DataBaseConverterWeb.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;   // ✅ Correct import
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class MultiDBConfig {

    // ===== MYSQL CONFIG =====
    @Bean
    @ConfigurationProperties("spring.datasource.mysql")
    public DataSourceProperties mysqlProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "mysqlDataSource")
    public DataSource mysqlDataSource() {
        return mysqlProperties().initializeDataSourceBuilder().build();
    }

    @Bean(name = "mysqlJdbcTemplate")
    public JdbcTemplate mysqlJdbcTemplate(@Qualifier("mysqlDataSource") DataSource ds) {
        return new JdbcTemplate(ds);
    }

    // ===== ORACLE CONFIG =====
    @Bean
    @ConfigurationProperties("spring.datasource.oracle")
    public DataSourceProperties oracleProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "oracleDataSource")
    public DataSource oracleDataSource() {
        return oracleProperties().initializeDataSourceBuilder().build();
    }

    @Bean(name = "oracleJdbcTemplate")
    public JdbcTemplate oracleJdbcTemplate(@Qualifier("oracleDataSource") DataSource ds) {
        return new JdbcTemplate(ds);
    }

    // ===== POSTGRES CONFIG =====
    @Bean
    @ConfigurationProperties("spring.datasource.postgres")
    public DataSourceProperties postgresProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "postgresDataSource")
    public DataSource postgresDataSource() {
        return postgresProperties().initializeDataSourceBuilder().build();
    }

    @Bean(name = "postgresJdbcTemplate")
    public JdbcTemplate postgresJdbcTemplate(@Qualifier("postgresDataSource") DataSource ds) {
        return new JdbcTemplate(ds);
    }
}
