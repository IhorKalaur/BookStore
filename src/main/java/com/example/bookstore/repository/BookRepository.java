package com.example.bookstore.repository;

import com.example.bookstore.model.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByCategories_Id(Long id);
    @Query("SELECT DISTINCT b FROM Book b LEFT JOIN FETCH b.categories")
    List<Book> findAllWithCategories(Pageable pageable);

    @Query("SELECT DISTINCT b FROM Book b LEFT JOIN FETCH b.categories WHERE b.id = :id")
    Optional<Book> findByIdWithCategories(Long id);
}
