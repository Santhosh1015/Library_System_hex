package com.lms.library_system.service;

import com.lms.library_system.Mapper.BookMapper;
import com.lms.library_system.dto.request.BookDto;
import com.lms.library_system.dto.response.BookResDto;
import com.lms.library_system.enums.BookStatus;
import com.lms.library_system.enums.Role;
import com.lms.library_system.enums.Status;
import com.lms.library_system.exception.ResourceNotFoundException;
import com.lms.library_system.model.Book;
import com.lms.library_system.model.User;
import com.lms.library_system.repository.BookRepository;
import com.lms.library_system.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    @Transactional
    public void addBook(String librarianUsername, @Valid BookDto bookDto) {
        // check librarian
        User user = userRepository.findByUserName(librarianUsername);

        if(user.getStatus() != Status.ACTIVE || user.getRole() != Role.LIBRARIAN){
            throw new AccessDeniedException("Access Denied");
        }

        Book book = BookMapper.createBook(bookDto);

        bookRepository.save(book);
        log.info("Book registered Successfully..");

    }

    @Transactional
    public void updateBook(String librarianUsername, Long bookId, @Valid BookDto bookDto) {
        // validate the bookId
        Book book = bookRepository.findById(bookId)
                .orElseThrow(()-> new ResourceNotFoundException("Invalid Book Id"));

        // check it is librarian
        User user = userRepository.findByUserName(librarianUsername);
        if(user.getStatus() != Status.ACTIVE){
            throw new AccessDeniedException("User is inactive");
        }
        if(user.getRole() != Role.LIBRARIAN){
            throw new AccessDeniedException("Only Librarian can update Book");
        }

        // update the fields
        book.setTitle(bookDto.title());
        book.setAuthor(bookDto.author());
        book.setPublicationYear(bookDto.publicationYear());
        book.setStatus(bookDto.status());

        bookRepository.save(book);
        log.info("Book updated successfully");
    }


    @Transactional
    public void deleteBook(String librarianUsername, String isbn) {

        Book book = bookRepository.findByIsbn(isbn);
        if(book == null){
            throw new ResourceNotFoundException("Book with ISBN is not found");
        }

        User user = userRepository.findByUserName(librarianUsername);
        if (user.getStatus() != Status.ACTIVE) {
            throw new AccessDeniedException("User is inactive");
        }

        if (user.getRole() != Role.LIBRARIAN) {
            throw new AccessDeniedException(
                    "Only a librarian can delete a book");
        }

        if(book.getStatus() == BookStatus.DELETED){
            throw new AccessDeniedException("Book is already Deleted");
        }
        book.setStatus(BookStatus.DELETED);
        bookRepository.save(book);
    }


    public List<BookResDto> fetchAllBooks(Integer page, Integer size, BookStatus status, Integer fromYear, Integer toYear) {
        Pageable pageable = PageRequest.of(page, size);
        return bookRepository.fetchAllBooks(status, fromYear, toYear, BookStatus.DELETED);
    }

    public BookResDto fetchBookByIsbn(String isbn) {

        Book book = bookRepository.findByIsbn(isbn);
        if(book == null){
            throw new ResourceNotFoundException("Book with ISBN is not found");
        }

        return BookMapper.createBookDto(book);

    }
}
