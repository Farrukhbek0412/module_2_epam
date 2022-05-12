package com.epam.esm.config;

import com.epam.esm.dto.BaseExceptionDTO;
import com.epam.esm.exception.BaseException;
import com.epam.esm.exception.DataNotFoundException;
import com.epam.esm.exception.UnknownDatabaseException;
import com.epam.esm.exception.gift_certificate.InvalidCertificationException;
import com.epam.esm.exception.tags.TagAlreadyExistException;
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
        return ResponseEntity.status(404).body(new BaseExceptionDTO(404,e.getMessage()));
    }

    @ExceptionHandler(UnknownDatabaseException.class)
    public ResponseEntity<?> unknownDatabaseExceptionHandler(UnknownDatabaseException e){
        return ResponseEntity.status(400).body(new BaseExceptionDTO(400,e.getMessage()));
    }

    @ExceptionHandler(InvalidCertificationException.class)
    public ResponseEntity<?> invalidCertificationDatabaseExceptionHandler(InvalidCertificationException e){
        return ResponseEntity.status(400).body(new BaseExceptionDTO(400,e.getMessage()));
    }
    @ExceptionHandler(TagAlreadyExistException.class)
    public ResponseEntity<?> tagAlreadyExistHandler(TagAlreadyExistException e){
        return ResponseEntity.status(400).body(new BaseExceptionDTO(400,e.getMessage()));
    }


}