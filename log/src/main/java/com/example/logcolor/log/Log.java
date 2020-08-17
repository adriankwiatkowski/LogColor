package com.example.logcolor.log;

import com.example.logcolor.colorbuilder.builders.SimpleColorBuilder;
import com.example.logcolor.colorbuilder.interfaces.ColorBuilder;
import com.example.logcolor.colorbuilder.text.TextAlignment;
import com.example.logcolor.colorbuilder.text.TextAttribute;
import com.example.logcolor.log.models.LogLevel;
import com.example.logcolor.printers.PrintableManager;
import com.example.logcolor.printers.Printer;

import java.awt.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

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
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final Format DEFAULT_FORMATTER = new SimpleDateFormat(DEFAULT_DATE_FORMAT);

    private Log() {
    }

    private static void internal_err(String msg) {
        internal_err(null, msg);
    }

    private static void internal_err(String tag, String msg) {
        LogManager logManager = LogManager.getInstance();
        if (!logManager.isLoggable(LogLevel.INTERNAL_ERROR)) {
            return;
        }
        Color foreground = logManager.getColorInternalErrorForeground();
        Color background = logManager.getColorInternalErrorBackground();
        // Print log directly instead of scheduling.
        print(LogLevel.INTERNAL_ERROR.getLevelTag(), tag, msg, foreground, background);
    }

    public static void v(String msg) {
        v(null, msg);
    }

    public static void v(String tag, String msg) {
        LogManager logManager = LogManager.getInstance();
        if (!logManager.isLoggable(LogLevel.VERBOSE)) {
            return;
        }
        Color foreground = logManager.getColorVerboseForeground();
        Color background = logManager.getColorVerboseBackground();
        addLog(LogLevel.VERBOSE.getLevelTag(), tag, msg, foreground, background);
    }

    public static void i(String msg) {
        i(null, msg);
    }

    public static void i(String tag, String msg) {
        LogManager logManager = LogManager.getInstance();
        if (!logManager.isLoggable(LogLevel.INFO)) {
            return;
        }
        Color foreground = logManager.getColorInfoForeground();
        Color background = logManager.getColorInfoBackground();
        addLog(LogLevel.INFO.getLevelTag(), tag, msg, foreground, background);
    }

    public static void d(String msg) {
        d(null, msg);
    }

    public static void d(String tag, String msg) {
        LogManager logManager = LogManager.getInstance();
        if (!logManager.isLoggable(LogLevel.DEBUG)) {
            return;
        }
        Color foreground = logManager.getColorDebugForeground();
        Color background = logManager.getColorDebugBackground();
        addLog(LogLevel.DEBUG.getLevelTag(), tag, msg, foreground, background);
    }

    public static void w(String msg) {
        w(null, msg);
    }

    public static void w(String tag, String msg) {
        LogManager logManager = LogManager.getInstance();
        if (!logManager.isLoggable(LogLevel.WARNING)) {
            return;
        }
        Color foreground = logManager.getColorWarningForeground();
        Color background = logManager.getColorWarningBackground();
        addLog(LogLevel.WARNING.getLevelTag(), tag, msg, foreground, background);
    }

    public static void e(String msg) {
        e(null, msg);
    }

    public static void e(String tag, String msg) {
        LogManager logManager = LogManager.getInstance();
        if (!logManager.isLoggable(LogLevel.ERROR)) {
            return;
        }
        Color foreground = logManager.getColorErrorForeground();
        Color background = logManager.getColorErrorBackground();
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

        LogManager logManager = LogManager.getInstance();

        SimpleColorBuilder simpleColorBuilder = new SimpleColorBuilder.Builder().build();

        if (logManager.isShowLogLevel()) {
            appendLevelInfo(simpleColorBuilder, debugLevelInfo, foreground, background);
        }

        if (logManager.isShowDate()) {
            appendDateTag(simpleColorBuilder);
        }

        if (logManager.isShowTag()) {
            appendTag(simpleColorBuilder, tag);
        }

        appendMsg(simpleColorBuilder, msg, foreground, background);

        Printer.printForceOnNewLine(simpleColorBuilder);
    }

    private static void appendLevelInfo(ColorBuilder colorBuilder,
                                        String debugLevelInfo,
                                        Color foreground,
                                        Color background) {
        int extraSpace = LEVEL_INFO_CHAR_LIMIT - debugLevelInfo.length();
        LogManager logManager = LogManager.getInstance();
        TextAlignment textAlignment = logManager.getTextAlignmentLevelLogInfo();
        TextAttribute textAttribute = new TextAttribute.Builder().setForeground(foreground)
                                                                 .setBackground(background)
                                                                 .setExtraSpace(extraSpace)
                                                                 .setTextAlignment(textAlignment)
                                                                 .build();
        colorBuilder.append(debugLevelInfo, textAttribute);
        colorBuilder.append(TAG_MSG_SEPARATOR);
    }

    private static void appendDateTag(ColorBuilder colorBuilder) {
        String dateTag = createCurrentDateTag();
        int extraSpace = DATE_TAG_CHAR_LIMIT - dateTag.length();
        LogManager logManager = LogManager.getInstance();
        Color foreground = logManager.getColorDateTagForeground();
        Color background = logManager.getColorDateTagBackground();
        TextAlignment textAlignment = logManager.getTextAlignmentDateTag();
        TextAttribute textAttribute = new TextAttribute.Builder().setForeground(foreground)
                                                                 .setBackground(background)
                                                                 .setExtraSpace(extraSpace)
                                                                 .setTextAlignment(textAlignment)
                                                                 .build();
        colorBuilder.append(dateTag, textAttribute);
        colorBuilder.append(TAG_MSG_SEPARATOR);
    }

    private static void appendTag(ColorBuilder colorBuilder, String tag) {
        tag = (tag == null || tag.isEmpty()) ? DEFAULT_TAG : tag;
        tag = (tag.length() > TAG_CHAR_LIMIT) ? tag.substring(0, TAG_CHAR_LIMIT) : tag;
        int extraSpace = TAG_CHAR_LIMIT - tag.length();
        LogManager logManager = LogManager.getInstance();
        Color foreground = logManager.getColorTagForeground();
        Color background = logManager.getColorTagBackground();
        TextAlignment textAlignment = logManager.getTextAlignmentTag();
        TextAttribute textAttribute = new TextAttribute.Builder().setForeground(foreground)
                                                                 .setBackground(background)
                                                                 .setExtraSpace(extraSpace)
                                                                 .setTextAlignment(textAlignment)
                                                                 .build();
        colorBuilder.append(tag, textAttribute);
        colorBuilder.append(TAG_MSG_SEPARATOR);
    }

    private static void appendMsg(ColorBuilder colorBuilder, String msg, Color fg, Color bg) {
        LogManager logManager = LogManager.getInstance();
        TextAlignment textAlignment = logManager.getTextAlignmentMessage();
        TextAttribute textAttribute = new TextAttribute.Builder().setForeground(fg)
                                                                 .setBackground(bg)
                                                                 .setTextAlignment(textAlignment)
                                                                 .build();
        colorBuilder.append(msg, textAttribute);
        colorBuilder.appendNewLine();
    }

    private static String createCurrentDateTag() {
        return DEFAULT_FORMATTER.format(new Date());
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