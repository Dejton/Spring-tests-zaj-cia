package com.spring_boot_testing_142.repository;

import com.spring_boot_testing_142.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.api.InstanceOfAssertFactories.list;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;
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

    @DisplayName("test zapisywania książki")
    @Test
    void testingSavedBook() {
//        given
//        when
        Book savedBook = bookRepository.save(book);
//        then
        assertThat(savedBook).isNotNull();
        assertThat(savedBook).isEqualTo(book);
        assertThat(savedBook.getId()).isGreaterThan(0);
    }

    @DisplayName("test usuwania książki")
    @Test
    void testingDeletingBookById() {
//        given
        bookRepository.save(book);
//        when
        bookRepository.deleteById(book.getId());
        Optional<Book> optionalBook = bookRepository.findById(book.getId());
//        then
        assertThat(optionalBook).isEmpty();
    }

    @DisplayName("test pobierania wszystkich książek")
    @Test
    void testingAllBooksDownloading() {
//        given
        Book book1 = new Book(
                2L,
                "Harry Potter",
                "fantasy",
                "J.K Rowling",
                2000,
                "98765AC",
                new BigDecimal("29.90"),
                true,
                Arrays.asList(5, 6, 2, 1)
        );
        bookRepository.save(book);
        bookRepository.save(book1);
//        when
        List<Book> allBooks = bookRepository.findAll();
//        then
        assertThat(allBooks).isNotNull();
        assertThat(allBooks.size()).isEqualTo(2);
    }

    @DisplayName("test pobierania ksiązki przez id")
    @Test
    void testingBookDownloadingById() {
//        given
        bookRepository.save(book);
//        when
        Optional<Book> foundBook = bookRepository.findById(book.getId());
//        then
        assertThat(foundBook).isNotNull();
        assertThat(foundBook.get().getId()).isEqualTo(book.getId());
    }

    @DisplayName("test uaktualniania książki")
    @Test
    void testingBookUpdating() {
//        given
        Book savedBook = bookRepository.save(book);
//        Book savedBook = bookRepository.findById(book.getId()).get();
        savedBook.setPrice(new BigDecimal("59.90"));
        savedBook.setPublicationDate(1997);
//        when
        Book updatedBook = bookRepository.save(savedBook);
//        then
        assertThat(updatedBook).isNotNull();
        assertThat(updatedBook.getPrice()).isEqualTo("59.90");
        assertThat(updatedBook.getPublicationDate()).isEqualTo(1997);
    }

    @DisplayName("test wyszukiwania książek po gatunku")
    @Test
    void testingFindingBookByGenre() {
//        given
        String genre = bookRepository.save(book).getGenre();
//        when
        List<Book> foundBooks = bookRepository.findByGenre(genre);
//        then
        assertThat(foundBooks).isNotEmpty();
        assertThat(foundBooks.size()).isEqualTo(1);
        assertThat(foundBooks.get(0).getGenre()).isEqualTo(genre);
    }

    @DisplayName("test wyszukiwania książki po autorze i roku publikacji")
    @Test
    void testingFindingBookByAuthorAndPublicationDate() {
//        given
        Book savedBook = bookRepository.save(book);
//        when
        List<Book> foundBooks = bookRepository.findByAuthorAndPublicationDate(savedBook.getAuthor(), savedBook.getPublicationDate());
//        then
        assertThat(foundBooks).isNotEmpty();
        assertThat(foundBooks).allMatch((b) -> b.getAuthor().equals(savedBook.getAuthor()) && b.getPublicationDate() == savedBook.getPublicationDate());
    }

    @DisplayName("testowanie liczenia książek wg. roku wydania")
    @Test
    void testingCountingBookByPublicationDate() {
//        given
        Book book1 = new Book(
                2L,
                "Harry Potter",
                "fantasy",
                "J.K Rowling",
                1950,
                "98765AC",
                new BigDecimal("29.90"),
                true,
                Arrays.asList(5, 6, 2, 1)
        );
        bookRepository.save(book);
        bookRepository.save(book1);
//        when
        int countedBooks = bookRepository.countBooksByPublicationDate(1950);
//        then
        assertThat(countedBooks).isEqualTo(2);
    }

    @DisplayName("test obliczania sredniej oceny dla autora")
    @Test
    void testingCalculatingAvgRatingForAuthor() {
//        given
        Book book1 = new Book(2L, "Harry Potter", "fantasy", "J.K Rowling", 1950, "98765AE", new BigDecimal("29.90"), true, Arrays.asList(1, 9, 7, 6));
        Book book2 = new Book(3L, "Harry Potter", "fantasy", "J.K Rowling", 1950, "98765AD", new BigDecimal("29.90"), true, Arrays.asList(6, 7, 3, 4));
        Book book3 = new Book(4L, "Harry Potter", "fantasy", "J.K Rowling", 1950, "98765AS", new BigDecimal("29.90"), true, Arrays.asList(2, 5, 4, 3));

        bookRepository.save(book);
        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);

        List<Book> booksList = bookRepository.findByAuthor(book.getAuthor());
        Double avgRating = bookRepository.calculateAverageRatingByAuthor(book.getAuthor());
