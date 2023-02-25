package com.example.libraryapi.model;

import com.example.libraryapi.utils.Language;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String author;
    private String editorial;
    private Integer pages;
    private String edition;
    private Language language;
    private LocalDate release;

    public Book() {}

    public Long getId() {
        return id;
    }

    public Book setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Book setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Book setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getEditorial() {
        return editorial;
    }

    public Book setEditorial(String editorial) {
        this.editorial = editorial;
        return this;
    }

    public Integer getPages() {
        return pages;
    }

    public Book setPages(Integer pages) {
        this.pages = pages;
        return this;
    }

    public String getEdition() {
        return edition;
    }

    public Book setEdition(String edition) {
        this.edition = edition;
        return this;
    }

    public Language getLanguage() {
        return language;
    }

    public Book setLanguage(Language language) {
        this.language = language;
        return this;
    }

    public LocalDate getRelease() {
        return release;
    }

    public Book setRelease(LocalDate release) {
        this.release = release;
        return this;
    }
}
