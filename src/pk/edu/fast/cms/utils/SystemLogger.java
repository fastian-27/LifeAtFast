package pk.edu.fast.cms.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Singleton logger utility to record system events to a text file.
 * INTEGRATION: Teammates will call SystemLogger.getInstance().log(...) for their modules.
 */
public class SystemLogger {

    // Relative path to the logs directory at the project root
    private static final String LOG_FILE = "logs/application_log.txt";
    private static SystemLogger instance;
    private final DateTimeFormatter formatter;

    public enum Level { INFO, WARNING, ERROR, CRITICAL }

    // Private constructor to enforce Singleton pattern
    private SystemLogger() {
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * Retrieves the single synchronized instance of the logger.
     */
    public static synchronized SystemLogger getInstance() {
        if (instance == null) {
            instance = new SystemLogger();
        }
        return instance;
    }

    /**
     * Appends a formatted log entry to the log file.
     *
     * @param level     Severity level of the log.
     * @param className The class where the event occurred.
     * @param userId    The ID of the user performing the action (or "SYSTEM").
     * @param message   Details of the event.
     */
    public synchronized void log(Level level, String className, String userId, String message) {
        String timestamp = LocalDateTime.now().format(formatter);
        String logEntry = String.format("[%s] [%s] [%s] [%s] - %s",
                timestamp, level.name(), className, userId, message);

        // Using try-with-resources to ensure the file is closed automatically
        try (PrintWriter out = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            out.println(logEntry);
        } catch (IOException e) {
            // Fallback to console if file writing fails
            System.err.println("CRITICAL LOGGER FAILURE: Could not write to " + LOG_FILE);
            e.printStackTrace();
        }
    }
}