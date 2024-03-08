package com.spring_boot_testing_142.service.impl;

import com.spring_boot_testing_142.entity.Book;
import com.spring_boot_testing_142.repository.BookRepository;
import com.spring_boot_testing_142.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookServiceImplTest {
    private final BookRepository bookRepository = mock(BookRepository.class);
    private final BookServiceImpl bookServiceImpl = new BookServiceImpl(bookRepository);
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
                false,
                Arrays.asList(2, 1, 4, 8));
    }

    @DisplayName("test zapisywania książki")
    @Test
    void testingSavingBooks() {
//        given
        when(bookRepository.save(any(Book.class))).thenReturn(book);
//        when
        Book savedBook = bookServiceImpl.saveBook(book);
//        then
        assertThat(savedBook).isNotNull();
        assertThat(savedBook).isEqualTo(book);
        verify(bookRepository).save(book);
    }

    @DisplayName("test pobierania wszystkich książek pozytywny scenariusz")
    @Test
    void testingGetAllBooksHappyPath() {
//        given
        Book book2 = book = new Book(2L, "Władca Pierscieni", "fantasy", "J.R.R Tolkien", 2000, "123456AB", new BigDecimal("300.00"), true, Arrays.asList(5, 4, 9, 3));
        when(bookRepository.findAll()).thenReturn(List.of(book, book2));
//        when
        List<Book> books = bookServiceImpl.findAllBooks();
//        then
        assertThat(books).isNotEmpty();
        assertThat(books.size()).isEqualTo(2);

    }

    @DisplayName("test pobierania wszystkich książek negatywny scenariusz")
    @Test
    void testingFindAllBooksNegativeScenario() {
//        given
        when(bookRepository.findAll()).thenReturn(Collections.emptyList());
//        when
        List<Book> books = bookServiceImpl.findAllBooks();
//        then
        assertThat(books).isEmpty();
    }

    @DisplayName("test wyszukiwania książki po Id")
    @Test
    void testingFindBookById(){
//        given
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
//        when
        Optional<Book> foundBook = bookServiceImpl.findBookById(book.getId());
//        then
        assertThat(foundBook).isNotNull();
        assertThat(foundBook.get()).isEqualTo(book);
    }
