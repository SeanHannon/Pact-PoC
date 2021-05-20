package com.example.pactproviderspring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class CharacterController {

    private CharacterRepository characterRepository;

    @Autowired
    public CharacterController(CharacterRepository characterRepository) {this.characterRepository = characterRepository;}

    @GetMapping("characters")
    public List<Character> getAllCharacters(){
        return characterRepository.fetchAll();
    }

    @GetMapping("character/{name}")
    public ResponseEntity<Character> getCharacterByName(@PathVariable("name") String name){
        Optional<Character> character = characterRepository.getByName(name);

        return ResponseEntity.of(character);
    }
}
