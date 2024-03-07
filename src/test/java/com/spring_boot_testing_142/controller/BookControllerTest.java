package com.spring_boot_testing_142.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring_boot_testing_142.entity.Book;
import com.spring_boot_testing_142.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.mock;
import static org.hamcrest.Matchers.is;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(BookController.class)
class BookControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookService bookService;
    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book(1L,
                "Władca Pierscieni",
                "fantasy",
                "J.R.R Tolkien",
                2000,
                "123456AC",
                new BigDecimal("300.00"),
                true,
                Arrays.asList(2, 1, 4, 8));
    }

    @DisplayName("test zapisywania książki")
    @Test
    void testingSaveBook() throws Exception {
//        given
        given(bookService.saveBook(any(Book.class))).willReturn(book);
//        when
        ResultActions response = mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)));
//        then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is(book.getTitle())));
//                .andExpect(content().json(String.valueOf(book)));
    }

    @DisplayName("test pobierania wszystkich książek")
    @Test
    void testingGetAllBooks() throws Exception {
//        given
        Book book2 = new Book(2L, "Władca Pierscieni", "fantasy", "J.R.R Tolkien", 2000, "123456AF", new BigDecimal("300.00"), false, Arrays.asList(2, 1, 4, 8));
        List<Book> listOfBooks = List.of(book, book2);
        given(bookService.findAllBooks()).willReturn(listOfBooks);
//        when
        ResultActions response = mockMvc.perform(get("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(listOfBooks)));
//        then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(listOfBooks.size())));
    }

    @DisplayName("test wyszukiwania książki po id")
    @Test
    void testingGetBookById() throws Exception {
//        given
        given(bookService.findBookById(book.getId())).willReturn(Optional.of(book));
//        when
        ResultActions response = mockMvc.perform(get("/api/books/{id}", book.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)));
//        then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(book.getTitle())))
                .andExpect(jsonPath("$.author", is(book.getAuthor())));
    }
//    -----------------dodać do metody parametr ID--------------------------------

    @DisplayName("test uaktualniania książki")
    @Test
    void testingUpdateBook() throws Exception {
//        given
        given(bookService.updateBook(eq(book.getId()), any(Book.class))).willReturn(Optional.of(book));
//        when
        ResultActions response = mockMvc.perform(put("/api/books/{id}", book.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)));
//        then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(book.getTitle())));
//                .andExpect(jsonPath("$.id", is(book.getId())));
    }

    @DisplayName("test usuwania książki")
    @Test
    void testingDeleteBook() throws Exception {
//        given
        willDoNothing().given(bookService).deleteBook(book.getId());
//        when
        ResultActions response = mockMvc.perform(delete("/api/books/{id}", book.getId()));
//        then
        response.andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("test wyszukiwania książki po gatunku")
    @Test
    void testingGetByGenre() throws Exception {
//        given
        Book book2 = new Book(2L, "Władca Pierscieni", "fantasy", "J.R.R Tolkien", 2000, "123456AF", new BigDecimal("300.00"), false, Arrays.asList(2, 1, 4, 8));
        List<Book> books = Arrays.asList(book, book2);
        given(bookService.findByGenre(anyString())).willReturn(books);
//        when
        ResultActions response = mockMvc.perform(get("/api/books/genre/{genre}", book.getGenre())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(books)));
//        then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(books.size())));
    }

    @DisplayName("test wyszukiwania książki po autorze i dacie publikacji")
    @Test
    void testingGetByAuthorNadPublicationDate() throws Exception {
//        given
        Book book2 = new Book(2L, "Władca Pierscieni", "fantasy", "J.R.R Tolkien", 2000, "123456AF", new BigDecimal("300.00"), false, Arrays.asList(2, 1, 4, 8));
        List<Book> books = Arrays.asList(book, book2);
        given(bookService.findByAuthorAndPublicationDate(anyString(), anyInt())).willReturn(books);
//        when
        ResultActions response = mockMvc.perform(get("/api/books/{author}/{publicationDate}", book.getAuthor(), book.getPublicationDate())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(books)));
