package com.rhapsydev.alkemy.disney.service;

import com.rhapsydev.alkemy.disney.mapstruct.dto.UserDto;
import org.springframework.security.core.Authentication;

public interface UserService {

    void checkEmailAvailability(String email);

    Authentication authenticate(UserDto user);

    void saveUser(UserDto userDto);

    String logInUser(UserDto user);
}
