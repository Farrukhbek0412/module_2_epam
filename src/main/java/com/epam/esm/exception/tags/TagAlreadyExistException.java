package com.epam.esm.exception.tags;

public class TagAlreadyExistException extends RuntimeException{

    public TagAlreadyExistException(String message){
        super(message);
    }
}