package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        var config = AppConfig.load("config.yaml");
        var scanner = new Scanner(System.in);
        var library = new Library();
        var logFile = new File(config.getStorage().getLogfile());
        logFile.getParentFile().mkdirs();
        var printStream = new PrintStream(new FileOutputStream(logFile), true);
        var commandHandler = new CommandHandler(library, printStream);
        while (true) {
            var command = scanner.nextLine().split(" ", 2);
            commandHandler.processCommand(command[0], InputParser.parseQuotedInput(command.length == 2 ? command[1] : ""));
        }
    }


}