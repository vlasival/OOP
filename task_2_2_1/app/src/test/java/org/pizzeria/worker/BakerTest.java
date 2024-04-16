package org.pizzeria.worker;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.pizzeria.io.logger.Logger;
import org.pizzeria.queue.CustomBlockingQueue;
import org.pizzeria.queue.InBlockingQueue;
import org.pizzeria.state.Order;
import org.pizzeria.worker.baker.Baker;

/**
 * Test class for the Baker class.
 */
@TestInstance(Lifecycle.PER_CLASS)
public class BakerTest {

    Baker baker;
    InBlockingQueue<Order> orders;
    InBlockingQueue<Order> storage;

    /**
     * Sets up the test environment by initializing the necessary objects.
     */
    @BeforeAll
    public void setup() {
        orders = new CustomBlockingQueue<>(5);

        storage = new CustomBlockingQueue<>(5);
        baker = new Baker(
            "John",
            5, 
            new Logger(getClass(), "John"), 
            orders, 
            storage
        );
    }

    /**
     * Helper method to add one order to the orders queue.
     */
    private void putOneOrder() {
        try {
            orders.put(new Order(1, "null"));
        } catch (InterruptedException ignored) {
            ignored.printStackTrace();
        }
    }

    /**
     * Helper method to wait for the baker to finish processing all orders.
     */
    private void waitForBaker() {
        synchronized (orders) {
            try {
                orders.wait();
            } catch (InterruptedException ignored) {
                ignored.printStackTrace();
            }
        }
    }

    /**
     * Tests the bakeOrder() method of the Baker class.
     */
    @Test
    public void testBakeOrder() {
        Thread thread = new Thread(baker);
        thread.start();
        waitForBaker();
        putOneOrder();
        waitForBaker();
        putOneOrder();
        waitForBaker();

        baker.stopWorking();
        try {
            thread.join();
        } catch (InterruptedException ignored) {
            ignored.printStackTrace();
        }

        assertTrue(orders.isEmpty());
        assertTrue(storage.size() == 2);
    }
}