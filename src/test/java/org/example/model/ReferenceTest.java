package org.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReferenceTest {

    Reference reference;

    @BeforeEach
    void setUp() {
        reference = new Reference("car reference", "farhad", 1125);
    }
    

    @Test
    void testToString() {
        var expected = "Reference [title: car reference, author: farhad, releaseDate: 1125]";
        assertEquals(expected, reference.toString());
    }

    @Test
    void toCSV() {
        var expected = "Reference,car reference,farhad,1125";
        assertEquals(expected, reference.toCSV());
    }

    Reference createDefaultReference() {
        return new Reference("technology reference", "maryam", 1989);
    }
    
    @Test
    void testEquals() {
        var reference1 = createDefaultReference();
        var reference2 = createDefaultReference();

        assertEquals(reference1, reference1);
        assertEquals(reference1, reference2);
        assertNotEquals(null, reference1);
        assertNotEquals(reference1, reference);
    }
}