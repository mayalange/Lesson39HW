package org.example.config;

import jakarta.ws.rs.ApplicationPath;
import org.example.controller.*;
import org.example.repository.*;
import org.example.service.*;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api")
public class ApplicationConfig extends ResourceConfig {
    public ApplicationConfig() {
        HibernateUtils.getSessionFactory();

        BookRepository bookRepo = new BookRepository(HibernateUtils.getSessionFactory());

        BookService bookService = new BookService(bookRepo);

        register(new BookController(bookService));
        register(new ReaderController(new ReaderService(new ReaderRepository(HibernateUtils.getSessionFactory()))));
        register(new BorrowController(new BorrowService(
                new BorrowedBookRepository(HibernateUtils.getSessionFactory()),
                bookRepo,
                new ReaderRepository(HibernateUtils.getSessionFactory())
        )));
    }
}