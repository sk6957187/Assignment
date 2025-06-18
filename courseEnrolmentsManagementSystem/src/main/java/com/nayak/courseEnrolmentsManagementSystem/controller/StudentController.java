package com.nayak.courseEnrolmentsManagementSystem.controller;

import java.util.List;

import com.nayak.courseEnrolmentsManagementSystem.exception.IdNotFoundException;
import com.nayak.courseEnrolmentsManagementSystem.model.Course;
import com.nayak.courseEnrolmentsManagementSystem.model.ResponseStructure;
import com.nayak.courseEnrolmentsManagementSystem.model.Student;
import com.nayak.courseEnrolmentsManagementSystem.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/course-enrolments/student")
public class StudentController {
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService service;

    @PostMapping
    public ResponseEntity<ResponseStructure<Student>> saveStudent(@RequestBody Student student) {
        return service.saveStudent(student);

    }

    @GetMapping
    public ResponseEntity<ResponseStructure<List<Student>>> getAllStudent(){
        return service.getStudent();

    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<Student>> getStudentById(@PathVariable int id){
        return service.getStudentById(id);

    }

    @PutMapping("/update")
    public ResponseEntity<ResponseStructure<Student>> updateStd(@RequestBody Student std){
    	return service.updateStdDt(std);


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<Student>> deleteStd(@PathVariable int id){
        return service.deleteStd(id);

    }

    @GetMapping("/course/{id}")
    public ResponseEntity<ResponseStructure<List<Course>>> getCourseByStudentId(@PathVariable int id){
        return service.getCourseByStudentId(id);

    }

}
