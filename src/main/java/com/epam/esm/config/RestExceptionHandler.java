package com.epam.esm.config;

import com.epam.esm.dto.BaseExceptionDTO;
import com.epam.esm.exception.BaseException;
import com.epam.esm.exception.DataNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<?> baseExceptionHandler(BaseException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(new BaseExceptionDTO(e.getStatus(), e.getMessage()));
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<?> dataNotFoundExceptionHandler(DataNotFoundException e){
        return ResponseEntity.status(404).body(new BaseExceptionDTO(e.getMessage()));
    }
}