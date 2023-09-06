package com.example.bookstore.repository.impl;

import com.example.bookstore.exceptions.DataProcessingException;
import com.example.bookstore.exceptions.EntityNotFoundException;
import com.example.bookstore.model.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaQuery;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

public class BookRepositoryImpl {
    private final EntityManagerFactory entityManagerFactory;


    public Book save(Book book) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(book);
            transaction.commit();
            return book;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert book into DB" + book, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public List<Book> findAll() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            CriteriaQuery<Book> criteriaQuery = entityManager.getCriteriaBuilder()
                    .createQuery(Book.class);
            criteriaQuery.from(Book.class);
            return entityManager.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all books", e);
        }
    }

    public Book getById(Long id) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            Book book = entityManager.find(Book.class, id);
            if (book == null) {
                throw new EntityNotFoundException("Can't get book with id: " + id);
            }
            return book;
        }
    }
}