//        then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(books.size())));
    }

    @DisplayName("test liczenia książek po dacie publikacji")
    @Test
    void testingCountByPublicationDate() throws Exception {
//        given
        given(bookService.countBooksByPublicationDate(book.getPublicationDate())).willReturn(1);
//        when
        ResultActions response = mockMvc.perform(get("/api/books/count/{publicationDate}", book.getPublicationDate())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book))
        );
//        then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(1)));
    }

    @DisplayName("test wyszukiwania książek po autrze")
    @Test
    void testingGetByAuthor() throws Exception {
//        given
        List<Book> books = Arrays.asList(book);
        given(bookService.findByAuthor(book.getAuthor())).willReturn(books);
//        when
        ResultActions response = mockMvc.perform(get("/api/books/author/{author}", book.getAuthor())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book))
        );
//        then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(books.size())));
    }

    @DisplayName("test liczenia sredniej ocen po autorze")
    @Test
    void testingCalculateAvgRatingsByAuthor() throws Exception{
//        given
        given(bookService.calculateAverageRatingByAuthor(book.getAuthor())).willReturn(3.75);
//        when
        ResultActions response = mockMvc.perform(get("/api/books/avgRating/{author}", book.getAuthor())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book))
        );
//        then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(3.75)));
    }
    @DisplayName("test wyszukiwania książki tańszej niż")
    @Test
    void testingGetCheaperThan () throws Exception {
//        given

        List<Book> books = List.of(book);
        given(bookService.findBooksCheaperThen(book.getPrice())).willReturn(books);
//        when
        ResultActions response = mockMvc.perform(get("/api/books/cheaperThan/{price}", book.getPrice())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)));

//        then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(books.size())));
    }
    @DisplayName("test wyszukiwania ostatnio dodanych książek po gatunku")
    @Test
    void testingGetRecentBooksByGenre() throws Exception{
//        given
        List<Book> books = List.of(book);
        given(bookService.findRecentBooksByGenre(book.getPublicationDate(), book.getGenre())).willReturn(books);
//        when
        ResultActions response = mockMvc.perform(get("/api/books/recent/{genre}/{publicationDate}", book.getGenre(), book.getPublicationDate())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book))
        );
//        then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(books.size())));
    }

    @DisplayName("test wyszukiwania wszystkich dostępnych książek")
    @Test
    void testingGetAllAvailableBooks() throws Exception {
//        given
        List<Book> books = List.of(book);
        given(bookService.findAllByIsAvailableIsTrue()).willReturn(books);
//        when
        ResultActions response = mockMvc.perform(get("/api/books/allAvailable")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book))
        );
//        then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", is(book.getTitle())))
                .andExpect(jsonPath("$[0].author", is(book.getAuthor())));
    }
    @DisplayName("test liczenia książek po gatunku")
    @Test
    void testingCountByGenre() throws Exception {
//        given
        given(bookService.countByGenre(book.getGenre())).willReturn(1L);
//        when
        ResultActions response = mockMvc.perform(get("/api/books/countGenre/{genre}", book.getGenre())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book))
        );
//        then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(1)));
    }
    @DisplayName("test wyszukiwania najdroższych książek")
    @Test
    void testingGetMostExpensiveBooks() throws Exception {
//        given
        List<Book> books = List.of(book);
        given(bookService.findMostExpensiveBook()).willReturn(books);
//        when
        ResultActions response = mockMvc.perform(get("/api/books/mostExpensive")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book))
        );
//        then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(books.size())));
    }
    @DisplayName("test wyszukiwania najtańszych książek")
    @Test
    void testingGetMostCheapestBooks() throws Exception {
//        given
        List<Book> books = List.of(book);
        given(bookService.findMostCheapestBook()).willReturn(books);
//        when
        ResultActions response = mockMvc.perform(get("/api/books/mostCheapest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book))
        );
//        then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(books.size())));
    }
    @DisplayName("test wyszukiwania książek z oceną poniżej")
    @Test
    void testingGetAllWithRatingAbove() throws Exception {
//        given
        List<Book> books = List.of(book);
        given(bookService.findBooksWithRatingAbove(3)).willReturn(books);
//        when
        ResultActions response = mockMvc.perform(get("/api/books/ratingAbove/{threshold}", 3)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book))
        );
//        then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(books.size())));
    }
}

















