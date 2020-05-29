package com.example.printers.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PrinterAppExecutors {

    private static final int TIMEOUT_TERMINATION_SECONDS = 60;

    private static final Object LOCK = new Object();
    private static PrinterAppExecutors sInstance;
    private final ExecutorService mLogExecutor;

    private PrinterAppExecutors(ExecutorService logExecutor) {
        this.mLogExecutor = logExecutor;
    }

    public static PrinterAppExecutors getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new PrinterAppExecutors(
                            Executors.newSingleThreadExecutor());
                }
            }
        }

        return sInstance;
    }

    public Executor logThread() {
        return mLogExecutor;
    }

    public void shutdownExecutors() {
        shutdownAndAwaitTermination(mLogExecutor);
    }

    private void shutdownAndAwaitTermination(ExecutorService executorService) {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(
                    TIMEOUT_TERMINATION_SECONDS, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
                if (!executorService.awaitTermination(
                        TIMEOUT_TERMINATION_SECONDS, TimeUnit.SECONDS)) {
                    // Error terminating thread.
                    System.err.println("ExecutorService did not terminate.");
                }
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}
