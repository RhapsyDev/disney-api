package com.rhapsydev.alkemy.disney.controller;

import com.rhapsydev.alkemy.disney.mapstruct.dto.UserDto;
import com.rhapsydev.alkemy.disney.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registration successful", content = @Content),
            @ApiResponse(responseCode = "400", description = "Email already in use by another user", content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDto userDto) {
        userService.saveUser(userDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.logInUser(userDto), HttpStatus.OK);
    }
}
