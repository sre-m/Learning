package org.example.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class MagazineTest {
    Magazine magazine;

    @BeforeEach
    void setUp() {
        magazine = new Magazine("car magazine", "babak", 2022, "Nashre Danesh");
    }


    @Test
    void getPublisher() {
        assertEquals("Nashre Danesh", magazine.getPublisher());
    }

    @Test
    void setPublisher() {
        magazine.setPublisher("Nashre Iran");
        assertEquals("Nashre Iran", magazine.getPublisher());
    }

    @Test
    void testToString() {
        var expected = "Magazine [title: car magazine, author: babak, releaseDate: 2022, publisher: Nashre Danesh]";
        assertEquals(expected, magazine.toString());
    }

    @Test
    void toCSV() {
        var expected = "Magazine,car magazine,babak,2022,Nashre Danesh";
        assertEquals(expected, magazine.toCSV());
    }

    Magazine createDefaultMagazine() {
        return new Magazine("technology magazine", "ali", 2022, "Iran Paper");
    }
    @Test
    void testEquals() {
        var magazine1 = createDefaultMagazine();
        var magazine2 = createDefaultMagazine();

        assertEquals(magazine1, magazine1);
        assertEquals(magazine1, magazine2);
        assertNotEquals(null, magazine1);
        assertNotEquals(magazine1, magazine);
    }
}