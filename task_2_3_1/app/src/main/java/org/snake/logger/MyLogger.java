package org.snake.logger;

public class MyLogger {
    public static void info(String message, Class<?> cls) {
        System.out.println("INFO: " + cls.getName() 
            + " " + message);
    }

    public static void warn(String message, Class<?> cls) {
        System.out.println("WARNING: in class " + cls.getName() 
            + " " + message);
    }

    public static void err(String message, Class<?> cls) {
        System.out.println("ERROR: in class " + cls.getName() 
            + "\n" + message);
    }
}
