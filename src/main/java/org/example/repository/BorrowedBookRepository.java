package org.example.repository;

import org.example.model.BorrowedBook;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class BorrowedBookRepository {
    private final SessionFactory sessionFactory;

    public BorrowedBookRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(BorrowedBook borrowedBook) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(borrowedBook);
            session.getTransaction().commit();
        }
    }

    public long countBorrowedBooks(int bookId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery(
                    "SELECT COUNT(b) FROM BorrowedBook b WHERE b.book.id = :bookId AND b.status = 'borrowed'",
                    Long.class);
            query.setParameter("bookId", bookId);
            return query.uniqueResult();
        }
    }
}
