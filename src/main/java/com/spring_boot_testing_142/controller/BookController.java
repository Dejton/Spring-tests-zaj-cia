package com.spring_boot_testing_142.controller;

import com.spring_boot_testing_142.entity.Book;
import com.spring_boot_testing_142.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book saveBook(@RequestBody Book book) {
        return bookService.saveBook(book);
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.findAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable long id) {
        return bookService.findBookById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable long id, Book book) {
        return bookService.updateBook(id, book)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable long id) {
       bookService.deleteBook(id);
       return new ResponseEntity<>("Book deleted successfull", HttpStatus.OK);
    }

    @GetMapping("/genre/{genre}")
    public List<Book> getBookByGenre(@PathVariable String genre) {
        return bookService.findByGenre(genre);
    }

    @GetMapping("/{author}/{publicationDate}")
    public List<Book> getByAuthorAndPublicationDate(@PathVariable String author, @PathVariable int publicationDate) {
        return bookService.findByAuthorAndPublicationDate(author, publicationDate);
    }
    @GetMapping("/count/{publicationDate}")
    public int countByPublicationDate(@PathVariable int publicationDate) {
        return bookService.countBooksByPublicationDate(publicationDate);
    }
    @GetMapping("/author/{author}")
    public List<Book> getByAuthor(@PathVariable String author) {
        return bookService.findByAuthor(author);
    }
    @GetMapping("/avgRating/{author}")
    public double calculateAvgRatingsByAuthor(@PathVariable String author) {
        return bookService.calculateAverageRatingByAuthor(author);
    }

    @GetMapping("/cheaperThan/{price}")
    public List<Book> getCheaperThan(@PathVariable BigDecimal price) {
        return bookService.findBooksCheaperThen(price);
    }
    @GetMapping("/recent/{genre}/{publicationDate}")
    public  List<Book> getRecentBooksByGenre(@PathVariable int publicationDate, @PathVariable String genre) {
        return bookService.findRecentBooksByGenre(publicationDate, genre);
    }
    @GetMapping("/allAvailable")
    public List<Book> getAllAvailableIsTrue() {
        return bookService.findAllByIsAvailableIsTrue();
    }
    @GetMapping("/countGenre/{genre}")
    public long countByGenre(@PathVariable String genre) {
        return bookService.countByGenre(genre);
    }
    @GetMapping("/mostExpensive")
    public List<Book> getMostExpensive() {
        return bookService.findMostExpensiveBook();
    }
    @GetMapping("/mostCheapest")
    public List<Book> getMostCheapest() {
        return bookService.findMostCheapestBook();
    }
    @GetMapping("/ratingAbove/{threshold}")
    public List<Book> getBooksWithRatingAbove(@PathVariable int threshold) {
        return bookService.findBooksWithRatingAbove(threshold);
    }
}































