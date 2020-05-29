package com.example.logcolor.utils.log;

import com.example.logcolor.models.AnsiColor;

import java.util.Arrays;
import java.util.Comparator;

import static com.example.logcolor.models.AnsiColor.*;

public enum LogLevel {

    INTERNAL_ERROR(0, "INTERNAL_ERROR", ANSI_BLUE, ANSI_BRIGHT_BG_CYAN, ANSI_BRIGHT_WHITE, ANSI_BG_BLACK),
    VERBOSE(1, "VERBOSE", ANSI_BLACK, ANSI_BG_WHITE, ANSI_BRIGHT_WHITE, ANSI_BG_WHITE),
    INFO(2, "INFO", ANSI_BLACK, ANSI_BRIGHT_BG_BLACK, ANSI_BRIGHT_WHITE, ANSI_BG_BLUE),
    DEBUG(3, "DEBUG", ANSI_GREEN, ANSI_BRIGHT_BG_BLACK, ANSI_BRIGHT_GREEN, ANSI_BRIGHT_BG_BLACK),
    WARNING(4, "WARNING", ANSI_BRIGHT_YELLOW, ANSI_BG_BLACK, ANSI_BRIGHT_WHITE, ANSI_BRIGHT_BG_YELLOW),
    ERROR(5, "ERROR", ANSI_BLACK, ANSI_BRIGHT_BG_RED, ANSI_RED, ANSI_BRIGHT_BG_WHITE);

    private final int level;
    private final String levelTag;
    private final AnsiColor dayThemeFg;
    private final AnsiColor dayThemeBg;
    private final AnsiColor nightThemeFg;
    private final AnsiColor nightThemeBg;

    LogLevel(int level,
             String levelTag,
             AnsiColor dayThemeFg,
             AnsiColor dayThemeBg,
             AnsiColor nightThemeFg,
             AnsiColor nightThemeBg) {
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

    public AnsiColor getDayThemeFg() {
        return dayThemeFg;
    }

    public AnsiColor getDayThemeBg() {
        return dayThemeBg;
    }

    public AnsiColor getNightThemeFg() {
        return nightThemeFg;
    }

    public AnsiColor getNightThemeBg() {
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