package com.kshitij.employee_leave_management.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity 
@Table (name = "User_Details")
public class User {
   
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    
    private Long id;
    @OneToOne
    @JoinColumn(name = "employee_id", unique = true)
    private Employees employee;
    private String role;
    private String username;
    private String password;
    public User(){}
    public User( Long id , Employees employee,String role , String username , String password){
        this.employee = employee;
        this.id = id;
        this.role = role;
        this.username = username;
        this.password = password;
    
    }
    public Long getId() {
        return id;
    }
    public Employees getEmployee() {
        return employee;
    }
    public void setEmployee(Employees employee) {
        this.employee = employee;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
}
