package org.pizzeria.worker;

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
        try {
            orders.put(new Order(0, "null"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        storage = new BlockingQueue<>(5);
        baker = new Baker(
            "John",
            5, 
            new Logger(getClass(), "John"), 
            orders, 
            storage
        );
    }

    @Test
    public void testBakeOrder() throws Exception {
        Thread thread = new Thread(baker);
        thread.start();
        // storage.isEmpty().wait();

    }
}