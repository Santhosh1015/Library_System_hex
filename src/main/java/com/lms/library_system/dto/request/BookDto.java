package com.lms.library_system.dto.request;

import com.lms.library_system.enums.BookStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookDto(
        @NotBlank(message = "title is required")
        @NotNull(message = "title is required")
        String title,

        @NotBlank(message = "author is required")
        @NotNull(message = "author is required")
        String author,

        BookStatus  status,

        @NotBlank(message = "isbn is required")
        @NotNull(message = "isbn is required")
        String isbn,

        @NotNull(message = "publicationYear is required")
        Integer publicationYear

) {
}
