package com.company.consolecolors;

import com.company.consolecolors.cli.CommandLine;
import com.company.consolecolors.models.AnsiColor;
import com.company.consolecolors.models.TextAlignment;
import com.company.consolecolors.utils.AppExecutors;
import com.company.consolecolors.utils.log.Log;
import com.company.consolecolors.utils.printers.Printer;

public class Main {

    public static void main(String[] args) {
        new Main().start();
    }

    private void start() {
        AppExecutors.getInstance().mainThread().execute(() -> {
            printSampleText();

            startCommandLineInterface();

            AppExecutors.getInstance().shutdownMainThread();
        });
    }

    private void startCommandLineInterface() {
        new CommandLine().readCommands();
    }

    private void printSampleText() {
        Printer.print(AnsiColor.ANSI_RED, "Red text");
        Printer.print(AnsiColor.ANSI_BRIGHT_BLUE, " Blue text");
        Printer.println(AnsiColor.ANSI_BRIGHT_GREEN, " Green text.");
        Printer.println(AnsiColor.ANSI_BRIGHT_BLUE, "Blue text again.");

        Printer.println();
        Printer.println();

        Printer.print(AnsiColor.ANSI_RED, AnsiColor.ANSI_BRIGHT_BG_WHITE, "Red text");
        Printer.print(AnsiColor.ANSI_BRIGHT_BLUE, " Blue text");
        Printer.println(AnsiColor.ANSI_BRIGHT_GREEN, " Green text.");
        Printer.println(AnsiColor.ANSI_BRIGHT_BLUE, "Blue text again.");

        Printer.println();
        Printer.println();

        Printer.println("Message.");
        Printer.println(AnsiColor.ANSI_BRIGHT_BLUE, "Message.");
        Printer.println(AnsiColor.ANSI_BRIGHT_BG_WHITE, AnsiColor.ANSI_BRIGHT_BLUE, "Message.");
        Printer.println(TextAlignment.CENTER, 10, "Message.");
        Printer.println(AnsiColor.ANSI_BRIGHT_BLUE, TextAlignment.CENTER, 10, "Message.");
        Printer.println(AnsiColor.ANSI_BRIGHT_BLUE, AnsiColor.ANSI_BRIGHT_BG_WHITE, TextAlignment.CENTER, 10, "Message.");
        Printer.print("Message.");
        Printer.print(AnsiColor.ANSI_BRIGHT_BLUE, "Message.");
        Printer.print(AnsiColor.ANSI_BRIGHT_BG_WHITE, AnsiColor.ANSI_BRIGHT_BLUE, "Message.");
        Printer.print(TextAlignment.CENTER, 10, "Message.");
        Printer.print(AnsiColor.ANSI_BRIGHT_BLUE, TextAlignment.CENTER, 10, "Message.");
        Printer.print(AnsiColor.ANSI_BRIGHT_BLUE, AnsiColor.ANSI_BRIGHT_BG_WHITE, TextAlignment.CENTER, 10, "Message.");

        Printer.println();
        Printer.println();
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