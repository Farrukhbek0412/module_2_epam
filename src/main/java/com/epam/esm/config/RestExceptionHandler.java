package com.epam.esm.config;

import com.epam.esm.dto.BaseExceptionDTO;
import com.epam.esm.exception.*;
import com.epam.esm.exception.gift_certificate.InvalidCertificationException;
import com.epam.esm.exception.tags.TagAlreadyExistException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> dataNotFoundExceptionHandler(DataNotFoundException e) {
        return ResponseEntity.status(404).body(new BaseExceptionDTO(404, e.getMessage()));
    }

    @ExceptionHandler(UnknownDatabaseException.class)
    public ResponseEntity<?> unknownDatabaseExceptionHandler(UnknownDatabaseException e) {
        return ResponseEntity.status(400).body(new BaseExceptionDTO(400, e.getMessage()));
    }

    @ExceptionHandler(InvalidCertificationException.class)
    public ResponseEntity<?> invalidCertificationDatabaseExceptionHandler(InvalidCertificationException e) {
        return ResponseEntity.status(400).body(new BaseExceptionDTO(400, e.getMessage()));
    }

    @ExceptionHandler(TagAlreadyExistException.class)
    public ResponseEntity<?> tagAlreadyExistHandler(TagAlreadyExistException e) {
        return ResponseEntity.status(400).body(new BaseExceptionDTO(400, e.getMessage()));
    }

    @ExceptionHandler({
            JsonMappingException.class,
            JsonProcessingException.class, InvalidFormatException.class
    })
    public final ResponseEntity<?> handleJsonException(final RuntimeException exc) {


        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new BaseExceptionDTO(HttpStatus.UNPROCESSABLE_ENTITY.value(), exc.getMessage()));
    }
}