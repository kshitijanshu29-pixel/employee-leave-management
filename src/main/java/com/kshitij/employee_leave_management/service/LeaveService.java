package com.kshitij.employee_leave_management.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.Authentication;

import com.kshitij.employee_leave_management.dto.CreateLeaveRequest;
import com.kshitij.employee_leave_management.dto.LeaveResponse;
import com.kshitij.employee_leave_management.exception.EmployeeNotFoundException;
import com.kshitij.employee_leave_management.exception.LeaveNotFoundException;
import com.kshitij.employee_leave_management.model.Employees;
import com.kshitij.employee_leave_management.model.Leave;
import com.kshitij.employee_leave_management.model.User;

import com.kshitij.employee_leave_management.repository.LeaveRepository;
import com.kshitij.employee_leave_management.repository.UserRepository;

import java.util.List;

@Service
public class LeaveService {
    
   
    private final LeaveRepository leaveRepository;
    private final UserRepository userRepository;
    public LeaveService( LeaveRepository leaveRepository , UserRepository userRepository){
     
        this.leaveRepository = leaveRepository;
        this.userRepository = userRepository;                                   
    }

    public LeaveResponse createRequest(CreateLeaveRequest request){
        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new IllegalArgumentException("End date cannot be before start date    ");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        User user = userRepository.findByUsername(username)
        .orElseThrow(() ->
                new UsernameNotFoundException("User not found"));
                        
        Employees employee = user.getEmployee();
        if (employee == null){
            throw new EmployeeNotFoundException("no employee linked to the user");
        }
            
        Leave leave = new Leave();
        leave.setEmployee(employee);
        leave.setLeaveType(request.getLeaveType());
        leave.setStartDate(request.getStartDate());
        leave.setEndDate(request.getEndDate());
        leave.setCause(request.getCause());
        leave.setStatus("PENDING");

        Leave saved = leaveRepository.save(leave);
        return new LeaveResponse(
                saved.getId(),
                saved.getEmployee().getEmployeeNumber(),
                saved.getLeaveType(),
                saved.getStartDate(),
                saved.getEndDate(),
                saved.getCause(),
                saved.getStatus());
    }

    private  LeaveResponse mapToDto(Leave leave){
     return new LeaveResponse(leave.getId() , leave.getEmployee().getEmployeeNumber(), leave.getLeaveType(), leave.getStartDate(), leave.getEndDate(),leave.getCause(),leave.getStatus());
   }
   
    public Page<LeaveResponse> getAllLeaves(int page , int size){
        Pageable pageable = PageRequest.of(page, size);
        return leaveRepository.findAll(pageable).map(this::mapToDto);
    }

        public List<LeaveResponse> getMyLeaves() {
        Authentication authentication =
            SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Employees employee = user.getEmployee();

        if (employee == null) {
            throw new EmployeeNotFoundException(
                "No employee linked to this user");
        }
                    
        List<Leave> leaves = leaveRepository.findByEmployeeEmployeeNumber(employee.getEmployeeNumber());
        return leaves.stream()
            .map(this::mapToDto)
            .toList();
        }
    

    public LeaveResponse updateLeaveStatus(Long id, String status) {

    Leave leave = leaveRepository.findById(id)
            .orElseThrow(() ->
                    new LeaveNotFoundException("Leave not found"));

    if (!status.equalsIgnoreCase("APPROVED")
            && !status.equalsIgnoreCase("REJECTED")) {

        throw new IllegalArgumentException("Invalid leave status");
    }

    leave.setStatus(status.toUpperCase());

    Leave saved = leaveRepository.save(leave);

    return mapToDto(saved);

}   
}
