package org.easydto.conversion.converter.impl;

import org.easydto.domain.PropertyConfiguration;
import org.easydto.exception.DtoException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class ReflectionUtils {

    public static List<PropertyConfiguration> getDtoProperties(Class<?> targetClass) {
        List<PropertyConfiguration> propertyConfigurations = new ArrayList<>();
        getAllProperties(propertyConfigurations, targetClass);
        return propertyConfigurations;
    }

    private static void getAllProperties(List<PropertyConfiguration> configurations, Class<?> targetClass) {
        for (Field field : targetClass.getDeclaredFields()) {
            PropertyConfiguration.load(field).ifPresent(configurations::add);
        }

        for (Method method : targetClass.getMethods()) {
            PropertyConfiguration.load(method).ifPresent(configurations::add);
        }

        if (targetClass.getSuperclass() != null) {
            getAllProperties(configurations, targetClass.getSuperclass());
        }
    }

    public static <T> T createNoArgInstance(Class<T> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new DtoException("Failed to invoke no-arg construtor for " + clazz, e);
        } catch (NoSuchMethodException e) {
            throw new DtoException("Please declare a no-arg constructor for" + clazz, e);
        }
    }

}