//---------------------------------Ogarnąć czemu nie działa opcja z Id-------------------------
    @DisplayName("test uaktualniania książki")
    @Test
    void testingUpdateBook() {
//        given
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        book.setAuthor("Adam Mickiewicz");
        book.setGenre("Dramat");
//        when
        Book updatedBook = bookServiceImpl.updateBook(anyLong(), book).get();
//        then
        assertThat(updatedBook).isNotNull();
        assertThat(updatedBook.getAuthor()).isEqualTo("Adam Mickiewicz");
        assertThat(updatedBook.getGenre()).isEqualTo("Dramat");
    }

    @DisplayName("test usuwania książki")
    @Test
    void testingDeleteBook() {
//        given
        when(bookRepository.save(any(Book.class))).thenReturn(book);
//        when
        bookServiceImpl.deleteBook(book.getId());
//        then
        verify(bookRepository).deleteById(book.getId());
    }

    @DisplayName("test wyszukiwania książki po gatunku")
    @Test
    void testingFindBookByGenre() {
//        given
        String genre = book.getGenre();
        when(bookRepository.findByGenre(genre)).thenReturn(List.of(book));
//        when
        List<Book> foundBooks= bookServiceImpl.findByGenre(genre);
//        then
        assertThat(foundBooks).isNotEmpty();
        assertThat(foundBooks.size()).isEqualTo(1);
    }

    @DisplayName("test wyszukiwania książki po autorze i dacie publikacji")
    @Test
    void testingFindBookByAuthorAndPublicationDate() {
//        given
        int year = book.getPublicationDate();
        String author = book.getAuthor();
        Book book2 = book = new Book(2L, "Władca Pierscieni", "fantasy", "J.R.R Tolkien", 2000, "123456AB", new BigDecimal("300.00"), true, Arrays.asList(5, 4, 9, 3));
        when(bookRepository.findByAuthorAndPublicationDate(anyString(), anyInt())).thenReturn(List.of(book, book2));
//        when
        List<Book> books = bookServiceImpl.findByAuthorAndPublicationDate(author, year);
//        then
        assertThat(books).isNotEmpty();
        assertThat(books.size()).isEqualTo(2);
        assertThat(books.get(0)).isEqualTo(book);
    }

    @DisplayName("test liczenia książek po dacie publikacji")
    @Test
    void testingCountBooksByPublicationDate() {
//        given
        when(bookRepository.countBooksByPublicationDate(anyInt())).thenReturn(1);
//        when
        int counter = bookServiceImpl.countBooksByPublicationDate(2000);
//        then
        assertThat(counter).isEqualTo(1);
    }

    @DisplayName("test wyszukiwania po autorze")
    @Test
    void testingFindBookByAuthor() {
//        given
        when(bookRepository.findByAuthor(anyString())).thenReturn(List.of(book));
//        when
        List<Book> books = bookServiceImpl.findByAuthor(book.getAuthor());
//        then
        assertThat(books).isNotEmpty();
        assertThat(books.size()).isEqualTo(1);
    }

    @DisplayName("test obliczania sredniej ocen po autorze")
    @Test
    void testingCalculateAverageRatingsByAuthor() {
//        given
        Book book2 = book = new Book(2L, "Władca Pierscieni", "fantasy", "J.R.R Tolkien", 2000, "123456AB", new BigDecimal("300.00"), true, Arrays.asList(6, 4, 9, 8));
        Book book3 = book = new Book(3L, "Władca Pierscieni", "fantasy", "J.R.R Tolkien", 2000, "123456AB", new BigDecimal("300.00"), true, Arrays.asList(5, 3, 5, 3));
        Book book4 = book = new Book(4L, "Władca Pierscieni", "fantasy", "J.R.R Tolkien", 2000, "123456AB", new BigDecimal("300.00"), true, Arrays.asList(1, 4,7, 4));
        List<Book> books = Arrays.asList(book, book2, book3, book4);
        System.out.println(books);
        double avgRating= books.stream()
                .mapToDouble(Book::countAvgRating)
                .average()
                .orElse(0.0);
        when(bookRepository.calculateAverageRatingByAuthor(anyString())).thenReturn(avgRating);
        System.out.println(avgRating);

//        when
        Double avg = bookServiceImpl.calculateAverageRatingByAuthor(book.getAuthor());
//        then
        assertThat(avg).isNotNull();
        assertThat(avg).isEqualTo(avgRating);
    }

    @DisplayName("test wyszukiwania książek tańszych niż podana cena")
    @Test
    void testingFindBookCheapestThan() {
//        given
        Book book2 = book = new Book(2L, "Władca Pierscieni", "fantasy", "J.R.R Tolkien", 2000, "123456Ag", new BigDecimal("75.00"), true, Arrays.asList(6, 4, 9, 8));
        Book book3 = book = new Book(3L, "Władca Pierscieni", "fantasy", "J.R.R Tolkien", 2000, "123456gB", new BigDecimal("50.00"), true, Arrays.asList(5, 3, 5, 3));
        Book book4 = book = new Book(4L, "Władca Pierscieni", "fantasy", "J.R.R Tolkien", 2000, "123h6AB", new BigDecimal("50.00"), true, Arrays.asList(1, 4,7, 4));
        BigDecimal price = new BigDecimal("100.0");

        when(bookRepository.findBooksCheaperThen(price)).thenReturn(List.of(book3, book4, book2));
//        when
        List<Book> booksList = bookServiceImpl.findBooksCheaperThen(price);
        System.out.println(booksList);
//        then
        assertThat(booksList).isNotEmpty();
        assertThat(booksList).hasSize(3);
    }

    @DisplayName("test wyszukiwania książek niedawno dodanych w danym gatunku")
    @Test
    void testingFindRecentBooksByGenre() {
//        given
        Book book3 = book = new Book(3L, "Władca Pierscieni", "fantasy", "J.R.R Tolkien", 2000, "123456gB", new BigDecimal("50.00"), true, Arrays.asList(5, 3, 5, 3));
        int year = 1900;
        String genre = "fantasy";
        when(bookRepository.findRecentBooksByGenre(year, genre)).thenReturn(List.of(book, book3));
//        when
        List<Book> books = bookServiceImpl.findRecentBooksByGenre(year, genre);
//        then
        assertThat(books).isNotEmpty();
        assertThat(books).hasSize(2);
    }

    @DisplayName("test wyszukiwania wszystkich dostępnych książek")
    @Test
    void testingFindAllAvailableBooks() {
//        given
        Book book3 = book = new Book(3L, "Władca Pierscieni", "fantasy", "J.R.R Tolkien", 2000, "123456gB", new BigDecimal("50.00"), true, Arrays.asList(5, 3, 5, 3));
        when(bookRepository.findAllByIsAvailableIsTrue()).thenReturn(List.of(book3));
//        when
        List<Book> books = bookServiceImpl.findAllByIsAvailableIsTrue();
//        then
        assertThat(books).isNotEmpty();
        assertThat(books).hasSize(1);
    }

    @DisplayName("test liczenia książek w danym gatunku")
    @Test
    void testingCountBooksByGenre() {
//        given
        when(bookRepository.countByGenre(anyString())).thenReturn(1L);
//        when
        long number = bookServiceImpl.countByGenre(book.getGenre());
//        then
        assertThat(number).isGreaterThan(0);
        assertThat(number).isEqualTo(1);
    }

    @DisplayName("test wyszukiwania najdroższej książki")
    @Test
    void testingFindMostExpensiveBook() {
//        given
        when(bookRepository.findMostExpensiveBook()).thenReturn(List.of(book));
//        when
        List<Book> books = bookServiceImpl.findMostExpensiveBook();
//        then
        assertThat(books).isNotEmpty();
        assertThat(books).hasSize(1);
    }

    @DisplayName("test wyszukiwania najtańszej książki")
    @Test
    void testingFindMostCheapestBook() {
//        given
        when(bookRepository.findMostCheapestBook()).thenReturn(List.of(book));
//        when
        List<Book> books = bookServiceImpl.findMostCheapestBook();
//        then
        assertThat(books).isNotEmpty();
        assertThat(books).hasSize(1);
    }

    @DisplayName("test wyszukiwania książek z oceną większą niż")
    @Test
    void testingFindBooksWithRatingAbove() {
//        given
        Book book1 = book = new Book(3L, "Władca Pierscieni", "fantasy", "J.R.R Tolkien", 2000, "123456gB", new BigDecimal("50.00"), true, Arrays.asList(2, 2, 2, 2));

        when(bookRepository.findBooksWithRatingAbove(anyInt())).thenReturn(List.of(book1));
//        when
        List<Book> books = bookServiceImpl.findBooksWithRatingAbove(1);
//        then
        assertThat(books).isNotEmpty();
        assertThat(books).hasSize(1);
    }
}


































