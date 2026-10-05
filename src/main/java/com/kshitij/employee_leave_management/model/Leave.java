package com.kshitij.employee_leave_management.model;

import java.time.LocalDate;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "leave_requests")
public class Leave {
@Id 
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
@ManyToOne
@JoinColumn(name = "employee_id")
private Employees employee;

private String leaveType;
private LocalDate startdate;
private LocalDate enddate;
private String cause;
private String status;

public Leave(){};
public Leave( Employees employees ,String leaveType , LocalDate startdate , LocalDate enddate ,String cause , String status){
    this.leaveType=leaveType;
    this.startdate = startdate;
    this.enddate = enddate;
    this.cause=cause;
    this.status=status;
    
}
public Long getId() {
    return id;
}
public void setId(Long id) {
    this.id = id;
}
public Employees getEmployee() {
    return employee;
}
public void setEmployee(Employees employee) {
    this.employee = employee;
}
public String getLeaveType() {
    return leaveType;
}
public void setLeaveType(String leaveType) {
    this.leaveType = leaveType;
}
public LocalDate getStartDate() {
    return startdate;
}
public void setStartDate(LocalDate startdate) {
    this.startdate = startdate;
}
public LocalDate getEndDate() {
    return enddate;
}
public void setEndDate(LocalDate enddate) {
    this.enddate = enddate;
}
public String getCause() {
    return cause;
}
public void setCause(String cause) {
    this.cause = cause;
}
public String getStatus() {
    return status;
}
public void setStatus(String status) {
    this.status = status;
}
    
}
