package com.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Exercise 9: Run the Spring Boot application and test the REST endpoints,
 * e.g. with curl:
 *
 *   curl -X POST http://localhost:8080/api/books \
 *        -H "Content-Type: application/json" \
 *        -d '{"title":"Effective Java","author":"Joshua Bloch","isbn":"9780134685991"}'
 *
 *   curl http://localhost:8080/api/books
 */
@SpringBootApplication
public class LibraryManagementBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementBootApplication.class, args);
    }
}
