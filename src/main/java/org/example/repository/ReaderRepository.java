package org.example.repository;

import org.example.model.Reader;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class ReaderRepository {

    private final SessionFactory sessionFactory;

    public ReaderRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(Reader reader) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(reader);
            transaction.commit();
        }
    }

    public Reader findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Reader.class, id);
        }
    }
}
