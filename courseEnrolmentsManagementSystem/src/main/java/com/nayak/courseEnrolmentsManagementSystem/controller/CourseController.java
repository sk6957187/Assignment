package com.nayak.courseEnrolmentsManagementSystem.controller;

import com.nayak.courseEnrolmentsManagementSystem.exception.IdNotFoundException;
import com.nayak.courseEnrolmentsManagementSystem.model.Course;
import com.nayak.courseEnrolmentsManagementSystem.model.ResponseStructure;
import com.nayak.courseEnrolmentsManagementSystem.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/course-enrolments/course")
public class CourseController {
    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private CourseService courseService;


    @PostMapping
    public ResponseEntity<ResponseStructure<Course>> saveCourse(@RequestBody Course course){
        logger.info("Course data ");
        return courseService.saveCourse(course);
    }

    @GetMapping
    public ResponseEntity<ResponseStructure<List<Course>>> getAllCourse(){
        return courseService.getAllCourse();

    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseStructure<Course>> getCourseById(@PathVariable int id){
        return courseService.getCourseById(id);
    }

    @PutMapping
    public ResponseEntity<ResponseStructure<Course>> updateCourse(@RequestBody Course course){
        logger.info("data for course: {}",course);
        return courseService.updateCourse(course);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<Course>> deleteData(@PathVariable int id){
        logger.info("course id: {}",id);
        return courseService.deleteCourse(id);
    }

    @GetMapping("/instructor/{id}")
    public ResponseEntity<ResponseStructure<List<Course>>> getCourseByInstructorId(@PathVariable int id){
        logger.info("Instructor id {}",id);
        return courseService.getAllCourseByInstId(id);
    }
}
