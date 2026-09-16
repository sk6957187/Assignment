package com.example.Diet_Plan.exceptions;

import com.example.Diet_Plan.model.ResponseStructure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseStructure<String>> handleException(Exception ex){
        ResponseStructure<String> res = new ResponseStructure<>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setMsg(ex.getMessage());

        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataNotFound.class)
    public ResponseEntity<ResponseStructure<String>> handleDataNotFound(DataNotFound ex){
        ResponseStructure<String> res = new ResponseStructure<>();
        res.setStatusCode(HttpStatus.NO_CONTENT.value());
        res.setMsg(ex.getMessage());
        res.getData();

        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }

}
