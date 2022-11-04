package com.rhapsydev.alkemy.disney.service;

import com.rhapsydev.alkemy.disney.exception.ResourceNotFoundException;
import com.rhapsydev.alkemy.disney.model.Character;
import com.rhapsydev.alkemy.disney.model.Genre;
import com.rhapsydev.alkemy.disney.model.Movie;
import com.rhapsydev.alkemy.disney.repository.CharacterRepository;
import com.rhapsydev.alkemy.disney.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction;

@Transactional
@RequiredArgsConstructor
@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final CharacterRepository characterRepository;

    @Override
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    @Override
    public List<Movie> findAllSorted(Direction sortOrder) {
        return movieRepository.findAll(Sort.by(sortOrder, "releasedOn"));
    }

    @Override
    public Movie findById(Long id) {
        return movieRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Movie not found with id = " + id));
    }

    @Override
    public List<Movie> findByTitle(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title);
    }

    @Override
    public List<Movie> findByGenre(Long idGenre) {
        return movieRepository.findByGenre(Genre.builder().id(idGenre).build());
    }

    @Override
    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Movie update(Movie movie, Long id) {
        this.findById(id);
        return this.save(movie);
    }

    @Override
    public void delete(Long id) {
        this.findById(id);
        movieRepository.deleteById(id);
    }

    @Override
    public void addCharacter(Long movieId, Long characterId) {
        Movie movie = this.findById(movieId);
        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid character with id " + characterId));

        movie.addCharacter(character);
        movieRepository.save(movie);
    }

    @Override
    public void removeCharacter(Long movieId, Long characterId) {
        Movie movie = this.findById(movieId);
        Character character = movie.getCharacters().stream()
                .filter(movieCharacter -> movieCharacter.getId().equals(characterId)).findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Unable to find character with id " + characterId + " in movie " + movieId));

        movie.removeCharacter(character);
        movieRepository.save(movie);
    }
}
