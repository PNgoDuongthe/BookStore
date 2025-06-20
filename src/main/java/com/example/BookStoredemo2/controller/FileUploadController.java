package com.example.BookStoredemo2.controller;

import com.example.BookStoredemo2.entity.BookEntity;
import com.example.BookStoredemo2.service.BookService;
import com.example.BookStoredemo2.service.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Book;
import java.io.IOException;
import java.util.List;

@Controller
public class FileUploadController {
    private final FileUpload fileUpload;

    @Autowired
    public FileUploadController(FileUpload fileUpload) {
        this.fileUpload = fileUpload;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("image") MultipartFile multipartFile, Model model) {
        try {
            if (multipartFile.isEmpty()) {
                model.addAttribute("error", "Please select an image file.");
                return "addbook";
            }

            String imageURL = fileUpload.uploadFile(multipartFile);

            model.addAttribute("imageURL", imageURL);
            model.addAttribute("success", "Image uploaded successfully!");

            return "addbook";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error uploading file: " + e.getMessage());
            return "addbook";
        }
    }
}

