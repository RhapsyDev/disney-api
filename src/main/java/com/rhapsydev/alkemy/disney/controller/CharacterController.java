package com.rhapsydev.alkemy.disney.controller;

import com.rhapsydev.alkemy.disney.exception.ErrorDetails;
import com.rhapsydev.alkemy.disney.mapstruct.dto.CharacterBasicDto;
import com.rhapsydev.alkemy.disney.mapstruct.dto.CharacterDto;
import com.rhapsydev.alkemy.disney.mapstruct.mapper.MapStructMapper;
import com.rhapsydev.alkemy.disney.model.Character;
import com.rhapsydev.alkemy.disney.service.CharacterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Gets all characters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the characters", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = CharacterBasicDto.class))}),
            @ApiResponse(responseCode = "404", description = "Characters not found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    @GetMapping
    public ResponseEntity<List<CharacterBasicDto>> getAllCharacters() {
        return ResponseEntity.ok(mapper.charactersToCharacterBasicDTOs(characterService.findAll()));
    }

    @Operation(summary = "Find a character by ID and shows his details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Character found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CharacterDto.class))}),
            @ApiResponse(responseCode = "404", description = "Character not found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    @GetMapping("{id}")
    public ResponseEntity<CharacterDto> getCharacterDetails(@PathVariable Long id) {
        return new ResponseEntity<>(mapper.characterToCharacterDTO(characterService.findById(id)), HttpStatus.OK);
    }

    @GetMapping(params = "name")
    public ResponseEntity<List<CharacterBasicDto>> getCharactersByName(@Parameter(description = "Filter by name") @RequestParam(value = "name", required = false) String name) {
        List<Character> charactersByName = characterService.findByName(name);

        if (charactersByName.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(mapper.charactersToCharacterBasicDTOs(charactersByName), HttpStatus.OK);
    }

    @GetMapping(params = "age")
    public ResponseEntity<List<CharacterBasicDto>> getCharactersByAge(@Parameter(description = "Filter by age") @RequestParam(value = "age", required = false) int age) {
        List<Character> charactersByAge = characterService.findByAge(age);

        if (charactersByAge.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(mapper.charactersToCharacterBasicDTOs(charactersByAge), HttpStatus.OK);
    }

    @GetMapping(params = "movies")
    public ResponseEntity<List<CharacterBasicDto>> getCharactersByMovie(@Parameter(description = "Filter by movie ID") @RequestParam(value = "movies", required = false) Long id) {
        List<Character> charactersByMovie = characterService.findByMovie(id);

        if (charactersByMovie.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(mapper.charactersToCharacterBasicDTOs(charactersByMovie), HttpStatus.OK);
    }

    @Operation(summary = "Saves a character")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Character created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CharacterDto.class))}),
            @ApiResponse(responseCode = "400", description = "Validation errors", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    @PostMapping
    public ResponseEntity<CharacterDto> create(@Valid @RequestBody CharacterDto characterDto) {
        Character newCharacter = characterService.save(mapper.characterDtoToCharacter(characterDto));
        return new ResponseEntity<>(mapper.characterToCharacterDTO(newCharacter), HttpStatus.CREATED);
    }

    @Operation(summary = "Updates a character's info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Character updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CharacterDto.class))}),
            @ApiResponse(responseCode = "404", description = "Character not found with ID", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))}),
            @ApiResponse(responseCode = "400", description = "Validation errors", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    @PutMapping("/{id}")
    public ResponseEntity<CharacterDto> update(@Valid @RequestBody CharacterDto characterDto, @PathVariable Long id) {
        Character updatedCharacter = characterService.update(mapper.characterDtoToCharacter(characterDto), id);
        return ResponseEntity.ok(mapper.characterToCharacterDTO(updatedCharacter));
    }

    @Operation(summary = "Deletes a character by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Character deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Character not found with ID", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        characterService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}