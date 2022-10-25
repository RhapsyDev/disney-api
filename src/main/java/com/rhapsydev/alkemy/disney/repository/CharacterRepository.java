package com.rhapsydev.alkemy.disney.repository;

import com.rhapsydev.alkemy.disney.model.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CharacterRepository extends JpaRepository<Character, Long> {

    List<Character> findByName(String name);

    List<Character> findByAge(Integer age);

//    List<Character> findByMovie(Long movieId);
}
