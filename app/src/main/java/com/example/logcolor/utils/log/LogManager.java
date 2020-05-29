package com.example.logcolor.utils.log;

public class LogManager {

    private static LogManager sInstance;

    private static final Object LOCK = new Object();

    private int mMinLogLevel = 0;

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
        return mMinLogLevel <= logLevel.getLevel();
    }

    public void setMinLogLevel(LogLevel mMinLogLevel) {
        setMinLogLevel(mMinLogLevel.getLevel());
    }

    public void setMinLogLevel(int newMinLevel) {
        if (newMinLevel < LogLevel.MIN_LOG_LEVEL)
            throw new IllegalArgumentException("New min log level cannot be lower than lowest level.");
        if (newMinLevel > LogLevel.MAX_LOG_LEVEL)
            throw new IllegalArgumentException("New max log level cannot be grater than highest level.");

        mMinLogLevel = newMinLevel;
    }
}
