package com.rhapsydev.alkemy.disney.service;

import com.rhapsydev.alkemy.disney.model.Character;

import java.util.List;
import java.util.Optional;

public interface CharacterService {

    List<Character> findAll();

    Optional<Character> findById(Long id);

    List<Character> findByName(String name);

    List<Character> findByAge(Integer age);

    List<Character> findByMovie(Long idMovie);

    Character save(Character character);

    void delete(Long id);
}
