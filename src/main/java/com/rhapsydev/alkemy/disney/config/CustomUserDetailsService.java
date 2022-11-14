package com.rhapsydev.alkemy.disney.config;

import com.rhapsydev.alkemy.disney.model.User;
import com.rhapsydev.alkemy.disney.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = repository.findByEmail(email);

        return new org.springframework.security.core.userdetails.User(user.orElseThrow().getEmail(),
                user.orElseThrow().getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("read")));
    }
}
