package com.rhapsydev.alkemy.disney.controller;

import com.rhapsydev.alkemy.disney.exception.ErrorDetails;
import com.rhapsydev.alkemy.disney.mapstruct.dto.MovieBasicDto;
import com.rhapsydev.alkemy.disney.mapstruct.dto.MovieDto;
import com.rhapsydev.alkemy.disney.mapstruct.mapper.MapStructMapper;
import com.rhapsydev.alkemy.disney.model.Movie;
import com.rhapsydev.alkemy.disney.service.MovieService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction;


@OpenAPIDefinition(info = @Info(title = "Disney API", description = "REST API which provides information from Disney Movies and Characters. (As part of Alkemy Java Challenge)", version = "v1",
        license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0")))

@RequiredArgsConstructor
@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;
    private final MapStructMapper mapper;

    @Operation(summary = "Gets all movies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the movies", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = MovieBasicDto.class))}),
            @ApiResponse(responseCode = "404", description = "Movies not found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    @GetMapping
    public ResponseEntity<List<MovieBasicDto>> getAllMovies() {
        return ResponseEntity.ok(mapper.moviesToMovieBasicDTOs(movieService.findAll()));
    }

    @Operation(summary = "Find a movie by ID and shows its details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MovieDto.class))}),
            @ApiResponse(responseCode = "404", description = "Movie not found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    @GetMapping("{id}")
    public ResponseEntity<MovieDto> getMovieDetails(@PathVariable Long id) {
        return new ResponseEntity<>(mapper.movieToMovieDTO(movieService.findById(id)), HttpStatus.OK);
    }

    @GetMapping(params = "order")
    public ResponseEntity<List<MovieBasicDto>> getAllMoviesSortedByReleaseDate(@Parameter(description = "Order by Release Date") @RequestParam(value = "order", required = false) Direction sortMethod) {
        return ResponseEntity.ok(mapper.moviesToMovieBasicDTOs(movieService.findAllSorted(sortMethod)));
    }

    @GetMapping(params = "name")
    public ResponseEntity<List<MovieBasicDto>> getMoviesByTitle(@Parameter(description = "Filter by movie's title") @RequestParam(value = "name", required = false) String title) {
        List<Movie> moviesByTitle = movieService.findByTitle(title);

        if (moviesByTitle.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(mapper.moviesToMovieBasicDTOs(moviesByTitle), HttpStatus.OK);
    }

    @GetMapping(params = "genre")
    public ResponseEntity<List<MovieBasicDto>> getMoviesByGenre(@Parameter(description = "Filter by genre ID") @RequestParam(value = "genre", required = false) Long id) {
        List<Movie> moviesByGenre = movieService.findByGenre(id);

        if (moviesByGenre.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(mapper.moviesToMovieBasicDTOs(moviesByGenre), HttpStatus.OK);
    }

    @Operation(summary = "Saves a movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movie created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MovieDto.class))}),
            @ApiResponse(responseCode = "400", description = "Validation errors", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    @PostMapping
    public ResponseEntity<MovieDto> create(@Valid @RequestBody MovieDto movieDto) {
        Movie newMovie = movieService.save(mapper.movieDtoToMovie(movieDto));
        return new ResponseEntity<>(mapper.movieToMovieDTO(newMovie), HttpStatus.CREATED);
    }

    @Operation(summary = "Updates a movie's info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MovieDto.class))}),
            @ApiResponse(responseCode = "404", description = "Movie not found with ID", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))}),
            @ApiResponse(responseCode = "400", description = "Validation errors", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    @PutMapping("/{id}")
    public ResponseEntity<MovieDto> update(@Valid @RequestBody MovieDto movieDto, @PathVariable Long id) {
        Movie updatedMovie = movieService.update(mapper.movieDtoToMovie(movieDto), id);
        return ResponseEntity.ok(mapper.movieToMovieDTO(updatedMovie));
    }

    @Operation(summary = "Deletes a movie by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Movie deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Movie not found with ID", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        movieService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Given a character ID, adds the character to the movie's characters list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Character added", content = @Content),
            @ApiResponse(responseCode = "404", description = "Movie not found with ID", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))}),
            @ApiResponse(responseCode = "400", description = "Validation errors", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    @PutMapping("/{movieId}/add-character/{characterId}")
    public ResponseEntity<MovieDto> addCharacterToMovie(@PathVariable Long movieId, @PathVariable("characterId") Long characterId) {
        movieService.addCharacter(movieId, characterId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Given a character ID, removes the character from the movie's characters list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Character removed", content = @Content),
            @ApiResponse(responseCode = "404", description = "Movie not found with ID", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))}),
            @ApiResponse(responseCode = "400", description = "Validation errors", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    @DeleteMapping("/{movieId}/remove-character/{characterId}")
    public ResponseEntity<MovieDto> removeCharacterFromMovie(@PathVariable Long movieId, @PathVariable("characterId") Long characterId) {
        movieService.removeCharacter(movieId, characterId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
