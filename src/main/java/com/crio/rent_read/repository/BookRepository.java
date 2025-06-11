package com.crio.rent_read.repository;

import com.crio.rent_read.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends JpaRepository<Book , Long> {
    
}
