package com.practice.backend.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EntityDtoMapper<E, D> {

    public D mapEntityToDto(E entity, Class<D> dtoClass) throws Exception {
        if (entity == null) {
            return null;
        }

        D dto = dtoClass.getDeclaredConstructor().newInstance();
        Field[] entityFields = entity.getClass().getDeclaredFields();
        Field[] dtoFields = dtoClass.getDeclaredFields();

        for (Field entityField : entityFields) {
            entityField.setAccessible(true);
            for (Field dtoField : dtoFields) {
                dtoField.setAccessible(true);
                if (entityField.getName().equals(dtoField.getName()) && entityField.getType().equals(dtoField.getType())) {
                    // Handle nested entities
                    if (!isPrimitiveOrWrapper(entityField.getType()) && !entityField.getType().equals(String.class)) {
                        Object nestedEntity = entityField.get(entity);
                        Object nestedDto = mapEntityToDto((E) nestedEntity, (Class<D>) dtoField.getType());
                        dtoField.set(dto, nestedDto);
                    } 
                    // Handle collections
                    else if (Collection.class.isAssignableFrom(entityField.getType())) {
                        Collection<?> nestedEntities = (Collection<?>) entityField.get(entity);
                        Collection<?> nestedDtos = mapCollection(nestedEntities, getGenericType(dtoField));
                        dtoField.set(dto, nestedDtos);
                    } else {
                        dtoField.set(dto, entityField.get(entity));
                    }
                    break;
                }
            }
        }

        return dto;
    }

    public List<D> mapEntitiesToDtos(List<E> entities, Class<D> dtoClass) throws Exception {
        List<D> dtos = new ArrayList<>();
        for (E entity : entities) {
            dtos.add(mapEntityToDto(entity, dtoClass));
        }
        return dtos;
    }

    private boolean isPrimitiveOrWrapper(Class<?> type) {
        return type.isPrimitive() ||
                type == Boolean.class ||
                type == Byte.class ||
                type == Character.class ||
                type == Double.class ||
                type == Float.class ||
                type == Integer.class ||
                type == Long.class ||
                type == Short.class;
    }

    private Class<?> getGenericType(Field field) {
        try {
            String typeName = ((java.lang.reflect.ParameterizedType) field.getGenericType()).getActualTypeArguments()[0].getTypeName();
            return Class.forName(typeName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to determine generic type for field: " + field.getName(), e);
        }
    }

    private <T> Collection<T> mapCollection(Collection<?> source, Class<T> targetType) throws Exception {
        Collection<T> target = new ArrayList<>();
        for (Object item : source) {
            target.add((T) mapEntityToDto((E) item, (Class<D>) targetType));
        }
        return target;
    }
}
