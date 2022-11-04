package com.rhapsydev.alkemy.disney.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rhapsydev.alkemy.disney.mapstruct.mapper.MapStructMapperImpl;
import com.rhapsydev.alkemy.disney.model.Character;
import com.rhapsydev.alkemy.disney.service.CharacterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static com.rhapsydev.alkemy.disney.util.RepositoryDataUtil.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ContextConfiguration(classes = {MapStructMapperImpl.class})
@Import(CharacterController.class)
@WebMvcTest(CharacterController.class)
class CharacterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CharacterService characterService;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldFetchAllCharacters() throws Exception {
        when(characterService.findAll()).thenReturn(ALL_CHARACTERS);

        mockMvc.perform(get("/characters").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(ALL_CHARACTERS.size())));
    }

    @Test
    void shouldFetchOneCharacterById() throws Exception {
        when(characterService.findById(anyLong())).thenReturn(SINGLE_CHARACTER);

        mockMvc.perform(get("/characters/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(SINGLE_CHARACTER)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(SINGLE_CHARACTER.getName()))
                .andExpect(jsonPath("$.weight").value(SINGLE_CHARACTER.getWeight()));
    }

    @Test
    void shouldFetchCharactersByName() throws Exception {
        when(characterService.findByName(anyString())).thenReturn(SAME_NAME_CHARACTERS);

        mockMvc.perform(get("/characters?name={name}", anyString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(SAME_NAME_CHARACTERS)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(SAME_NAME_CHARACTERS.size())))
                .andExpect(jsonPath("$.[0].name").value(SAME_NAME_CHARACTERS.get(0).getName()))
                .andExpect(jsonPath("$.[0].image").value(SAME_NAME_CHARACTERS.get(0).getImage()));
    }

    @Test
    void shouldFetchCharactersByAge() throws Exception {
        when(characterService.findByAge(anyInt())).thenReturn(Collections.singletonList(SINGLE_CHARACTER));

        mockMvc.perform(get("/characters?age={age}", 30)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(SINGLE_CHARACTER)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].name").value(SINGLE_CHARACTER.getName()));
    }

    @Test
    void shouldFetchCharactersByMovie() throws Exception {
        when(characterService.findByMovie(3L)).thenReturn(SAME_MOVIE_CHARACTERS);

        mockMvc.perform(get("/characters?movies={id}", 3L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(SAME_MOVIE_CHARACTERS)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].name").value(SAME_MOVIE_CHARACTERS.get(0).getName()))
                .andExpect(jsonPath("$.[0].image").value(SAME_MOVIE_CHARACTERS.get(0).getImage()))
                .andExpect(jsonPath("$.[1].name").value(SAME_MOVIE_CHARACTERS.get(1).getName()))
                .andExpect(jsonPath("$.[1].image").value(SAME_MOVIE_CHARACTERS.get(1).getImage()));
    }

    @Test
    void shouldCreateNewCharacterAndReturnSame() throws Exception {
        SINGLE_CHARACTER.setId(null);
        SINGLE_CHARACTER.setName("Aladdin 2022");

        when(characterService.save(any(Character.class))).then(invocation -> {
            Character newCharacter = invocation.getArgument(0);
            newCharacter.setId(10L);
            return newCharacter;
        });

        mockMvc.perform(post("/characters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(SINGLE_CHARACTER)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(10)))
                .andExpect(jsonPath("$.name", is("Aladdin 2022")));

        verify(characterService).save(any());
    }

    @Test
    void shouldUpdateCharacter() throws Exception {
        SINGLE_CHARACTER.setName("Aladdin updated");
        SINGLE_CHARACTER.setMovies(new HashSet<>());

        when(characterService.findById(1L)).thenReturn(SINGLE_CHARACTER);
        when(characterService.update(any(Character.class), anyLong())).thenReturn(SINGLE_CHARACTER);

        mockMvc.perform(put("/characters/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(SINGLE_CHARACTER)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Aladdin updated")))
                .andExpect(jsonPath("$.age", is(SINGLE_CHARACTER.getAge())))
                .andExpect(jsonPath("$.movies", hasSize(0)));

        verify(characterService).update(any(), anyLong());
    }

    @Test
    void shouldDeleteCharacterAndReturnEmpty() throws Exception {
        when(characterService.findById(anyLong())).thenReturn(SINGLE_CHARACTER);

        mockMvc.perform(delete("/characters/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(characterService).delete(1L);
    }

    @Test
    void shouldThrowExceptionWhenCreateInvalidCharacter() throws Exception {
        SINGLE_CHARACTER.setName(null); // name is required

        mockMvc.perform(put("/characters/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(SINGLE_CHARACTER)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(
                        result.getResolvedException() instanceof MethodArgumentNotValidException));
    }
}