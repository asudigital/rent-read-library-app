package com.crio.rent_read.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.crio.rent_read.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User implements UserDetails{
    
    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;
    

    
//   🔁 Before @JsonIgnore:
// User → Rental → User → Rental → User → BOOM! ❌ Infinite loop

// ✅ After @JsonIgnore:
// User → Rental ⛔ (stops here) → no loop, no crash
    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
    private List<Rental> rentals = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

}
