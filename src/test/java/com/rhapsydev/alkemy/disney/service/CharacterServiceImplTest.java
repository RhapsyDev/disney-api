package com.rhapsydev.alkemy.disney.service;

import com.rhapsydev.alkemy.disney.model.Character;
import com.rhapsydev.alkemy.disney.model.Movie;
import com.rhapsydev.alkemy.disney.repository.CharacterRepository;
import com.rhapsydev.alkemy.disney.repository.MovieRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static com.rhapsydev.alkemy.disney.util.RepositoryDataUtil.SINGLE_CHARACTER;
import static com.rhapsydev.alkemy.disney.util.RepositoryDataUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CharacterServiceImplTest {

    @Mock
    CharacterRepository characterRepository;

    @Mock
    MovieRepository movieRepository;

    @InjectMocks
    CharacterServiceImpl characterService;

    @BeforeEach
    void setUp() {
//        System.out.println("Single character is... " + SINGLE_CHARACTER.getId());
    }

    @Test
    void findAll() {
        when(characterRepository.findAll()).thenReturn(ALL_CHARACTERS);
        List<Character> characterList = characterService.findAll();

        assertNotNull(characterList);
        assertEquals(ALL_CHARACTERS.size(), characterList.size());
        assertNotNull(characterList.stream().filter(
                character -> character.getName().equals("Goofy")).findFirst().orElseThrow().getId());

        verify(characterRepository, atLeastOnce()).findAll();
    }

    @Test
    void findById() {
        when(characterRepository.findById(anyLong())).thenReturn(Optional.of(SINGLE_CHARACTER_2));
        Character singleCharacter = characterService.findById(10L);

        assertNotNull(singleCharacter);
        assertEquals(SINGLE_CHARACTER_2.getId(), singleCharacter.getId());
        assertEquals(SINGLE_CHARACTER_2.getWeight(), singleCharacter.getWeight());

        verify(characterRepository).findById(anyLong());
    }

    @Test
    void findByName() {
        when(characterRepository.findByNameContainingIgnoreCase(anyString())).thenReturn(SAME_NAME_CHARACTERS);

        List<Character> byName = characterService.findByName("mouse");

        assertNotNull(byName);
        assertEquals(2, byName.size());
        assertEquals(1, byName.stream().filter(c -> c.getName().contains("Mickey")).count());

        verify(characterRepository, atLeastOnce()).findByNameContainingIgnoreCase(anyString());
    }

    @Test
    void findByAge() {
        when(characterRepository.findByAge(anyInt())).thenReturn(ALL_CHARACTERS);

        List<Character> byAge = characterService.findByAge(30);

        assertNotNull(byAge);
        assertEquals(30, byAge.stream().findAny().orElseThrow().getAge());
        assertEquals(2, byAge.stream().filter(c -> c.getAge().equals(30)).count());

        verify(characterRepository).findByAge(anyInt());
    }

    @Test
    void findByMovie() {
        when(characterRepository.findByMovies(any(Movie.class))).thenReturn(SAME_MOVIE_CHARACTERS);

        List<Character> byMovie = characterService.findByMovie(3L);

        assertNotNull(byMovie);
        assertNotNull(byMovie.stream().findFirst().orElseThrow().getMovies());
        assertEquals(SAME_MOVIE_CHARACTERS.size(), byMovie.size());

        verify(characterRepository).findByMovies(any(Movie.class));
    }

    @Test
    void shouldThrowExceptionWhenCharacterDoesNotExist() {
        when(characterRepository.findById(anyLong())).thenThrow(NoSuchElementException.class);

        NoSuchElementException ex = assertThrows(NoSuchElementException.class, () -> {
            characterService.findById(8L);
        });

        assertEquals(NoSuchElementException.class, ex.getClass());
    }

    @Test
    void save() {
        when(characterRepository.save(any(Character.class))).thenReturn(SINGLE_CHARACTER);
        Character newCharacter = characterService.save(SINGLE_CHARACTER);

        assertNotNull(newCharacter);
        assertEquals(SINGLE_CHARACTER.getId(), newCharacter.getId());
        assertEquals(SINGLE_CHARACTER.getImage(), newCharacter.getImage());
        assertFalse(newCharacter.getName().isEmpty());
        assertEquals(SINGLE_CHARACTER.getMovies().size(), newCharacter.getMovies().size());

        verify(characterRepository, times(1)).save(any(Character.class));
    }

    @Test
    void update() {
        when(characterRepository.findById(anyLong())).thenReturn(Optional.of(SINGLE_CHARACTER));
        when(characterRepository.save(any(Character.class))).thenReturn(SINGLE_CHARACTER);

        Character updatedCharacter = characterService.update(SINGLE_CHARACTER, anyLong());

        assertNotNull(updatedCharacter);
        assertEquals(SINGLE_CHARACTER.getId(), updatedCharacter.getId());
        assertEquals(SINGLE_CHARACTER.getImage(), updatedCharacter.getImage());
        assertEquals(SINGLE_CHARACTER.getMovies().size(), updatedCharacter.getMovies().size());

        verify(characterRepository).findById(anyLong());
        verify(characterRepository, times(1)).save(any(Character.class));
    }

    @Test
    void delete() {
        long characterId = 1L;
        when(characterRepository.findById(characterId)).thenReturn(Optional.ofNullable(SINGLE_CHARACTER));
        doNothing().when(characterRepository).delete(any(Character.class));

        characterService.delete(characterId);

        verify(movieRepository, atLeastOnce()).saveAll(anyIterable());
        verify(characterRepository, atLeastOnce()).delete(any(Character.class));
    }
}