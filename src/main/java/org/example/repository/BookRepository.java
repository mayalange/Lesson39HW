package org.example.repository;

import org.example.model.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class BookRepository {
    private final SessionFactory sessionFactory;

    public BookRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(Book book) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(book);
            session.getTransaction().commit();
        }
    }

    public List<Book> findByReaderId(int readerId) {
        try (Session session = sessionFactory.openSession()) {
            final Query<Book> query = session.createQuery(
                    "SELECT b FROM Book b JOIN b.borrowedBooks bb " +
                            "WHERE bb.reader.id = :readerId AND bb.status = 'borrowed'",
                    Book.class);
            query.setParameter("readerId", readerId);
            return query.getResultList();
        }
    }
    public Book findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Book.class, id);
        }
    }
}