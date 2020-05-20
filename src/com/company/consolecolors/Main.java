package com.company.consolecolors;

import com.company.consolecolors.cli.CommandLine;
import com.company.consolecolors.models.TextAlignment;
import com.company.consolecolors.printers.ConsoleColorPrinter;
import com.company.consolecolors.utils.Log;
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
        System.out.println("Normal output.");
        Log.v("Verbose.");
        Log.i("Info.");
        Log.d("Debug.");
        Log.w("Warning.");
        Log.e("Error.");

        ConsoleColorPrinter consoleColorPrinter =
                new ConsoleColorPrinter(TextAlignment.CENTER, SPACE_BETWEEN_TEXTS);
        consoleColorPrinter.printColorDebugInfo();
        consoleColorPrinter.printAllColorsIndexed();
        consoleColorPrinter.printIncrementalNumbers(MAX_BOUND_INCREMENTAL);
        consoleColorPrinter.printAllColorsText(MAX_BOUND_TEXT_LENGTH, TEXT_COUNT);

        RandomUtils.printRandomDebugInfo();

        CommandLine commandLine = new CommandLine();
        commandLine.readCommands();

        Log.v("Not colored verbose tag", "Useless verbose message.");
        Log.i("Not colored info tag", "Informative message.");
        Log.d("DEBUG_TAG", "Something not working here.");
        Log.w("Not colored warning tag", "Warning, take care!");
        Log.e("Not colored error tag", "Behold almighty error...");
        Log.w("Warning tag that will exceed max character limit.", "Friendly warning message.");
    }
}