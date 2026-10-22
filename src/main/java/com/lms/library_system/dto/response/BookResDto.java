package com.lms.library_system.dto.response;

import com.lms.library_system.enums.BookStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookResDto(
        String title,
        String author,
        BookStatus status,
        String isbn,
        Integer publicationYear

) {
}
