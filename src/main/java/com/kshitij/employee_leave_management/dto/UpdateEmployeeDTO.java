package com.kshitij.employee_leave_management.dto;

public class UpdateEmployeeDTO {
    
    private String name;
    private String department ;
    private String position;
    private String email;
    public UpdateEmployeeDTO(){}
   
    public UpdateEmployeeDTO( String name , String department , String position , String email){
        
        this.name = name;
        this.department = department;
        this.position = position;
        this.email = email;
    }
    
    public String getName() {
        return name;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}
