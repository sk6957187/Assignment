package com.nayak.courseEnrolmentsManagementSystem.controller;

import com.nayak.courseEnrolmentsManagementSystem.exception.IdNotFoundException;
import com.nayak.courseEnrolmentsManagementSystem.model.Enrolment;
import com.nayak.courseEnrolmentsManagementSystem.model.ResponseStructure;
import com.nayak.courseEnrolmentsManagementSystem.service.EnrolmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course-enrolments/enrolment")
public class EnrolmentController {
    private static final Logger logger = LoggerFactory.getLogger(EnrolmentController.class);

    @Autowired
    private EnrolmentService enrolmentService;

    @PostMapping
    public ResponseEntity<ResponseStructure<Enrolment>> saveEnrolment(@RequestBody Enrolment enrolment){
        return enrolmentService.saveEnrolment(enrolment);

    }

    @GetMapping
    public ResponseEntity<ResponseStructure<List<Enrolment>>> getAllEnrrolment(){
        return enrolmentService.getAllEnrolment();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<Enrolment>> deleteEnro(@PathVariable int id){
        return enrolmentService.deleteEnrolment(id);

    }
    @PutMapping("/{id}")
    public ResponseEntity<ResponseStructure<Enrolment>> updateById(@RequestBody Enrolment enrolment,
                                                                   @PathVariable int id){
        return enrolmentService.updateById(id, enrolment);

    }

    @GetMapping("/student/{id}")
    public ResponseEntity<ResponseStructure<List<Enrolment>>> getEnrolmentByStudentId(@PathVariable int id){
        return enrolmentService.getEnrolmentByStudentId(id);
    }
    @GetMapping("/course/{cId}")
    public ResponseEntity<ResponseStructure<List<Enrolment>>> getEnrolmentByCourseId(@PathVariable int cId){
        return enrolmentService.getEnrolmentByCourseId(cId);
    }

}
