package com.crio.rent_read.controller;

import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import java.util.List;
import com.crio.rent_read.model.Rental;
import com.crio.rent_read.service.RentalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;
    
      // 8. Rent A Book
    @PostMapping("/users/{userId}/books/{bookId}")
    public ResponseEntity<Rental> rentBook( @PathVariable ("userId")Long userId ,  @PathVariable("bookId") Long bookId){

        Rental rental = rentalService.rentBook(userId, bookId);
        return ResponseEntity.status(HttpStatus.CREATED).body(rental);    
    }

        // 9. GET active rentals for a User
        @GetMapping("/active-rentals/users/{userId}")
        public ResponseEntity<List<Rental>> getActiveRentals(@PathVariable Long userId) {
            List<Rental> activeRentals = rentalService.getActiveRentals(userId);
            return ResponseEntity.ok(activeRentals);
        }

        
    // 10. Return A Book
    @PutMapping("/{rentalId}")
    public ResponseEntity<Void> returnBook(@PathVariable Long rentalId) {
        rentalService.returnBook(rentalId);
        return ResponseEntity.noContent().build();
    }
}
