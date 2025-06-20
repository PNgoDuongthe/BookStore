package com.example.BookStoredemo2.repository;

import com.example.BookStoredemo2.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    @Query("SELECT book FROM BookEntity book WHERE CONCAT(book.id, ' ', book.author, ' ', book.name, ' ', book.image, ' ', book.description) LIKE %?1%")
    List<BookEntity> searchBooks(String keyword);
    List<BookEntity> findByNameContainingIgnoreCase(String name);
}
