package org.snake.logger;

/**
 * Simple dumb logger.
 */
public class MyLogger {
    /**
     * Information log.
     *
     * @param message specific message
     * @param cls class where log was called
     */
    public static void info(String message, Class<?> cls) {
        System.out.println("INFO: " + cls.getName() 
            + " " + message);
    }

    /**
     * Warning log.
     *
     * @param message specific message
     * @param cls class where log was called
     */
    public static void warn(String message, Class<?> cls) {
        System.out.println("WARNING: in class " + cls.getName() 
            + " " + message);
    }

    /**
     * Error log.
     *
     * @param message specific message
     * @param cls class where log was called
     */
    public static void err(String message, Class<?> cls) {
        System.out.println("ERROR: in class " + cls.getName() 
            + "\n" + message);
    }
}
