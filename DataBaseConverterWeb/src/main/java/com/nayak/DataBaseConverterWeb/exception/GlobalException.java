package com.nayak.DataBaseConverterWeb.exception;

import com.nayak.DataBaseConverterWeb.entity.ResponseStructure;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleDataNotFound(DataNotFoundException exception){
        ResponseStructure<String> resp = new ResponseStructure<>();
        resp.setStatusCode(HttpStatus.NOT_FOUND.value());
        resp.setMsg("Data not found..!!");
        resp.setData(exception.getMessage());
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNoHandlerFound(NoHandlerFoundException exception, HttpServletRequest request, Model model) {
        int statusCode = 404;
        String errorMessage = "Page Not Found";
        
        model.addAttribute("statusCode", statusCode);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("message", "The requested endpoint does not exist");
        model.addAttribute("exception", exception);
        
        return "error";
    }
}
