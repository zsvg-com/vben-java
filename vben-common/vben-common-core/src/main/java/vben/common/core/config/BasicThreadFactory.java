package vben.common.core.config;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 从org.apache.common.lang3 拷贝
 */
public class BasicThreadFactory implements ThreadFactory {
    private final AtomicLong threadCounter;
    private final ThreadFactory wrappedFactory;
    private final Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
    private final String namingPattern;
    private final Integer priority;
    private final Boolean daemon;

    private BasicThreadFactory(BasicThreadFactory.Builder builder) {
        this.wrappedFactory = builder.factory != null ? builder.factory : Executors.defaultThreadFactory();
        this.namingPattern = builder.namingPattern;
        this.priority = builder.priority;
        this.daemon = builder.daemon;
        this.uncaughtExceptionHandler = builder.exceptionHandler;
        this.threadCounter = new AtomicLong();
    }

    public final Boolean getDaemonFlag() {
        return this.daemon;
    }

    public final String getNamingPattern() {
        return this.namingPattern;
    }

    public final Integer getPriority() {
        return this.priority;
    }

    public long getThreadCount() {
        return this.threadCounter.get();
    }

    public final Thread.UncaughtExceptionHandler getUncaughtExceptionHandler() {
        return this.uncaughtExceptionHandler;
    }

    public final ThreadFactory getWrappedFactory() {
        return this.wrappedFactory;
    }

    private void initializeThread(Thread thread) {
        if (this.getNamingPattern() != null) {
            Long count = this.threadCounter.incrementAndGet();
            thread.setName(String.format(this.getNamingPattern(), count));
        }

        if (this.getUncaughtExceptionHandler() != null) {
            thread.setUncaughtExceptionHandler(this.getUncaughtExceptionHandler());
        }

        if (this.getPriority() != null) {
            thread.setPriority(this.getPriority());
        }

        if (this.getDaemonFlag() != null) {
            thread.setDaemon(this.getDaemonFlag());
        }

    }

    public Thread newThread(Runnable runnable) {
        Thread thread = this.getWrappedFactory().newThread(runnable);
        this.initializeThread(thread);
        return thread;
    }

    public static class Builder implements vben.common.core.config.builder.Builder<BasicThreadFactory> {
        private ThreadFactory factory;
        private Thread.UncaughtExceptionHandler exceptionHandler;
        private String namingPattern;
        private Integer priority;
        private Boolean daemon;

        public Builder() {
        }

        public BasicThreadFactory build() {
            BasicThreadFactory factory = new BasicThreadFactory(this);
            this.reset();
            return factory;
        }

        public BasicThreadFactory.Builder daemon(boolean daemon) {
            this.daemon = daemon;
            return this;
        }

        public BasicThreadFactory.Builder namingPattern(String namingPattern) {
            this.namingPattern = (String) Objects.requireNonNull(namingPattern, "pattern");
            return this;
        }

        public BasicThreadFactory.Builder priority(int priority) {
            this.priority = priority;
            return this;
        }

        public void reset() {
            this.factory = null;
            this.exceptionHandler = null;
            this.namingPattern = null;
            this.priority = null;
            this.daemon = null;
        }

        public BasicThreadFactory.Builder uncaughtExceptionHandler(Thread.UncaughtExceptionHandler exceptionHandler) {
            this.exceptionHandler = (Thread.UncaughtExceptionHandler)Objects.requireNonNull(exceptionHandler, "handler");
            return this;
        }

        public BasicThreadFactory.Builder wrappedFactory(ThreadFactory factory) {
            this.factory = (ThreadFactory)Objects.requireNonNull(factory, "factory");
            return this;
        }
    }
}
