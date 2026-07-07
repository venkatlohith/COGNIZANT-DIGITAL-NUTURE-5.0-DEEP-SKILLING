package com.library;

import com.library.model.Book;
import com.library.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class LibraryManagementApplication {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        BookService bookService = context.getBean(BookService.class);

        bookService.addBook("Effective Java", "Joshua Bloch");
        bookService.addBook("Clean Code", "Robert C. Martin");

        List<Book> books = bookService.getAllBooks();
        System.out.println("Books in library:");
        books.forEach(System.out::println);
    }
}

