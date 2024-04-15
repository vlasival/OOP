package org.pizzeria.worker;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.pizzeria.customQueue.BlockingQueue;
import org.pizzeria.customQueue.IBlockingQueue;
import org.pizzeria.io.logger.Logger;
import org.pizzeria.state.Order;
import org.pizzeria.worker.baker.Baker;

@TestInstance(Lifecycle.PER_CLASS)
public class BakerTest {

    Baker baker;
    IBlockingQueue<Order> orders;
    IBlockingQueue<Order> storage;

    @BeforeAll
    public void setup() {
        orders = new BlockingQueue<>(5);

        storage = new BlockingQueue<>(5);
        baker = new Baker(
            "John",
            5, 
            new Logger(getClass(), "John"), 
            orders, 
            storage
        );
    }

    private void putOneOrder() {
        try {
            orders.put(new Order(1, "null"));
        } catch (InterruptedException ignored) { }
    }

    private void waitForBaker() {
        synchronized (orders) {
            try {
                orders.wait();
            } catch (InterruptedException ignored) { }
        }
    }

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
        } catch (InterruptedException ignored) { }

        assertTrue(orders.isEmpty());
        assertTrue(storage.size() == 2);
    }
}