package com.kshitij.employee_leave_management.dto;

import java.time.LocalDate;
public class LeaveResponse {  
    private  Long id;
    private int employeeNumber;
    
    private String leaveType;
    
    private LocalDate startDate;
    
    private LocalDate endDate;
    
    private String cause;

    private String status;
public LeaveResponse(Long id ,int employeeNumber , String leaveType , LocalDate startDate  , LocalDate endDate , String cause ,String status){
    this.id = id;
    this.employeeNumber = employeeNumber;
    this.leaveType = leaveType;
    this.startDate = startDate;
    this.endDate = endDate;
    this.cause = cause;
    this.status=status;

}
public int getEmployeeNumber() {
    return employeeNumber;
}
public String getLeaveType() {
    return leaveType;
}
public LocalDate getStartDate() {
    return startDate;
}
public LocalDate getEndDate() {
    return endDate;
}
public String getCause() {
    return cause;
}
public String getStatus(){
    return status;
}
public Long getId(){
    return id;
}

}

