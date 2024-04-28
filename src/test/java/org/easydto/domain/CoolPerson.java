package org.easydto.domain;

import org.easydto.annotation.DtoProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoolPerson extends Person {
    @DtoProperty
    public boolean isCool;

    @DtoProperty
    public List<String> coolThings = List.of("cool1", "cool2", "cool3");

    @DtoProperty
    public Map<String, Boolean> coolPersons = Map.of("Luca", true, "Nick", false);
}
