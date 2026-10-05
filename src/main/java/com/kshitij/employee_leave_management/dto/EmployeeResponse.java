package com.kshitij.employee_leave_management.dto;

public class EmployeeResponse {
    private String name;
    private int employeeNumber;
    private String department;
    private String position;
    private String email;
    public EmployeeResponse(String name ,int employeeNumber,String department, String position,String email){
        this.name = name;
        this.employeeNumber =  employeeNumber;
        this.department = department;
        this.position = position;
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public Integer getEmployeeNumber() {
        return employeeNumber;
    }
    public String getDepartment() {
        return department;
    }
    public String getPosition() {
        return position;
    }
    public String getEmail() {
        return email;
    }
  

}
