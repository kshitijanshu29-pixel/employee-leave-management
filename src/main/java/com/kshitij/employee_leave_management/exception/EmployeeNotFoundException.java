package com.kshitij.employee_leave_management.exception;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException( String message){
        super(message);
    }

}
