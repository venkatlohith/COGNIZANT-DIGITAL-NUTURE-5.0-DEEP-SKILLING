package com.library.repository;

import com.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Exercise 9: BookRepository interface, extending Spring Data JPA's
 * JpaRepository to get CRUD operations for free.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
