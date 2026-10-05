package com.kshitij.employee_leave_management.exception;

public class LeaveNotFoundException  extends RuntimeException{
    public LeaveNotFoundException(String message){
    super(message);
}

}
