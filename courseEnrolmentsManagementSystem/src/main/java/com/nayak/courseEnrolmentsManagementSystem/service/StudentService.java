package com.nayak.courseEnrolmentsManagementSystem.service;

import com.nayak.courseEnrolmentsManagementSystem.dao.DAOStudent;
import com.nayak.courseEnrolmentsManagementSystem.exception.IdNotFoundException;
import com.nayak.courseEnrolmentsManagementSystem.model.Course;
import com.nayak.courseEnrolmentsManagementSystem.model.ResponseStructure;
import com.nayak.courseEnrolmentsManagementSystem.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    private DAOStudent dao;

//    @Autowired
//    StudentRepository studentRepository;


    public ResponseEntity<ResponseStructure<Student>> saveStudent(Student student) {
//        return
        ResponseStructure<Student> responseStructure = new ResponseStructure<>();
        logger.info("Student data: {}", student);
        String status = dao.saveStudent(student);
        if ("success".equals(status)) {
            responseStructure.setMessage(status);
            responseStructure.setStatusCode(HttpStatus.OK.value());
            responseStructure.setData(student);
            return new ResponseEntity<>(responseStructure, HttpStatus.OK);
        } else {
            throw new IdNotFoundException(status);
        }
    }

    public ResponseEntity<ResponseStructure<List<Student>>> getStudent() {
//        return
        ResponseStructure<List<Student>> str = new ResponseStructure<>();
        List<Student> studentList = dao.getStudent();
        if (studentList.size() >0){
            str.setStatusCode(HttpStatus.OK.value());
            str.setMessage("success");
            str.setData(studentList);
            return new ResponseEntity<ResponseStructure<List<Student>>>(str,HttpStatus.OK);
        }else {
            throw new IdNotFoundException("not found");
        }
    }

    public ResponseEntity<ResponseStructure<Student>> getStudentById(int id) {
//        return
        ResponseStructure<Student> str = new ResponseStructure<>();
        Student student = dao.getStudentById(id);
        if(student != null){
            str.setData(student);
            str.setStatusCode(HttpStatus.OK.value());
            str.setMessage("success");
            return new ResponseEntity<ResponseStructure<Student>>(str, HttpStatus.OK);
        } else {
            throw new IdNotFoundException("id not found");
        }
    }
    
    public ResponseEntity<ResponseStructure<Student>> updateStdDt(Student std) {
//		return
        ResponseStructure<Student> str = new ResponseStructure<Student>();
        logger.info("Student data for update: {}", std);
        if(std.getId() <= 0){
            str.setData(std);
            str.setMessage("id is not available");
            str.setStatusCode(HttpStatus.OK.value());
            return new ResponseEntity<ResponseStructure<Student>>(str, HttpStatus.OK);
        }

        Student stdUp = dao.updateStdDa(std);
        if(stdUp != null) {
            str.setStatusCode(HttpStatus.OK.value());
            str.setData(stdUp);
            str.setMessage("success");
            return new ResponseEntity<ResponseStructure<Student>>(str, HttpStatus.OK);
        } else {
            throw new IdNotFoundException("id not found");
        }
	}

    public ResponseEntity<ResponseStructure<Student>> deleteStd(int id) {
//        return
        logger.info("student id: {}", id);
        ResponseStructure<Student> str = new ResponseStructure<>();
        Student std = dao.deleteStd(id);
        if(std != null) {
            str.setStatusCode(HttpStatus.OK.value());
            str.setData(std);
            str.setMessage("success");
            return new ResponseEntity<ResponseStructure<Student>>(str, HttpStatus.OK);
        } else {
            throw new IdNotFoundException("id not found");
        }
    }

    public ResponseEntity<ResponseStructure<List<Course>>> getCourseByStudentId(int id) {
//        return
        ResponseStructure<List<Course>> str = new ResponseStructure<>();
        logger.info("student id: {}", id);
        if(id <= 0){
            str.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
            str.setMessage("id is invalid");
            return new ResponseEntity<>(str,HttpStatus.OK);
        }
        List<Course> cl = dao.getCourseByStudentId(id);
        if(cl != null && !cl.isEmpty()){
            str.setMessage("success");
            str.setData(cl);
            str.setStatusCode(HttpStatus.OK.value());
        } else {
            throw new IdNotFoundException("data not found");
        }
        return new ResponseEntity<>(str, HttpStatus.OK);
    }
}