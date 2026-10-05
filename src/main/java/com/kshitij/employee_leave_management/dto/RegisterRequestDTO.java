package com.kshitij.employee_leave_management.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public class RegisterRequestDTO{

@NotBlank(message = "message  is required")
private String username;
@NotBlank(message = "password  is required")
@Pattern(
    regexp =  "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
    message = "Password must contain at least 8 characters, including uppercase, lowercase, number and special character"
)
private String password;

@NotNull @Positive 
private Integer employeeNumber;
   


public RegisterRequestDTO(){}

public String getUsername() {
    return username;
}
public String getPassword() {
    return password;
}


public Integer getEmployeeNumber(){
    return employeeNumber;
}


public void setEmployeeNumber(Integer employeeNumber){
    this.employeeNumber = employeeNumber;
}
public void setUsername(String username) {
    this.username = username;   
}
public void setPassword(String password) {
    this.password = password;
}
}


