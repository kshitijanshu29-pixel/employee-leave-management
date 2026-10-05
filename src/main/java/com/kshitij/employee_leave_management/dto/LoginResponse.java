package com.kshitij.employee_leave_management.dto;

public class LoginResponse {
    private String username;
    private String token;
    private String role;
    public LoginResponse(String username , String token , String role ){
        this.username = username;
        this.token = token;
        this.role = role;   
    }
    public String getUsername() {
        return username;
    }
    public String getToken() {
        return token;
    }
    public String getRole() {
        return role;
    }
    

}
