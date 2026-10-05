package com.kshitij.employee_leave_management.repository;

import com.kshitij.employee_leave_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByEmployee_EmployeeNumber(int employeeNumber);
    boolean existsByRole(String role);
}

