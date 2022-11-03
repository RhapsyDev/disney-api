package com.rhapsydev.alkemy.disney.model;

import lombok.*;

import javax.persistence.*;
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

    private String title;

    @Temporal(TemporalType.DATE)
    private Date releasedOn;

    private int rating;

    private String image;

    @ManyToOne()
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @ManyToMany()
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
