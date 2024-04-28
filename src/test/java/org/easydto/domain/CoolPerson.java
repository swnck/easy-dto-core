package org.easydto.domain;

import org.easydto.annotation.DtoProperty;

public class CoolPerson extends Person {
    @DtoProperty
    public boolean isCool;
}
