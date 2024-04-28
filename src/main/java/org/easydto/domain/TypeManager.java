package org.easydto.domain;

import org.easydto.enums.PropertyType;

import java.time.temporal.TemporalAccessor;
import java.util.*;

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

    public static boolean isDateTimeType(Class<?> clazz) {
        return Date.class.isAssignableFrom(clazz) ||
                Calendar.class.isAssignableFrom(clazz) ||
                TemporalAccessor.class.isAssignableFrom(clazz);
    }

    PropertyType resolveType(Property property) {
        Class<?> propertyType = property.getType();

        if (propertyType.isPrimitive() || propertyType == String.class) {
            return PropertyType.SIMPLE;
        }

        if (propertyType == List.class) {
            return PropertyType.LIST;
        }

        if (propertyType == Map.class) {
            return PropertyType.MAP;
        }

        if (wrapperTypes.contains(propertyType)) {
            return PropertyType.BOXED;
        }

        if (propertyType.isEnum()) {
            return PropertyType.ENUM;
        }

        if (specialTypes.contains(propertyType) || isDateTimeType(propertyType)) {
            return PropertyType.SPECIAL;
        }

        return PropertyType.COMPLEX;
    }
}
