package com.company.consolecolors.utils.log;

public class LogManager {

    public static LogManager sInstance;

    private static final Object LOCK = new Object();

    private static int minLogLevel = 0;

    private LogManager() {
    }

    public static LogManager getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new LogManager();
                }
            }
        }

        return sInstance;
    }

    public boolean isLoggable(LogLevel logLevel) {
        return minLogLevel <= logLevel.getLevel();
    }

    public void setMinLogLevel(LogLevel minLogLevel) {
        setMinLogLevel(minLogLevel.getLevel());
    }

    public void setMinLogLevel(int newMinLevel) {
        if (newMinLevel < LogLevel.MIN_LOG_LEVEL)
            throw new IllegalArgumentException("New min log level cannot be lower than lowest level.");
        if (newMinLevel > LogLevel.MAX_LOG_LEVEL)
            throw new IllegalArgumentException("New max log level cannot be grater than highest level.");

        minLogLevel = newMinLevel;
    }
}
