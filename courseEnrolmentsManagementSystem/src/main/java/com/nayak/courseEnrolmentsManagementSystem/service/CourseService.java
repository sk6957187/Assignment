package com.nayak.courseEnrolmentsManagementSystem.service;

import com.nayak.courseEnrolmentsManagementSystem.dao.DAOCourse;
import com.nayak.courseEnrolmentsManagementSystem.exception.IdNotFoundException;
import com.nayak.courseEnrolmentsManagementSystem.model.Course;
import com.nayak.courseEnrolmentsManagementSystem.model.ResponseStructure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private static final Logger logger = LoggerFactory.getLogger(CourseService.class);

    @Autowired
    private DAOCourse daoCourse;


    public ResponseEntity<ResponseStructure<Course>> saveCourse(Course course) {
        String courseUp = daoCourse.saveCourse(course);
        ResponseStructure<Course> str = new ResponseStructure<>();
        if(courseUp == "success"){
            str.setStatusCode(HttpStatus.OK.value());
            str.setData(course);
            str.setMessage(courseUp);
            return new ResponseEntity<>(str, HttpStatus.OK);
        } else {
            throw new IdNotFoundException(courseUp);
        }
    }

    public ResponseEntity<ResponseStructure<List<Course>>> getAllCourse() {
        ResponseStructure<List<Course>> str = new ResponseStructure<>();
        List<Course> cl = daoCourse.getAllCourse();
        if(cl.size()>0){
            str.setStatusCode(HttpStatus.OK.value());
            str.setMessage("success");
            str.setData(cl);
        }else {
            str.setMessage("course not available");
            str.setStatusCode(HttpStatus.NOT_FOUND.value());
        }
        return new ResponseEntity<>(str,HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<Course>> getCourseById(int id) {
        ResponseStructure<Course> str = new ResponseStructure<>();
        Course course = daoCourse.getCourseById(id);;
        if(course != null){
            str.setMessage("success");
            str.setStatusCode(HttpStatus.OK.value());
            str.setData(course);
        }else{
            throw new IdNotFoundException("course not found");
        }
        return new ResponseEntity<>(str,HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<Course>> updateCourse(Course course) {
//        return daoCourse.updateCourse(course);
        ResponseStructure<Course> str = new ResponseStructure<>();
        if(course.getId() <= 0){
            str.setMessage("Id is not available");
            str.setData(course);
            str.setStatusCode(HttpStatus.NO_CONTENT.value());
        }
        Course upCourse = daoCourse.updateCourse(course);
        if(upCourse != null){
            str.setData(upCourse);
            str.setMessage("update");
            str.setStatusCode(HttpStatus.OK.value());
        } else {
            throw new IdNotFoundException("course not updated");
        }
        return new ResponseEntity<>(str, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<Course>> deleteCourse(int id) {
        ResponseStructure<Course> str = new ResponseStructure<>();
        if(id <= 0){
            str.setMessage("id invalid");
            str.setStatusCode(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(str, HttpStatus.OK);
        }
        Course course = daoCourse.deleteCourseById(id);
        if(course != null){
            str.setStatusCode(HttpStatus.OK.value());
            str.setData(course);
            str.setMessage("success");
        }else {
            throw new IdNotFoundException("not delete");
        }
        return new ResponseEntity<>(str,HttpStatus.OK);
//        return daoCourse.deleteCourseById(id);
    }

    public ResponseEntity<ResponseStructure<List<Course>>> getAllCourseByInstId(int id) {
//        return daoCourse.getAllCourseByInstId(id);
        List<Course> cl = daoCourse.getAllCourseByInstId(id);
        ResponseStructure<List<Course>> str = new ResponseStructure<>();
        if(cl != null && !cl.isEmpty()){
            str.setMessage("success");
            str.setStatusCode(HttpStatus.OK.value());
            str.setData(cl);
        } else {
            throw new IdNotFoundException("data not available");
        }
        return new ResponseEntity<>(str,HttpStatus.OK);
    }
}
