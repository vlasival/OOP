package org.pizzeria.queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

/**
 * This class contains unit tests for the BlockingQueue class.
 */
@TestInstance(Lifecycle.PER_CLASS)
public class BlockingQueueTest {
    
    InBlockingQueue<String> queue;

    /**
     * Fills the blocking queue with initial values before running the tests.
     */
    @BeforeAll
    void filling() {
        queue = new CustomBlockingQueue<>(10);
        try {
            queue.put("One");
            queue.put("Two");
            queue.put("Three");
            queue.put("Four");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests the put method of the BlockingQueue class.
     * Verifies that the queue is not empty after putting an element.
     */
    @Test
    void putTest() {
        try {
            queue.put("Five");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            assertFalse(queue.isEmpty());
        }
    }

    /**
     * Tests the get method of the BlockingQueue class.
     * Verifies that the returned value is equal to the expected value.
     */
    @Test
    void getTest() {
        String str = null;
        try {
            str = queue.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            assertEquals("One", str);
        }
    
    }
}
