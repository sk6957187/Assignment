package io.mountblue.BlogApplication.exception;

import io.mountblue.BlogApplication.entity.ResponseStructure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleDataNotFoundException(NoDataFoundException exception) {

        ResponseStructure<String> resp = new ResponseStructure<>();
        resp.setMessage(exception.getMessage());
        resp.setData(null);
        resp.setStatus(HttpStatus.NO_CONTENT.value());

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
