package com.example.logcolor.utils;

import com.example.logcolor.color.models.AnsiColor;
import com.example.logcolor.color.models.TextAlignment;
import com.example.logcolor.log.Log;
import com.example.logcolor.printers.Printer;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class DebugTextUtils {

    public static void printSampleText() {
        Color foreground = AnsiColor.ANSI_BRIGHT_BLUE.getColor();
        Color background = AnsiColor.ANSI_BRIGHT_BG_WHITE.getColor();
        TextAlignment center = TextAlignment.CENTER;
        int space = 10;
        String msg = "Message.";
        Printer.println(msg);
        Printer.println(msg, foreground, background);
        Printer.println(msg, center, space);
        Printer.println(msg, foreground, background, center, space);
        Printer.print(msg);
        Printer.print(msg, foreground, background);
        Printer.print(msg, center, space);
        Printer.print(msg, foreground, background, center, space);

        Printer.println();
        Printer.println();
    }

    public static void printAllLevelLogsTest() {
        Log.v("Verbose tag", "Useless verbose message.");
        Log.i("Info tag", "Informative message.");
        Log.d("Debug tag", "Something not working here.");
        Log.w("Warning tag", "Warning, take care!");
        Log.e("Error tag", "Behold almighty error...");
        Log.w("Warning tag that will exceed max character limit.", "Friendly warning message.");
    }

    public static void printRandomDebugInfo() {
        ThreadLocalRandom.current().ints(20, 75).limit(100).forEach(r -> {
            String msg = generateRandomAsciiString(r);
            randomLog(msg, ThreadLocalRandom.current().nextInt(0, 4 + 1));
        });
    }

    private static void randomLog(String msg, int random) {
        switch (random) {
            case 0:
                Log.v(msg);
                break;
            case 1:
                Log.i(msg);
                break;
            case 2:
                Log.d(msg);
                break;
            case 3:
                Log.w(msg);
                break;
            case 4:
                Log.e(msg);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Generate random string.
     *
     * @return Returns random string.
     */
    private static String generateRandomAsciiString(int length) {
        return generateRandomAsciiString(0x41, 0x5A + 1, length);
    }

    /**
     * Generate random string.
     *
     * @param minAscii inclusive int min 0.
     * @param maxAscii exclusive int max 127.
     * @return Returns random string.
     * @throws IllegalArgumentException if min or max is < 0 || > 127.
     */
    private static String generateRandomAsciiString(int minAscii, int maxAscii, int maxLength) {
        if (minAscii < 0 || minAscii > 127) {
            throw new IllegalArgumentException(
                    "min ascii must greater than 0 and lesser than 127 and was: " + minAscii + ".");
        }
        if (maxAscii < 0 || maxAscii > 127) {
            throw new IllegalArgumentException(
                    "max Ascii must greater than 0 and lesser than 127 and was: " + minAscii + ".");
        }

        StringBuilder sb = new StringBuilder(maxLength);

        int randomLength = ThreadLocalRandom.current().nextInt(maxLength, maxLength + 1);
        ThreadLocalRandom.current()
                         .ints(minAscii, maxAscii)
                         .limit(randomLength)
                         .forEach(i -> sb.append((char) i));

        return sb.toString();
    }
}
