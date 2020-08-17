package com.example.logcolor.log.models;

import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;

import static com.example.logcolor.colorbuilder.utils.AnsiColor.*;

public enum LogLevel {

    INTERNAL_ERROR(0,
                   "INTERNAL_ERROR",
                   ANSI_BLUE.getColor(),
                   ANSI_BRIGHT_BG_CYAN.getColor(),
                   ANSI_BRIGHT_WHITE.getColor(),
                   ANSI_BG_BLACK.getColor()),
    VERBOSE(1,
            "VERBOSE",
            ANSI_BLACK.getColor(),
            ANSI_BRIGHT_BG_WHITE.getColor(),
            ANSI_BLACK.getColor(),
            ANSI_BG_WHITE.getColor()),
    INFO(2,
         "INFO",
         ANSI_BLACK.getColor(),
         ANSI_BRIGHT_BG_BLACK.getColor(),
         ANSI_BLACK.getColor(),
         ANSI_BRIGHT_BG_BLUE.getColor()),
    DEBUG(3,
          "DEBUG",
          ANSI_BRIGHT_GREEN.getColor(),
          ANSI_BG_BLACK.getColor(),
          ANSI_BRIGHT_GREEN.getColor(),
          ANSI_BG_BLACK.getColor()),
    WARNING(4,
            "WARNING",
            ANSI_BRIGHT_YELLOW.getColor(),
            ANSI_BG_BLACK.getColor(),
            ANSI_BLACK.getColor(),
            ANSI_BRIGHT_BG_YELLOW.getColor()),
    ERROR(5,
          "ERROR",
          ANSI_WHITE.getColor(),
          ANSI_BG_RED.getColor(),
          ANSI_RED.getColor(),
          ANSI_BG_WHITE.getColor());

    private final int level;
    private final String levelTag;
    private final Color dayThemeFg;
    private final Color dayThemeBg;
    private final Color nightThemeFg;
    private final Color nightThemeBg;

    LogLevel(int level,
             String levelTag,
             Color dayThemeFg,
             Color dayThemeBg,
             Color nightThemeFg,
             Color nightThemeBg) {
        this.level = level;
        this.levelTag = levelTag;
        this.dayThemeFg = dayThemeFg;
        this.dayThemeBg = dayThemeBg;
        this.nightThemeFg = nightThemeFg;
        this.nightThemeBg = nightThemeBg;
    }

    public int getLevel() {
        return level;
    }

    public String getLevelTag() {
        return levelTag;
    }

    public Color getDayThemeFg() {
        return dayThemeFg;
    }

    public Color getDayThemeBg() {
        return dayThemeBg;
    }

    public Color getNightThemeFg() {
        return nightThemeFg;
    }

    public Color getNightThemeBg() {
        return nightThemeBg;
    }

    public static final int MIN_LOG_LEVEL = Arrays.stream(LogLevel.values())
                                                  .min(Comparator.comparingInt(LogLevel::getLevel))
                                                  .get()
                                                  .getLevel();
    public static final int MAX_LOG_LEVEL = Arrays.stream(LogLevel.values())
                                                  .max(Comparator.comparingInt(LogLevel::getLevel))
                                                  .get()
                                                  .getLevel();
}