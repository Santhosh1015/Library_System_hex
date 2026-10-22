package com.lms.library_system.config;


import com.lms.library_system.dto.response.ErrorDto;
import com.lms.library_system.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice // it is a global handling of controllers in a project
//especially for exception over all controllers
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDto> handleResourceNotFoundException(ResourceNotFoundException e) {
        return
                ResponseEntity
                        .badRequest()
                        .body(new
                                ErrorDto(
                                e.getMessage(),
                                "Id is not found in the DB",
                                LocalDateTime.now()
                        ));
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> map = new HashMap<>();
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> list = bindingResult.getFieldErrors();

        list.forEach(
                fieldError -> map.put(fieldError.getField(), fieldError.getDefaultMessage())
        );

        //since it may have different errors from single request,
        // we can go with MAP
        return ResponseEntity
                .badRequest()
                .body(map);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDto> handleAccessDeniedException(AccessDeniedException e) {
        return
                ResponseEntity
                        .badRequest()
                        .body(new
                                ErrorDto(
                                e.getMessage(),
                                "Access Denied",
                                LocalDateTime.now()
                        ));
    }


}

