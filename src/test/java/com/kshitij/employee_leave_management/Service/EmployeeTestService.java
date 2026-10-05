package com.kshitij.employee_leave_management.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;

import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.kshitij.employee_leave_management.dto.CreateEmployeeRequest;
import com.kshitij.employee_leave_management.dto.EmployeeResponse;
import com.kshitij.employee_leave_management.dto.UpdateEmployeeDTO;
import com.kshitij.employee_leave_management.exception.EmployeeAlreadyExistException;
import com.kshitij.employee_leave_management.exception.EmployeeNotFoundException;
import com.kshitij.employee_leave_management.model.Employees;
import com.kshitij.employee_leave_management.repository.EmployeeRepository;
import com.kshitij.employee_leave_management.service.EmployeeService;

import java.util.Optional;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class EmployeeTestService {
    @Mock 
    private EmployeeRepository employeeRepository;
    @InjectMocks 
    private EmployeeService employeeService;

    @Test
    void getEmployeeById_shouldReturnEmployee(){
        Employees employees = new Employees();                                              //arrange
        employees.setName("kshitij");
        employees.setEmployeeNumber(1001);
        employees.setDepartment("computer science");
        employees.setPosition("Student");
        employees.setEmail("abc@gmail.com");
        when(employeeRepository.findById(1L))
        .thenReturn(Optional.of(employees));
        EmployeeResponse result = employeeService.getEmployeeById(1L);                    //act
        assertEquals("kshitij" , result.getName());
        assertEquals(1001, result.getEmployeeNumber());
    }
    @Test
    void getEmployeeById_ShouldThrowException_WhenEmployeeNotFound(){
     when(employeeRepository.findById(999L))
     . thenReturn(Optional.empty());                           //act
     assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployeeById(999L)); //act+assert     


    }

    @Test
    void createEmployee_ShouldReturnEmployee(){
        CreateEmployeeRequest request = new CreateEmployeeRequest();
        request.setEmployeeNumber(111);

            Employees employees = new Employees();
            employees.setEmployeeNumber(111);

            when(employeeRepository.findByEmployeeNumber(111))
            .thenReturn(Optional.empty());
            when(employeeRepository.save(any(Employees.class)))
            .thenReturn(employees);
            EmployeeResponse result = employeeService.createEmployee(request);
            assertEquals(111,result.getEmployeeNumber());
           
        }

    @Test
    void findEmployeeByEmployeeNumber_ShouldThrowException_WhenDuplicateEmployeefound(){
        CreateEmployeeRequest request = new CreateEmployeeRequest();
        request.setEmployeeNumber(111);
         Employees employees = new Employees();
         employees.setEmployeeNumber(111);
         when(employeeRepository.findByEmployeeNumber(111))
         .thenReturn(Optional.of(employees));
         assertThrows(EmployeeAlreadyExistException.class,() -> employeeService.createEmployee(request));
    }     

    //++++++++++++++++++ find by name  ++++++++++++++++++++++++++++++++++++++++++++
    @Test 
    void getEmployeeByName_ShouldReturnEmployee(){
        CreateEmployeeRequest request = new CreateEmployeeRequest();
        request.setName("kshitij");
        Employees employees = new Employees();
        employees.setName("kshitij");
        when(employeeRepository.findByName("kshitij"))
        .thenReturn(List.of(employees));
        List<EmployeeResponse> result = employeeService.getEmployeeByName("kshitij");
        assertEquals("kshitij", result.get(0).getName());
    }
    @Test
    void getEmployeeByName_ShouldTheowException_WhenEmployeeNotFound(){
        CreateEmployeeRequest request = new CreateEmployeeRequest();
        request.setName("kshitij");
        Employees employees = new Employees();
        employees.setName("kshitij");
        when(employeeRepository.findByName("kshitij"))
        .thenReturn(List.of());
        assertThrows(EmployeeNotFoundException.class,() -> employeeService.getEmployeeByName(request.getName()));
    }

//++++++++++++++++++++++++++++ UPDATE A EMPLOYEE+++++++++++++++++++++++++++++++++++++++++++++
@Test 

void updateEmployee_ShouldReturnUpdatedEmployee(){
    Long id = 1L;
    Employees employees = new Employees();
    
    employees.setName("kshitij");
    employees.setEmployeeNumber(111);
    employees.setDepartment("Computer Science Engineering");
    employees.setPosition("teacher");
    employees.setEmail("XYZ@gmail.com");

    UpdateEmployeeDTO request = new UpdateEmployeeDTO();
    request.setName("kshitij");
    request.setDepartment("Electrical Engineering");
    request.setPosition("teacher");
    request.setEmail("XYZ@gmail.com");
   
    when(employeeRepository.findById(id))
    .thenReturn(Optional.of(employees));
    when(employeeRepository.save(any(Employees.class)))
    .thenReturn(employees);
    EmployeeResponse result = employeeService.updateEmployee(id, request);
    assertEquals("kshitij", result.getName());
    assertEquals(111, result.getEmployeeNumber());
    assertEquals("Electrical Engineering", result.getDepartment());
    assertEquals("teacher", result.getPosition());
    assertEquals("XYZ@gmail.com", result.getEmail());
}
@Test
void updateEmployee_shouldThrowException_WhenEmployeeDoesntExist(){
    Long id = 1L;
    UpdateEmployeeDTO request = new UpdateEmployeeDTO();
    when(employeeRepository.findById(id))
    .thenReturn(Optional.empty());
    assertThrows(EmployeeNotFoundException.class,()-> employeeService.updateEmployee(id, request));
}
@Test 

void  findById_ShouldDeleteEmployee(){
    Long id = 1L;
    Employees employees = new Employees();
    employees.setId(id);
    when(employeeRepository.findById(id))
    .thenReturn(Optional.of(employees));
    employeeService.deleteEmployee(id);
    verify(employeeRepository).delete(employees);
}
@Test
void findById_ShouldThrowException_WhenEmployeeNotFound(){
    when(employeeRepository.findById(99L))
    .thenReturn(Optional.empty());
    assertThrows(EmployeeNotFoundException.class, () -> employeeService.deleteEmployee(99L));
}

}
