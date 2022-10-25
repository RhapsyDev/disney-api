package com.rhapsydev.alkemy.disney.mapstruct.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
public class CharacterDto {

    private Long id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    private Integer age;
    private double weight;
    @NotBlank(message = "History is mandatory")
    private String history;
    private String image;
    private List<MovieBasicDto> movies;
}