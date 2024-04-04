package org.pizzeria.logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger implements ILogger {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private String className;
    private String name;

    public Logger(Class<?> classObj, String name) {
        this.className = classObj.getSimpleName();
        this.name = name;
    }

    @Override
    public void log(String message) {
        LocalDateTime now = LocalDateTime.now();
        System.out.printf("%s (%s %s): %s\n", formatter.format(now), className, name, message);
    }
    
}
