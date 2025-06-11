package com.crio.rent_read.service;

import java.util.List;
import com.crio.rent_read.model.Rental;

public interface RentalService {
    
    public Rental rentBook(Long userId, Long bookId);
    public List<Rental> getActiveRentals(Long userId);
    public void returnBook(Long rentalId);


}
