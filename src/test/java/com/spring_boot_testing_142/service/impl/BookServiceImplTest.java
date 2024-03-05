package com.spring_boot_testing_142.service.impl;

import com.spring_boot_testing_142.entity.Book;
import com.spring_boot_testing_142.repository.BookRepository;
import com.spring_boot_testing_142.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookServiceImplTest {
    private BookRepository bookRepository = mock(BookRepository.class);

    private BookServiceImpl bookService = new BookServiceImpl(bookRepository);
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