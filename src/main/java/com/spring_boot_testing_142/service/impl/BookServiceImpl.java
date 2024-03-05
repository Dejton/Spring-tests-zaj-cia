package com.spring_boot_testing_142.service.impl;

import com.spring_boot_testing_142.entity.Book;
import com.spring_boot_testing_142.repository.BookRepository;
import com.spring_boot_testing_142.service.BookService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BookServiceImpl implements BookService{
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findByGenre(String fantasy) {
        return null;
    }

    @Override
    public List<Book> findByAuthorAndPublicationDate(String s, int i) {
        return null;
    }

    @Override
    public int countBooksByPublicationDate(int publicationDate) {
        return 0;
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return null;
    }

    @Override
    public Double calculateAverageRatingByAuthor(String author) {
        return null;
    }

    @Override
    public List<Book> findBooksCheaperThen(BigDecimal price) {
        return null;
    }

    @Override
    public List<Book> findByPriceLessThan(BigDecimal price) {
        return null;
    }

    @Override
    public List<Book> findRecentBooksByGenre(int year, String genre) {
        return null;
    }

    @Override
    public List<Book> findAllByIsAvailableIsTrue() {
        return null;
    }

    @Override
    public long countByGenre(String fantasy) {
        return 0;
    }

    @Override
    public List<Book> findMostExpensiveBook() {
        return null;
    }

    @Override
    public List<Book> findMostCheapestBook() {
        return null;
    }

    @Override
    public List<Book> findBooksWithRatingAbove(int ratingThreshold) {
        return null;
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }
}
