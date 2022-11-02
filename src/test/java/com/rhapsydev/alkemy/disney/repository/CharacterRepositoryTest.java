package com.rhapsydev.alkemy.disney.repository;

import com.rhapsydev.alkemy.disney.model.Character;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CharacterRepositoryTest {

    @Autowired
    private CharacterRepository repository;

    @BeforeEach
    void setUp() {
        Character character1 = Character.builder()
                .id(2L).name("Test Character").age(130).weight(35.8)
                .history("This is my test history 2").image("Test Image 2").movies(null).build();
        repository.save(character1);
    }

    @Test
    void findByName() {
        List<Character> character = repository.findByNameContainingIgnoreCase("mouse");
        assertFalse(character.isEmpty());
        assertEquals(2, character.size());
        assertEquals("Mickey Mouse", character.stream().findFirst().orElseThrow().getName());
    }

    @Test
    void findByAge() {
        List<Character> characters = repository.findByAge(130);
        assertNotNull(characters);
        assertEquals(130, characters.stream().findFirst().orElseThrow().getAge());
        assertEquals(1, characters.size());
    }
}