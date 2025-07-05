package com.astar.java.library.utils;

import org.apache.commons.lang3.ThreadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.concurrent.*;


public class ThreadUtility extends ThreadUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadUtility.class);
    private static final ExecutorService ASYNC_EXECUTOR = Executors.newCachedThreadPool();
    private static final ScheduledExecutorService SCHEDULED_EXECUTOR = Executors.newScheduledThreadPool(
            Math.max(2, Runtime.getRuntime().availableProcessors() / 2)
            // At least 2 threads, or half CPU cores
    );

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOGGER.info("ThreadUtility: Initiating graceful shutdown of executor services...");
            shutdownExecutor(ASYNC_EXECUTOR, "ASYNC_EXECUTOR");
            shutdownExecutor(SCHEDULED_EXECUTOR, "SCHEDULED_EXECUTOR");
            LOGGER.info("ThreadUtility: All executor services shut down.");
        }, "ThreadUtility-ShutdownHook"));
    }

    public static Runnable wrapRunnableWithMDC(Runnable runnable) {
        final var contextMap = MDC.getCopyOfContextMap();
        return () -> {
            if (contextMap != null) {
                MDC.setContextMap(contextMap);
            }
            try {
                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }

    private static void shutdownExecutor(ExecutorService executor, String name) {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
                LOGGER.warn(
                        "ThreadUtility: {} did not terminate gracefully within 30 seconds. Forcing shutdown.",
                        name);
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            LOGGER.error("ThreadUtility: {} shutdown interrupted. Forcing shutdown.", name, e);
            Thread.currentThread().interrupt();
        }
    }

    public static Future<?> asyncExecute(Runnable runnable) {
        return ASYNC_EXECUTOR.submit(wrapRunnableWithMDC(runnable));
    }

    public static Future<?> asyncExecute(Runnable runnable, long millisecondDelays) {
        return SCHEDULED_EXECUTOR.schedule(runnable, millisecondDelays, TimeUnit.MILLISECONDS);
    }


    public static <T> T executeWithTimer(Callable<T> callable) throws Exception {
        long start = System.currentTimeMillis();
        T res = callable.call();
        long end = System.currentTimeMillis();
        long elapsedTime = end - start;
        LOGGER.info("<1> Time Taken : {}s", (elapsedTime / 1000.0));
        return res;
    }

    public static void executeWithTimer(Runnable runnable) {
        long start = System.currentTimeMillis();
        runnable.run();
        long end = System.currentTimeMillis();
        long elapsedTime = end - start;
        LOGGER.info("<2> Time Taken : {}s", (elapsedTime / 1000.0));
    }
    public static void executeWithTimer(Runnable runnable, int id) {
        LOGGER.info("Starting Task {}", id);
        long start = System.currentTimeMillis();
        runnable.run();
        long end = System.currentTimeMillis();
        long elapsedTime = end - start;
        LOGGER.info("<3> Task {} Time Taken : {}s",id , (elapsedTime / 1000.0));
    }

    public static void executeWithPreciseTimer(Runnable runnable) {
        long start = System.nanoTime();
        runnable.run();
        long end = System.nanoTime();
        long elapsedTime = end - start;
        System.out.print("<3> Time Taken : " + (elapsedTime / 1_000_000) + "ms");
    }
}
