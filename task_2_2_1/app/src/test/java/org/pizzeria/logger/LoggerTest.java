package org.pizzeria.logger;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.pizzeria.Pizzeria;
import org.pizzeria.io.logger.ILogger;
import org.pizzeria.io.logger.Logger;

@TestInstance(Lifecycle.PER_CLASS)
public class LoggerTest {

    private ILogger logger;
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    /**
     * Sets up the test case by redirecting System.out to outputStreamCaptor
     * and instantiating the Logger object for testing.
     */
    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(outputStreamCaptor));
        logger = new Logger(Pizzeria.class, "KFC");
    }

    /**
     * Tests whether the logger logs the message with the expected format.
     */
    @Test
    public void shouldLogMessageWithCurrentTimeAndClassNameAndName() {
        String message = "Pizza is ready";

        logger.log(message);

        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        assertTrue(outputStreamCaptor.toString().trim().contains(now));
        assertTrue(outputStreamCaptor.toString().trim().contains("Pizzeria"));
        assertTrue(outputStreamCaptor.toString().trim().contains("KFC"));
        assertTrue(outputStreamCaptor.toString().trim().contains(message));
    }
    
    /**
     * Restores the original System.out and cleans up 
     * the logger object after all tests are executed.
     */
    @AfterAll
    public void tearDown() {
        System.setOut(standardOut);
        logger = null;
    }
}