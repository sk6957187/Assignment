package com.nayak.courseEnrolmentsManagementSystem.controller;

import com.nayak.courseEnrolmentsManagementSystem.exception.IdNotFoundException;
import com.nayak.courseEnrolmentsManagementSystem.model.Instructor;
import com.nayak.courseEnrolmentsManagementSystem.model.ResponseStructure;
import com.nayak.courseEnrolmentsManagementSystem.model.Student;
import com.nayak.courseEnrolmentsManagementSystem.service.InstructorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course-enrolments/instructor")
public class InstructorController {

    private static final Logger logger = LoggerFactory.getLogger(InstructorController.class);

    @Autowired
    private InstructorService service;

    @PostMapping
    public ResponseEntity<ResponseStructure<Instructor>> saveStudent(@RequestBody Instructor instructor) {
        return service.saveInstructor(instructor);
    }

    @GetMapping
    public ResponseEntity<ResponseStructure<List<Instructor>>> getInstructor(){
        return service.getInstructor();

    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<Instructor>> getStudentById(@PathVariable int id){
        return service.getInstructorById(id);

    }

    @PutMapping("/update")
    public ResponseEntity<ResponseStructure<Instructor>> updateStd(@RequestBody Instructor instructor){
        return service.updateInstructorDt(instructor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<Instructor>> deleteInstructor(@PathVariable int id){
        return service.deleteInstructor(id);

    }
    @GetMapping("/student/{id}")
    public ResponseEntity<ResponseStructure<List<Student>>> getStudentByInstId(@PathVariable int id){
        return service.getStudentByInstId(id);

    }
}
