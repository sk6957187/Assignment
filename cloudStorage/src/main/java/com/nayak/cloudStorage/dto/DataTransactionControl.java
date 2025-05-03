package com.nayak.cloudStorage.dto;

import com.nayak.cloudStorage.dta.DataAccess;

import java.io.File;

public class DataTransactionControl {

    public String uploadImage(File file) {
        DataAccess da = new DataAccess();
        return da.uploadImage(file);
    }
}
