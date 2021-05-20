package com.example.pactproviderspring;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@Provider("CharacterService")
@PactFolder("pacts")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CharacterPactProviderTest {

    @LocalServerPort
    int port;

    @MockBean
    private CharacterRepository characterRepository;

    @BeforeEach
    void setup(PactVerificationContext context){
        context.setTarget(new HttpTestTarget("localhost", port));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void verifyPact(PactVerificationContext context){
        context.verifyInteraction();
    }

    @State("character exist")
    void toCharacterExistState() {
        when(characterRepository.fetchAll()).thenReturn(
            List.of(new Character("Ted", "Fast talking priest with money resting in his account"),
                    new Character("Ted", "Fast talking priest with money resting in his account")));
    }

    @State("character with name Dougal exists")
    void toCharacterWithNameDougalExistsState(){
        when(characterRepository.getByName("Dougal")).thenReturn(Optional.of(new Character("Dougal", "Simpleton; " +
                                                                                                     "loves spider baby; priest and former milkman")));
    }
}
