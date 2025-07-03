package com.nayak.DataBaseConverterWeb.exception;

import com.nayak.DataBaseConverterWeb.entity.ResponseStructure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleDataNotFound(DataNotFoundException exception){
        ResponseStructure<String> resp = new ResponseStructure<>();
        resp.setStatusCode(HttpStatus.NOT_FOUND.value());
        resp.setMsg("Data not found..!!");
        resp.setData(exception.getMessage());
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
