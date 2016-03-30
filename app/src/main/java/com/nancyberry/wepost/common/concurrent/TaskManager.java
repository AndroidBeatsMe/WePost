package com.nancyberry.wepost.common.concurrent;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class TaskManager {

    protected static TaskExecutor EXECUTOR = null;

    private TaskManager() {
    }

    public static boolean hasExecutor() {
        return EXECUTOR != null;
    }

    public static void setExecutor(TaskExecutor executor) {
        EXECUTOR = executor;
    }

    public static void run(Runnable r) {
        if (TaskManager.EXECUTOR == null) {
            TaskManager.EXECUTOR = new DefaultTaskExecutor();
        }
        TaskManager.EXECUTOR.run(r);
    }

    public static void runOnUI(Runnable r) {
        if (TaskManager.EXECUTOR == null) {
            TaskManager.EXECUTOR = new DefaultTaskExecutor();
        }
        TaskManager.EXECUTOR.runOnUI(r);
    }

    public interface TaskExecutor {

        void run(Runnable r);

        void runOnUI(Runnable r);
    }

    protected static class DefaultTaskExecutor implements TaskExecutor {

        protected ExecutorService taskExecutor = new ThreadPoolExecutor(5, 20, 5, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>());

        protected Handler uiHandler;

        public DefaultTaskExecutor() {
            uiHandler = new Handler(Looper.getMainLooper());
        }

        @Override
        public void run(Runnable r) {
            taskExecutor.execute(r);
        }

        @Override
        public void runOnUI(Runnable r) {
            uiHandler.post(r);
        }
    }
}
