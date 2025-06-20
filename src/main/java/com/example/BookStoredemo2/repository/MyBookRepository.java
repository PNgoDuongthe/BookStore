package com.example.BookStoredemo2.repository;

import com.example.BookStoredemo2.entity.MyBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyBookRepository extends JpaRepository<MyBookEntity , Long > {
}
