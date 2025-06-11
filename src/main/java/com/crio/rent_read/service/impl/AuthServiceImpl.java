package com.crio.rent_read.service.impl;

import lombok.RequiredArgsConstructor;
import com.crio.rent_read.enums.Role;
import com.crio.rent_read.exception.EmailAlreadyExistsException;
import com.crio.rent_read.exception.UserNotFoundException;
import com.crio.rent_read.exchange.LoginRequest;
import com.crio.rent_read.exchange.ResponseDTO;
import com.crio.rent_read.exchange.SignUpRequest;
import com.crio.rent_read.model.User;
import com.crio.rent_read.repository.UserRepository;
import com.crio.rent_read.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public ResponseDTO signup(SignUpRequest signUpRequest) {
        
       if(userRepository.existsByEmail(signUpRequest.getEmail())){
        throw new EmailAlreadyExistsException("Email already in use");
       }
       User user = User.builder()
                       .email(signUpRequest.getEmail())
                       .password(passwordEncoder.encode(signUpRequest.getPassword()))
                       .firstName(signUpRequest.getFirstName())
                       .lastName(signUpRequest.getLastName())
                       .role( signUpRequest.getRole() != null ? signUpRequest.getRole() : Role.USER)
                       .build();

      User saved = userRepository.save(user);
      return mapToDto(saved);
    }

    @Override
    public ResponseDTO login(LoginRequest loginRequest) {

        User user = userRepository.findByEmail(loginRequest.getEmail())
        .orElseThrow( () -> new UserNotFoundException("Invalid credentials"));

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new UserNotFoundException("Invalid Credeentials");
        }
      
        return mapToDto(user);
    }
    
    private ResponseDTO mapToDto(User user) {
        return ResponseDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
    
}
