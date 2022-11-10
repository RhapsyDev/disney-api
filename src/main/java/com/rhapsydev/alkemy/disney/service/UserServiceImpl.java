package com.rhapsydev.alkemy.disney.service;

import com.rhapsydev.alkemy.disney.exception.EmailAlreadyInUseException;
import com.rhapsydev.alkemy.disney.mapstruct.dto.UserDto;
import com.rhapsydev.alkemy.disney.mapstruct.mapper.MapStructMapper;
import com.rhapsydev.alkemy.disney.model.User;
import com.rhapsydev.alkemy.disney.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MapStructMapper mapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    @Override
    public void checkEmailAvailability(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyInUseException(email + " is already in use by another user");
        }
    }

    @Override
    public Authentication authenticate(UserDto user) {
        return null;
    }

    @Override
    public void saveUser(UserDto userDto) {
        checkEmailAvailability(userDto.getEmail());
        User user = User.builder()
                .email(userDto.getEmail())
                .password(bCryptPasswordEncoder.encode(userDto.getPassword())).build();

        userRepository.save(user);
    }

    @Override
    public String logInUser(UserDto user) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        return tokenService.generateToken(authentication);
    }
}
