package com.company.consolecolors.utils;

import com.company.consolecolors.builders.ColorBuilder;
import com.company.consolecolors.models.AnsiColor;
import com.company.consolecolors.models.TextAlignment;
import com.company.consolecolors.models.TextAttribute;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static com.company.consolecolors.models.AnsiColor.*;

public class Log {

    private static final String LOG_ERROR_TAG = "LOG_ERROR";
    private static final String VERBOSE_TAG = "VERBOSE";
    private static final String INFO_TAG = "INFO";
    private static final String DEBUG_TAG = "DEBUG";
    private static final String WARNING_TAG = "WARNING";
    private static final String ERROR_TAG = "ERROR";

    private static final String TAG_MSG_SEPARATOR = ": ";

    private static final String DEFAULT_TAG = createDefaultTag();

    private static final int TAG_CHAR_LIMIT = 30;
    private static final String ERROR_TAG_LOG_LIMIT = "ERR_MAX_CHAR_LIMIT";
    private static final String ERROR_TAG_LOG_LIMIT_MSG = "Tag too long, truncating to "
            + TAG_CHAR_LIMIT
            + " characters.";

    private static final AnsiColor TAG_COLOR = ANSI_BLUE;

    private static final int DEFAULT_EXTRA_SPACE = 0;

    private static final int DEBUG_LEVEL_INFO_LENGTH_LIMIT =
            List.of(LOG_ERROR_TAG, VERBOSE_TAG, INFO_TAG, DEBUG_TAG, WARNING_TAG, ERROR_TAG)
                    .stream()
                    .max(Comparator.comparingInt(String::length))
                    .get()
                    .length();

    private Log() {
    }

    private static void internal_err(String msg) {
        internal_err(null, msg);
    }

    private static void internal_err(String tag, String msg) {
        print(ANSI_BRIGHT_WHITE, ANSI_BG_BLACK, LOG_ERROR_TAG, tag, msg);
    }

    public static void v(String msg) {
        v(null, msg);
    }

    public static void v(String tag, String msg) {
        print(ANSI_BRIGHT_WHITE, ANSI_BG_WHITE, VERBOSE_TAG, tag, msg);
    }

    public static void i(String msg) {
        i(null, msg);
    }

    public static void i(String tag, String msg) {
        print(ANSI_BRIGHT_WHITE, ANSI_BG_BLUE, INFO_TAG, tag, msg);
    }

    public static void d(String msg) {
        d(null, msg);
    }

    public static void d(String tag, String msg) {
        print(ANSI_BRIGHT_GREEN, ANSI_BRIGHT_BG_BLACK, DEBUG_TAG, tag, msg);
    }

    public static void w(String msg) {
        w(null, msg);
    }

    public static void w(String tag, String msg) {
        print(ANSI_BRIGHT_WHITE, ANSI_BRIGHT_BG_YELLOW, WARNING_TAG, tag, msg);
    }

    public static void e(String msg) {
        e(null, msg);
    }

    public static void e(String tag, String msg) {
        print(ANSI_RED, ANSI_BRIGHT_BG_WHITE, ERROR_TAG, tag, msg);
    }

    private static void print(AnsiColor color, String debugLevelInfo, String tag, String msg) {
        print(color, color, debugLevelInfo, tag, msg);
    }

    private static void print(AnsiColor fg, AnsiColor bg, String debugLevelInfo, String tag, String msg) {
        if (msg == null || msg.isEmpty()) {
            throw new IllegalArgumentException("Log message cannot be null or empty.");
        }

        TextAttribute textAttribute =
                new TextAttribute(TextAlignment.LEFT,
                        getDebugLevelInfoColorLength(debugLevelInfo),
                        DEFAULT_EXTRA_SPACE);
        ColorBuilder colorBuilder = new ColorBuilder.Builder(textAttribute).build();

        appendDebugLevelInfo(colorBuilder, bg, debugLevelInfo);

        appendDateTag(colorBuilder);

        appendTag(colorBuilder, tag);

        colorBuilder.setTextAttributeMaxTextLength(getCurrentTextAttributeIndex(), msg.length());

        appendColoredText(colorBuilder, fg, bg, msg);

        colorBuilder.appendAnsiReset_newLine();

        System.out.print(colorBuilder.getStringText_clear());
    }

    private static void appendDebugLevelInfo(ColorBuilder colorBuilder, AnsiColor bg, String debugLevelInfo) {
        int diff = DEBUG_LEVEL_INFO_LENGTH_LIMIT - debugLevelInfo.length();
        debugLevelInfo += getWhiteSpaces(diff);
        appendColoredText(colorBuilder, bg, debugLevelInfo);
        colorBuilder.appendAnsiReset();
        colorBuilder.append(TAG_MSG_SEPARATOR);
    }

    private static int getDebugLevelInfoColorLength(String debugLevelInfo) {
        return DEBUG_LEVEL_INFO_LENGTH_LIMIT;
    }

    private static void appendDateTag(ColorBuilder colorBuilder) {
        String dateTag = createCurrentDateTag();
        colorBuilder.setTextAttributeMaxTextLength(getCurrentTextAttributeIndex(), dateTag.length());
        appendColoredText(colorBuilder, TAG_COLOR, dateTag);
        colorBuilder.appendAnsiReset();
        colorBuilder.append(TAG_MSG_SEPARATOR);
    }

    private static void appendTag(ColorBuilder colorBuilder, String tag) {
        tag = (tag == null || tag.isEmpty()) ? DEFAULT_TAG : tag;

        if (tag.length() > TAG_CHAR_LIMIT) {
            tag = tag.substring(0, TAG_CHAR_LIMIT);
            Log.internal_err(ERROR_TAG_LOG_LIMIT, ERROR_TAG_LOG_LIMIT_MSG);
        }

        int diff = TAG_CHAR_LIMIT - tag.length();
        tag += getWhiteSpaces(diff);

        colorBuilder.setTextAttributeMaxTextLength(getCurrentTextAttributeIndex(), tag.length());
        appendColoredText(colorBuilder, TAG_COLOR, tag);
        colorBuilder.appendAnsiReset();
        colorBuilder.append(TAG_MSG_SEPARATOR);
    }

    private static String getWhiteSpaces(int count) {
        if (count <= 0)
            return "";

        StringBuilder sbWhiteSpace = new StringBuilder(count);
        while (count-- > 0) {
            sbWhiteSpace.append(' ');
        }
        return sbWhiteSpace.toString();
    }

    private static void appendColoredText(ColorBuilder colorBuilder, AnsiColor color, String msg) {
        colorBuilder.appendColoredText(color, msg);
    }

    private static void appendColoredText(ColorBuilder colorBuilder,
                                          AnsiColor fg, AnsiColor bg, String msg) {
        colorBuilder.appendColoredText(fg, bg, msg);
    }

    private static int getCurrentTextAttributeIndex() {
        return 0;
    }

    private static String createCurrentDateTag() {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(new Date());
    }

    private static String createDefaultTag() {
        StringBuilder sb = new StringBuilder(TAG_CHAR_LIMIT);
        int count = TAG_CHAR_LIMIT;
        while (count-- > 0) {
            sb.append('-');
        }
        return sb.toString();
    }
}
