package com.example.logcolor.models;

import com.example.logcolor.utils.log.Log;

import java.util.Optional;

public enum LogType {
    VERBOSE("VERBOSE") {
        @Override
        public void log(String tag, String msg) {
            Log.v(tag, msg);
        }
    }, INFO("INFO") {
        @Override
        public void log(String tag, String msg) {
            Log.i(tag, msg);
        }
    }, DEBUG("DEBUG") {
        @Override
        public void log(String tag, String msg) {
            Log.d(tag, msg);
        }
    }, WARNING("WARNING") {
        @Override
        public void log(String tag, String msg) {
            Log.w(tag, msg);
        }
    }, ERROR("ERROR") {
        @Override
        public void log(String tag, String msg) {
            Log.e(tag, msg);
        }
    };

    private final String logType;

    LogType(String logType) {
        this.logType = logType;
    }

    public abstract void log(String tag, String msg);

    public static Optional<LogType> extractLogType(String string) {
        for (LogType logType : LogType.values()) {
            if (logType.logType.equalsIgnoreCase(string))
                return Optional.of(logType);
        }

        return Optional.empty();
    }

    public String getLogType() {
        return logType;
    }
}
