package com.example.BookStoredemo2.controller;

import com.example.BookStoredemo2.entity.BookEntity;
import com.example.BookStoredemo2.entity.MyBookEntity;
import com.example.BookStoredemo2.repository.BookRepository;
import com.example.BookStoredemo2.service.BookService;
import com.example.BookStoredemo2.service.FileUpload;
import com.example.BookStoredemo2.service.MyBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class BookController {
    @Autowired
    public BookService bookService;
    @Autowired
    public MyBookService myBookService;
    @Autowired
    public BookRepository bookRepository;

    public FileUpload fileUpload;
    @Autowired
    public BookController(FileUpload fileUpload) {
        this.fileUpload = fileUpload;
    }

    @Controller
    public class RedirectController {
        @GetMapping("/")
        public String redirectToHomepage() {
            return "redirect:/homepage";
        }
    }



    @GetMapping("/homepage")
    public String homepage(Model model, @RequestParam(value = "keyword", required = false) String keyword) {
        List<BookEntity> books;
        if (keyword != null && !keyword.isEmpty()) {
            books = bookRepository.findByNameContainingIgnoreCase(keyword);
        } else {
            books = bookRepository.findAll();
        }
        model.addAttribute("books", books);
        model.addAttribute("keyword", keyword);  // Thêm keyword vào model để truyền vào form
        return "homepage";
    }

    @GetMapping("/addbook")
    public String addbook(){
        return "addbook";
    }
    @GetMapping("/availablebook")
    public ModelAndView getAllBook(){
        List<BookEntity> list = bookService.getAllBook();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("books",list);
        return modelAndView;
    }
    @PostMapping("/save")
    public String saveBook(
            @RequestParam("name") String name,
            @RequestParam("author") String author,
            @RequestParam("price") double price,
            @RequestParam("quantity") int quantity,
            @RequestParam("description") String description,
            @RequestParam("image") MultipartFile imageFile,
            Model model) {
        try {
            if (imageFile.isEmpty()) {
                model.addAttribute("error", "Please select an image file.");
                return "addbook";
            }

            String imageUrl = fileUpload.uploadFile(imageFile);
            BookEntity book = new BookEntity();
            book.setName(name);
            book.setAuthor(author);
            book.setPrice(price);
            book.setQuantity(quantity);
            book.setDescription(description);
            book.setImage(imageUrl);

            bookService.save(book);

            return "redirect:/homepage";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error uploading file: " + e.getMessage());
            return "addbook";
        }
    }

    @RequestMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable("id") Long id){
        bookService.deleteBookById(id);
        return "redirect:/availablebook";
    }
    @GetMapping("/mybook")
    public String mybook(Model model){
        List<MyBookEntity> list = myBookService.getAllMyBook();
        model.addAttribute("books",list);
        return "mybook";
    }
    @GetMapping("/mybook/{id}")
    public String getMyBook(@PathVariable("id") Long id ){
        Optional<BookEntity> bookEntity = bookService.getBookById(id);
        if(bookEntity.isPresent()) {
            MyBookEntity myBookEntity = new MyBookEntity();
            myBookEntity.setName(bookEntity.get().getName());
            myBookEntity.setAuthor(bookEntity.get().getAuthor());
            myBookEntity.setPrice(bookEntity.get().getPrice());
            myBookEntity.setQuantity(bookEntity.get().getQuantity());

            myBookService.saveMyBook(myBookEntity);
            return "redirect:/mybook";
        }
        else {
            return "redirect:/availablebook?error=notfound";
        }
    }

    @PostMapping("/mybook/{id}")
    public String addCart(@PathVariable("id") Long id , @RequestParam("quantity") int quantity ){
        Optional<BookEntity> BookEntity = bookService.getBookById(id);
       if(BookEntity.isPresent()){
           MyBookEntity myBook = new MyBookEntity();
           myBook.setName(BookEntity.get().getName());
           myBook.setAuthor(BookEntity.get().getAuthor());
           myBook.setPrice(BookEntity.get().getPrice());
           myBook.setQuantity(quantity);
           myBookService.saveMyBook(myBook);
           return "redirect:/viewdetails/{id}";
       }
       else {
           return "redirect:/availablebook?error=notfound";
       }
    }

    @PostMapping("/saveviewdetails")
    public String saveViewDetails(
            @RequestParam("name") String name,
            @RequestParam("author") String author,
            @RequestParam("price") double price,
            @RequestParam("description") String description,
            @RequestParam("image") MultipartFile imageFile,
            Model model) {
        try {
            if (imageFile.isEmpty()) {
                model.addAttribute("error", "Please select an image file.");
                return "/homepage";
            }

            String imageUrl = fileUpload.uploadFile(imageFile);
            BookEntity book = new BookEntity();
            book.setName(name);
            book.setAuthor(author);
            book.setPrice(price);
            book.setDescription(description);
            book.setImage(imageUrl);
            bookService.save(book);

            return "redirect:/viewdetails";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error uploading file: " + e.getMessage());
            return "/homepage";
        }
    }

    @GetMapping("/viewdetails/{id}")
    public String viewdetails(@PathVariable Long id, Model model) {
        Optional<BookEntity> bookEntity = bookService.getBookById(id);

        if (bookEntity.isPresent()) {
            model.addAttribute("book", bookEntity.get());
            return "viewdetails";
        } else {
            model.addAttribute("error", "Book not found!");
            return "redirect:/homepage";
        }
    }


}
