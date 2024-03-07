package com.spring_boot_testing_142.service;

import com.spring_boot_testing_142.entity.Book;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BookService {
    Book saveBook(Book book);

    List<Book> findAllBooks();

    Optional<Book> findBookById(Long id);

    Optional<Book> updateBook(long id, Book book);

    void deleteBook(Long id);
    List<Book> findByGenre(String genre);

    List<Book> findByAuthorAndPublicationDate(String author, int publicationDate);

    int countBooksByPublicationDate(int publicationDate);

    List<Book> findByAuthor(String author);

    Double calculateAverageRatingByAuthor(String author);

    List<Book> findBooksCheaperThen(BigDecimal price);

    List<Book> findByPriceLessThan(BigDecimal price);

    List<Book> findRecentBooksByGenre(int year, String genre);

    List<Book> findAllByIsAvailableIsTrue();

    long countByGenre(String genre);

    List<Book> findMostExpensiveBook();

    List<Book> findMostCheapestBook();

    List<Book> findBooksWithRatingAbove(int ratingThreshold);

}
