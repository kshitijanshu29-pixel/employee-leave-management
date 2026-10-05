package com.kshitij.employee_leave_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kshitij.employee_leave_management.model.Employees;

import java.util.List;
import java.util.Optional;
public interface EmployeeRepository extends JpaRepository<Employees ,Long> {
    Optional<Employees>findByEmployeeNumber(int employeeNumber);
    List<Employees>findByName(String name);
    
}
    
