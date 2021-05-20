package com.example.pactconsumerspring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class PactConsumerSpringApplication {

    @Bean
    RestTemplate restTemplate(@Value("${provider.port:8085}") int port) {
        return new RestTemplateBuilder().rootUri(String.format("http://localhost:%d", port)).build();
    }

    public static void main(String[] args) {
        SpringApplication.run(PactConsumerSpringApplication.class, args);
    }

}
