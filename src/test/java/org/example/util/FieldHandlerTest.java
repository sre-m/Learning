package org.example.util;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FieldHandlerTest {

    static class Parent {
        String inheritedField;
    }

    static class Person extends Parent {
        private String name;
        private int age;

        Person(String name, int age, String inheritedField) {
            this.name = name;
            this.age = age;
            this.inheritedField = inheritedField;
        }
    }

    @Test
    void copyFields() {
        Person source = new Person("Alice", 30, "parentData");
        Person target = new Person("Bob", 25, "oldData");

        FieldHandler.copyFields(source, target);

        assertEquals("Alice", target.name);
        assertEquals(30, target.age);
        assertEquals("parentData", target.inheritedField);
    }

    @Test
    void searchFields() {
        Person p1 = new Person("Alice", 30, "parentData");
        Person p2 = new Person("Alice", 23, "parentData");
        Person p3 = new Person("Alice", 11, "parentData");

        var res = FieldHandler.searchFields(List.of(p1,p2,p3),List.of("name","age"),"3");
        assertEquals(2, res.size());
        assertEquals(List.of(p1,p2), res);
    }

}