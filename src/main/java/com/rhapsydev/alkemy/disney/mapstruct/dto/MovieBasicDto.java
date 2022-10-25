package com.rhapsydev.alkemy.disney.mapstruct.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
public class MovieBasicDto {

    private Long id;
    @NotBlank(message = "Movie Title is mandatory")
    private String title;
    private String image;
    private Date releasedOn;
}
