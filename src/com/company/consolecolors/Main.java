package com.company.consolecolors;

import com.company.consolecolors.cli.CommandLine;
import com.company.consolecolors.models.TextAlignment;
import com.company.consolecolors.printers.ConsoleColorPrinter;
import com.company.consolecolors.utils.AppExecutors;
import com.company.consolecolors.utils.log.Log;
import com.company.consolecolors.utils.RandomUtils;

public class Main {

    private static final int SPACE_BETWEEN_TEXTS = 0b101;
    private static final int MAX_BOUND_INCREMENTAL = 0xFF;
    private static final int MAX_BOUND_TEXT_LENGTH = 0xC;
    private static final int TEXT_COUNT = 0b1000;

    public static void main(String[] args) {
        new Main().start();
    }

    private void start() {
        alignedConsolePrinterTest();
        startCommandLineInterface();
        printRandomLogsTest();
        printAllLevelLogsTest();

        AppExecutors.getInstance().shutdownExecutors();
    }

    private void startCommandLineInterface() {
        CommandLine commandLine = new CommandLine();
        commandLine.readCommands();
    }

    private void alignedConsolePrinterTest() {
        ConsoleColorPrinter consoleColorPrinter =
                new ConsoleColorPrinter(TextAlignment.CENTER, SPACE_BETWEEN_TEXTS);
        consoleColorPrinter.printColorDebugInfo();
        consoleColorPrinter.printAllColorsIndexed();
        consoleColorPrinter.printIncrementalNumbers(MAX_BOUND_INCREMENTAL);
        consoleColorPrinter.printAllColorsText(MAX_BOUND_TEXT_LENGTH, TEXT_COUNT);
    }

    private void printRandomLogsTest() {
        RandomUtils.printRandomDebugInfo();
    }

    private void printAllLevelLogsTest() {
        Log.v("Verbose tag", "Useless verbose message.");
        Log.i("Info tag", "Informative message.");
        Log.d("DEBUG_TAG", "Something not working here.");
        Log.w("Warning tag", "Warning, take care!");
        Log.e("Error tag", "Behold almighty error...");
        Log.w("Warning tag that will exceed max character limit.",
                "Friendly warning message.");
    }
}