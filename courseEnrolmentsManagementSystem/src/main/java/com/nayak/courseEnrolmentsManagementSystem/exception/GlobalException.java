package com.nayak.courseEnrolmentsManagementSystem.exception;

import com.nayak.courseEnrolmentsManagementSystem.model.ResponseStructure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleIdNotFoundException(IdNotFoundException exception){
        ResponseStructure<String> resp = new ResponseStructure<>();
        resp.setStatusCode(HttpStatus.NOT_FOUND.value());
        resp.setMessage("ID Not Found");
        resp.setData(exception.getMessage());  // add this to show the actual message
        return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(StudentIdNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleStudentIdNotFoundException(IdNotFoundException exception){
        ResponseStructure<String> resp=new ResponseStructure<>();
        resp.setStatusCode(HttpStatus.NOT_FOUND.value());
        resp.setMessage("Student ID Not Found");
        return new ResponseEntity<>(resp,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InstructorIdNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleInstructorIdNotFoundException(IdNotFoundException exception){
        ResponseStructure<String> resp=new ResponseStructure<>();
        resp.setStatusCode(HttpStatus.NOT_FOUND.value());
        resp.setMessage("Instructor ID Not Found");
        return new ResponseEntity<>(resp,HttpStatus.NOT_FOUND);
    }

}
