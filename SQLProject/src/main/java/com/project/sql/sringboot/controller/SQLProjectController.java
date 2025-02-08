package com.project.sql.sringboot.controller;

import com.project.sql.sringboot.service.SqlService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/")
public class SQLProjectController {

    // In-memory list to store student data
    private final List<Student> students = new ArrayList<>();
    public static SqlService sqlService;

    // GET method to return stored data
    @GetMapping("/")
    public List<Student> showData() {
        System.out.println("Fetching student data...");
        if (students.isEmpty()) {
            return sqlService.getData();
//            return List.of(new Student("No data available", 0, "N/A", null));
        }
//        System.out.println(students);
        return students;
    }

    // POST method to receive and store data
    @PostMapping(value = "/sql", consumes = {"multipart/form-data"})
    public String addStudent(@RequestPart("student") Student student, @RequestPart("photo") MultipartFile photo) {
        try {
            // Save the photo as a byte array
            student.setPhoto(photo.getBytes());

            // Add the student to the in-memory list
            students.add(student);

            return "Student data added successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to add student data: " + e.getMessage();
        }
    }
}
