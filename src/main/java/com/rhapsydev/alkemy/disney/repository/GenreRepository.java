package com.rhapsydev.alkemy.disney.repository;

import com.rhapsydev.alkemy.disney.model.Genre;
import org.springframework.data.repository.CrudRepository;

public interface GenreRepository extends CrudRepository<Genre, Long> {
}
