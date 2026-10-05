package com.kshitij.employee_leave_management.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String password = "Anshubabu@29";

        System.out.println(encoder.encode(password));
    }
}
