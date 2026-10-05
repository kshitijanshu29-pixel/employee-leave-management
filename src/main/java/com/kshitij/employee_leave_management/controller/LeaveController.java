package com.kshitij.employee_leave_management.controller;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kshitij.employee_leave_management.service.LeaveService;
import com.kshitij.employee_leave_management.dto.CreateLeaveRequest;
import com.kshitij.employee_leave_management.dto.LeaveResponse;
import java.util.List;
import jakarta.validation.Valid;
@RestController 
public class LeaveController {
    private final LeaveService leaveService;
    public LeaveController(LeaveService leaveService){
        this.leaveService = leaveService;
    }
    @PostMapping("/leave")
    public ResponseEntity<LeaveResponse> leaveRequest(@Valid @RequestBody CreateLeaveRequest request){
        LeaveResponse reponse = leaveService.createRequest(request);
        return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(reponse);
    }
    
    @GetMapping ("/Allleaverequests/pages")
    public Page<LeaveResponse> fetchaAllLeaveRequest(
        @RequestParam int page,
        @RequestParam int size ){
            return leaveService.getAllLeaves(page, size);
        
    }
    @GetMapping("/my-leaves")
    public List<LeaveResponse> getMyLeaves() {
    return leaveService.getMyLeaves();

    }
    @PutMapping ("/updaterequest/{id}/status")
    public ResponseEntity<LeaveResponse> updatedLeaveStatus(@PathVariable Long id , @RequestParam String status){
        LeaveResponse response = leaveService.updateLeaveStatus(id, status);
        return ResponseEntity.ok(response);       
    }
    
    
}
