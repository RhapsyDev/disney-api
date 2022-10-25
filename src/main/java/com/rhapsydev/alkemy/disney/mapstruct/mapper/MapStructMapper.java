package com.rhapsydev.alkemy.disney.mapstruct.mapper;

import com.rhapsydev.alkemy.disney.mapstruct.dto.*;
import com.rhapsydev.alkemy.disney.model.Character;
import com.rhapsydev.alkemy.disney.model.Genre;
import com.rhapsydev.alkemy.disney.model.Movie;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    List<CharacterBasicDto> charactersToCharacterBasicDTOs(List<Character> characters);
    CharacterDto characterToCharacterDTO(Character character);
    Character characterDtoToCharacter(CharacterDto characterDto);

    GenreDto genreToGenreDto(Genre genre);
    List<GenreDto> genresToGenreDTOs(List<Genre> genres);

    MovieBasicDto movieToMovieBasicDTO(Movie movie);
    List<MovieDto> moviesToMovieBasicDTOs(List<Movie> movies);
}
