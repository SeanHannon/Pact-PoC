package com.example.pactconsumerspring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CharacterService {
    private final RestTemplate restTemplate;

    @Autowired
    public CharacterService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Character> getAllCharacters(){
        return restTemplate.exchange("/characters", HttpMethod.GET, null,
                                     new ParameterizedTypeReference<List<Character>>(){}).getBody();
    }

    public Character getCharacter(String name){
        return restTemplate.getForEntity("/character/{name}", Character.class, name).getBody();
    }
}
