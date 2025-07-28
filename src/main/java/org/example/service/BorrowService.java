package org.example.service;

import jakarta.ws.rs.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.dto.BorrowDTO;
import org.example.model.Book;
import org.example.model.BorrowedBook;
import org.example.model.Reader;
import org.example.repository.BookRepository;
import org.example.repository.BorrowedBookRepository;
import org.example.repository.ReaderRepository;

public class BorrowService {
    private final BorrowedBookRepository borrowedBookRepository;
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;
    private final Logger logger = LogManager.getLogger();


    public BorrowService(
            BorrowedBookRepository borrowedBookRepository,
            BookRepository bookRepository,
            ReaderRepository readerRepository
    ) {
        this.borrowedBookRepository = borrowedBookRepository;
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;
    }

    public void borrowBook(BorrowDTO borrowDTO) {
        final Book book = bookRepository.findById(borrowDTO.getBookId());
        if (book == null) {
            logger.error("Book not found with ID: {}", borrowDTO.getBookId());
            throw new NotFoundException("Book not found with id: " + borrowDTO.getBookId());
        }


        final Reader reader = readerRepository.findById(borrowDTO.getReaderId());
        if (reader == null) {
            logger.error("Reader not found with ID: {}", borrowDTO.getReaderId());
            throw new NotFoundException("Reader not found with id: " + borrowDTO.getReaderId());
        }

        if (isBookAlreadyBorrowed(book.getId())) {
            logger.warn("Book ID {} is already borrowed", book.getId());
            throw new IllegalStateException("Book is already borrowed");        }

        final BorrowedBook borrowedBook = new BorrowedBook();
        borrowedBook.setBook(book);
        borrowedBook.setReader(reader);
        borrowedBook.setBorrowDate(borrowDTO.getBorrowDate());
        borrowedBook.setReturnDate(borrowDTO.getReturnDate());
        borrowedBook.setStatus(borrowDTO.getStatus());

        try {
            borrowedBookRepository.create(borrowedBook);
            logger.info("Book borrowed successfully: BookID={}, ReaderID={}",
                    book.getId(), reader.getId());
        } catch (Exception e) {
            logger.error("Error borrowing book: {}", e.getMessage(), e);
            throw e;
        }    }

    private boolean isBookAlreadyBorrowed(int bookId) {
        logger.debug("Checking if book ID {} is already borrowed", bookId);
        return borrowedBookRepository.countBorrowedBooks(bookId) > 0;
    }
}
