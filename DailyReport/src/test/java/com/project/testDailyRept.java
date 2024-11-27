package com.project;

import com.project.service.DailyReportService;
import com.project.view.TableDataRepository;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class testDailyRept {
    public static final Logger logger = LoggerFactory.getLogger(testDailyRept.class);

    public DailyReportConfiguration getAppConfig() {
        String configPath = "A:/workspace/Assignment/DailyReport/meta-data/app_env_config.yml";
        DailyReportConfiguration configuration = DailyReportService.getAppConfig(configPath);
        logger.info("yml data: {}",configuration);
        return configuration;
    }

    @Test
    public void TestData() {
        DailyReportConfiguration configuration = this.getAppConfig();
        if (configuration == null) {
            logger.error("Configuration could not be loaded. Test aborted.");
            return;
        }
        logger.info("Loaded configuration: {}", configuration);
        TableDataRepository tableDataRepository = new TableDataRepository(configuration.getOracleSqlConfig());
        ArrayList<ArrayList<String>> data = tableDataRepository.sqlData();
        logger.info("Data fetched: {}", data);
    }

}
