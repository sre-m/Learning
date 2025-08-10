package org.example;

import org.junit.jupiter.api.Test;

import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class InputParserTest {

    @Test
    void testParseQuotedInput_withQuotes() {
        String input = "add \"The Lord of the Rings\" Tolkien 1954 EXIST";
        Queue<String> tokens = InputParser.parseQuotedInput(input);

        assertEquals(5, tokens.size());
        assertEquals("add", tokens.poll());
        assertEquals("The Lord of the Rings", tokens.poll());
        assertEquals("Tolkien", tokens.poll());
        assertEquals("1954", tokens.poll());
        assertEquals("EXIST", tokens.poll());
    }

    @Test
    void testParseQuotedInput_withoutQuotes() {
        String input = "add War_and_Peace \"War and Peace\" 1869 EXIST";
        Queue<String> tokens = InputParser.parseQuotedInput(input);

        assertEquals(5, tokens.size());
        assertEquals("add", tokens.poll());
        assertEquals("War_and_Peace", tokens.poll());
        assertEquals("War and Peace", tokens.poll());
        assertEquals("1869", tokens.poll());
        assertEquals("EXIST", tokens.poll());
    }

    @Test
    void testParseQuotedInput_mixedQuotedAndUnquoted() {
        String input = "search title \"Harry Potter\"";
        Queue<String> tokens = InputParser.parseQuotedInput(input);

        assertEquals(3, tokens.size());
        assertEquals("search", tokens.poll());
        assertEquals("title", tokens.poll());
        assertEquals("Harry Potter", tokens.poll());
    }

    @Test
    void testParseQuotedInput_emptyString() {
        String input = "";
        Queue<String> tokens = InputParser.parseQuotedInput(input);

        assertTrue(tokens.isEmpty());
    }

    @Test
    void testParseQuotedInput_onlyQuotes() {
        String input = "\"Just text in quotes\"";
        Queue<String> tokens = InputParser.parseQuotedInput(input);

        assertEquals(1, tokens.size());
        assertEquals("Just text in quotes", tokens.poll());
    }
}
