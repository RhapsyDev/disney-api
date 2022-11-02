package com.rhapsydev.alkemy.disney.repository;

import com.rhapsydev.alkemy.disney.model.Genre;
import com.rhapsydev.alkemy.disney.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByTitleContainingIgnoreCase(String title);

    List<Movie> findByGenre(Genre genre);
}
