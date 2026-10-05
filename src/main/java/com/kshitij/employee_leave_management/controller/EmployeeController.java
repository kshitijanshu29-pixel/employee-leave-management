package com.kshitij.employee_leave_management.controller;
 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kshitij.employee_leave_management.dto.CreateEmployeeRequest;
import com.kshitij.employee_leave_management.dto.EmployeeResponse;
import com.kshitij.employee_leave_management.dto.UpdateEmployeeDTO;
import com.kshitij.employee_leave_management.service.EmployeeService;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import java.util.List;
@RequestMapping 
@RestController 
public class EmployeeController {
    private final EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService){
    this.employeeService = employeeService;
    }
@PostMapping("/employee")
public ResponseEntity<EmployeeResponse> addEmployee(@Valid @RequestBody CreateEmployeeRequest request){
    EmployeeResponse response = employeeService.createEmployee(request);
    return ResponseEntity
    .status(HttpStatus.CREATED)
     .body(response);
}
@GetMapping("/Allemployees/pages")
public Page<EmployeeResponse> viewAllEmployees(
    @RequestParam int page,
    @RequestParam int size,
    @RequestParam String sort,
    @RequestParam String direction){
        return employeeService.getAllEmployee(page, size, direction, sort);
     }
@GetMapping("/Getemployee/{id}")
public ResponseEntity<EmployeeResponse> showEmployee(@PathVariable ("id") Long id ){
    EmployeeResponse response = employeeService.getEmployeeById(id);
    return ResponseEntity
    .status(HttpStatus.OK)
    .body(response);
}
@GetMapping("/Getemployees/name")
public List<EmployeeResponse> viewEmployee(@RequestParam String name){
    return employeeService.getEmployeeByName(name);
}

@PutMapping("Updateemployee/{id}")
public ResponseEntity<EmployeeResponse>updatedEmployee(@Valid @PathVariable("id") Long id ,@RequestBody UpdateEmployeeDTO request){
    EmployeeResponse employees = employeeService.updateEmployee(id,request);
    return ResponseEntity.ok(employees);

}
@DeleteMapping("/Deleteemployee/{id}")
public ResponseEntity<Void> deletedEmployee(@PathVariable("id") Long id){
    employeeService.deleteEmployee(id);
    return ResponseEntity.noContent().build();   
}

}
