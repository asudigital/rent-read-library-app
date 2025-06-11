package com.crio.rent_read.service;

import com.crio.rent_read.model.Book;

public interface BookService {
    
    public Book createBook(Book book);
    public Book updateBook(Book book, Long book_id);
    public void deleteBook(Long book_id);
}
