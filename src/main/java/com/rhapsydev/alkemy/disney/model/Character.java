package com.rhapsydev.alkemy.disney.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "characters")
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer age;

    private double weight;

    private String history;

    private String image;

    @ManyToMany(mappedBy = "characters")
    private Set<Movie> movies = new HashSet<>();
}