//        when

//        then
        assertThat(avgRating).isNotNull();
        assertThat(avgRating).isEqualTo(booksList
                .stream()
                .mapToDouble(Book::countAvgRating)
                .average()
                .orElse(0.0));
    }

    @DisplayName("test wyszukiwania książek tańszych niż podana cena")
    @Test
    void testingFindingBooksCheapestThanPrice() {
//        given
        bookRepository.save(book);
        BigDecimal price = new BigDecimal("50.00");
//        when
        List<Book> books = bookRepository.findBooksCheaperThen(price);

//        then
        assertThat(books).isNotEmpty();
    }

    @DisplayName("test wyszukiwania książek tańszych niż podana cenav2")
    @Test
    void testingFindingBooksCheapestThanPricev2() {
//        given
        bookRepository.save(book);
        BigDecimal price = new BigDecimal("50.00");
//        when
        List<Book> books = bookRepository.findByPriceLessThan(price);

//        then
        assertThat(books).isNotEmpty();
    }

    @DisplayName("test wyszukiwania wszystkich dostępnych książek (scenariusz pozytywny)")
    @Test
    void testingFindingAllAvailableBooksWithCorrectBook() {
//        given
        Book book1 = new Book(2L, "Harry Potter", "fantasy", "J.K Rowling", 1950, "98765AE", new BigDecimal("29.90"), true, Arrays.asList(1, 9, 7, 6));
        Book book2 = new Book(3L, "Harry Potter", "fantasy", "J.K Rowling", 1950, "98765AD", new BigDecimal("29.90"), false, Arrays.asList(6, 7, 3, 4));
        Book book3 = new Book(4L, "Harry Potter", "fantasy", "J.K Rowling", 1950, "98765AS", new BigDecimal("29.90"), false, Arrays.asList(2, 5, 4, 3));

        bookRepository.save(book);
        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);
//        when
        List<Book> books = bookRepository.findAllByIsAvailableIsTrue();
