package com.example.demo.books.repo;

import com.example.demo.books.model.Books;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends JpaRepository<Books, Integer> {

}