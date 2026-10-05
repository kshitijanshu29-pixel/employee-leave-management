package com.kshitij.employee_leave_management.exception;

public class EmployeeAlreadyExistException extends RuntimeException{
    public EmployeeAlreadyExistException (String message){
        super(message);
    }

}
