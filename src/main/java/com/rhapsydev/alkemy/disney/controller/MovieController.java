package com.rhapsydev.alkemy.disney.controller;

import com.rhapsydev.alkemy.disney.mapstruct.dto.MovieBasicDto;
import com.rhapsydev.alkemy.disney.mapstruct.dto.MovieDto;
import com.rhapsydev.alkemy.disney.mapstruct.mapper.MapStructMapper;
import com.rhapsydev.alkemy.disney.model.Movie;
import com.rhapsydev.alkemy.disney.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction;


@RequiredArgsConstructor
@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;
    private final MapStructMapper mapper;

    @GetMapping
    public ResponseEntity<List<MovieBasicDto>> getAllMovies() {
        return ResponseEntity.ok(mapper.moviesToMovieBasicDTOs(movieService.findAll()));
    }

    @GetMapping(params = "order")
    public ResponseEntity<List<MovieBasicDto>> getAllMoviesSortedByReleaseDate(@RequestParam("order") Direction sortMethod) {
        return ResponseEntity.ok(mapper.moviesToMovieBasicDTOs(movieService.findAllSorted(sortMethod)));
    }

    @GetMapping("{id}")
    public ResponseEntity<MovieDto> getMovieDetails(@PathVariable Long id) {
        return new ResponseEntity<>(mapper.movieToMovieDTO(movieService.findById(id)), HttpStatus.OK);
    }

    @GetMapping(params = "name")
    public ResponseEntity<List<MovieBasicDto>> getMoviesByTitle(@RequestParam("name") String title) {
        List<Movie> moviesByTitle = movieService.findByTitle(title);

        if (moviesByTitle.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(mapper.moviesToMovieBasicDTOs(moviesByTitle), HttpStatus.OK);
    }

    @GetMapping(params = "genre")
    public ResponseEntity<List<MovieBasicDto>> getMoviesByGenre(@RequestParam("genre") Long id) {
        List<Movie> moviesByGenre = movieService.findByGenre(id);

        if (moviesByGenre.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(mapper.moviesToMovieBasicDTOs(moviesByGenre), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MovieDto> create(@Valid @RequestBody MovieDto movieDto) {
        Movie newMovie = movieService.save(mapper.movieDtoToMovie(movieDto));
        return new ResponseEntity<>(mapper.movieToMovieDTO(newMovie), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDto> update(@Valid @RequestBody MovieDto movieDto, @PathVariable Long id) {
        Movie updatedMovie = movieService.update(mapper.movieDtoToMovie(movieDto), id);
        return ResponseEntity.ok(mapper.movieToMovieDTO(updatedMovie));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        movieService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/add-character/{id}")
    public ResponseEntity<MovieDto> addCharacterToMovie(@RequestBody MovieDto movieDto, @PathVariable("id") Long characterId) {
        movieService.addCharacter(movieDto.getId(), characterId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/remove-character/{id}")
    public ResponseEntity<MovieDto> removeCharacterFromMovie(@RequestBody MovieDto movieDto, @PathVariable("id") Long characterId) {
        movieService.removeCharacter(movieDto.getId(), characterId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
