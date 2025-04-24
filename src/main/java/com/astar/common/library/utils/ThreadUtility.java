package com.astar.common.library.utils;

import org.apache.commons.lang3.ThreadUtils;
import org.slf4j.MDC;

public abstract class ThreadUtility extends ThreadUtils {

    public static Runnable wrapWithMDC(Runnable runnable) {
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
}
