package com.lms.library_system.repository;

import com.lms.library_system.dto.response.BookResDto;
import com.lms.library_system.enums.BookStatus;
import com.lms.library_system.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("""
            select b.title,b.author,b.status,b.isbn,b.publicationYear
            from Book b
            where ( ?1 is null or b.status = ?1)
            and (?2 is null or b.publicationYear >= ?2)
            and (?3 is null or b.publicationYear <= ?3)
            and b.status <> ?4
            """)
    List<BookResDto> fetchAllBooks(BookStatus status, Integer fromYear, Integer toYear, BookStatus deleted);

    Book findByIsbn(String isbn);
}
