package com.kshitij.employee_leave_management.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration 
public class SecurityConfig {
    private final AuthenticationEntryPoint customAuthenticationEntryPoint;
    private final AccessDeniedHandler AccessDeniedHandler;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter , AuthenticationEntryPoint customAuthenticationEntryPoint,AccessDeniedHandler customAccessDeniedHandler) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.AccessDeniedHandler = customAccessDeniedHandler;
    }
    @Bean 
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }
   @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .sessionManagement(session ->
                    session.sessionCreationPolicy(
                            SessionCreationPolicy.STATELESS
                    )
            )
            .exceptionHandling(exception ->
                exception.accessDeniedHandler(AccessDeniedHandler)
                .authenticationEntryPoint(customAuthenticationEntryPoint)
            )

            
                   
            .authorizeHttpRequests(auth -> auth
                    // EMPLOYEE ENDPOINTS
                    .requestMatchers("/auth/login", "/auth/register").permitAll()
                    .requestMatchers(HttpMethod.GET, "/Allemployees/**", "/Getemployees/**", "/Getemployee/**" )
                    .hasAnyRole("EMPLOYEE" , "ADMIN")
                    .requestMatchers(HttpMethod.PUT , "/Updateemployee/**")
                    .hasAnyRole("ADMIN")
                    .requestMatchers(HttpMethod.POST , "/employee")
                    .hasAnyRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE , "/Deleteemployee/**")
                    .hasAnyRole("ADMIN")
                    
                    // LEAVE ENDPOINTS
                    .requestMatchers(HttpMethod.GET, "/my-leaves**" )
                    .hasAnyRole("EMPLOYEE")
                    .requestMatchers(HttpMethod.GET, "/Allleaverequests/**")
                    .hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT , "/updaterequest/**")
                    .hasAnyRole("ADMIN")
                    .requestMatchers(HttpMethod.POST , "/leave**")
                    .hasAnyRole("ADMIN" , "EMPLOYEE")
                    .anyRequest().authenticated()

            )

            .addFilterBefore(
                    jwtAuthenticationFilter,
                    UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
    
}

    

