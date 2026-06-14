package com.lld.patterns.creational.singleton;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Practical Example: Logger Singleton
 * Ensures single logger instance across application.
 */
public class Logger {
    private static Logger instance;
    private List<String> logs;

    private Logger() {
        this.logs = new ArrayList<>();
        System.out.println("[Logger] Instance created");
    }

    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void info(String message) {
        String log = "[INFO] " + LocalDateTime.now() + " - " + message;
        logs.add(log);
        System.out.println(log);
    }

    public void error(String message, Exception e) {
        String log = "[ERROR] " + LocalDateTime.now() + " - " + message;
        if (e != null) {
            log += " | " + e.getMessage();
        }
        logs.add(log);
        System.err.println(log);
    }

    public List<String> getLogs() {
        return new ArrayList<>(logs);
    }
}
