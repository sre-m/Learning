package org.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ThesisTest {
    
    Thesis thesis;

    @BeforeEach
    void setUp() {
        thesis = new Thesis("car thesis", "farhad", 1945);
    }


    @Test
    void testToString() {
        var expected = "Thesis [title: car thesis, author: farhad, releaseDate: 1945]";
        assertEquals(expected, thesis.toString());
    }

    @Test
    void toCSV() {
        var expected = "Thesis,car thesis,farhad,1945";
        assertEquals(expected, thesis.toCSV());
    }

    Thesis createDefaultThesis() {
        return new Thesis("technology thesis", "maryam", 1989);
    }

    @Test
    void testEquals() {
        var reference1 = createDefaultThesis();
        var reference2 = createDefaultThesis();

        assertEquals(reference1, reference1);
        assertEquals(reference1, reference2);
        assertNotEquals(null, reference1);
        assertNotEquals(reference1, thesis);
    }
}