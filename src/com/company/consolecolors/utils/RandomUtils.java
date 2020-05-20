package com.company.consolecolors.utils;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {

    private RandomUtils() {
    }

    public static int generateRandomNumber() {
        return ThreadLocalRandom.current().nextInt();
    }

    public static int generateRandomNumber(int maxBound) {
        return ThreadLocalRandom.current().nextInt(maxBound);
    }

    public static int generateRandomNumber(int origin, int bound) {
        return ThreadLocalRandom.current().nextInt(origin, bound);
    }

    /**
     * Generate random string.
     *
     * @return Returns random string.
     */
    public static String generateRandomAsciiString(int length) {
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
    public static String generateRandomAsciiString(int minAscii, int maxAscii, int maxLength) {
        if (minAscii < 0 || minAscii > 127)
            throw new IllegalArgumentException(
                    "min ascii must greater than 0 and lesser than 127 and was: "
                            + minAscii
                            + ".");
        if (maxAscii < 0 || maxAscii > 127)
            throw new IllegalArgumentException(
                    "max Ascii must greater than 0 and lesser than 127 and was: "
                            + minAscii
                            + ".");

        StringBuilder sb = new StringBuilder(maxLength);

        int randomLength = ThreadLocalRandom.current().nextInt(maxLength, maxLength + 1);
        ThreadLocalRandom.current()
                .ints(minAscii, maxAscii)
                .limit(randomLength)
                .forEach(i -> sb.append((char) i));

        return sb.toString();
    }

    public static void printRandomDebugInfo() {
        ThreadLocalRandom.current()
                .ints(20, 75)
                .limit(100)
                .forEach(r -> {
                    String msg = RandomUtils.generateRandomAsciiString(r);
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
}
