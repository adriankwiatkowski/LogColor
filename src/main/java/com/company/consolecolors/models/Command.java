package com.company.consolecolors.models;

import java.util.Optional;

public enum Command {
    EXIT("exit"), PRINT_INDEXED("index"), PRINT_INCREMENTAL("inc"), PRINT_TEXTS("text"), LOG("log");

    private final String command;

    Command(String command) {
        this.command = command;
    }

    public static Optional<Command> extractCommand(String string) {
        for (Command command : Command.values()) {
            if (command.command.equalsIgnoreCase(string))
                return Optional.of(command);
        }

        return Optional.empty();
    }

    public String getCommand() {
        return command;
    }
}
