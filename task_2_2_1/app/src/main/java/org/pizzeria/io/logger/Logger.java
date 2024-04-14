package org.pizzeria.io.logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Logger is used to log messages to console.
 */
public class Logger implements ILogger {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private String className;
    private String name;

    /**
     * Logger constructor.
     *
     * @param classObj class
     * @param name name
     */
    public Logger(Class<?> classObj, String name) {
        this.className = classObj.getSimpleName();
        this.name = name;
    }

    /**
     * Main log method.
     *
     * @param message message to log
     */
    @Override
    public void log(String message) {
        LocalDateTime now = LocalDateTime.now();
        System.out.printf("%s (%s %s): %s\n", formatter.format(now), className, name, message);
    }
    
}