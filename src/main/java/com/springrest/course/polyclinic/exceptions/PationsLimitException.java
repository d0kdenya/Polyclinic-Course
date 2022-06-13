package com.springrest.course.polyclinic.exceptions;

public class PationsLimitException extends Exception{
    public PationsLimitException(String message) {
        super(message);
    }
}
