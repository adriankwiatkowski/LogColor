package com.example.logcolor.log;

import com.example.logcolor.color.models.AnsiColor;
import com.example.logcolor.color.models.TextAlignment;
import com.example.logcolor.colorbuilder.builders.SimpleColorBuilder;
import com.example.logcolor.colorbuilder.interfaces.ColorBuilder;
import com.example.logcolor.log.models.LogLevel;
import com.example.logcolor.printers.PrintableManager;
import com.example.logcolor.printers.Printer;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import static com.example.logcolor.color.models.AnsiColor.*;

public class Log {

    private static final int DATE_TAG_EXTRA_CHAR_LIMIT = 8;
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

    private static final AnsiColor DATE_TAG_COLOR_DAY = ANSI_BRIGHT_BG_BLACK;
    private static final AnsiColor DATE_TAG_COLOR_NIGHT = ANSI_BRIGHT_BG_WHITE;
    private static final AnsiColor TAG_COLOR_DAY = ANSI_BLUE;
    private static final AnsiColor TAG_COLOR_NIGHT = ANSI_BLUE;

    private static final TextAlignment TEXT_ALIGNMENT_LEVEL_LOG_INFO = TextAlignment.CENTER;
    private static final TextAlignment TEXT_ALIGNMENT_DATE_TAG = TextAlignment.CENTER;
    private static final TextAlignment TEXT_ALIGNMENT_TAG = TextAlignment.NONE;
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
        AnsiColor fg;
        AnsiColor bg;
        if (PrintableManager.getInstance().isDayTheme()) {
            fg = LogLevel.INTERNAL_ERROR.getDayThemeFg();
            bg = LogLevel.INTERNAL_ERROR.getDayThemeBg();
        } else {
            fg = LogLevel.INTERNAL_ERROR.getNightThemeFg();
            bg = LogLevel.INTERNAL_ERROR.getNightThemeBg();
        }
        print(fg, bg, LogLevel.INTERNAL_ERROR.getLevelTag(), tag, msg);
    }

    public static void v(String msg) {
        v(null, msg);
    }

    public static void v(String tag, String msg) {
        if (!LogManager.getInstance().isLoggable(LogLevel.VERBOSE)) {
            return;
        }
        AnsiColor fg;
        AnsiColor bg;
        if (PrintableManager.getInstance().isDayTheme()) {
            fg = LogLevel.VERBOSE.getDayThemeFg();
            bg = LogLevel.VERBOSE.getDayThemeBg();
        } else {
            fg = LogLevel.VERBOSE.getNightThemeFg();
            bg = LogLevel.VERBOSE.getNightThemeBg();
        }
        addLog(fg, bg, LogLevel.VERBOSE.getLevelTag(), tag, msg);
    }

    public static void i(String msg) {
        i(null, msg);
    }

    public static void i(String tag, String msg) {
        if (!LogManager.getInstance().isLoggable(LogLevel.INFO)) {
            return;
        }
        AnsiColor fg;
        AnsiColor bg;
        if (PrintableManager.getInstance().isDayTheme()) {
            fg = LogLevel.INFO.getDayThemeFg();
            bg = LogLevel.INFO.getDayThemeBg();
        } else {
            fg = LogLevel.INFO.getNightThemeFg();
            bg = LogLevel.INFO.getNightThemeBg();
        }
        addLog(fg, bg, LogLevel.INFO.getLevelTag(), tag, msg);
    }

    public static void d(String msg) {
        d(null, msg);
    }

    public static void d(String tag, String msg) {
        if (!LogManager.getInstance().isLoggable(LogLevel.DEBUG)) {
            return;
        }
        AnsiColor fg;
        AnsiColor bg;
        if (PrintableManager.getInstance().isDayTheme()) {
            fg = LogLevel.DEBUG.getDayThemeFg();
            bg = LogLevel.DEBUG.getDayThemeBg();
        } else {
            fg = LogLevel.DEBUG.getNightThemeFg();
            bg = LogLevel.DEBUG.getNightThemeBg();
        }
        addLog(fg, bg, LogLevel.DEBUG.getLevelTag(), tag, msg);
    }

    public static void w(String msg) {
        w(null, msg);
    }

    public static void w(String tag, String msg) {
        if (!LogManager.getInstance().isLoggable(LogLevel.WARNING)) {
            return;
        }
        AnsiColor fg;
        AnsiColor bg;
        if (PrintableManager.getInstance().isDayTheme()) {
            fg = LogLevel.WARNING.getDayThemeFg();
            bg = LogLevel.WARNING.getDayThemeBg();
        } else {
            fg = LogLevel.WARNING.getNightThemeFg();
            bg = LogLevel.WARNING.getNightThemeBg();
        }
        addLog(fg, bg, LogLevel.WARNING.getLevelTag(), tag, msg);
    }

    public static void e(String msg) {
        e(null, msg);
    }

    public static void e(String tag, String msg) {
        if (!LogManager.getInstance().isLoggable(LogLevel.ERROR)) {
            return;
        }
        AnsiColor fg;
        AnsiColor bg;
        if (PrintableManager.getInstance().isDayTheme()) {
            fg = LogLevel.ERROR.getDayThemeFg();
            bg = LogLevel.ERROR.getDayThemeBg();
        } else {
            fg = LogLevel.ERROR.getNightThemeFg();
            bg = LogLevel.ERROR.getNightThemeBg();
        }
        addLog(fg, bg, LogLevel.ERROR.getLevelTag(), tag, msg);
    }

    private static void addLog(AnsiColor color, String debugLevelInfo, String tag, String msg) {
        addLog(color, color, debugLevelInfo, tag, msg);
    }

    private static void addLog(AnsiColor fg,
                               AnsiColor bg,
                               String debugLevelInfo,
                               String tag,
                               String msg) {
        PrintableManager.getInstance().logThread(() -> print(fg, bg, debugLevelInfo, tag, msg));
    }

    private static void print(AnsiColor fg,
                              AnsiColor bg,
                              String debugLevelInfo,
                              String tag,
                              String msg) {
        if (msg == null || msg.isEmpty()) {
            throw new IllegalArgumentException(ERROR_MESSAGE_EMPTY);
        }

        SimpleColorBuilder simpleColorBuilder = new SimpleColorBuilder.Builder().build();

        simpleColorBuilder.setTextLength(LEVEL_INFO_CHAR_LIMIT);
        simpleColorBuilder.setTextAlignment(TEXT_ALIGNMENT_LEVEL_LOG_INFO);
        appendLevelInfo(simpleColorBuilder, fg, bg, debugLevelInfo);

        String dateTag = createCurrentDateTag();
        int desiredLength = dateTag.length() + DATE_TAG_EXTRA_CHAR_LIMIT;
        simpleColorBuilder.setTextLength(desiredLength);
        simpleColorBuilder.setTextAlignment(TEXT_ALIGNMENT_DATE_TAG);
        appendDateTag(simpleColorBuilder, dateTag);

        simpleColorBuilder.setTextLength(TAG_CHAR_LIMIT);
        simpleColorBuilder.setTextAlignment(TEXT_ALIGNMENT_TAG);
        appendTag(simpleColorBuilder, tag);

        simpleColorBuilder.resetTextLength();
        simpleColorBuilder.setTextAlignment(TEXT_ALIGNMENT_MSG);
        appendMsg(simpleColorBuilder, fg, bg, msg);

        Printer.printForceOnNewLine(simpleColorBuilder.getText_Flush());
    }

    private static void appendLevelInfo(ColorBuilder colorBuilder,
                                        AnsiColor fg,
                                        AnsiColor bg,
                                        String debugLevelInfo) {
        appendTextColor_Reset_Separator(colorBuilder, fg, bg, debugLevelInfo, TAG_MSG_SEPARATOR);
    }

    private static void appendDateTag(ColorBuilder colorBuilder,
                                      String dateTag) {
        AnsiColor color;
        if (PrintableManager.getInstance().isDayTheme()) {
            color = DATE_TAG_COLOR_DAY;
        } else {
            color = DATE_TAG_COLOR_NIGHT;
        }
        appendTextColor_Reset_Separator(colorBuilder, color, dateTag, TAG_MSG_SEPARATOR);
    }

    private static void appendTag(ColorBuilder colorBuilder, String tag) {
        tag = (tag == null || tag.isEmpty()) ? DEFAULT_TAG : tag;

        AnsiColor color;
        if (PrintableManager.getInstance().isDayTheme()) {
            color = TAG_COLOR_DAY;
        } else {
            color = TAG_COLOR_NIGHT;
        }
        appendTextColor_Reset_Separator(colorBuilder, color, tag, TAG_MSG_SEPARATOR);
    }

    private static void appendMsg(ColorBuilder colorBuilder,
                                  AnsiColor fg,
                                  AnsiColor bg,
                                  String msg) {
        colorBuilder.appendTextColor(fg, bg, msg);
        colorBuilder.appendColorReset_NewLine();
    }

    private static void appendTextColor_Reset_Separator(ColorBuilder colorBuilder,
                                                        AnsiColor color,
                                                        String msg,
                                                        String separator) {
        colorBuilder.appendTextColor_Reset(color, msg);
        colorBuilder.append(separator);
    }

    private static void appendTextColor_Reset_Separator(ColorBuilder colorBuilder,
                                                        AnsiColor fg,
                                                        AnsiColor bg,
                                                        String msg,
                                                        String separator) {
        colorBuilder.appendTextColor_Reset(fg, bg, msg);
        colorBuilder.append(separator);
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