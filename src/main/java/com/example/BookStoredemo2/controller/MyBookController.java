package com.example.BookStoredemo2.controller;

import com.example.BookStoredemo2.service.BookService;
import com.example.BookStoredemo2.service.MyBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyBookController {
    @Autowired
    private MyBookService myBookService;

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        myBookService.deleteById(id);
        return "redirect:/mybook";
    }
}
