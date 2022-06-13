package com.springrest.course.polyclinic.exceptions;

public class DepartmentNotFoundException extends Exception {
    public DepartmentNotFoundException(String message) {
        super(message);
    }
}
