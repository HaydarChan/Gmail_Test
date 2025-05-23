package com.gmailtest.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

public class Logger {
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger("TestLogger");

    private static FileHandler fileHandler; // simpan handler supaya bisa flush

    static {
        try {
            Files.createDirectories(Paths.get("logs"));
            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            String logFilePath = System.getProperty("user.dir") + "/logs/test-log-" + timestamp + ".txt";

            fileHandler = new FileHandler(logFilePath, true);
            fileHandler.setFormatter(new CustomFormatter());

            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new CustomFormatter());

            logger.addHandler(fileHandler);
            logger.addHandler(consoleHandler);
            logger.setUseParentHandlers(false);

            System.out.println("Logger initialized. Log file at: " + logFilePath);

        } catch (IOException e) {
            System.err.println("Logger setup failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static class CustomFormatter extends Formatter {
        private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        @Override
        public String format(LogRecord record) {
            return String.format("[%s] %-7s - %s%n",
                    sdf.format(new Date(record.getMillis())),
                    record.getLevel(),
                    record.getMessage());
        }
    }

    public static void info(String message) {
        logger.info(message);
        flushFileHandler();
    }

    public static void warn(String message) {
        logger.warning(message);
        flushFileHandler();
    }

    public static void error(String message) {
        logger.severe(message);
        flushFileHandler();
    }

    private static void flushFileHandler() {
        if (fileHandler != null) {
            fileHandler.flush();
        }
    }

    public static void shutdown() {
        if (fileHandler != null) {
            fileHandler.flush();
            fileHandler.close();
        }
    }
}

