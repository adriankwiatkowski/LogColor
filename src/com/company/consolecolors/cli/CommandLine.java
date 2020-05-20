package com.company.consolecolors.cli;

import com.company.consolecolors.models.Command;
import com.company.consolecolors.models.LogType;
import com.company.consolecolors.models.TextAlignment;
import com.company.consolecolors.printers.ConsoleColorPrinter;
import com.company.consolecolors.utils.CLIHelper;

import java.util.Optional;
import java.util.Scanner;

public class CommandLine {

    private static final String ERROR_UNKNOWN_COMMAND = "Unknown command.";
    private static final String ERROR_INVALID_COMMAND = "Invalid command.";
    private static final String COMMAND_INFO = createCommandInfo();
    private static final String ERROR_INVALID_LOG_TYPE = "Invalid log level.";
    private static final String LOG_TYPE_INFO = createLogTypeInfo();
    private static final String EXITING_MSG = "Exiting...";

    private static final int SPACE_BETWEEN_TEXTS = 0b101;

    private ConsoleColorPrinter mConsoleColorPrinter;
    private Scanner mScanner;

    private boolean mIsReading = false;

    public CommandLine() {
    }

    public void readCommands() {
        mConsoleColorPrinter = new ConsoleColorPrinter(TextAlignment.CENTER, SPACE_BETWEEN_TEXTS);
        mScanner = new Scanner(System.in);
        mIsReading = true;

        while (mIsReading) {
            CLIHelper.readCommand(mScanner, COMMAND_INFO)
                    .ifPresentOrElse(
                            this::processCommand,
                            () -> System.out.println(ERROR_INVALID_COMMAND));
        }

        mScanner.close();
    }

    private void processCommand(Command command) {
        switch (command) {
            case EXIT:
                System.out.println(EXITING_MSG);
                mIsReading = false;
                break;
            case PRINT_INDEXED:
                printIndexed();
                break;
            case PRINT_INCREMENTAL:
                printIncremental();
                break;
            case PRINT_TEXTS:
                printTexts();
                break;
            case LOG:
                writeLog();
                break;
            default:
                throw new AssertionError(ERROR_UNKNOWN_COMMAND);
        }
    }

    private void printIndexed() {
        mConsoleColorPrinter.printAllColorsIndexed();
    }

    private void printIncremental() {
        int maxBoundIncremental = CLIHelper.readInt(mScanner, "Max incremental random: ");
        mConsoleColorPrinter.printIncrementalNumbers(maxBoundIncremental);
    }

    private void printTexts() {
        int maxTextLength = CLIHelper.readInt(mScanner, "Max text length: ");
        int textCountInColumn = CLIHelper.readInt(mScanner, "How many texts in one column: ");
        mConsoleColorPrinter.printAllColorsText(maxTextLength, textCountInColumn);
    }

    private void writeLog() {
        String tag = CLIHelper.readString(mScanner, "Type tag: ");
        String msg = CLIHelper.readString(mScanner, "Type message: ");

        while (true) {
            Optional<LogType> optionalLogType = CLIHelper.readLogType(mScanner, LOG_TYPE_INFO);
            if (optionalLogType.isPresent()) {
                optionalLogType.get().log(tag, msg);
                break;
            } else {
                System.out.println(ERROR_INVALID_LOG_TYPE);
            }
        }
    }

    private static String createCommandInfo() {
        StringBuilder commands = new StringBuilder("All commands: [");
        for (Command command : Command.values()) {
            commands.append(command.getCommand()).append(", ");
        }
        commands.deleteCharAt(commands.length() - 1);
        commands.deleteCharAt(commands.length() - 1);
        commands.append("]\nType command: ");
        return commands.toString();
    }

    private static String createLogTypeInfo() {
        StringBuilder logTypes = new StringBuilder("All log level: [");
        for (LogType logType : LogType.values()) {
            logTypes.append(logType.getLogType()).append(", ");
        }
        logTypes.deleteCharAt(logTypes.length() - 1);
        logTypes.deleteCharAt(logTypes.length() - 1);
        logTypes.append("]\nType log level: ");
        return logTypes.toString();
    }
}
