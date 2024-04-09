package org.pizzeria.logger;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.pizzeria.io.logger.Logger;

public class LoggerTest {
    @Test
    void loggerTest() {
        Logger logger = new Logger(this.getClass(), "LogTest");
        logger.log("Hello World!");
        assertTrue(true);
    }
}
