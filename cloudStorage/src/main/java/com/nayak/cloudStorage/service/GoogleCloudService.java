package com.nayak.cloudStorage.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.nayak.cloudStorage.dto.DataTransactionControl;
import com.nayak.cloudStorage.model.BioData;
import com.nayak.cloudStorage.model.Res;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@org.springframework.stereotype.Service
public class GoogleCloudService {

    @Autowired
    private DataTransactionControl dc;

    public String uploadFile(File file) {
        Res res = new Res();
        String url = dc.uploadImage(file);
//        if(url != null){
//            res.setStatus(200);
//            res.setMessage("Image successfully uploaded to drive");
//            res.setUrl(url);
//        } else {
//            res.setStatus(500);
//            res.setMessage(null);
//        }
        return url;
    }


    public List<BioData> getBioData() {
        return dc.getBioData();
    }

    public BioData setBioData(BioData bd) {
        return dc.saveBioData(bd);
    }
}
