package com.company.consolecolors.utils;

import com.company.consolecolors.models.Command;
import com.company.consolecolors.models.LogType;
import com.company.consolecolors.utils.log.Log;

import java.util.Optional;
import java.util.Scanner;

public class CLIHelper {

    private CLIHelper() {
    }

    public static Optional<Command> readCommand(Scanner scanner, String tag, String msg) {
        Log.i(tag, msg);
        System.out.println(msg);
        return Command.extractCommand(scanner.nextLine().trim());
    }

    public static Optional<LogType> readLogType(Scanner scanner, String tag, String msg) {
        Log.i(tag, msg);
        return LogType.extractLogType(scanner.nextLine().trim());
    }

    public static String readString(Scanner scanner, String tag, String msg) {
        Log.i(tag, msg);
        return scanner.nextLine().trim();
    }

    public static int readInt(Scanner scanner, String tag, String msg) {
        Log.i(tag, msg);
        while (true) {
            System.out.println(msg);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                Log.i(tag, "Invalid number format.");
            }
        }
    }
}
