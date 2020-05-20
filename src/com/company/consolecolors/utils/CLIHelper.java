package com.company.consolecolors.utils;

import com.company.consolecolors.models.Command;
import com.company.consolecolors.models.LogType;

import java.util.Optional;
import java.util.Scanner;

public class CLIHelper {

    public static Optional<Command> readCommand(Scanner scanner, String msg) {
        System.out.println(msg);
        return Command.extractCommand(scanner.nextLine().trim());
    }

    public static Optional<LogType> readLogType(Scanner scanner, String msg) {
        System.out.println(msg);
        return LogType.extractLogType(scanner.nextLine().trim());
    }

    public static String readString(Scanner scanner, String msg) {
        System.out.println(msg);
        return scanner.nextLine().trim();
    }

    public static int readInt(Scanner scanner, String msg) {
        while (true) {
            System.out.println(msg);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format.");
            }
        }
    }
}
