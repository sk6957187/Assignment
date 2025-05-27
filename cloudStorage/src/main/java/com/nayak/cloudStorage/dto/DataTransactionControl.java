package com.nayak.cloudStorage.dto;

import com.nayak.cloudStorage.dta.DataAccess;
import com.nayak.cloudStorage.model.BioData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataTransactionControl {
    private static final Logger logger = LoggerFactory.getLogger(DataTransactionControl.class);
    @Autowired
    private  DataAccess dataAccess;

    public String uploadImage(File file) {
        return dataAccess.uploadFile(file);
    }

    public List<BioData> getBioData() {
        return dataAccess.getBioData();
    }

    public BioData saveBioData(BioData bd) {
        bd.setCreatedTime(LocalDateTime.now());
        bd.setUpdateTime(LocalDateTime.now());
        logger.info("data for save: {}",bd);
        return dataAccess.saveBioData(bd);
    }
}
