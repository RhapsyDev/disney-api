package com.rhapsydev.alkemy.disney.mapstruct.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class MovieDto {

    private Long id;
    @NotBlank(message = "Title is mandatory")
    private String title;
    private Date releasedOn;

    @NotNull(message = "Rating can't be null")
    @Min(value = 1, message = "Rating must be greater or equal to 1")
    @Max(value = 5, message = "Rating must be less or equal to 5")
    private int rating;
    private String image;

    @NotNull(message = "Genre is mandatory")
    private GenreDto genre;
    private List<CharacterBasicDto> characters;
}
