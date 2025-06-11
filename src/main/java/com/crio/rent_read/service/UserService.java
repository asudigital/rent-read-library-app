package com.crio.rent_read.service;


import lombok.extern.slf4j.Slf4j;
import com.crio.rent_read.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository; // Inject your user repo

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    
          
    com.crio.rent_read.model.User user = userRepository.findByEmail(username)
    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

log.info("User found: {}", user.getEmail());

if (user.getRole() == null) {
    throw new UsernameNotFoundException("User role is null");
}

        // Build Spring Security UserDetails object (fully qualified name used)
        return org.springframework.security.core.userdetails.User.builder()
            .username(user.getUsername())
            .password(user.getPassword()) // should be already BCrypt-hashed
            .roles(user.getRole().name()) // e.g., "USER" or "ADMIN"
            .build();

    }


    }
    

