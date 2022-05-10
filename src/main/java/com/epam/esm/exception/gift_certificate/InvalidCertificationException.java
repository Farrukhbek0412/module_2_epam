package com.epam.esm.exception.gift_certificate;

public class InvalidCertificationException extends RuntimeException{
    public InvalidCertificationException(String message){
        super(message);
    }
}