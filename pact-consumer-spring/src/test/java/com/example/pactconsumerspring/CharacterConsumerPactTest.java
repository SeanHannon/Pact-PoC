package com.example.pactconsumerspring;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static io.pactfoundation.consumer.dsl.LambdaDsl.newJsonArrayMinLike;
import static io.pactfoundation.consumer.dsl.LambdaDsl.newJsonBody;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(PactConsumerTestExt.class)
public class CharacterConsumerPactTest {

    @Pact(consumer = "CharacterConsumer", provider = "CharacterService")
    RequestResponsePact getAllCharacters(PactDslWithProvider builder){
        return builder.given("character exist")
            .uponReceiving("get all characters")
            .method("GET")
            .path("/characters")
            .willRespondWith()
            .status(200)
            .headers(Map.of("Content-Type", "application/json"))
            .body(newJsonArrayMinLike(2, array ->
                array.object(object->{
                                 object.stringType("name", "Ted");
                                 object.stringType("description", "Fast talking priest with money resting in his " +
                                                                  "account");
                })).build())
            .toPact();
    }

    @Pact(consumer = "CharacterConsumer", provider = "CharacterService")
    RequestResponsePact getOneCharacter(PactDslWithProvider builder){
        return builder.given("character with name Dougal exists")
            .uponReceiving("get character name with Dougal")
            .method("GET")
            .path("/character/Dougal")
            .willRespondWith()
            .status(200)
            .headers(Map.of("Content-Type", "application/json"))
            .body(newJsonBody(object -> {
                object.stringType("name", "Dougal");
                object.stringType("description", "Simpleton; loves spider baby; priest and former milkman");
            }).build())
            .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "getAllCharacters")
    void getAllCharacters_whenCharactersExist(MockServer mockServer){
        Character character = new Character();
        character.setName("Ted");
        character.setDescription("Fast talking priest with children's charity money resting in his account");
        List<Character> expected = List.of(character, character);

        RestTemplate restTemplate = new RestTemplateBuilder().rootUri(mockServer.getUrl()).build();
        List<Character> characters = new CharacterService(restTemplate).getAllCharacters();

        assertEquals(expected.size(), characters.size());
    }

    @Test
    @PactTestFor(pactMethod = "getOneCharacter")
    void getCharacterByName_whenCharacterExistForName(MockServer mockServer){
        Character expected = new Character();
        expected.setName("Dougal");
        expected.setDescription("Simpleton; loves spider baby; priest and former milkman");

        RestTemplate restTemplate = new RestTemplateBuilder()
            .rootUri(mockServer.getUrl())
            .build();
        Character character = new CharacterService(restTemplate).getCharacter("Dougal");

        assertEquals(expected, character);
    }
}
