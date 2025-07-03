package com.nayak.cloudStorage.controller;

import com.nayak.cloudStorage.model.BioData;
import com.nayak.cloudStorage.service.GoogleCloudService;
import com.nayak.cloudStorage.model.Res;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@CrossOrigin
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
//            Res res = service.uploadImageToDrive(temFile);
            String imageLink = service.uploadFile(temFile);
            return imageLink;
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

        BioData bd = new BioData();
        bd.setName(name);
        bd.setAge(Integer.parseInt(age));
        bd.setDob(LocalDate.parse(dob));
        bd.setAddress(address);

        if (image != null && !image.isEmpty()) {
            logger.info("Received Image: {}", image.getOriginalFilename());
            String extension = image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf('.'));
            File tempFile = File.createTempFile("temp-image", extension);
            image.transferTo(tempFile);
            String imageLink = service.uploadFile(tempFile);
            logger.info("Uploaded image to Google Drive: {}", imageLink);
            bd.setImage(imageLink);
            tempFile.delete();
        }

        if (video != null && !video.isEmpty()) {
            logger.info("Received Video: {}", video.getOriginalFilename());
            String extension = video.getOriginalFilename().substring(video.getOriginalFilename().lastIndexOf('.'));
            File tempFile = File.createTempFile("temp-video", extension);
            video.transferTo(tempFile);
            String videoLink = service.uploadFile(tempFile);
            logger.info("Uploaded video to Google Drive: {}", videoLink);
            bd.setVideo(videoLink);
            tempFile.delete();
        }

        if (audio != null && !audio.isEmpty()) {
            logger.info("Received Audio: {}", audio.getOriginalFilename());
            String extension = audio.getOriginalFilename().substring(audio.getOriginalFilename().lastIndexOf('.'));
            File tempFile = File.createTempFile("temp-audio", extension);
            audio.transferTo(tempFile);
            String audioLink = service.uploadFile(tempFile);
            logger.info("Uploaded audio to Google Drive: {}", audioLink);
            bd.setAudio(audioLink);
            tempFile.delete();
        }

        if (textFile != null && !textFile.isEmpty()) {
            logger.info("Received Text File: {}", textFile.getOriginalFilename());
            String extension = textFile.getOriginalFilename().substring(textFile.getOriginalFilename().lastIndexOf('.'));
            File tempFile = File.createTempFile("temp-text", extension);
            textFile.transferTo(tempFile);
            String textLink = service.uploadFile(tempFile);
            logger.info("Uploaded text file to Google Drive: {}", textLink);
            bd.setTextFile(textLink);
            tempFile.delete();
        }

        BioData newBd = service.setBioData(bd);

        return ResponseEntity.ok("Form data uploaded successfully!\n "+newBd);
    }

    
    @GetMapping("/view")
    public ResponseEntity<List<BioData>> getBiodata(){
        List<BioData> bd = service.getBioData();
        return new ResponseEntity<>(bd, HttpStatusCode.valueOf(HttpStatus.SC_OK));
    }

}
