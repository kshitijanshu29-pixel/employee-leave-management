package com.kshitij.employee_leave_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kshitij.employee_leave_management.model.Leave;
import java.util.List;
import java.util.Optional;
public interface LeaveRepository extends JpaRepository<Leave, Long>{
    List<Leave> findByEmployeeEmployeeNumber(int employeeNumber);
    Optional<Leave> findById(Long id);

}
    