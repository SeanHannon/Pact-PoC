package com.example.pactproviderspring;

import java.util.Objects;

public class Character {
    private String name;
    private String description;

    public Character() {
    }

    public Character(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        Character character = (Character) o;
        return Objects.equals(name, character.name) &&
               Objects.equals(description, character.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }
}
