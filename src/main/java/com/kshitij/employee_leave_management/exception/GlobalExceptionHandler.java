package com.kshitij.employee_leave_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EmployeeAlreadyExistException.class)
    public ResponseEntity<String> handleEmployeeAlreadyExistException(EmployeeAlreadyExistException ex){
        return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body(ex.getMessage());
    }
    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<String> handleEmployeeNotFoundException(EmployeeNotFoundException ex){
        return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ex.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String , String>> handleValidation(MethodArgumentNotValidException ex ){
        Map<String , String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
            errors.put(err.getField(), err.getDefaultMessage()));
            return ResponseEntity
            .badRequest()
            .body(errors);
    }
    @ExceptionHandler(LeaveNotFoundException.class)
    public ResponseEntity<String> handleLeaveNotFoundException(LeaveNotFoundException ex){
        return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ex.getMessage());
    }
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<String> handleUserAlreadyExistException(UserAlreadyExistException ex){
        return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ex.getMessage());
    }


}
