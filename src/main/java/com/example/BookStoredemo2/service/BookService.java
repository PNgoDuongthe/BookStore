package com.example.BookStoredemo2.service;

import com.example.BookStoredemo2.entity.BookEntity;
import com.example.BookStoredemo2.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    public BookRepository bookRepository;
    public void save(BookEntity bookEntity){
        bookRepository.save(bookEntity);
    }
    public List<BookEntity> getAllBook(){
        return bookRepository.findAll();
    }
    public Optional<BookEntity> getBookById(Long id ){
        return bookRepository.findById(id);
    }
    public void deleteBookById(Long id){
        bookRepository.deleteById(id);
    }
}
