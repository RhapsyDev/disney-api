package com.rhapsydev.alkemy.disney.mapstruct.mapper;

import com.rhapsydev.alkemy.disney.mapstruct.dto.*;
import com.rhapsydev.alkemy.disney.model.Character;
import com.rhapsydev.alkemy.disney.model.Genre;
import com.rhapsydev.alkemy.disney.model.Movie;
import com.rhapsydev.alkemy.disney.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    CharacterDto characterToCharacterDTO(Character character);

    Character characterDtoToCharacter(CharacterDto characterDto);

    List<CharacterBasicDto> charactersToCharacterBasicDTOs(List<Character> characters);

    GenreDto genreToGenreDto(Genre genre);

    MovieDto movieToMovieDTO(Movie movie);

    Movie movieDtoToMovie(MovieDto movieDto);

    List<MovieBasicDto> moviesToMovieBasicDTOs(List<Movie> movies);

    UserDto userToUserDTO(User user);

    User userDtoToUser(UserDto userDto);
}
