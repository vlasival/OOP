package org.pizzeria.queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.pizzeria.customQueue.BlockingQueue;

@TestInstance(Lifecycle.PER_CLASS)
public class BlockingQueueTest {
    
    BlockingQueue<String> queue;

    @BeforeAll
    void filling() {
        queue = new BlockingQueue<>(10);
        try {
            queue.put("One");
            queue.put("Two");
            queue.put("Three");
            queue.put("Four");
        } catch (InterruptedException e) { }
    }

    @Test
    void putTest() {
        try {
           queue.put("Five");
       } catch (InterruptedException e) {

        } finally {
            assertFalse(queue.isEmpty());
        }
       
    }

    @Test
    void getTest() {
        String str = null;
        try {
            str = queue.get();
        } catch (InterruptedException e) {

        } finally {
            assertEquals("One", str);
        }
    
    }
}
