package com.example.logcolor.log;

import com.example.logcolor.color.models.TextAlignment;
import com.example.logcolor.color.models.TextAttribute;
import com.example.logcolor.colorbuilder.builders.SimpleColorBuilder;
import com.example.logcolor.colorbuilder.interfaces.ColorBuilder;
import com.example.logcolor.log.models.LogLevel;
import com.example.logcolor.printers.PrintableManager;
import com.example.logcolor.printers.Printer;

import java.awt.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import static com.example.logcolor.color.models.AnsiColor.*;

public final class Log {

    private static final int DATE_TAG_CHAR_LIMIT = 22;
    private static final int TAG_CHAR_LIMIT = 30;
    private static final int LEVEL_INFO_CHAR_LIMIT = Arrays.stream(LogLevel.values())
                                                           .map(LogLevel::getLevelTag)
                                                           .max(Comparator.comparingInt(String::length))
                                                           .get()
                                                           .length();

    private static final String ERROR_MESSAGE_EMPTY = "Log message cannot be null or empty.";

    private static final String DEFAULT_TAG = createDefaultTag();
    private static final String TAG_MSG_SEPARATOR = ": ";
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final Format FORMATTER_DEFAULT = new SimpleDateFormat(DATE_FORMAT);

    private static final Color DATE_TAG_COLOR_FOREGROUND_DAY = ANSI_BLACK.getColor();
    private static final Color DATE_TAG_COLOR_BACKGROUND_DAY = ANSI_BRIGHT_BG_WHITE.getColor();
    private static final Color DATE_TAG_COLOR_FOREGROUND_NIGHT = ANSI_WHITE.getColor();
    private static final Color DATE_TAG_COLOR_BACKGROUND_NIGHT = ANSI_BRIGHT_BG_BLACK.getColor();
    private static final Color TAG_COLOR_FOREGROUND_DAY = ANSI_BLACK.getColor();
    private static final Color TAG_COLOR_BACKGROUND_DAY = ANSI_BRIGHT_BG_WHITE.getColor();
    private static final Color TAG_COLOR_FOREGROUND_NIGHT = ANSI_WHITE.getColor();
    private static final Color TAG_COLOR_BACKGROUND_NIGHT = ANSI_BRIGHT_BG_BLACK.getColor();

    private static final TextAlignment TEXT_ALIGNMENT_LEVEL_LOG_INFO = TextAlignment.CENTER;
    private static final TextAlignment TEXT_ALIGNMENT_DATE_TAG = TextAlignment.RIGHT;
    private static final TextAlignment TEXT_ALIGNMENT_TAG = TextAlignment.CENTER;
    private static final TextAlignment TEXT_ALIGNMENT_MSG = TextAlignment.NONE;

    private Log() {
    }

    private static void internal_err(String msg) {
        internal_err(null, msg);
    }

    private static void internal_err(String tag, String msg) {
        if (!LogManager.getInstance().isLoggable(LogLevel.INTERNAL_ERROR)) {
            return;
        }
        // Print log directly instead of scheduling.
        Color foreground;
        Color background;
        if (PrintableManager.getInstance().isDayTheme()) {
            foreground = LogLevel.INTERNAL_ERROR.getDayThemeFg();
            background = LogLevel.INTERNAL_ERROR.getDayThemeBg();
        } else {
            foreground = LogLevel.INTERNAL_ERROR.getNightThemeFg();
            background = LogLevel.INTERNAL_ERROR.getNightThemeBg();
        }
        print(LogLevel.INTERNAL_ERROR.getLevelTag(), tag, msg, foreground, background);
    }

    public static void v(String msg) {
        v(null, msg);
    }

    public static void v(String tag, String msg) {
        if (!LogManager.getInstance().isLoggable(LogLevel.VERBOSE)) {
            return;
        }
        Color foreground;
        Color background;
        if (PrintableManager.getInstance().isDayTheme()) {
            foreground = LogLevel.VERBOSE.getDayThemeFg();
            background = LogLevel.VERBOSE.getDayThemeBg();
        } else {
            foreground = LogLevel.VERBOSE.getNightThemeFg();
            background = LogLevel.VERBOSE.getNightThemeBg();
        }
        addLog(LogLevel.VERBOSE.getLevelTag(), tag, msg, foreground, background);
    }

    public static void i(String msg) {
        i(null, msg);
    }

    public static void i(String tag, String msg) {
        if (!LogManager.getInstance().isLoggable(LogLevel.INFO)) {
            return;
        }
        Color foreground;
        Color background;
        if (PrintableManager.getInstance().isDayTheme()) {
            foreground = LogLevel.INFO.getDayThemeFg();
            background = LogLevel.INFO.getDayThemeBg();
        } else {
            foreground = LogLevel.INFO.getNightThemeFg();
            background = LogLevel.INFO.getNightThemeBg();
        }
        addLog(LogLevel.INFO.getLevelTag(), tag, msg, foreground, background);
    }

    public static void d(String msg) {
        d(null, msg);
    }

    public static void d(String tag, String msg) {
        if (!LogManager.getInstance().isLoggable(LogLevel.DEBUG)) {
            return;
        }
        Color foreground;
        Color background;
        if (PrintableManager.getInstance().isDayTheme()) {
            foreground = LogLevel.DEBUG.getDayThemeFg();
            background = LogLevel.DEBUG.getDayThemeBg();
        } else {
            foreground = LogLevel.DEBUG.getNightThemeFg();
            background = LogLevel.DEBUG.getNightThemeBg();
        }
        addLog(LogLevel.DEBUG.getLevelTag(), tag, msg, foreground, background);
    }

    public static void w(String msg) {
        w(null, msg);
    }

