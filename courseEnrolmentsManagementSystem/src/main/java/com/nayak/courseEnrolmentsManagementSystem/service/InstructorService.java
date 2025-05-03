package com.nayak.courseEnrolmentsManagementSystem.service;


import com.nayak.courseEnrolmentsManagementSystem.dao.DAOInstructor;
import com.nayak.courseEnrolmentsManagementSystem.exception.IdNotFoundException;
import com.nayak.courseEnrolmentsManagementSystem.model.Instructor;

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
public class InstructorService {
    private static final Logger logger = LoggerFactory.getLogger(InstructorService.class);
    
    @Autowired
    private DAOInstructor daoInstructor;
    
    public ResponseEntity<ResponseStructure<Instructor>> saveInstructor(Instructor instructor) {
        ResponseStructure<Instructor> responseStructure = new ResponseStructure<>();
        logger.info("Student data: {}", instructor);
        String status = daoInstructor.saveInstructor(instructor);
        if ("success".equals(status)) {
            responseStructure.setMessage(status);
            responseStructure.setStatusCode(HttpStatus.OK.value());
            responseStructure.setData(instructor);
            return new ResponseEntity<>(responseStructure, HttpStatus.OK);
        } else {
//            responseStructure.setMessage(status);
//            responseStructure.setStatusCode(HttpStatus.FAILED_DEPENDENCY.value());
//            return new ResponseEntity<>(responseStructure, HttpStatus.FAILED_DEPENDENCY);
            throw new IdNotFoundException(status);
        }
    }

    public ResponseEntity<ResponseStructure<List<Instructor>>> getInstructor() {
//        return
        ResponseStructure<List<Instructor>> str = new ResponseStructure<>();
        List<Instructor> instructorList = daoInstructor.getInstructor();
        if (instructorList.size() >0){
            str.setStatusCode(HttpStatus.OK.value());
            str.setMessage("success");
            str.setData(instructorList);
            return new ResponseEntity<ResponseStructure<List<Instructor>>>(str,HttpStatus.OK);
        }else {
//            str.setMessage("id not found");
//            str.setStatusCode(HttpStatus.OK.value());
//            return new ResponseEntity<ResponseStructure<List<Instructor>>>(str,HttpStatus.OK);
            throw new IdNotFoundException("id not found");
        }
    }

    public ResponseEntity<ResponseStructure<Instructor>> getInstructorById(int id) {
        ResponseStructure<Instructor> str = new ResponseStructure<>();
        Instructor instructor = daoInstructor.getInstructorById(id);
        if(instructor != null){
            str.setData(instructor);
            str.setStatusCode(HttpStatus.OK.value());
            str.setMessage("success");
            return new ResponseEntity<ResponseStructure<Instructor>>(str, HttpStatus.OK);
        } else {
//            str.setStatusCode(HttpStatus.OK.value());
//            str.setMessage("id not found");
//            return new ResponseEntity<ResponseStructure<Instructor>>(str, HttpStatus.OK);
            throw new IdNotFoundException("id not found");
        }
    }

    public ResponseEntity<ResponseStructure<Instructor>> updateInstructorDt(Instructor instructor) {
//        return
        ResponseStructure<Instructor> str = new ResponseStructure<Instructor>();
        logger.info("Student data for update: {}", instructor);
        int id = instructor.getId();
        logger.info("id: {}",id);
        if(instructor.getId() <= 0){
            str.setData(instructor);
            str.setMessage("id is not available");
            str.setStatusCode(HttpStatus.OK.value());
            return new ResponseEntity<ResponseStructure<Instructor>>(str, HttpStatus.OK);
        }

        Instructor instUp = daoInstructor.updateInstructor(instructor);
        if(instUp != null) {
            str.setStatusCode(HttpStatus.OK.value());
            str.setData(instUp);
            str.setMessage("success");
            return new ResponseEntity<ResponseStructure<Instructor>>(str, HttpStatus.OK);
        } else {
//            str.setMessage("id not found");
//            str.setStatusCode(HttpStatus.OK.value());
//            return new ResponseEntity<ResponseStructure<Instructor>>(str, HttpStatus.OK);
            throw new IdNotFoundException("id not found");
        }
    }

    public ResponseEntity<ResponseStructure<Instructor>> deleteInstructor(int id) {
//        return
        logger.info("student id: {}", id);

        ResponseStructure<Instructor> str = new ResponseStructure<>();
        if(id <= 0){
            str.setMessage("id not found");
            str.setStatusCode(HttpStatus.OK.value());
            return new ResponseEntity<ResponseStructure<Instructor>>(str, HttpStatus.OK);
        }
        Instructor instructor = daoInstructor.deleteInstructorById(id);
        if(instructor != null) {
            str.setStatusCode(HttpStatus.OK.value());
            str.setData(instructor);
            str.setMessage("success");
            return new ResponseEntity<ResponseStructure<Instructor>>(str, HttpStatus.OK);
        } else {
//            str.setMessage("id not found");
//            str.setStatusCode(HttpStatus.OK.value());
//            return new ResponseEntity<ResponseStructure<Instructor>>(str, HttpStatus.OK);
            throw new IdNotFoundException("id not found");
        }
    }

    public ResponseEntity<ResponseStructure<List<Student>>> getStudentByInstId(int id) {
//        return
        logger.info("instructor id: {}", id);
        ResponseStructure<List<Student>> str = new ResponseStructure<>();
        if(id <= 0){
            str.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
            str.setMessage("id is invalid");
            return new ResponseEntity<>(str, HttpStatus.OK);
        }
        List<Student> sl = daoInstructor.getStudentByInstId(id);
        if(sl.size()>0){
            str.setMessage("success");
            str.setStatusCode(HttpStatus.OK.value());
            str.setData(sl);
        }else{
//            str.setMessage("data not found");
//            str.setStatusCode(HttpStatus.NOT_FOUND.value());
            throw new IdNotFoundException("data not found");
        }
        return new ResponseEntity<>(str,HttpStatus.OK);
    }
}
