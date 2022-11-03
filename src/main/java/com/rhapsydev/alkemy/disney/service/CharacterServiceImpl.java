package com.rhapsydev.alkemy.disney.service;

import com.rhapsydev.alkemy.disney.exception.ResourceNotFoundException;
import com.rhapsydev.alkemy.disney.model.Character;
import com.rhapsydev.alkemy.disney.model.Movie;
import com.rhapsydev.alkemy.disney.repository.CharacterRepository;
import com.rhapsydev.alkemy.disney.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository characterRepository;
    private final MovieRepository movieRepository;

    @Override
    public List<Character> findAll() {
        return characterRepository.findAll();
    }

    @Override
    public Character findById(Long id) {
        return characterRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Character with id = " + id));
    }

    @Override
    public List<Character> findByName(String name) {
        return characterRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Character> findByAge(Integer age) {
        return characterRepository.findByAge(age);
    }

    @Override
    public List<Character> findByMovie(Long idMovie) {
        return characterRepository.findByMovies(Movie.builder().id(idMovie).build());
    }

    @Override
    public Character save(Character character) {
        return characterRepository.save(character);
    }

    @Override
    public Character update(Character character, Long id) {
        this.findById(id);
        return this.save(character);
    }

    @Override
    public void delete(Long id) {
        Character character = this.findById(id);
        List<Movie> moviesByCharacter = movieRepository.findByCharacters(character);

        moviesByCharacter.forEach(movie -> movie.removeCharacter(character));
        movieRepository.saveAll(moviesByCharacter);
        characterRepository.delete(character);
    }
}
