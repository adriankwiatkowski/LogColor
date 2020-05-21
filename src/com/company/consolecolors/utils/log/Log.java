package com.company.consolecolors.utils.log;

import com.company.consolecolors.builders.SimpleColorBuilder;
import com.company.consolecolors.interfaces.Printable;
import com.company.consolecolors.models.AnsiColor;
import com.company.consolecolors.models.TextAlignment;
import com.company.consolecolors.utils.AppExecutors;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import static com.company.consolecolors.models.AnsiColor.*;

public class Log {

    private static final int DATE_TAG_EXTRA_CHAR_LIMIT = 8;
    private static final int TAG_CHAR_LIMIT = 30;
    private static final int LEVEL_INFO_CHAR_LIMIT =
            Arrays.stream(LogLevel.values())
                    .map(LogLevel::getLevelTag)
                    .max(Comparator.comparingInt(String::length))
                    .get()
                    .length();

    private static final String ERROR_MESSAGE_EMPTY = "Log message cannot be null or empty.";

    private static final String DEFAULT_TAG = createDefaultTag();
    private static final String TAG_MSG_SEPARATOR = ": ";
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final AnsiColor DATE_TAG_COLOR = ANSI_BRIGHT_BG_WHITE;
    private static final AnsiColor TAG_COLOR = ANSI_BLUE;

    private static final TextAlignment TEXT_ALIGNMENT_LEVEL_LOG_INFO = TextAlignment.CENTER;
    private static final TextAlignment TEXT_ALIGNMENT_DATE_TAG = TextAlignment.CENTER;
    private static final TextAlignment TEXT_ALIGNMENT_TAG = TextAlignment.NONE;
    private static final TextAlignment TEXT_ALIGNMENT_MSG = TextAlignment.NONE;

    private static final LogManager mLogManager = LogManager.getInstance();

    private Log() {
    }

    private static void internal_err(String msg) {
        internal_err(null, msg);
    }

    private static void internal_err(String tag, String msg) {
        if (!mLogManager.isLoggable(LogLevel.INTERNAL_ERROR))
            return;
        // Print log directly instead of scheduling.
        print(ANSI_BRIGHT_WHITE, ANSI_BG_BLACK,
                LogLevel.INTERNAL_ERROR.getLevelTag(), tag, msg);
    }

    public static void v(String msg) {
        v(null, msg);
    }

    public static void v(String tag, String msg) {
        if (!mLogManager.isLoggable(LogLevel.VERBOSE))
            return;
        addLog(ANSI_BRIGHT_WHITE, ANSI_BG_WHITE,
                LogLevel.VERBOSE.getLevelTag(), tag, msg);
    }

    public static void i(String msg) {
        i(null, msg);
    }

    public static void i(String tag, String msg) {
        if (!mLogManager.isLoggable(LogLevel.INFO))
            return;
        addLog(ANSI_BRIGHT_WHITE, ANSI_BG_BLUE,
                LogLevel.INFO.getLevelTag(), tag, msg);
    }

    public static void d(String msg) {
        d(null, msg);
    }

    public static void d(String tag, String msg) {
        if (!mLogManager.isLoggable(LogLevel.DEBUG))
            return;
        addLog(ANSI_BRIGHT_GREEN, ANSI_BRIGHT_BG_BLACK,
                LogLevel.DEBUG.getLevelTag(), tag, msg);
    }

    public static void w(String msg) {
        w(null, msg);
    }

    public static void w(String tag, String msg) {
        if (!mLogManager.isLoggable(LogLevel.WARNING))
            return;
        addLog(ANSI_BRIGHT_WHITE, ANSI_BRIGHT_BG_YELLOW,
                LogLevel.WARNING.getLevelTag(), tag, msg);
    }

    public static void e(String msg) {
        e(null, msg);
    }

    public static void e(String tag, String msg) {
        if (!mLogManager.isLoggable(LogLevel.ERROR))
            return;
        addLog(ANSI_RED, ANSI_BRIGHT_BG_WHITE,
                LogLevel.ERROR.getLevelTag(), tag, msg);
    }

    private static void addLog(AnsiColor color, String debugLevelInfo,
                               String tag, String msg) {
        addLog(color, color, debugLevelInfo, tag, msg);
    }

    private static void addLog(AnsiColor fg, AnsiColor bg, String debugLevelInfo,
                               String tag, String msg) {
        AppExecutors appExecutors = AppExecutors.getInstance();
        if (!appExecutors.isLogExecutorShutdown()) {
            appExecutors.logThread().execute(() ->
                    print(fg, bg, debugLevelInfo, tag, msg));
        }
    }

    private static void print(AnsiColor fg, AnsiColor bg, String debugLevelInfo,
                              String tag, String msg) {
        if (msg == null || msg.isEmpty()) {
            throw new IllegalArgumentException(ERROR_MESSAGE_EMPTY);
        }

        SimpleColorBuilder simpleColorBuilder = new SimpleColorBuilder.Builder().build();

        simpleColorBuilder.setTextLength(LEVEL_INFO_CHAR_LIMIT);
        simpleColorBuilder.setTextAlignment(TEXT_ALIGNMENT_LEVEL_LOG_INFO);
        appendLevelInfo(simpleColorBuilder, bg, debugLevelInfo);

        String dateTag = createCurrentDateTag();
        int desiredLength = dateTag.length() + DATE_TAG_EXTRA_CHAR_LIMIT;
        simpleColorBuilder.setTextLength(desiredLength);
        simpleColorBuilder.setTextAlignment(TEXT_ALIGNMENT_DATE_TAG);
        appendDateTag(simpleColorBuilder, dateTag, desiredLength);

        simpleColorBuilder.setTextLength(TAG_CHAR_LIMIT);
        simpleColorBuilder.setTextAlignment(TEXT_ALIGNMENT_TAG);
        appendTag(simpleColorBuilder, tag);

        simpleColorBuilder.resetTextLength();
        simpleColorBuilder.setTextAlignment(TEXT_ALIGNMENT_MSG);
        appendMsg(simpleColorBuilder, fg, bg, msg);

        System.out.print(simpleColorBuilder.getText_Flush());
    }

    private static void appendLevelInfo(Printable printable, AnsiColor bg,
                                        String debugLevelInfo) {
        appendTextColor_Reset_Separator(printable, bg, debugLevelInfo, TAG_MSG_SEPARATOR);
    }

    private static void appendDateTag(Printable printable, String dateTag,
                                      int desiredLength) {
        appendTextColor_Reset_Separator(printable, DATE_TAG_COLOR, dateTag, TAG_MSG_SEPARATOR);
    }

    private static void appendTag(Printable printable, String tag) {
        tag = (tag == null || tag.isEmpty()) ? DEFAULT_TAG : tag;

        appendTextColor_Reset_Separator(printable, TAG_COLOR, tag, TAG_MSG_SEPARATOR);
    }

    private static void appendMsg(Printable printable, AnsiColor fg,
                                  AnsiColor bg, String msg) {
        printable.appendTextColor(fg, bg, msg);
        printable.appendColorReset_NewLine();
    }

    private static void appendTextColor_Reset_Separator(Printable printable, AnsiColor color,
                                                        String msg, String separator) {
        printable.appendTextColor_Reset(color, msg);
        printable.append(separator);
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