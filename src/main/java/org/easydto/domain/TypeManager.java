package org.easydto.domain;

import org.easydto.enums.PropertyType;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class TypeManager {

    public final static TypeManager instance = new TypeManager();

    private final Set<Class<?>> wrapperTypes;
    private final Set<Class<?>> specialTypes;

    private TypeManager() {
        wrapperTypes = new HashSet<>(16);
        wrapperTypes.add(Integer.class);
        wrapperTypes.add(Byte.class);
        wrapperTypes.add(Character.class);
        wrapperTypes.add(Boolean.class);
        wrapperTypes.add(Double.class);
        wrapperTypes.add(Float.class);
        wrapperTypes.add(Long.class);
        wrapperTypes.add(Short.class);
        wrapperTypes.add(Void.class);

        specialTypes = new HashSet<>(1);
        specialTypes.add(UUID.class);
    }

    PropertyType resolveType(Property property){
        Class<?> propertyType = property.getType();

        if (propertyType.isPrimitive() || propertyType == String.class) {
            return PropertyType.SIMPLE;
        }
        if(wrapperTypes.contains(propertyType)) {
            return PropertyType.BOXED;
        }
        if(propertyType.isEnum()) {
            return PropertyType.ENUM;
        }
        if(specialTypes.contains(propertyType)) {
            return PropertyType.SPECIAL;
        }
        return PropertyType.COMPLEX;
    }
}
