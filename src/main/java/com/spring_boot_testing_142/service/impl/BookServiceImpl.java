package com.spring_boot_testing_142.service.impl;

import com.spring_boot_testing_142.entity.Book;
import com.spring_boot_testing_142.repository.BookRepository;
import com.spring_boot_testing_142.service.BookService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService{
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findByGenre(String genre) {
        return bookRepository.findByGenre(genre);
    }

    @Override
    public List<Book> findByAuthorAndPublicationDate(String author, int publicationDate) {
        return bookRepository.findByAuthorAndPublicationDate(author,publicationDate);
    }

    @Override
    public int countBooksByPublicationDate(int publicationDate) {
        return bookRepository.countBooksByPublicationDate(publicationDate);
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    @Override
    public Double calculateAverageRatingByAuthor(String author) {
        return bookRepository.calculateAverageRatingByAuthor(author);
    }

    @Override
    public List<Book> findBooksCheaperThen(BigDecimal price) {
        return bookRepository.findBooksCheaperThen(price);
    }

    @Override
    public List<Book> findByPriceLessThan(BigDecimal price) {
        return null;
    }

    @Override
    public List<Book> findRecentBooksByGenre(int year, String genre) {
        return bookRepository.findRecentBooksByGenre(year,genre);
    }

    @Override
    public List<Book> findAllByIsAvailableIsTrue() {
        return bookRepository.findAllByIsAvailableIsTrue();
    }

    @Override
    public long countByGenre(String genre) {
        return bookRepository.countByGenre(genre);
    }

    @Override
    public List<Book> findMostExpensiveBook() {
        return bookRepository.findMostExpensiveBook();
    }

    @Override
    public List<Book> findMostCheapestBook() {
        return bookRepository.findMostCheapestBook();
    }

    @Override
    public List<Book> findBooksWithRatingAbove(int ratingThreshold) {
        return bookRepository.findBooksWithRatingAbove(ratingThreshold);
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> findBookById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Optional<Book> updateBook(long id, Book book) {
        return findBookById(id).map(
                savedBook -> {
                    savedBook.setTitle(book.getTitle());
                    savedBook.setAuthor(book.getAuthor());
                    savedBook.setGenre(book.getGenre());
                    savedBook.setRating(book.getRating());
                    savedBook.setPrice(book.getPrice());
                    savedBook.setPublicationDate(book.getPublicationDate());
                    savedBook.setIsbn(book.getIsbn());
                    return bookRepository.save(book);
        });

    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
