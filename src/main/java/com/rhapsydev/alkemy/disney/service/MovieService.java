package com.rhapsydev.alkemy.disney.service;

import com.rhapsydev.alkemy.disney.model.Movie;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Direction;

public interface MovieService {

    List<Movie> findAll();

    List<Movie> findAllSorted(Direction sortOrder);

    Movie findById(Long id);

    List<Movie> findByTitle(String title);

    List<Movie> findByGenre(Long idGenre);

    Movie save(Movie movie);

    Movie update(Movie movie,  Long id);

    void delete(Long id);

    void addCharacter(Long movieId, Long characterId);

    void removeCharacter(Long movieId, Long characterId);
}
