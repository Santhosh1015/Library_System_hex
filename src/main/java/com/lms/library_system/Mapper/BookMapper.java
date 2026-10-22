package com.lms.library_system.Mapper;

import com.lms.library_system.dto.request.BookDto;
import com.lms.library_system.dto.response.BookResDto;
import com.lms.library_system.model.Book;
import jakarta.validation.Valid;

public class BookMapper {
    public static Book createBook(@Valid BookDto bookDto) {
        Book book = new Book();
        book.setAuthor(bookDto.author());
        book.setTitle(bookDto.title());
        book.setIsbn(bookDto.isbn());
        book.setPublicationYear(bookDto.publicationYear());
        return  book;
    }

    public static BookResDto createBookDto(Book book) {
        return new BookResDto(
                book.getTitle(),
                book.getAuthor(),
                book.getStatus(),
                book.getIsbn(),
                book.getPublicationYear()
        );
    }
}
