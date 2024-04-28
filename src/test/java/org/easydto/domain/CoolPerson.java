package org.easydto.domain;

import org.easydto.annotation.DtoProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoolPerson extends Person {
    @DtoProperty
    public boolean isCool;

    @DtoProperty
    public List<Person> coolThings;

    public CoolPerson() {
        Person person1 = new Person();
        person1.name("Luca");

        Person person2 = new Person();
        person2.name("Nick");

        coolThings = List.of(person1, person2);
    }

    @DtoProperty
    public Map<String, Boolean> coolPersons = Map.of("Luca", true, "Nick", false);
}