//        then
        assertThat(books).isNotEmpty();
    }



    @DisplayName("test wyszukiwania niedawno dodanych książek w danym gatunku")
    @Test
    void testingFindingRecentBooksByGenre() {
//        given

        Book book1= new Book(2L, "Władca Pierscieni", "fantasy", "J.R.R Tolkien", 2000, "123456AC", new BigDecimal("49.90"), true, Arrays.asList(5, 4, 9, 3));
        Book book2= new Book(3L, "Władca Pierscieni", "fantasy", "J.R.R Tolkien", 1900, "123456ACC", new BigDecimal("49.90"), true, Arrays.asList(5, 4, 9, 3));
        bookRepository.save(book);
        bookRepository.save(book1);
        bookRepository.save(book2);
        String genre = book.getGenre();
        int year = 1900;
//        when
        List<Book> books = bookRepository.findRecentBooksByGenre(year, genre);
//        then
        assertThat(books).isNotEmpty();
        assertThat(books).allMatch((b) -> b.getPublicationDate() > year && b.getGenre().equals(genre));
    }

    @DisplayName("test liczenia wszystkich książek")
    @Test
    void testCalculateAllBooks() {
//        given
        Book book1= new Book(2L, "Władca Pierscieni", "fantasy", "J.R.R Tolkien", 2000, "123456AC", new BigDecimal("49.90"), true, Arrays.asList(5, 4, 9, 3));
        Book book2= new Book(3L, "Władca Pierscieni", "fantasy", "J.R.R Tolkien", 1900, "123456ACC", new BigDecimal("49.90"), true, Arrays.asList(5, 4, 9, 3));
        bookRepository.save(book);
        bookRepository.save(book1);
        bookRepository.save(book2);
//        when
        long countedBooks = bookRepository.count();
//        then
        assertThat(countedBooks).isEqualTo(3);
    }

    @DisplayName("test liczenia wszystkich książek z danego gatunku")
    @Test
    void testFindAllBookByGenre() {
//        given
        Book book1= new Book(2L, "Władca Pierscieni", "fantasy", "J.R.R Tolkien", 2000, "123456AC", new BigDecimal("49.90"), true, Arrays.asList(5, 4, 9, 3));
        Book book2= new Book(3L, "Władca Pierscieni", "fantasy", "J.R.R Tolkien", 1900, "123456ACC", new BigDecimal("49.90"), true, Arrays.asList(5, 4, 9, 3));
        bookRepository.save(book);
        bookRepository.save(book1);
        bookRepository.save(book2);
//        when
        long books =  bookRepository.countByGenre("fantasy");
//        then
        assertThat(books).isEqualTo(3);
    }

    @DisplayName("test wyszukiwania najdroższej książki")
    @Test
    void testFindMostExpensiveBook() {
//        given
        Book book1= new Book(2L, "Władca Pierscieni", "fantasy", "J.R.R Tolkien", 2000, "123456AC", new BigDecimal("300.00"), true, Arrays.asList(5, 4, 9, 3));
        Book book2= new Book(3L, "Władca Pierscieni", "fantasy", "J.R.R Tolkien", 1900, "123456ACC", new BigDecimal("49.90"), true, Arrays.asList(5, 4, 9, 3));
        bookRepository.save(book);
        bookRepository.save(book1);
        bookRepository.save(book2);
//        when
        List<Book> books = bookRepository.findMostExpensiveBook();
//        then
        assertThat(books.size()).isEqualTo(1);
    }

    @DisplayName("test wyszukiwania najtańszej książki")
    @Test
    void testFindMostCheapestBook() {
//        given
        Book book1= new Book(2L, "Władca Pierscieni", "fantasy", "J.R.R Tolkien", 2000, "123456AC", new BigDecimal("300.00"), true, Arrays.asList(5, 4, 9, 3));
        Book book2= new Book(3L, "Władca Pierscieni", "fantasy", "J.R.R Tolkien", 1900, "123456ACC", new BigDecimal("49.90"), true, Arrays.asList(5, 4, 9, 3));
        bookRepository.save(book);
        bookRepository.save(book1);
        bookRepository.save(book2);
//        when
        List<Book> books = bookRepository.findMostCheapestBook();
//        then
        assertThat(books.size()).isEqualTo(1);
    }

    @DisplayName("test wyszukiwanie książek z odpowiednimi ocenami powyżej podanej wartosci")
    @Test
    void testFindBookWithRatingAboveThreshold() {
//        given
        Book book1= new Book(2L, "Władca Pierscieni", "fantasy", "J.R.R Tolkien", 2000, "123456AC", new BigDecimal("300.00"), true, Arrays.asList(1,1,1,1));
        Book book2= new Book(3L, "Władca Pierscieni", "fantasy", "J.R.R Tolkien", 1900, "123456ACC", new BigDecimal("49.90"), true, Arrays.asList(2,2,2,2));
        bookRepository.save(book);
        bookRepository.save(book1);
        bookRepository.save(book2);
        int threshold = 4;
//        when
        List<Book> books = bookRepository.findBooksWithRatingAbove(4);
        books.forEach(book -> System.out.printf("Książka z ratingiem %s: oraz id: %d%n", book.getRating(), book.getId()));
//        then
        assertThat(books).isNotEmpty();
        assertThat(books).allMatch((b) -> b.getRating().stream().anyMatch(rating -> rating > threshold));
    }
}

































