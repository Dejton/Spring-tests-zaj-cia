package com.spring_boot_testing_142.repository;

import com.spring_boot_testing_142.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByGenre(String fantasy);

    List<Book> findByAuthorAndPublicationDate(String s, int i);

    int countBooksByPublicationDate(int publicationDate);

    List<Book> findByAuthor(String author);

    @Query("SELECT AVG(r) FROM Book b JOIN b.rating r WHERE b.author = :author")
    Double calculateAverageRatingByAuthor(String author);

    @Query(value = "SELECT * FROM books b WHERE b.price < ?1", nativeQuery = true)
    List<Book> findBooksCheaperThen(BigDecimal price);
    List<Book> findByPriceLessThan(BigDecimal price);
    @Query(value = "SELECT * FROM books WHERE publication_date > :year AND genre = :genre", nativeQuery = true)
    List<Book> findRecentBooksByGenre(int year, String genre);


    List<Book> findAllByIsAvailableIsTrue();

    long countByGenre(String fantasy);
@Query(value = "SELECT b FROM Book b ORDER BY b.price DESC LIMIT 1")
    List<Book> findMostExpensiveBook();
    @Query("SELECT b FROM Book b ORDER BY b.price LIMIT 1")
    List<Book> findMostCheapestBook();
@Query("SELECT b FROM Book b WHERE :ratingThreshold < SOME (SELECT r FROM b.rating r)")
    List<Book> findBooksWithRatingAbove(int ratingThreshold);
}
