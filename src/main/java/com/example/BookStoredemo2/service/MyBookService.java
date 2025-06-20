package com.example.BookStoredemo2.service;

import com.example.BookStoredemo2.entity.MyBookEntity;
import com.example.BookStoredemo2.repository.MyBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyBookService {
    private final MyBookRepository myBookRepository;
    @Autowired
    public MyBookService(MyBookRepository myBookRepository){
        this.myBookRepository = myBookRepository;
    }

    public void saveMyBook(MyBookEntity myBookEntity){
        myBookRepository.save(myBookEntity);
    }
    public List<MyBookEntity> getAllMyBook(){
        return myBookRepository.findAll();
    }
    public void deleteById(Long id ){
        myBookRepository.deleteById(id);
    }

}
