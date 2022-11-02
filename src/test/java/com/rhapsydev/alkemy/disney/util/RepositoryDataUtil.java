package com.rhapsydev.alkemy.disney.util;

import com.rhapsydev.alkemy.disney.model.Character;
import com.rhapsydev.alkemy.disney.model.Genre;
import com.rhapsydev.alkemy.disney.model.Movie;

import java.util.*;

public class RepositoryDataUtil {

    public static final List<Genre> GENRES = Arrays.asList(new Genre(1L, "Accion", new HashSet<>()),
            new Genre(2L, "Comedia", new HashSet<>()), new Genre(3L, "Aventura", new HashSet<>()));

    public static final Movie SINGLE_MOVIE_1 = Movie.builder().id(1L)
            .title("Pinocchio").image("pinocchio.jpg").releasedOn(new Date(1940 - 2 - 7))
            .rating(5).genre(GENRES.stream().findAny().orElseThrow()).build();

    public static final Movie SINGLE_MOVIE_2 = Movie.builder().id(2L)
            .title("Los Increibles").image("increibles.jpg").releasedOn(new Date(1940 - 2 - 7))
            .rating(3).genre(GENRES.stream().findAny().orElseThrow()).build();

    public static final List<Movie> ALL_MOVIES = Arrays.asList(SINGLE_MOVIE_1, SINGLE_MOVIE_2,
            Movie.builder().id(3L).title("Bambi").image("bambi.jpg")
                    .releasedOn(new Date(1942 - 8 - 13)).rating(3).genre(GENRES.stream().findAny().orElseThrow()).build(),
            Movie.builder().id(4L).title("Toy Story 3").image("buzz.jpg")
                    .releasedOn(new Date(2010 - 6 - 18)).rating(4).genre(GENRES.stream().findAny().orElseThrow()).build());

    public static final Character SINGLE_CHARACTER =
            Character.builder().id(1L).name("Aladdin").age(30).weight(35.8)
                    .history("Aladdin history").image("aladdin.jpg").movies(Set.of(SINGLE_MOVIE_1, SINGLE_MOVIE_2)).build();

    public static final Character SINGLE_CHARACTER_2 = Character.builder().id(10L)
            .name("Snow White").age(25).weight(50.2).history("Snow white history")
            .movies(Set.of(SINGLE_MOVIE_2)).build();

    public static final List<Character> ALL_CHARACTERS = Arrays.asList(
            SINGLE_CHARACTER,
            Character.builder().id(2L).name("Goofy").age(15).weight(65.0)
                    .history("Goofy History").image("goofy.jpg").movies(new HashSet<>(ALL_MOVIES)).build(),
            Character.builder().id(3L).name("Tigger").age(75).weight(35.0)
                    .history("Tigger History").image("tigger.png").movies(Collections.emptySet()).build(),
            Character.builder().id(4L).name("Tigger Tiny").age(15).weight(23.0)
                    .history("Tigger Tinny History").image("tigger-tiny.png").movies(Collections.emptySet()).build(),
            Character.builder().id(5L).name("Woody").age(30).weight(23.0)
                    .history("Woody History").image("woody.png").movies(Collections.emptySet()).build(),
            Character.builder().id(8L).name("Pluto").age(15).weight(65.0)
                    .history("Pluto History").image("pluto.jpg").movies(Collections.singleton(SINGLE_MOVIE_1)).build()
    );

    public static final List<Character> SAME_NAME_CHARACTERS = Arrays.asList(
            Character.builder().id(6L).name("Mickey Mouse").age(15).weight(65.0)
                    .history("Mickey History").image("mickey.jpg").movies(null).build(),
            Character.builder().id(7L).name("Minnie Mouse").age(75).weight(35.0)
                    .history("Minnie History").image("minnie.png").movies(null).build()
    );

    public static final List<Character> SAME_MOVIE_CHARACTERS = Arrays.asList(
            SINGLE_CHARACTER,
            Character.builder().id(5L).name("Woody").age(30).weight(23.0)
                    .history("Woody History").image("woody.png").movies(Collections.singleton(SINGLE_MOVIE_2)).build()
    );
}
