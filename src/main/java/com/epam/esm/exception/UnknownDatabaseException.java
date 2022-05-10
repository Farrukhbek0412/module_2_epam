package com.epam.esm.exception;

public class UnknownDatabaseException extends RuntimeException {

    public UnknownDatabaseException(String message) {
        super(message);
    }
}