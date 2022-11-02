package com.rhapsydev.alkemy.disney.mapstruct.mapper;

import com.rhapsydev.alkemy.disney.mapstruct.dto.*;
import com.rhapsydev.alkemy.disney.model.Character;
import com.rhapsydev.alkemy.disney.model.Genre;
import com.rhapsydev.alkemy.disney.model.Movie;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    CharacterDto characterToCharacterDTO(Character character);
    Character characterDtoToCharacter(CharacterDto characterDto);
    List<CharacterBasicDto> charactersToCharacterBasicDTOs(List<Character> characters);
    List<CharacterDto> charactersToCharactersDTOs(List<Character> characters);

    GenreDto genreToGenreDto(Genre genre);
    List<GenreDto> genresToGenreDTOs(List<Genre> genres);

    MovieBasicDto movieToMovieBasicDTO(Movie movie);
    Movie movieDtoToMovie(MovieDto movieDto);
    List<MovieBasicDto> moviesToMovieBasicDTOs(List<Movie> movies);
    List<MovieDto> moviesToMovieDTOs(List<Movie> movies);
}
