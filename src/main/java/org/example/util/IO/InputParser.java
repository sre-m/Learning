package org.example.util.IO;

import org.example.util.LinkedList2;

import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputParser {
    public static Queue<String> parseQuotedInput(String input) {
        Queue<String> tokens = new LinkedList2<>();

        // Regex: match "quoted text" or plain non-space sequences
        Pattern pattern = Pattern.compile("\"([^\"]*)\"|(\\S+)");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            if (matcher.group(1) != null) {
                tokens.add(matcher.group(1)); // inside quotes
            } else {
                tokens.add(matcher.group(2)); // normal token
            }
        }
        return tokens;
    }
}
