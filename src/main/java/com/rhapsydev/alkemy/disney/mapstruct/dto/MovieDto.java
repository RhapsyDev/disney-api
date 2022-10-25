package com.rhapsydev.alkemy.disney.mapstruct.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class MovieDto {

    private Long id;
    private String title;
    private Date releasedOn;
    private int rating;
    private String image;
    private List<CharacterBasicDto> characters;
    private GenreDto genre;
}
