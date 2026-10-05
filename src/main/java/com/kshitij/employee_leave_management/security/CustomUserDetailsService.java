package com.kshitij.employee_leave_management.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.kshitij.employee_leave_management.model.User;

import com.kshitij.employee_leave_management.repository.UserRepository;
import org.springframework.stereotype.Service;
@Service 
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository; 
    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username)
    throws UsernameNotFoundException{
        User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("enter a valid username"));
        return org.springframework.security.core.userdetails.User
        .withUsername(user.getUsername())
        .password(user.getPassword())
        .roles(user.getRole())
        .build();           

    }

    

}
