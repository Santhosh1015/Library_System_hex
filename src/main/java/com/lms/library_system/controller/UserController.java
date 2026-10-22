package com.lms.library_system.controller;

import com.lms.library_system.dto.request.UserDto;
import com.lms.library_system.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/user/add")
    public void addUser(@Valid @RequestBody UserDto userDto){
        userService.addUser(userDto);
    }
}
