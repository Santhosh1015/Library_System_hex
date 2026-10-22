package com.lms.library_system.controller;

import com.lms.library_system.dto.request.BookDto;
import com.lms.library_system.dto.response.BookResDto;
import com.lms.library_system.enums.BookStatus;
import com.lms.library_system.service.BookService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/api/book/add")
    public void addBook(@Valid @RequestBody BookDto bookDto,
                        Principal principal){
        bookService.addBook(principal.getName(), bookDto);
    }
    @PutMapping("/api/book/update/{bookId}")
    public void updateBook(@Valid @RequestBody BookDto bookDto,
                           @PathVariable Long bookId,
                           Principal principal){
        bookService.updateBook(principal.getName(), bookId, bookDto);
    }
    @DeleteMapping("/api/book/{isbn}")
    public void deleteBook(@PathVariable String isbn,
                           Principal principal) {

        bookService.deleteBook(principal.getName(), isbn);
    }

    @GetMapping("/api/books")
    public List<BookResDto> fetchAllBooks(@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                                          @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                                          @RequestParam(name = "status", required = false)BookStatus status,
                                          @RequestParam(name = "fromYear", required = false) Integer fromYear,
                                          @RequestParam(name = "toYear", required = false) Integer toYear

                                          ){
        return bookService.fetchAllBooks(page, size, status, fromYear, toYear);
    }

    @GetMapping("/api/book/{isbn}")
    public BookResDto fetchBookByIsbn(@PathVariable String isbn)
    {
        return bookService.fetchBookByIsbn(isbn);
    }


}
