package com.lms.library_system.service;

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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    public void addBookTest(){

        String librarianUsername = "librarian1";

        BookDto dto1 = new BookDto("title1","author1", BookStatus.AVAILABLE,"isbn1",1999);

        User user = new User();
        user.setUserName(librarianUsername);
        user.setRole(Role.LIBRARIAN);
        user.setStatus(Status.ACTIVE);

        Book book = new Book();

        when(userRepository.findByUserName(librarianUsername))
                .thenReturn(user);

        when(bookRepository.save(any(Book.class))).thenReturn(any(Book.class));

        bookService.addBook(librarianUsername, dto1);

        verify(userRepository, times(1)).findByUserName(librarianUsername);

        verify(bookRepository, times(1)).save(any(Book.class));



    }
    @Test
    public void addBookInvalidTest() {

        String librarianUsername = "librarian1";

        BookDto dto1 = new BookDto("title1","author1", BookStatus.AVAILABLE,"isbn1",1999);

        User user = new User();
        user.setUserName(librarianUsername);
        user.setStatus(Status.INACTIVE);


        User user1 = new User();
        user.setUserName(librarianUsername);
        user.setRole(Role.STUDENT);

        when(userRepository.findByUserName(librarianUsername)).thenReturn(user);
        when(userRepository.findByUserName(librarianUsername)).thenReturn(user1);
        assertThrows(AccessDeniedException.class, ()-> bookService.addBook(librarianUsername, dto1));
        assertThrows(AccessDeniedException.class, ()-> bookService.addBook(librarianUsername, dto1));

        verify(userRepository, times(2)).findByUserName(librarianUsername);

    }

    @Test
    public void updateBookTest() {

        String librarianUsername = "librarian1";
        Long bookId = 1L;

        BookDto dto1 = new BookDto( "title1", "author1",BookStatus.AVAILABLE, "isbn1", 1999);

        User user = new User();
        user.setUserName(librarianUsername);
        user.setStatus(Status.ACTIVE);
        user.setRole(Role.LIBRARIAN);

        Book book = new Book();

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(userRepository.findByUserName(librarianUsername)).thenReturn(user);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        bookService.updateBook(librarianUsername, bookId, dto1);

        verify(bookRepository, times(1)).findById(bookId);
        verify(userRepository, times(1)).findByUserName(librarianUsername);
        verify(bookRepository, times(1)).save(any(Book.class));
        verifyNoMoreInteractions(bookRepository);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void updateBookInvalidTest() {

        String librarianUsername = "librarian1";
        Long bookId = 1L;

        BookDto dto1 = new BookDto( "title1", "author1",BookStatus.AVAILABLE, "isbn1", 1999);


        User user = new User();
        user.setUserName(librarianUsername);
        user.setStatus(Status.INACTIVE);
        user.setRole(Role.LIBRARIAN);

        User user1 = new User();
        user1.setUserName(librarianUsername);
        user1.setStatus(Status.ACTIVE);
        user1.setRole(Role.STUDENT);

        Book book = new Book();

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        when(userRepository.findByUserName(librarianUsername)).thenReturn(user);
        when(userRepository.findByUserName(librarianUsername)).thenReturn(user1);

        assertThrows(AccessDeniedException.class, ()-> bookService.updateBook(librarianUsername, bookId, dto1));
        assertThrows(AccessDeniedException.class, ()-> bookService.updateBook(librarianUsername, bookId, dto1));

        verify(bookRepository, times(2)).findById(bookId);
        verify(userRepository, times(2)).findByUserName(librarianUsername);

        verifyNoMoreInteractions(bookRepository);
        verifyNoMoreInteractions(userRepository);
    }
    @Test
    public void fetchAllBooksTest() {
        BookResDto bookResDto = new BookResDto( "title1", "author1",BookStatus.AVAILABLE, "isbn1", 1999);
        when(bookRepository.fetchAllBooks(null, null, null, BookStatus.DELETED)).thenReturn(List.of(bookResDto));

        List<BookResDto> result = bookService.fetchAllBooks(0, 5, null, null, null);
        assertEquals(1, result.size());

        verify(bookRepository, times(1)).fetchAllBooks(null, null, null, BookStatus.DELETED);
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    public void fetchBookByIsbnTest() {

        String isbn = "isbn1";
        Book book = new Book();
        book.setIsbn(isbn);

        BookResDto bookResDto = new BookResDto( "title1", "author1",BookStatus.AVAILABLE, "isbn1", 1999);

        when(bookRepository.findByIsbn(isbn)).thenReturn(book);

        BookResDto result = bookService.fetchBookByIsbn(isbn);

        assertEquals("isbn1", result.isbn() );

        verify(bookRepository, times(1)).findByIsbn(isbn);
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    public void fetchBookByIsbnInvalidTest() {

        String isbn = "isbn";

        when(bookRepository.findByIsbn(isbn)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class,
                () -> bookService.fetchBookByIsbn(isbn));

        verify(bookRepository, times(1)).findByIsbn(isbn);

        verifyNoMoreInteractions(bookRepository);
    }

}