    public static void w(String tag, String msg) {
        if (!LogManager.getInstance().isLoggable(LogLevel.WARNING)) {
            return;
        }
        Color foreground;
        Color background;
        if (PrintableManager.getInstance().isDayTheme()) {
            foreground = LogLevel.WARNING.getDayThemeFg();
            background = LogLevel.WARNING.getDayThemeBg();
        } else {
            foreground = LogLevel.WARNING.getNightThemeFg();
            background = LogLevel.WARNING.getNightThemeBg();
        }
        addLog(LogLevel.WARNING.getLevelTag(), tag, msg, foreground, background);
    }

    public static void e(String msg) {
        e(null, msg);
    }

    public static void e(String tag, String msg) {
        if (!LogManager.getInstance().isLoggable(LogLevel.ERROR)) {
            return;
        }
        Color foreground;
        Color background;
        if (PrintableManager.getInstance().isDayTheme()) {
            foreground = LogLevel.ERROR.getDayThemeFg();
            background = LogLevel.ERROR.getDayThemeBg();
        } else {
            foreground = LogLevel.ERROR.getNightThemeFg();
            background = LogLevel.ERROR.getNightThemeBg();
        }
        addLog(LogLevel.ERROR.getLevelTag(), tag, msg, foreground, background);
    }

    private static void addLog(String debugLevelInfo,
                               String tag,
                               String msg,
                               Color foreground,
                               Color background) {
        PrintableManager.getInstance()
                        .logThread(() -> print(debugLevelInfo, tag, msg, foreground, background));
    }

    private static void print(String debugLevelInfo,
                              String tag,
                              String msg,
                              Color foreground,
                              Color background) {
        if (msg == null || msg.isEmpty()) {
            internal_err("Log", ERROR_MESSAGE_EMPTY);
            return;
        }

        SimpleColorBuilder simpleColorBuilder = new SimpleColorBuilder.Builder().build();

        appendLevelInfo(simpleColorBuilder, debugLevelInfo, foreground, background);

        appendDateTag(simpleColorBuilder);

        appendTag(simpleColorBuilder, tag);

        appendMsg(simpleColorBuilder, msg, foreground, background);

        Printer.printForceOnNewLine(simpleColorBuilder);
    }

    private static void appendLevelInfo(ColorBuilder colorBuilder,
                                        String debugLevelInfo,
                                        Color foreground,
                                        Color background) {
        int extraSpace = LEVEL_INFO_CHAR_LIMIT - debugLevelInfo.length();
        TextAttribute textAttribute = new TextAttribute.Builder().setForeground(foreground)
                                                                 .setBackground(background)
                                                                 .setExtraSpace(extraSpace)
                                                                 .setTextAlignment(
                                                                         TEXT_ALIGNMENT_LEVEL_LOG_INFO)
                                                                 .build();
        colorBuilder.append(debugLevelInfo, textAttribute);
        colorBuilder.append(TAG_MSG_SEPARATOR);
    }

    private static void appendDateTag(ColorBuilder colorBuilder) {
        String dateTag = createCurrentDateTag();
        int extraSpace = DATE_TAG_CHAR_LIMIT - dateTag.length();
        Color foreground;
        Color background;
        if (PrintableManager.getInstance().isDayTheme()) {
            foreground = DATE_TAG_COLOR_FOREGROUND_DAY;
            background = DATE_TAG_COLOR_BACKGROUND_DAY;
        } else {
            foreground = DATE_TAG_COLOR_FOREGROUND_NIGHT;
            background = DATE_TAG_COLOR_BACKGROUND_NIGHT;
        }
        TextAttribute textAttribute = new TextAttribute.Builder().setForeground(foreground)
                                                                 .setBackground(background)
                                                                 .setExtraSpace(extraSpace)
                                                                 .setTextAlignment(
                                                                         TEXT_ALIGNMENT_DATE_TAG)
                                                                 .build();
        colorBuilder.append(dateTag, textAttribute);
        colorBuilder.append(TAG_MSG_SEPARATOR);
    }

    private static void appendTag(ColorBuilder colorBuilder, String tag) {
        tag = (tag == null || tag.isEmpty()) ? DEFAULT_TAG : tag;
        tag = (tag.length() > TAG_CHAR_LIMIT) ? tag.substring(0, TAG_CHAR_LIMIT) : tag;
        int extraSpace = TAG_CHAR_LIMIT - tag.length();
        Color foreground;
        Color background;
        if (PrintableManager.getInstance().isDayTheme()) {
            foreground = TAG_COLOR_FOREGROUND_DAY;
            background = TAG_COLOR_BACKGROUND_DAY;
        } else {
            foreground = TAG_COLOR_FOREGROUND_NIGHT;
            background = TAG_COLOR_BACKGROUND_NIGHT;
        }
        TextAttribute textAttribute = new TextAttribute.Builder().setForeground(foreground)
                                                                 .setBackground(background)
                                                                 .setExtraSpace(extraSpace)
                                                                 .setTextAlignment(
                                                                         TEXT_ALIGNMENT_TAG)
                                                                 .build();
        colorBuilder.append(tag, textAttribute);
        colorBuilder.append(TAG_MSG_SEPARATOR);
    }

    private static void appendMsg(ColorBuilder colorBuilder, String msg, Color fg, Color bg) {
        TextAttribute textAttribute = new TextAttribute.Builder().setForeground(fg)
                                                                 .setBackground(bg)
                                                                 .setTextAlignment(
                                                                         TEXT_ALIGNMENT_MSG)
                                                                 .build();
        colorBuilder.append(msg, textAttribute);
        colorBuilder.appendNewLine();
    }

    private static String createCurrentDateTag() {
        return FORMATTER_DEFAULT.format(new Date());
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