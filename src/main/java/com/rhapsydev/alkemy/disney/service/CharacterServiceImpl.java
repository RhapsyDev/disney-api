package com.rhapsydev.alkemy.disney.service;

import com.rhapsydev.alkemy.disney.model.Character;
import com.rhapsydev.alkemy.disney.model.Movie;
import com.rhapsydev.alkemy.disney.repository.CharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository repository;

    @Override
    public List<Character> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Character> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Character> findByName(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Character> findByAge(Integer age) {
        return repository.findByAge(age);
    }

    @Override
    public List<Character> findByMovie(Long idMovie) {
        return repository.findByMovies(Movie.builder().id(idMovie).build());
    }

    @Override
    public Character save(Character character) {
        return repository.save(character);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
