package com.kshitij.employee_leave_management.service;



import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kshitij.employee_leave_management.dto.RegisterRequestDTO;

import com.kshitij.employee_leave_management.dto.UserResponseDTO;
import com.kshitij.employee_leave_management.model.User;
import com.kshitij.employee_leave_management.model.Employees;
import com.kshitij.employee_leave_management.repository.EmployeeRepository;
import com.kshitij.employee_leave_management.repository.UserRepository;
import com.kshitij.employee_leave_management.exception.EmployeeNotFoundException;
import com.kshitij.employee_leave_management.exception.UserAlreadyExistException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder , EmployeeRepository employeeRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.employeeRepository = employeeRepository;
    }

    public UserResponseDTO register(RegisterRequestDTO request) {
       
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UserAlreadyExistException("User already exist");
            }
            if (userRepository.existsByEmployee_EmployeeNumber(
                request.getEmployeeNumber())) {
                    throw new UserAlreadyExistException("User account already exists for this employee");
}
        Employees employee = employeeRepository.findByEmployeeNumber(request.getEmployeeNumber())
        .orElseThrow(() ->
                        new EmployeeNotFoundException("Employee not found"));

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("EMPLOYEE");
        user.setEmployee(employee);
        User saved = userRepository.save(user);
        return new UserResponseDTO(saved.getId(),saved.getUsername(),saved.getRole());
    }
}

