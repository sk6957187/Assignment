package com.nayak.cloudStorage.controller;

import com.nayak.cloudStorage.model.BioData;
import com.nayak.cloudStorage.service.GoogleCloudService;
import com.nayak.cloudStorage.model.Res;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;


@RestController
@RequestMapping("/")
public class GoogleCloudController {
    private static final Logger logger = LoggerFactory.getLogger(GoogleCloudController.class);
    @Autowired
    private GoogleCloudService service;

    @PostMapping("/uploadToGoogleDrive")
    public Object handleFileUpload(@RequestParam("image") MultipartFile file) throws IOException {
        if(file.isEmpty()){
            return "File is empty";
        }
        File temFile = File.createTempFile("temp", null);
        try {
            file.transferTo(temFile);
            Res res = service.uploadImageToDrive(temFile);
            return res;
        } finally {
            temFile.delete();
        }
    }

    @PostMapping("/sql/add-data")
    public ResponseEntity<String> handleFormUpload (
            @RequestParam("name") String name,
            @RequestParam("age") String age,
            @RequestParam("dob") String dob,
            @RequestParam("address") String address,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam(value = "video", required = false) MultipartFile video,
            @RequestParam(value = "audio", required = false) MultipartFile audio,
            @RequestParam(value = "textFile", required = false) MultipartFile textFile
    ) throws IOException {
        Res res = null;
        BioData bd = new BioData();
        bd.setName(name);
        bd.setAge(Integer.parseInt(age));
        bd.setDob(LocalDate.parse(dob));
        bd.setAddress(address);

        if (image != null && !image.isEmpty()){
            logger.info("Received Image: {}", image.getOriginalFilename());
            File tempFile = File.createTempFile("temp-image", null);
            image.transferTo(tempFile);
            res = service.uploadImageToDrive(tempFile);
            logger.info("Uploaded image to Google Drive: {}", res.getUrl());
            bd.setImage(res.getUrl());
            tempFile.delete();
        }
        if (video != null){
            logger.info("Received Video: {}", video.getOriginalFilename());
        }
        if (audio != null){
            logger.info("Received Audio: {}", audio.getOriginalFilename());
        }
        if (textFile != null){
            logger.info("Received Text File: {}", textFile.getOriginalFilename());
        }

        return ResponseEntity.ok("Form data uploaded successfully!\n "+res.getUrl());
    }



}
