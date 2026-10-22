package com.lms.library_system.Mapper;

import com.lms.library_system.dto.request.UserDto;
import com.lms.library_system.model.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public static User createUser(@Valid UserDto userDto) {
        User user = new User();
        user.setUserName(userDto.userName());
        user.setPassword(userDto.password());
        return  user;
    }
}
