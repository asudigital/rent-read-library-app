package com.crio.rent_read.service.impl;

import lombok.RequiredArgsConstructor;
import com.crio.rent_read.exception.BookNotFoundException;
import com.crio.rent_read.model.Book;
import com.crio.rent_read.repository.BookRepository;
import com.crio.rent_read.service.BookService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{
    
    private final BookRepository bookRepository;
    
    
    @Override
    public Book createBook(Book book) {
       return  bookRepository.save(book);
    }

    @Override
    public Book updateBook(Book updatedBook , Long book_id) {

        Book existingBook = bookRepository.findById(book_id)
        .orElseThrow( () -> new BookNotFoundException("Book not found with id: " + book_id));
      
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setGenre(updatedBook.getGenre());
        existingBook.setAvailabilityStatus(updatedBook.getAvailabilityStatus());
        return bookRepository.save(existingBook);
    }

    @Override
    public void deleteBook(Long book_id) {       
        Book existingBook = bookRepository.findById(book_id)
        .orElseThrow( () -> new BookNotFoundException("Book not found with id: " + book_id));
        bookRepository.delete(existingBook);
    }
    
}
