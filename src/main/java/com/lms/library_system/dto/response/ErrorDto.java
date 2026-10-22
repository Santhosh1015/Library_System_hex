package com.lms.library_system.dto.response;

import java.time.Instant;
import java.time.LocalDateTime;

public record ErrorDto(
        String message,
        String comments,
        LocalDateTime timestamp
) {
}
