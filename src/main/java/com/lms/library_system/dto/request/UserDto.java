package com.lms.library_system.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserDto (
    @NotBlank(message = "Username is required")
    String userName,

    @NotBlank(message = "Password is required")
    String password
){
}
