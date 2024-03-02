package com.spring_boot_testing_142.exceptions;

public class EmployeeAlreadyExists extends RuntimeException{
    public EmployeeAlreadyExists(String message) {
        super(message);
    }
}
