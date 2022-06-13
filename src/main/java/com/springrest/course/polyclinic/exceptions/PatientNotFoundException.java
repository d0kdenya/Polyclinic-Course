package com.springrest.course.polyclinic.exceptions;

public class PatientNotFoundException extends Exception {
    public PatientNotFoundException(String message) {
        super(message);
    }
}
