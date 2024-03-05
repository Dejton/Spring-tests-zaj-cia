package com.spring_boot_testing_142.service;

import com.spring_boot_testing_142.entity.Book;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> findByGenre(String fantasy);

    List<Book> findByAuthorAndPublicationDate(String s, int i);

    int countBooksByPublicationDate(int publicationDate);

    List<Book> findByAuthor(String author);

    Double calculateAverageRatingByAuthor(String author);

    List<Book> findBooksCheaperThen(BigDecimal price);

    List<Book> findByPriceLessThan(BigDecimal price);

    List<Book> findRecentBooksByGenre(int year, String genre);

    List<Book> findAllByIsAvailableIsTrue();

    long countByGenre(String fantasy);

    List<Book> findMostExpensiveBook();

    List<Book> findMostCheapestBook();

    List<Book> findBooksWithRatingAbove(int ratingThreshold);

}
