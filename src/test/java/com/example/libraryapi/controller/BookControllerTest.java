package com.example.libraryapi.controller;

import com.example.libraryapi.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.test.context.event.annotation.AfterTestClass;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private Integer port;

    private final String pathbook = "/apilib/book";
    private final String pathbooks = "/apilib/books";
    private final String url = "http://localhost:";

    @BeforeEach
    void setUp() {
        restTemplateBuilder = restTemplateBuilder.rootUri(url+ port);
        testRestTemplate = new TestRestTemplate(restTemplateBuilder);

        ///Preparamos la petición http
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        String json = """
                [
                             {
                                 "title":"Programming with Java",
                                 "author":"Oracle",
                                 "editorial":"The Java editorial",
                                 "pages":355,
                                 "edition":8,
                                 "language":"esES",
                                 "release":"2023-01-12"
                             },
                             {
                                 "title":"Programming with Kotlin",
                                 "author":"Jetbrains",
                                 "editorial":"The Jetbrains Editorial",
                                 "pages":225,
                                 "edition":6,
                                 "language":"esES",
                                 "release":"2022-09-20"
                             }
                 ]
                  """;

        //A continuación ejecutamos la petición
        HttpEntity<String> request = new HttpEntity<>(json, headers);
        ResponseEntity<Book[]> response =
                testRestTemplate.exchange(pathbooks, HttpMethod.POST, request, Book[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Book> result = List.of(Objects.requireNonNull(response.getBody()));
        assertTrue(result.size() >= 2);
    }

    @Test
    void getAll() {
        ResponseEntity<Book[]> response =
                testRestTemplate.getForEntity(pathbooks, Book[].class);
        List<Book> books = Arrays.asList(Objects.requireNonNull(response.getBody()));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(books.size() >= 2);
    }

    @Test
    void getBook() {
        ResponseEntity<Book> response =
                testRestTemplate.getForEntity(pathbook+"/1", Book.class);
        Book book = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(
                Objects.equals(book.getTitle(), "Programming with Java") ||
                        Objects.equals(book.getTitle(), "Clean Code")
        );
    }

    @Test
    void createBook() {
        //Preparamos la petición http
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        String json = """
                {
                          "title":"Clean Code",
                          "author":"Uncle Bob",
                          "editorial":"The Editorial",
                          "pages":245,
                          "edition":3,
                          "language":"esES",
                          "release":"2022-11-20"
                      }
                """;

        //A continuación ejecutamos la petición
        HttpEntity<String> request = new HttpEntity<>(json, headers);
        ResponseEntity<Book> response =
                testRestTemplate.exchange(pathbook, HttpMethod.POST, request, Book.class);
        Book result = response.getBody();
        assertEquals("Clean Code", Objects.requireNonNull(result).getTitle());
    }

    @Test
    void deleteById() {
        //Preparamos la petición http
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        //A continuación ejecutamos la petición
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<Book> response =
                testRestTemplate.exchange(pathbook+"/1", HttpMethod.DELETE, request, Book.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @AfterTestClass
    public void deleteAll() {
        //Preparamos la petición http
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        //A continuación ejecutamos la petición
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<Book> response =
                testRestTemplate.exchange("/apilib/books", HttpMethod.DELETE, request, Book.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}