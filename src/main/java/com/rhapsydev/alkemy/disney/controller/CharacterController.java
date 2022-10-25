package com.rhapsydev.alkemy.disney.controller;

import com.rhapsydev.alkemy.disney.exception.ResourceNotFoundException;
import com.rhapsydev.alkemy.disney.mapstruct.dto.CharacterBasicDto;
import com.rhapsydev.alkemy.disney.mapstruct.dto.CharacterDto;
import com.rhapsydev.alkemy.disney.mapstruct.mapper.MapStructMapper;
import com.rhapsydev.alkemy.disney.model.Character;
import com.rhapsydev.alkemy.disney.service.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/characters")
public class CharacterController {

    private final CharacterService characterService;
    private final MapStructMapper mapper;

    @GetMapping
    public ResponseEntity<List<CharacterBasicDto>> getAllCharacters() {
        return ResponseEntity.ok(mapper.charactersToCharacterBasicDTOs(characterService.findAll()));
    }

    @GetMapping("{id}")
    public ResponseEntity<CharacterDto> getCharacterDetails(@PathVariable Long id) {
        Character characterFromDb = characterService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Character with id = " + id));
        return new ResponseEntity<>(mapper.characterToCharacterDTO(characterFromDb), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CharacterDto> create(@Valid @RequestBody CharacterDto characterDto) {
        Character newCharacter = characterService.save(mapper.characterDtoToCharacter(characterDto));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.characterToCharacterDTO(newCharacter));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CharacterDto> update(@Valid @RequestBody CharacterDto characterDto, @PathVariable Long id) {
       characterService.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("Unable to found Character with id = " + id));
        Character updatedCharacter = characterService.save(mapper.characterDtoToCharacter(characterDto));
        return ResponseEntity.ok(mapper.characterToCharacterDTO(updatedCharacter));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id){
        characterService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unable to found Character with id = " + id));
        characterService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
