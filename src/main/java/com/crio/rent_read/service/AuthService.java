package com.crio.rent_read.service;

import com.crio.rent_read.exchange.LoginRequest;
import com.crio.rent_read.exchange.ResponseDTO;
import com.crio.rent_read.exchange.SignUpRequest;
import com.crio.rent_read.model.User;

public interface AuthService {
    
    ResponseDTO signup(SignUpRequest signUpRequest);
    ResponseDTO login(LoginRequest LoginRequest);
}
