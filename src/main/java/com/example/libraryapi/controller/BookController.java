package com.example.libraryapi.controller;

import com.example.libraryapi.model.Book;
import com.example.libraryapi.repository.BookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    //GET
    @GetMapping("/apilib/books")
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @GetMapping("/apilib/book/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        return optionalBook.isPresent() ?
                ResponseEntity.ok(optionalBook.get()) : ResponseEntity.notFound().build();
    }


    //POST
    @PostMapping("/apilib/books")
    public ResponseEntity<List<Book>> createBooks(@RequestBody List<Book> books) {
        bookRepository.saveAll(books);
        return ResponseEntity.ok(books);
    }

    @PostMapping("/apilib/book")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        bookRepository.save(book);
        return ResponseEntity.ok(book);
    }

    //PUT


    //DELETE
    @DeleteMapping("/apilib/books")
    public ResponseEntity<Book> deleteAll() {
        bookRepository.deleteAll();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/apilib/book/{id}")
    public ResponseEntity<Book> deleteById(@PathVariable Long id) {
        bookRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
