package com.example.bookstore.repository;

import com.example.bookstore.model.Book;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Modifying
    @Query("UPDATE Book b " +
            "SET b.title = :newTitle, " +
            "b.author = :newAuthor, " +
            "b.isbn = :newIsbn, " +
            "b.price = :newPrice, " +
            "b.description = :newDescription, " +
            "b.coverImage = :newCoverImage " +
            "WHERE b.id = :bookId")
    Book updateBookById(@Param("bookId") Long bookId,
                        @Param("newTitle") String newTitle,
                        @Param("newAuthor") String newAuthor,
                        @Param("newIsbn") String newIsbn,
                        @Param("newPrice") BigDecimal newPrice,
                        @Param("newDescription") String newDescription,
                        @Param("newCoverImage") String newCoverImage);

}
