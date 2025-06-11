package com.crio.rent_read.repository;

import java.util.Optional;
import com.crio.rent_read.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository <User , Long> {   
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    // Optional<User> findByUsername(String username);

}
