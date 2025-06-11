package com.crio.rent_read.exchange;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.crio.rent_read.enums.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role; 
}
