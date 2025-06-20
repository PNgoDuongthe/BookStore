package com.example.BookStoredemo2.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUpload {
    public String uploadFile(MultipartFile multipartFile) throws IOException;
}
