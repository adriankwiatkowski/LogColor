package com.company.consolecolors.utils.log;

import java.util.Arrays;
import java.util.Comparator;

public enum LogLevel {

    INTERNAL_ERROR(0, "INTERNAL_ERROR"),
    VERBOSE(1, "VERBOSE"),
    INFO(2, "INFO"),
    DEBUG(3, "DEBUG"),
    WARNING(4, "WARNING"),
    ERROR(5, "ERROR");

    private final int level;
    private final String tag;

    LogLevel(int level, String tag) {
        this.level = level;
        this.tag = tag;
    }

    public int getLevel() {
        return level;
    }

    public String getLevelTag() {
        return tag;
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