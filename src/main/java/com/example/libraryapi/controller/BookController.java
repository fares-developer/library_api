package com.example.libraryapi.controller;

import com.example.libraryapi.model.Book;
import com.example.libraryapi.repository.BookRepository;
import com.example.libraryapi.utils.Language;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class BookController {

    BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    //GET
    @CrossOrigin(origins = {"*"})
    @GetMapping("/apilib/books")
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @GetMapping("/apilib/book/{id}")
    public ResponseEntity<Book> getBook(@PathVariable String id) {
        Optional<Book> optionalBook = bookRepository.findById(Long.parseLong(id));
        return optionalBook.isPresent() ?
                ResponseEntity.ok(optionalBook.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/apilib/book/author={author}")
    public ResponseEntity<Book> getByAuthor(@PathVariable String author) {
        Optional<Book> optionalBook = bookRepository.findAll()
                .stream().filter(book -> Objects.equals(book.getAuthor(), author)).findFirst();
        return optionalBook.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/apilib/book/year={year}")
    public ResponseEntity<Book> getByYear(@PathVariable String year) {
        Optional<Book> optionalBook = bookRepository.findAll()
                .stream().filter(book -> book.getRelease().getYear() == Integer.parseInt(year)).findFirst();
        return optionalBook.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/apilib/books/lang={lang}")
    public ResponseEntity<List<Book>> getByLang(@PathVariable String lang) {

        Language l;
        switch (lang.substring(0,2).toLowerCase()) {
            case "es":
                l = Language.esES;
                break;
            case "en":
                l = Language.enEN;
                break;
            case "fr":
                l = Language.frFR;
                break;
            default:
                l = Language.UNKNOW;
        }
        Language finalL = l;
        List<Book> list = bookRepository.findAll()
                .stream().filter(book -> book.getLanguage() == finalL).toList();
        return list.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(list);
    }


    //POST
    @PostMapping("/apilib/books")
    public ResponseEntity<Book> create(@RequestBody List<Book> books) {
        bookRepository.saveAll(books);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/apilib/book")
    public ResponseEntity<Book> create(@RequestBody Book book) {
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
}