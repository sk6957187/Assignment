package com.AryabhataTech.company_portal.exception;

import com.AryabhataTech.company_portal.model.ResponseStructure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(DataNotFound.class)
    public ResponseEntity<ResponseStructure<String>> handleDataNotFound(DataNotFound ex){
        ResponseStructure<String> res = new ResponseStructure<>();
        res.setMessage("Data not found");
        res.setData(ex.getMessage());
        res.setStatus(HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }


}
