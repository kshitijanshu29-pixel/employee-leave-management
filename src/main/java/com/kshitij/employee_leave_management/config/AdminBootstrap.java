package com.kshitij.employee_leave_management.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kshitij.employee_leave_management.model.Employees;
import com.kshitij.employee_leave_management.model.User;
import com.kshitij.employee_leave_management.repository.EmployeeRepository;
import com.kshitij.employee_leave_management.repository.UserRepository;

@Component
public class AdminBootstrap implements CommandLineRunner {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminBootstrap(
            UserRepository userRepository,
            EmployeeRepository employeeRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        // If an ADMIN already exists, do nothing.
        if (userRepository.existsByRole("ADMIN")) {
            return;
        }

        String username = System.getenv("ADMIN_USERNAME");
        String password = System.getenv("ADMIN_PASSWORD");
        String employeeNumberValue = System.getenv("ADMIN_EMPLOYEE_NUMBER");

        // Bootstrap credentials must be configured.
        if (username == null || username.isBlank()
                || password == null || password.isBlank()
                || employeeNumberValue == null || employeeNumberValue.isBlank()) {

            throw new IllegalStateException(
                    "No ADMIN exists and ADMIN bootstrap environment variables are missing"
            );
        }

        int employeeNumber;

        try {
            employeeNumber = Integer.parseInt(employeeNumberValue);
        } catch (NumberFormatException e) {
            throw new IllegalStateException(
                    "ADMIN_EMPLOYEE_NUMBER must be a valid integer"
            );
        }

        // Make sure username isn't already being used.
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalStateException(
                    "ADMIN_USERNAME is already used by another account"
            );
        }

        // The admin must correspond to an existing employee.
        Employees employee = employeeRepository
                .findByEmployeeNumber(employeeNumber)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Employee not found for ADMIN_EMPLOYEE_NUMBER: "
                                        + employeeNumber
                        )
                );

        // Don't create another account for the same employee.
        if (userRepository.existsByEmployee_EmployeeNumber(employeeNumber)) {
            throw new IllegalStateException(
                    "A user account already exists for employee number: "
                            + employeeNumber
            );
        }

        User admin = new User();

        admin.setUsername(username);
        admin.setPassword(passwordEncoder.encode(password));
        admin.setRole("ADMIN");
        admin.setEmployee(employee);

        userRepository.save(admin);

        System.out.println("Initial ADMIN account created successfully.");
    }
}
