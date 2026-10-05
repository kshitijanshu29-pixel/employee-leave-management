package com.kshitij.employee_leave_management.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import com.kshitij.employee_leave_management.service.UserService;
import com.kshitij.employee_leave_management.dto.LoginRequest;
import com.kshitij.employee_leave_management.dto.LoginResponse;
import com.kshitij.employee_leave_management.dto.UserResponseDTO;
import com.kshitij.employee_leave_management.service.AuthService;
import com.kshitij.employee_leave_management.dto.RegisterRequestDTO;
@RestController 
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
    public AuthController(AuthService authService , UserService userService){
        this.authService = authService;
        this.userService = userService;
    }
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request ){
        return authService.login(request);
    }
    @PostMapping ("/register")
    public UserResponseDTO register(@Valid @RequestBody RegisterRequestDTO request){
        return userService.register(request);

    }
}
