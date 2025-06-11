package com.crio.rent_read.repository;

import java.util.*;
import com.crio.rent_read.model.Rental;
import com.crio.rent_read.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Long> {

    List<Rental> findByUserAndReturnedAtIsNull(User user);
}
