package com.example.pactproviderspring;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class CharacterRepository {

    private final Map<String, Character> CHARACTERS = Map.of(
        "Ted", new Character("Ted", "Fast talking priest with money resting in his account"),
        "Dougal", new Character("Dougal", "Simpleton; loves spider baby; priest and former milkman"),
        "MissDoyle", new Character("MissDoyle", "Housekeeper that won't take no when offering tea")
    );

    public List<Character> fetchAll(){
        return List.copyOf(CHARACTERS.values());
    }

    public Optional<Character> getByName(String name){
        return Optional.ofNullable(CHARACTERS.get(name));
    }
}
