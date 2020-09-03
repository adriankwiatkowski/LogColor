package com.adriankwiatkowski.logcolor.log.models;

import com.adriankwiatkowski.logcolor.colorbuilder.utils.AnsiColor;

import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;

public enum LogLevel {

    INTERNAL_ERROR(0,
                   "INTERNAL_ERROR",
                   AnsiColor.ANSI_BLUE.getColor(),
                   AnsiColor.ANSI_BRIGHT_BG_CYAN.getColor(),
                   AnsiColor.ANSI_BRIGHT_WHITE.getColor(),
                   AnsiColor.ANSI_BG_BLACK.getColor()),
    VERBOSE(1,
            "VERBOSE",
            AnsiColor.ANSI_BRIGHT_BLACK.getColor(),
            AnsiColor.ANSI_BG_WHITE.getColor(),
            AnsiColor.ANSI_BRIGHT_WHITE.getColor(),
            AnsiColor.ANSI_BG_BLACK.getColor()),
    INFO(2,
         "INFO",
         AnsiColor.ANSI_BRIGHT_WHITE.getColor(),
         AnsiColor.ANSI_BG_BLUE.getColor(),
         AnsiColor.ANSI_WHITE.getColor(),
         AnsiColor.ANSI_BRIGHT_BG_BLUE.getColor()),
    DEBUG(3,
          "DEBUG",
          AnsiColor.ANSI_BRIGHT_GREEN.getColor(),
          AnsiColor.ANSI_BG_BLACK.getColor(),
          AnsiColor.ANSI_BRIGHT_GREEN.getColor(),
          AnsiColor.ANSI_BG_BLACK.getColor()),
    WARNING(4,
            "WARNING",
            AnsiColor.ANSI_BRIGHT_YELLOW.getColor(),
            AnsiColor.ANSI_BG_BLACK.getColor(),
            AnsiColor.ANSI_BRIGHT_BLACK.getColor(),
            AnsiColor.ANSI_BRIGHT_BG_YELLOW.getColor()),
    ERROR(5,
          "ERROR",
          AnsiColor.ANSI_BLACK.getColor(),
          AnsiColor.ANSI_BRIGHT_BG_RED.getColor(),
          AnsiColor.ANSI_WHITE.getColor(),
          AnsiColor.ANSI_BG_RED.getColor());

    public static final int MIN_LOG_LEVEL = Arrays.stream(LogLevel.values())
                                                  .min(Comparator.comparingInt(LogLevel::getLevel))
                                                  .get()
                                                  .getLevel();
    public static final int MAX_LOG_LEVEL = Arrays.stream(LogLevel.values())
                                                  .max(Comparator.comparingInt(LogLevel::getLevel))
                                                  .get()
                                                  .getLevel();
    private final int level;
    private final String levelTag;
    private final Color dayThemeForeground;
    private final Color dayThemeBackground;
    private final Color nightThemeForeground;
    private final Color nightThemeBackground;

    LogLevel(int level,
             String levelTag,
             Color dayThemeForeground,
             Color dayThemeBackground,
             Color nightThemeForeground,
             Color nightThemeBackground) {
        this.level = level;
        this.levelTag = levelTag;
        this.dayThemeForeground = dayThemeForeground;
        this.dayThemeBackground = dayThemeBackground;
        this.nightThemeForeground = nightThemeForeground;
        this.nightThemeBackground = nightThemeBackground;
    }

    public int getLevel() {
        return level;
    }

    public String getLevelTag() {
        return levelTag;
    }

    public Color getDayThemeForeground() {
        return dayThemeForeground;
    }

    public Color getDayThemeBackground() {
        return dayThemeBackground;
    }

    public Color getNightThemeForeground() {
        return nightThemeForeground;
    }

    public Color getNightThemeBackground() {
        return nightThemeBackground;
    }
}