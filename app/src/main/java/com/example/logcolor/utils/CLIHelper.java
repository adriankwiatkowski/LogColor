package com.example.logcolor.utils;

import com.example.logcolor.models.AnsiColor;
import com.example.logcolor.models.Command;
import com.example.logcolor.models.LogType;
import com.example.logcolor.utils.log.Log;
import com.example.logcolor.utils.printers.Printer;

import java.util.Optional;
import java.util.Scanner;

public class CLIHelper {

    private static final String TAG_MSG_SEPARATOR = ": ";
    private static final AnsiColor BACKGROUND_COLOR = AnsiColor.ANSI_BRIGHT_BG_WHITE;
    private static final AnsiColor FOREGROUND_COLOR = AnsiColor.ANSI_BRIGHT_GREEN;

    private CLIHelper() {
    }

    public static Optional<Command> readCommand(Scanner scanner, String tag, String msg) {
        printInfo(tag, msg);
        return Command.extractCommand(scanner.nextLine().trim());
    }

    public static Optional<LogType> readLogType(Scanner scanner, String tag, String msg) {
        printInfo(tag, msg);
        return LogType.extractLogType(scanner.nextLine().trim());
    }

    public static String readString(Scanner scanner, String tag, String msg) {
        printInfo(tag, msg);
        return scanner.nextLine().trim();
    }

    public static int readInt(Scanner scanner, String tag, String msg) {
        while (true) {
            printInfo(tag, msg);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                Log.i(tag, "Invalid number format.");
            }
        }
    }

    private static void printInfo(String tag, String msg) {
        String[] split = msg.split("\n");
        for (String s : split) {
            Printer.println(FOREGROUND_COLOR, BACKGROUND_COLOR, concatStrings(tag, s));
        }
    }

    private static String concatStrings(String tag, String msg) {
        return tag + TAG_MSG_SEPARATOR + msg;
    }
}
