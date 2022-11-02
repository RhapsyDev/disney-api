package com.rhapsydev.alkemy.disney.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is mandatory")
    private String title;

    @Temporal(TemporalType.DATE)
    private Date releasedOn;

    @Size(min = 1, max = 5)
    private int rating;

    private String image;

    @ManyToOne()
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "movie_character",
            joinColumns = {@JoinColumn(name = "movie_id")},
            inverseJoinColumns = {@JoinColumn(name = "character_id")})
    private Set<Character> characters = new HashSet<>();

    // Utils methods
    public void addCharacter(Character character) {
        characters.add(character);
        character.getMovies().add(this);
    }

    public void removeCharacter(Character character) {
        characters.remove(character);
        character.getMovies().remove(this);
    }
}
