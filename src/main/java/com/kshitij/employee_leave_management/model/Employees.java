package com.kshitij.employee_leave_management.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
@Entity
@Table(name = "employee")
public class Employees {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long Id;
private String name;
private int employeeNumber;
private String department;
private String position;
private String email; 
public Employees(){}
public Employees(String name , int employeeNumber , String department , String position , String email){
    this.department =  department;
    this.name = name;
    this.employeeNumber = employeeNumber;
    this.department = department;
    this.position = position;
    this.email = email ;
}
public Long getId() {
    return Id;
}
public void setId(Long id) {
    Id = id;
}
public String getName() {
    return name;
}
public void setName(String name) {
    this.name = name;
}
public Integer getEmployeeNumber() {
    return employeeNumber;
}
public void setEmployeeNumber(Integer employeeNumber) {
    this.employeeNumber = employeeNumber;
}
public String getDepartment() {
    return department;
}
public void setDepartment(String department) {
    this.department = department;
}
public String getPosition() {
    return position;
}
public void setPosition(String position) {
    this.position = position;
}
public String getEmail() {
    return email;
}
public void setEmail(String email) {
    this.email = email;
}
}
