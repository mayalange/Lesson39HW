package org.example.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.dto.BookDTO;
import org.example.model.Book;
import org.example.repository.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

public class BookService {
    private final BookRepository bookRepository;
    private final Logger logger = LogManager.getLogger();


    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void addBook(BookDTO bookDTO) {
        final Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setPublishedYear(bookDTO.getPublishedYear());
        book.setGenre(bookDTO.getGenre());

        try {
            bookRepository.create(book);
            logger.info("Book created successfully: ID={}, Title={}", book.getId(), book.getTitle());
        } catch (Exception e) {
            logger.error("Failed to create book: {}", e.getMessage(), e);
            throw e;
        }    }

    public List<BookDTO> getBooksByReaderId(int readerId) {
        return bookRepository.findByReaderId(readerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private BookDTO convertToDTO(Book book) {
        final BookDTO dto = new BookDTO();
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setPublishedYear(book.getPublishedYear());
        dto.setGenre(book.getGenre());
        return dto;
    }
}
