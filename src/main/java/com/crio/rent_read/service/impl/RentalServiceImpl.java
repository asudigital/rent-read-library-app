package com.crio.rent_read.service.impl;

import lombok.RequiredArgsConstructor;
import java.time.LocalDate;
import java.util.List;
import com.crio.rent_read.exception.UserNotFoundException;
import com.crio.rent_read.model.Book;
import com.crio.rent_read.model.Rental;
import com.crio.rent_read.model.User;
import com.crio.rent_read.repository.BookRepository;
import com.crio.rent_read.repository.RentalRepository;
import com.crio.rent_read.repository.UserRepository;
import com.crio.rent_read.service.RentalService;

import org.springframework.stereotype.Service;
import com.crio.rent_read.enums.AvailabilityStatus;
import com.crio.rent_read.exception.BookNotFoundException;
import com.crio.rent_read.exception.BookUnavailableException;
import com.crio.rent_read.exception.RentalLimitExceededException;
import com.crio.rent_read.exception.RentalNotFoundException;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {
    
    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;


    public Rental rentBook(Long userId, Long bookId) {
     
        User user =  userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException("User not found"));
     

        Book book = bookRepository.findById(bookId)
         .orElseThrow( () -> new BookNotFoundException("Book not found with id: " + bookId));

         // 1. Enforce rental limit
         List<Rental> activeRental =  rentalRepository.findByUserAndReturnedAtIsNull(user);
         if(activeRental.size() >= 2){
            throw new RentalLimitExceededException("User has already reached maximum book rental limit!");
         }
    
            // 2. Check book availability
        if( book.getAvailabilityStatus() == AvailabilityStatus.NOT_AVAILABLE){
            throw new BookUnavailableException("Book is not available for rent");
        }
       
        //Update the current available  status 
        book.setAvailabilityStatus( AvailabilityStatus.NOT_AVAILABLE);
        bookRepository.save(book);

          // 4. Create and save rental
      Rental rental = Rental.builder()
                            .user(user)
                            .book(book)
                            .rentedAt(LocalDate.now())
                            .build();

                return rentalRepository.save(rental);
        
    }

    public List<Rental> getActiveRentals(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User not found"));
    
        return rentalRepository.findByUserAndReturnedAtIsNull(user);
    }


    @Override
    public void returnBook(Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId)
            .orElseThrow(() -> new RentalNotFoundException("Rental not found with id: " + rentalId));
    
        if (rental.getReturnedAt() != null) {
            throw new IllegalStateException("Book is already returned");
        }
    
        rental.setReturnedAt(LocalDate.now());
    
        Book book = rental.getBook();
        book.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);
    
        bookRepository.save(book);
        rentalRepository.save(rental);
    }
    
 
}
