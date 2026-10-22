package com.lms.library_system.service;

import com.lms.library_system.Mapper.UserMapper;
import com.lms.library_system.dto.request.UserDto;
import com.lms.library_system.enums.Role;
import com.lms.library_system.enums.Status;
import com.lms.library_system.model.User;
import com.lms.library_system.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public void addUser(@Valid UserDto userDto) {
        User user = UserMapper.createUser(userDto);
        user.setRole(Role.LIBRARIAN);
        user.setStatus(Status.ACTIVE);

        userRepository.save(user);
        log.info("User created Successfully..");
    }
}
