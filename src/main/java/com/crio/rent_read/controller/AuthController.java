package com.crio.rent_read.controller;

import lombok.RequiredArgsConstructor;
import com.crio.rent_read.exchange.LoginRequest;
import com.crio.rent_read.exchange.ResponseDTO;
import com.crio.rent_read.exchange.SignUpRequest;
import com.crio.rent_read.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
   
    @PostMapping("/signup")
    public ResponseEntity<ResponseDTO> userSignUpRequest(@RequestBody SignUpRequest signupRequest){
      
        ResponseDTO registeredUser = authService.signup(signupRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }
    
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginRequest loginRequest){
        ResponseDTO loggedInUser = authService.login(loginRequest);
        return ResponseEntity.ok(loggedInUser);
    }










}
