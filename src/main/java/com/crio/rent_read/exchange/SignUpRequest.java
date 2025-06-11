package com.crio.rent_read.exchange;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.crio.rent_read.enums.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
  private String email;
  private String password;
  private String firstName;
  private String lastName;
  private Role role; // Optional, default to user if NULL  
}
