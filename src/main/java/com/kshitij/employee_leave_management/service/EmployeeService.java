package com.kshitij.employee_leave_management.service;
import com.kshitij.employee_leave_management.exception.EmployeeAlreadyExistException;
import com.kshitij.employee_leave_management.exception.EmployeeNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.kshitij.employee_leave_management.dto.CreateEmployeeRequest;
import com.kshitij.employee_leave_management.dto.EmployeeResponse;
import com.kshitij.employee_leave_management.model.Employees;
import com.kshitij.employee_leave_management.repository.EmployeeRepository;
import com.kshitij.employee_leave_management.dto.UpdateEmployeeDTO;
@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
        public EmployeeService(EmployeeRepository employeeRepository){
            this.employeeRepository = employeeRepository;
        }

public EmployeeResponse  createEmployee(CreateEmployeeRequest  request){
    Optional<Employees> existingEmployee =
        employeeRepository.findByEmployeeNumber(request.getEmployeeNumber());
    if (existingEmployee.isPresent()) {
        throw new EmployeeAlreadyExistException( "Employee Already Exist");
        
    }
    Employees employee = new Employees();
    employee.setName(request.getName());
    employee.setEmployeeNumber(request.getEmployeeNumber());
    employee.setDepartment(request.getDepartment());
    employee.setPosition(request.getPosition());
    employee.setEmail(request.getEmail());
    Employees saved = employeeRepository.save(employee);
    return new EmployeeResponse( saved.getName(), saved.getEmployeeNumber(),saved.getDepartment(), saved.getPosition(), saved.getEmail());

}
private EmployeeResponse mapToDTO(Employees employees){      //internal helper method converts entity to dto
    return new EmployeeResponse(employees.getName(),employees.getEmployeeNumber() , employees.getDepartment(), employees.getPosition(), employees.getEmail());
}
public Page<EmployeeResponse> getAllEmployee(int page , int size , String direction , String sort){
    Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction)?Sort.Direction.DESC:Sort.Direction.ASC;
    Pageable pageable = PageRequest.of(page , size ,Sort.by(sortDirection, sort));
    return employeeRepository.findAll(pageable).map(this:: mapToDTO);

    
}           
public EmployeeResponse getEmployeeById(Long id){
    return employeeRepository.findById(id)
      .map(this::mapToDTO)
      .orElseThrow(() -> new EmployeeNotFoundException("Employee doesnot exist" + id));
}
public List<EmployeeResponse> getEmployeeByName(String name){
    List<EmployeeResponse> employees = employeeRepository.findByName(name).stream()
            .map(this::mapToDTO).toList();
    if (employees.isEmpty()) {
        throw new EmployeeNotFoundException("Employee doesnot exist");
    }
    return employees;
}
public EmployeeResponse updateEmployee(Long Id, UpdateEmployeeDTO updated) {
    Employees employees = employeeRepository.findById(Id)
        .orElseThrow(() -> new EmployeeNotFoundException("Employee Not Found" + Id));
    employees.setName(updated.getName());
    employees.setDepartment(updated.getDepartment());
    employees.setPosition(updated.getPosition());
    employees.setEmail(updated.getEmail());
    Employees saved = employeeRepository.save(employees);
    return mapToDTO(saved);
}
public void deleteEmployee(Long Id ){
    Employees employees = employeeRepository.findById(Id)
    .orElseThrow(() -> new  EmployeeNotFoundException("employee not found to be deleted"));
    employeeRepository.delete(employees);
}

}




