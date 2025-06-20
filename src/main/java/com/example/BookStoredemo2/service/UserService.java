package com.example.BookStoredemo2.service;

import com.example.BookStoredemo2.entity.UserEntity;
import com.example.BookStoredemo2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    public UserRepository userRepository;
    public void save(UserEntity userEntity){
        userRepository.save(userEntity);
    }
}
