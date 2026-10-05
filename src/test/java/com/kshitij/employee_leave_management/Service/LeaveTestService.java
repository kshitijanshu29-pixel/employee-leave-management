package com.kshitij.employee_leave_management.Service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.Authentication;

import com.kshitij.employee_leave_management.dto.CreateLeaveRequest;
import com.kshitij.employee_leave_management.dto.LeaveResponse;

import com.kshitij.employee_leave_management.exception.LeaveNotFoundException;
import com.kshitij.employee_leave_management.model.Employees;
import com.kshitij.employee_leave_management.model.Leave;
import com.kshitij.employee_leave_management.model.User;
import com.kshitij.employee_leave_management.repository.EmployeeRepository;
import com.kshitij.employee_leave_management.repository.LeaveRepository;
import com.kshitij.employee_leave_management.repository.UserRepository;
import com.kshitij.employee_leave_management.service.LeaveService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class LeaveTestService {
    @Mock 
    EmployeeRepository employeeRepository;

    @Mock
    LeaveRepository leaveRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks 
    LeaveService leaveService;

//++++++++++++++++++++ Create leave  request ++++++++++++++++++++++++++
    @Test
    @AfterEach
    void cleanup() {
    SecurityContextHolder.clearContext();
}
    void createRequest_ShouldCreateLeaveSuccessfully(){
        Employees employee = new Employees();
        employee.setId(1L);
        employee.setName("kshitij");
        employee.setEmployeeNumber(111);
        employee.setDepartment("computer");
        employee.setPosition("student");
        employee.setEmail("ABC@gmail.com");
                                                                                           //arrange
        Leave leave = new Leave();
        leave.setEmployee(employee);
        leave.setLeaveType("sick");
        leave.setStartDate(LocalDate.of(2026, 8, 12));
        leave.setEndDate(LocalDate.of(2026, 8, 16));
        leave.setCause("having viral fever");
        leave.setStatus("pending");

        CreateLeaveRequest request = new CreateLeaveRequest();
       request.setLeaveType("sick");
        request.setStartDate(LocalDate.of(2026, 8, 12));
        request.setEndDate(LocalDate.of(2026, 8, 16));
        request.setCause("having viral fever");
        
        Authentication authentication = mock(Authentication.class);
       SecurityContext securityContext = mock(SecurityContext.class);

    when(securityContext.getAuthentication())
            .thenReturn(authentication);

    when(authentication.getName())
            .thenReturn("Rahul");

    SecurityContextHolder.setContext(securityContext);
       
    User user = new User();
    user.setUsername("Rahul");
    user.setEmployee(employee);

    when(userRepository.findByUsername("Rahul"))
            .thenReturn(Optional.of(user));
   

    when(leaveRepository.save(any(Leave.class)))
        .thenReturn(leave);
    LeaveResponse result = leaveService.createRequest(request);
        
        assertEquals(111, result.getEmployeeNumber());
       

        
        ArgumentCaptor<Leave> captor =
        ArgumentCaptor.forClass(Leave.class);

      verify(leaveRepository).save(captor.capture());

      Leave capturedLeave = captor.getValue();
      assertEquals(111,capturedLeave.getEmployee().getEmployeeNumber());

}
@Test
void createRequest_ShouldThrowException_WhenUserNotFound() {

    // ARRANGE
    CreateLeaveRequest request = new CreateLeaveRequest();
    request.setLeaveType("sick");
    request.setStartDate(LocalDate.of(2026, 9, 12));
    request.setEndDate(LocalDate.of(2026, 9, 16));
    request.setCause("having viral fever");

    Authentication authentication = mock(Authentication.class);
    SecurityContext securityContext = mock(SecurityContext.class);

    when(securityContext.getAuthentication())
            .thenReturn(authentication);

    when(authentication.getName())
            .thenReturn("Rahul");

    SecurityContextHolder.setContext(securityContext);

    // Rahul doesn't exist in User table
    when(userRepository.findByUsername("Rahul"))
            .thenReturn(Optional.empty());


    // ACT + ASSERT
    assertThrows(
            UsernameNotFoundException.class,
            () -> leaveService.createRequest(request)
    );

    verify(userRepository).findByUsername("Rahul");

    // Saving should never happen
    verify(leaveRepository, never()).save(any(Leave.class));
}
@Test
void createRequest_ShouldThrowException_WhenDateRangeInvalid() {

    CreateLeaveRequest request = new CreateLeaveRequest();
    
    request.setStartDate(LocalDate.of(2026, 9, 10));
    request.setEndDate(LocalDate.of(2026, 9, 8));

    assertThrows(
        IllegalArgumentException.class,
        () -> leaveService.createRequest(request)
    );

    verify(leaveRepository, never())
        .save(any(Leave.class));
}
//++++++++++++++get all leave request++++++++++++++++++++
@Test
void shouldReturnAllTheRequests(){
   Employees employee = new Employees();
    employee.setId(1L);
    employee.setEmployeeNumber(111);
    employee.setName("kshitij");

    Leave leave = new Leave();
    leave.setId(1L);
    leave.setEmployee(employee);
    leave.setLeaveType("death");
    leave.setStartDate(LocalDate.of(2026, 8, 12));
    leave.setEndDate(LocalDate.of(2026, 8, 16));
    leave.setCause("having viral fever");
    leave.setStatus("pending");

    Leave leave2 = new Leave();
    leave2.setId(2L);
    leave2.setEmployee(employee);
    leave2.setLeaveType("sick");
    leave2.setStartDate(LocalDate.of(2026, 8, 12));
    leave2.setEndDate(LocalDate.of(2026, 8, 16));
    leave2.setCause("having viral fever");
    leave2.setStatus("approved");
    
    Page<Leave> leavePage =
            new PageImpl<>(List.of(leave , leave2));

    when(leaveRepository.findAll(any(Pageable.class)))
            .thenReturn(leavePage);
    
    Page<LeaveResponse> result = leaveService.getAllLeaves(0, 10);
     assertEquals(2, result.getTotalElements());

    assertEquals(111,result.getContent().get(0).getEmployeeNumber());
    assertEquals("death",result.getContent().get(0).getLeaveType());
    assertEquals("pending",result.getContent().get(0).getStatus()); 
    
    assertEquals(111,result.getContent().get(1).getEmployeeNumber());
    assertEquals("sick",result.getContent().get(1).getLeaveType());
    assertEquals("approved",result.getContent().get(1).getStatus()); 
    
}
//++++++++++++++++++get request by employee username++++++++++++++++++++++
@Test 
void searchByEmployeeNumber_ShouldReturnAllRequest(){
    Employees employee = new Employees();
    employee.setId(1L);
    employee.setEmployeeNumber(111);
    employee.setName("kshitij");
    Leave leave = new Leave();
    leave.setId(1L);
    leave.setEmployee(employee);
    leave.setLeaveType("death");
    leave.setStartDate(LocalDate.of(2026, 8, 12));
    leave.setEndDate(LocalDate.of(2026, 8, 16));
    leave.setCause("having viral fever");
    leave.setStatus("pending");
    User user = new User();
    user.setUsername("Rahul");
    user.setEmployee(employee);

    Authentication authentication = mock(Authentication.class);
    SecurityContext securityContext = mock(SecurityContext.class);

    when(securityContext.getAuthentication())
            .thenReturn(authentication);

    when(authentication.getName())
            .thenReturn("Rahul");

    SecurityContextHolder.setContext(securityContext);
    when(userRepository.findByUsername("Rahul"))
    .thenReturn(Optional.of(user));
    Leave leave1 = new Leave();
    leave1.setId(1L);
    leave1.setEmployee(employee);
    leave1.setLeaveType("SICK");
    leave1.setStatus("PENDING");

    Leave leave2 = new Leave();
    leave2.setId(2L);
    leave2.setEmployee(employee);
    leave2.setLeaveType("CASUAL");
    leave2.setStatus("APPROVED");


    when(leaveRepository.findByEmployeeEmployeeNumber(111))
            .thenReturn(List.of(leave1, leave2));


    // ACT
    
    List<LeaveResponse> result = leaveService.getMyLeaves();


    // ASSERT
    assertEquals(2, result.size());

    assertEquals(111, result.get(0).getEmployeeNumber());
    assertEquals(111, result.get(1).getEmployeeNumber());

    verify(userRepository).findByUsername("Rahul");

    verify(leaveRepository)
            .findByEmployeeEmployeeNumber(111);
}
// +++++++++++++++++++++++++++ UPDATE LEAVE REQUEST +++++++++++++++
@Test
void updateLeaveStatus_ShouldReturnUpdatedStatus(){
    Employees employee = new Employees();
    Leave leave = new Leave();
    leave.setId(1L);
    leave.setEmployee(employee);
    leave.setLeaveType("death");
    leave.setStartDate(LocalDate.of(2026, 8, 12));
    leave.setEndDate(LocalDate.of(2026, 8, 16));
    leave.setCause("having viral fever");
    leave.setStatus("pending");
    when(leaveRepository.findById(1L))
            .thenReturn(Optional.of(leave));

    when(leaveRepository.save(any(Leave.class)))
            .thenReturn(leave);

    // ACT
    LeaveResponse result =
            leaveService.updateLeaveStatus(1L, "APPROVED");

    // ASSERT
    assertEquals("APPROVED", result.getStatus());
}
@Test 
void findByEmployeeId_ShouldThrowException_WhenIdNotFound(){
    when(leaveRepository.findById(1L))
    .thenReturn(Optional.empty());
    assertThrows(LeaveNotFoundException.class,
            () -> leaveService.updateLeaveStatus(1L, "REJECTED"));
}


@Test
void updateLeaveStatus_ShouldThrowException_WhenStatusInvalid() {

    Leave leave = new Leave();
    leave.setId(1L);
    leave.setStatus("PENDING");

    when(leaveRepository.findById(1L))
            .thenReturn(Optional.of(leave));

    assertThrows(
        IllegalArgumentException.class,
        () -> leaveService.updateLeaveStatus(1L, "HELLO")
    );
}
}
