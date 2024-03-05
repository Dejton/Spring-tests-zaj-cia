package com.spring_boot_testing_142.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String genre;
    @Column(nullable = false)
    private String author;
    @Column(name = "publication_date", nullable = false)
    private int publicationDate;
    @Column(nullable = false, unique = true)
    private String isbn;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(name = "available", nullable = false)
    private boolean isAvailable;
    @ElementCollection
    @CollectionTable(name = "book_ratings", joinColumns = @JoinColumn(name = "book_id"))
    @Column(nullable = false)
    private List<Integer> rating;

    public double countAvgRating() {
        return this.getRating().stream()
                .mapToInt(Integer::intValue).average().orElse(0.0);
    };

}
