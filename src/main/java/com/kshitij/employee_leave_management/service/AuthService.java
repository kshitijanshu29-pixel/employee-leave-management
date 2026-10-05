package com.kshitij.employee_leave_management.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.kshitij.employee_leave_management.security.JwtService;
import com.kshitij.employee_leave_management.dto.LoginResponse;
import com.kshitij.employee_leave_management.dto.LoginRequest;
@Service 
public class AuthService {
    
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    public AuthService(AuthenticationManager authenticationManager,JwtService jwtService ){
        
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }
    public LoginResponse login(LoginRequest request){
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);
        String role = userDetails
        .getAuthorities()
        .iterator()
        .next()     
        .getAuthority();
        return new LoginResponse( userDetails.getUsername(), token, role);

    }

}

    


