package com.crio.rent_read.controller;

import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import com.crio.rent_read.model.Book;
import com.crio.rent_read.model.Rental;
import com.crio.rent_read.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

   private final BookService bookService; 

      // 4. Create a Book (Admin Only)
      @PostMapping    
      @PreAuthorize("hasRole('ADMIN')")
      public ResponseEntity<Book> createBook(@RequestBody Book book) {
          Book savedBook = bookService.createBook(book);
          return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
      }

         //   5. Delete A Book(Admin Only)
         @DeleteMapping("/{book_id}")       
         @PreAuthorize("hasRole('ADMIN')")
          public ResponseEntity<Void> deleteBook( @PathVariable("book_id") Long book_id){
            bookService.deleteBook(book_id);
            return ResponseEntity.noContent().build();
         }

    //   6. Update A Book(Admin Only)
      @PutMapping("/{book_id}")    
      @PreAuthorize("hasRole('ADMIN')")
      
      public ResponseEntity<Book> updateBook( @PathVariable("book_id")Long book_id,  @RequestBody Book book) {
        Book updatedBook = bookService.updateBook(book, book_id);
        return ResponseEntity.ok(updatedBook);
      }
     

    
}
