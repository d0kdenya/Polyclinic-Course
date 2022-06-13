package com.springrest.course.polyclinic.exceptions;

public class EmptyPatientsException extends Exception {
    public EmptyPatientsException(String message) {
        super(message);
    }
}
