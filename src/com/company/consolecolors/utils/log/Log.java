package com.company.consolecolors.utils.log;

import com.company.consolecolors.builders.ColorBuilder;
import com.company.consolecolors.models.AnsiColor;
import com.company.consolecolors.models.TextAlignment;
import com.company.consolecolors.models.TextAttribute;
import com.company.consolecolors.utils.AppExecutors;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import static com.company.consolecolors.models.AnsiColor.*;

public class Log {

    private static final String ERROR_MESSAGE_EMPTY = "Log message cannot be null or empty.";

    private static final String TAG_MSG_SEPARATOR = ": ";

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final String DEFAULT_TAG = createDefaultTag();

    private static final int TAG_CHAR_LIMIT = 30;
    private static final String ERROR_TAG_LOG_LIMIT = "ERR_MAX_CHAR_LIMIT";
    private static final String ERROR_TAG_LOG_LIMIT_MSG = "Tag too long, truncating to "
            + TAG_CHAR_LIMIT
            + " characters.";

    private static final AnsiColor TAG_COLOR = ANSI_BLUE;

    private static final int DEFAULT_EXTRA_SPACE = 0;

    private static final int LEVEL_INFO_LENGTH_LIMIT =
            Arrays.stream(LogLevel.values())
                    .map(LogLevel::getLevelTag)
                    .max(Comparator.comparingInt(String::length))
                    .get()
                    .length();

    private static final LogManager mLogManager = LogManager.getInstance();

    private Log() {
    }

    private static void internal_err(String msg) {
        internal_err(null, msg);
    }

    private static void internal_err(String tag, String msg) {
        if (!mLogManager.isLoggable(LogLevel.INTERNAL_ERROR))
            return;
        addLog(ANSI_BRIGHT_WHITE, ANSI_BG_BLACK, LogLevel.INTERNAL_ERROR.getLevelTag(), tag, msg);
    }

    public static void v(String msg) {
        v(null, msg);
    }

    public static void v(String tag, String msg) {
        if (!mLogManager.isLoggable(LogLevel.VERBOSE))
            return;
        addLog(ANSI_BRIGHT_WHITE, ANSI_BG_WHITE, LogLevel.VERBOSE.getLevelTag(), tag, msg);
    }

    public static void i(String msg) {
        i(null, msg);
    }

    public static void i(String tag, String msg) {
        if (!mLogManager.isLoggable(LogLevel.INFO))
            return;
        addLog(ANSI_BRIGHT_WHITE, ANSI_BG_BLUE, LogLevel.INFO.getLevelTag(), tag, msg);
    }

    public static void d(String msg) {
        d(null, msg);
    }

    public static void d(String tag, String msg) {
        if (!mLogManager.isLoggable(LogLevel.DEBUG))
            return;
        addLog(ANSI_BRIGHT_GREEN, ANSI_BRIGHT_BG_BLACK, LogLevel.DEBUG.getLevelTag(), tag, msg);
    }

    public static void w(String msg) {
        w(null, msg);
    }

    public static void w(String tag, String msg) {
        if (!mLogManager.isLoggable(LogLevel.WARNING))
            return;
        addLog(ANSI_BRIGHT_WHITE, ANSI_BRIGHT_BG_YELLOW, LogLevel.WARNING.getLevelTag(), tag, msg);
    }

    public static void e(String msg) {
        e(null, msg);
    }

    public static void e(String tag, String msg) {
        if (!mLogManager.isLoggable(LogLevel.ERROR))
            return;
        addLog(ANSI_RED, ANSI_BRIGHT_BG_WHITE, LogLevel.ERROR.getLevelTag(), tag, msg);
    }

    private static void addLog(AnsiColor color, String debugLevelInfo, String tag, String msg) {
        addLog(color, color, debugLevelInfo, tag, msg);
    }

    private static void addLog(AnsiColor fg, AnsiColor bg, String debugLevelInfo, String tag, String msg) {
        AppExecutors.getInstance().logThread().execute(() ->  {
            print(fg, bg, debugLevelInfo, tag, msg);
        });
    }

    private static void print(AnsiColor fg, AnsiColor bg, String debugLevelInfo, String tag, String msg) {

        if (msg == null || msg.isEmpty()) {
            throw new IllegalArgumentException(ERROR_MESSAGE_EMPTY);
        }

        TextAttribute textAttribute =
                new TextAttribute(TextAlignment.LEFT,
                        getDebugLevelInfoColorLength(debugLevelInfo),
                        DEFAULT_EXTRA_SPACE);
        ColorBuilder colorBuilder = new ColorBuilder.Builder(textAttribute).build();

        appendLevelInfo(colorBuilder, bg, debugLevelInfo);

        appendDateTag(colorBuilder);

        appendTag(colorBuilder, tag);

        colorBuilder.setTextAttributeMaxTextLength(getCurrentTextAttributeIndex(), msg.length());

        appendColoredText(colorBuilder, fg, bg, msg);

        colorBuilder.appendAnsiReset_newLine();

        System.out.print(colorBuilder.getStringText_clear());
    }

    private static void appendLevelInfo(ColorBuilder colorBuilder, AnsiColor bg, String debugLevelInfo) {
        int diff = LEVEL_INFO_LENGTH_LIMIT - debugLevelInfo.length();
        debugLevelInfo += getWhiteSpaces(diff);
        appendColoredText(colorBuilder, bg, debugLevelInfo);
        colorBuilder.appendAnsiReset();
        colorBuilder.append(TAG_MSG_SEPARATOR);
    }

    private static int getDebugLevelInfoColorLength(String debugLevelInfo) {
        return LEVEL_INFO_LENGTH_LIMIT;
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
        Format formatter = new SimpleDateFormat(DATE_FORMAT);
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
