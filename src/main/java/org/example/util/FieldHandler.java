package org.example.util;

import org.example.model.Asset;
import org.example.model.Book;

import java.lang.reflect.Field;
import java.util.List;

import org.example.model.Asset;
import org.example.model.Book;
import org.example.util.LinkedList2;

import java.lang.reflect.Field;
import java.util.List;

public class FieldHandler {

    public static <T> void copyFields(T source, T target) {
        if (source == null || target == null) {
            throw new IllegalArgumentException("Source and target cannot be null");
        }

        Class<?> clazz = source.getClass();
        while (clazz != null) { // Walk up inheritance hierarchy
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true); // allow access to private fields
                try {
                    Object value = field.get(source);
                    field.set(target, value);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Error copying field: " + field.getName(), e);
                }
            }
            clazz = clazz.getSuperclass();
        }
    }

    public static <T> List<T> searchFields(List<T> objects, List<String> fieldsToSearch, String searchTerm) {
        List<T> results = new LinkedList2<>();
        String lowerCaseTerm = searchTerm.toLowerCase();

        for (T t : objects) {
            for (String fieldName : fieldsToSearch) {
                try {
                    Field field = getFieldRecursive(t.getClass(), fieldName);

                    if (field != null) {
                        field.setAccessible(true);
                        T value = (T) field.get(t);

                        if (value != null && value.toString().toLowerCase().contains(lowerCaseTerm)) {
                            results.add(t);
                            break;
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return results;
    }

    // Helper method to also search fields in nested classes (like Author)
    private static Field getFieldRecursive(Class<?> clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            if (clazz.getSuperclass() != null) {
                return getFieldRecursive(clazz.getSuperclass(), fieldName);
            }
            return null;
        }
    }
}