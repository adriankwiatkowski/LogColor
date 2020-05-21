package com.company.consolecolors.cli;

import com.company.consolecolors.models.Command;
import com.company.consolecolors.models.LogType;
import com.company.consolecolors.models.TextAlignment;
import com.company.consolecolors.utils.printers.ConsoleColorPrinter;
import com.company.consolecolors.utils.CLIHelper;
import com.company.consolecolors.utils.log.Log;

import java.util.Optional;
import java.util.Scanner;

public class CommandLine {

    private static final String TAG = CommandLine.class.getSimpleName();

    private static final String MSG_INPUT_MAX_INC_RANDOM = "Max incremental random: ";
    private static final String MSG_INPUT_MAX_TEXT_LENGTH = "Max text length: ";
    private static final String MSG_INPUT_TEXT_IN_COLUMN = "How many texts in one column: ";
    private static final String MSG_INPUT_TYPE_TAG = "Type tag: ";
    private static final String MSG_INPUT_TYPE_MESSAGE = "Type message: ";

    private static final String ERROR_UNKNOWN_COMMAND = "Unknown command.";
    private static final String ERROR_INVALID_COMMAND = "Invalid command.";
    private static final String COMMAND_INFO = createCommandInfo();
    private static final String ERROR_INVALID_LOG_TYPE = "Invalid log level.";
    private static final String LOG_TYPE_INFO = createLogTypeInfo();
    private static final String EXITING_MSG = "Exiting...";

    private static final int EXTRA_SPACE = 0b101;

    private ConsoleColorPrinter mConsoleColorPrinter;
    private Scanner mScanner;

    private boolean mIsReading = false;

    public CommandLine() {
    }

    public void readCommands() {
        mConsoleColorPrinter = new ConsoleColorPrinter(TextAlignment.CENTER, EXTRA_SPACE);
        mScanner = new Scanner(System.in);
        mIsReading = true;

        while (mIsReading) {
            CLIHelper.readCommand(mScanner, TAG, COMMAND_INFO)
                    .ifPresentOrElse(
                            this::processCommand,
                            () -> Log.w(TAG, ERROR_INVALID_COMMAND));
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
        int maxBoundIncremental = CLIHelper.readInt(mScanner, TAG, MSG_INPUT_MAX_INC_RANDOM);
        mConsoleColorPrinter.printIncrementalNumbers(maxBoundIncremental);
    }

    private void printTexts() {
        int maxTextLength = CLIHelper.readInt(mScanner, TAG, MSG_INPUT_MAX_TEXT_LENGTH);
        int textCountInColumn = CLIHelper.readInt(mScanner, TAG, MSG_INPUT_TEXT_IN_COLUMN);
        mConsoleColorPrinter.printAllColorsText(maxTextLength, textCountInColumn);
    }

    private void writeLog() {
        String tag = CLIHelper.readString(mScanner, TAG, MSG_INPUT_TYPE_TAG);
        String msg = CLIHelper.readString(mScanner, TAG, MSG_INPUT_TYPE_MESSAGE);

        while (true) {
            Optional<LogType> optionalLogType = CLIHelper.readLogType(mScanner, TAG, LOG_TYPE_INFO);
            if (optionalLogType.isPresent()) {
                optionalLogType.get().log(tag, msg);
                break;
            } else {
                Log.w(TAG, ERROR_INVALID_LOG_TYPE);
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
