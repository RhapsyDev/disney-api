package com.rhapsydev.alkemy.disney.repository;

import com.rhapsydev.alkemy.disney.model.Movie;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, Long> {
}
