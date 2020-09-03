package com.adriankwiatkowski.logcolor.printers;

import java.util.ArrayList;
import java.util.List;

class PrintableThreads {

    private static final String TAG = PrintableAppExecutors.class.getSimpleName();

    private static final String ERROR_GET_DEINITALIZED_INSTANCE =
            TAG + " is closed!\n" + "Creating new instance.";

    private static List<Runnable> mCloseTaskQueue = new ArrayList<>();
    private static boolean mIsDeinitialized = false;

    static void printableInvoke(Runnable runnable) {
        if (mIsDeinitialized) {
            System.err.println(ERROR_GET_DEINITALIZED_INSTANCE);
        }

        PrintableAppExecutors.getInstance().execute(runnable);
    }

    static synchronized void shutdownThreads() {
        mIsDeinitialized = true;

        PrintableAppExecutors.getInstance().shutdownExecutors();

        executeCloseTasks(new ArrayList<>(mCloseTaskQueue));

        mCloseTaskQueue = null;
    }

    static synchronized boolean addCloseTask(Runnable runnable) {
        return mCloseTaskQueue.add(runnable);
    }

    static synchronized boolean removeCloseTask(Runnable runnable) {
        return mCloseTaskQueue.remove(runnable);
    }

    private static void executeCloseTasks(List<Runnable> closeTaskQueue) {
        for (Runnable runnable : closeTaskQueue) {
            runnable.run();
        }
    }
}
