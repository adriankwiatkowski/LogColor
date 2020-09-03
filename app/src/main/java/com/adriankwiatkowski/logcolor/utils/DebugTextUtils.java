package com.adriankwiatkowski.logcolor.utils;

import com.adriankwiatkowski.logcolor.colorbuilder.text.TextAlignment;
import com.adriankwiatkowski.logcolor.colorbuilder.text.TextStyle;
import com.adriankwiatkowski.logcolor.colorbuilder.utils.AnsiColor;
import com.adriankwiatkowski.logcolor.colorbuilder.text.TextAttribute;
import com.adriankwiatkowski.logcolor.log.Log;
import com.adriankwiatkowski.logcolor.printers.Printer;

import java.awt.*;
import java.util.EnumSet;
import java.util.concurrent.ThreadLocalRandom;

public class DebugTextUtils {

    public static void printSampleText() {
        Color foreground = AnsiColor.ANSI_BRIGHT_BLUE.getColor();
        Color background = AnsiColor.ANSI_BRIGHT_BG_WHITE.getColor();
        TextAlignment center = TextAlignment.CENTER;
        int space = 10;
        String msg = "Message.";
        Printer.println();
        Printer.println(msg);
        Printer.println(msg, foreground, background);
        Printer.println(msg, center, space);
        Printer.println(msg, foreground, background, center, space);
        Printer.print(msg);
        Printer.print(msg, foreground, background);
        Printer.print(msg, center, space);
        Printer.print(msg, foreground, background, center, space);

        TextAttribute textAttribute = new TextAttribute.Builder().setForeground(foreground)
                                                                 .setBackground(background)
                                                                 .setTextAlignment(center)
                                                                 .setExtraSpace(4)
                                                                 .setTextStyle(EnumSet.of(TextStyle.BOLD,
                                                                                          TextStyle.ITALIC,
                                                                                          TextStyle.UNDERLINE))
                                                                 .build();
        Printer.print(msg, textAttribute);
        Printer.println(msg, textAttribute);
    }

    public static void printAllLevelLogsTest() {
        Log.v("Verbose tag", "Useless verbose message.");
        Log.i("Info tag", "Informative message.");
        Log.d("Debug tag", "Something not working here.");
        Log.w("Warning tag", "Warning, take care!");
        Log.e("Error tag", "Behold almighty error...");
        Log.i("Informative message without tag!");
    }

    public static void printRandomDebugInfo() {
        ThreadLocalRandom.current().ints(20, 75).limit(100).forEach(length -> {
            String msg = generateRandomAsciiString(length);
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
        return generateRandomAsciiString(0x61, 0x7A, length);
    }

    /**
     * Generate random string.
     *
     * @param minAscii inclusive int min 0.
     * @param maxAscii inclusive int max 127.
     * @return Returns random string.
     * @throws IllegalArgumentException if min or max is < 0 || > 127.
     */
    private static String generateRandomAsciiString(int minAscii, int maxAscii, int maxLength) {
        if (minAscii < 0 || minAscii > 127) {
            throw new IllegalArgumentException(
                    "Min ascii must be in range 0-127 (0x000000-0x00007F) (0000-0177) (0b00000000-0b01111111) and was: " +
                    minAscii + ".");
        }
        if (maxAscii < 0 || maxAscii > 127) {
            throw new IllegalArgumentException(
                    "Max ascii must be in range 0-127 (0x000000-0x00007F) (0000-0177) (0b00000000-0b01111111) and was: " +
                    minAscii + ".");
        }

        StringBuilder sb = new StringBuilder(maxLength);

        int randomLength = ThreadLocalRandom.current().nextInt(maxLength, maxLength + 1);
        ThreadLocalRandom.current()
                         .ints(minAscii, maxAscii + 1)
                         .limit(randomLength)
                         .forEach(i -> sb.append((char) i));

        return sb.toString();
    }
}
