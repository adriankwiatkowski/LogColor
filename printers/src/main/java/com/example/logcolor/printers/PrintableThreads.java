package com.example.logcolor.printers;

import java.util.ArrayList;
import java.util.List;

class PrintableThreads {

    private static final String TAG = PrintableAppExecutors.class.getSimpleName();

    private static final String ERROR_GET_DEINITALIZED_INSTANCE =
            TAG + " is closed!\n" + "Creating new instance.";

    private static List<Runnable> mCloseTaskQueue = new ArrayList<>();
    private static boolean mIsDeinitialized = false;

    public static void printableInvoke(Runnable runnable) {
        if (mIsDeinitialized) {
            System.err.println(ERROR_GET_DEINITALIZED_INSTANCE);
        }

        PrintableAppExecutors.getInstance().logThread().execute(runnable);
    }

    public static synchronized void shutdownThreads() {
        mIsDeinitialized = true;
        PrintableAppExecutors.getInstance().shutdownExecutors();
        executeCloseTasks(new ArrayList<>(mCloseTaskQueue));
    }

    public static synchronized void addCloseTask(Runnable runnable) {
        mCloseTaskQueue.add(runnable);
    }

    public static synchronized void removeCloseTask(Runnable runnable) {
        mCloseTaskQueue.remove(runnable);
    }

    private static void executeCloseTasks(List<Runnable> closeTaskQueue) {
        for (Runnable runnable : closeTaskQueue) {
            runnable.run();
        }
    }
}
