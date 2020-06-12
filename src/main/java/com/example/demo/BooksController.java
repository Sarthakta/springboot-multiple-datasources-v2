package com.example.demo;

import com.example.demo.books.model.Books;
import com.example.demo.books.repo.BooksRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BooksController {

  @Autowired
  BooksRepository booksRepository;

  @GetMapping(value = "/library/books")
  public  List<Books> getAllBooks(){
    return booksRepository.findAll();

  }

  @PostMapping(value = "/library/book")
  public Books saveBook(@RequestBody Books books){
    return booksRepository.save(books);

  }

}
