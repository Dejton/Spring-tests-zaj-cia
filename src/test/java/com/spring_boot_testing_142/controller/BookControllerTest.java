package com.spring_boot_testing_142.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring_boot_testing_142.entity.Book;
import com.spring_boot_testing_142.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(BookController.class)
class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookService bookService;
    @Autowired
    private ObjectMapper objectMapper;
    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book(
                1L,
                "Władca Pierscieni",
                "fantasy",
                "J.R.R Tolkien",
                1950,
                "123456AB",
                new BigDecimal("49.90"),
                true,
                Arrays.asList(5, 4, 9, 3)
        );
    }


}