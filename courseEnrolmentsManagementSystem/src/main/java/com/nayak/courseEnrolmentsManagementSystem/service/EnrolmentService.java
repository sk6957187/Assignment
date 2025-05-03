package com.nayak.courseEnrolmentsManagementSystem.service;


import com.nayak.courseEnrolmentsManagementSystem.dao.DAOEnrolment;
import com.nayak.courseEnrolmentsManagementSystem.exception.IdNotFoundException;
import com.nayak.courseEnrolmentsManagementSystem.model.Enrolment;
import com.nayak.courseEnrolmentsManagementSystem.model.ResponseStructure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrolmentService {
    private final static Logger logger = LoggerFactory.getLogger(EnrolmentService.class);

    @Autowired
    private DAOEnrolment daoenrolment;

    public ResponseEntity<ResponseStructure<Enrolment>> saveEnrolment(Enrolment enrolment) {
        ResponseStructure<Enrolment> str = new ResponseStructure<>();
        logger.info("Enrolment data: {}",enrolment);
        String status = daoenrolment.saveEnrolment(enrolment);
        if(status == "success"){
            str.setStatusCode(HttpStatus.OK.value());
            str.setMessage(status);
            str.setData(enrolment);
        } else {
            str.setMessage(status);
            str.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
        }
        return new ResponseEntity<>(str,HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<List<Enrolment>>> getAllEnrolment() {
        ResponseStructure<List<Enrolment>> str = new ResponseStructure<>();
        List<Enrolment> le = daoenrolment.getAllEnrolment();
        if(le.size()>0){
            str.setStatusCode(HttpStatus.OK.value());
            str.setMessage("success");
            str.setData(le);
        }else{
            throw new IdNotFoundException("data not available");
        }
        return new ResponseEntity<>(str, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<Enrolment>> deleteEnrolment(int id) {
        ResponseStructure<Enrolment> str = new ResponseStructure<>();
        if(id<=0){
            str.setMessage("id not valid");
            str.setStatusCode(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(str,HttpStatus.OK);
        }

        Enrolment enrolment = daoenrolment.deleteEnrolment(id);
        if(enrolment != null){
            str.setMessage("success");
            str.setData(enrolment);
            str.setStatusCode(HttpStatus.OK.value());
        }else{
            throw new IdNotFoundException("id not found");
        }
        return new ResponseEntity<>(str, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<Enrolment>> updateById(int id, Enrolment enrolment) {
        ResponseStructure<Enrolment> str = new ResponseStructure<>();
        if(id <= 0){
            str.setStatusCode(HttpStatus.NOT_FOUND.value());
            str.setMessage("invalid id");
            return new ResponseEntity<>(str,HttpStatus.OK);
        }
        logger.info("data for update: {}",enrolment);
        Enrolment upEnr = daoenrolment.updateById(id, enrolment);
        if(upEnr != null){
            str.setMessage("success");
            str.setData(upEnr);
            str.setStatusCode(HttpStatus.OK.value());
        }else{
            throw new IdNotFoundException("data is not available");
        }
        return new ResponseEntity<>(str,HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<List<Enrolment>>> getEnrolmentByStudentId(int id) {
        ResponseStructure<List<Enrolment>> str = new ResponseStructure<>();
        logger.info("Student id: {}", id);
        if(id <= 0){
            str.setMessage("id is invalid");
            str.setStatusCode(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(str, HttpStatus.OK);
        }
        List<Enrolment> el = daoenrolment.getEnrolmentByStudentId(id);
        if (el != null && !el.isEmpty()) {
            str.setStatusCode(HttpStatus.OK.value());
            str.setMessage("success");
            str.setData(el);
            return new ResponseEntity<>(str, HttpStatus.OK);
        } else {
            throw new IdNotFoundException("data not found");
        }
    }

    public ResponseEntity<ResponseStructure<List<Enrolment>>> getEnrolmentByCourseId(int cId) {
        ResponseStructure<List<Enrolment>> str = new ResponseStructure<>();
        logger.info("Course id: {}", cId);
        if(cId <= 0){
            str.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
            str.setMessage("id is invalid");
            return new ResponseEntity<>(str, HttpStatus.OK);
        }
        List<Enrolment> le = daoenrolment.getEnrolmentByCourseId(cId);
        if(le != null && !le.isEmpty()){
            str.setMessage("success");
            str.setData(le);
            str.setStatusCode(HttpStatus.OK.value());
        } else {
            throw new IdNotFoundException("data not found");
        }
        return new ResponseEntity<>(str, HttpStatus.OK);
    }
}
