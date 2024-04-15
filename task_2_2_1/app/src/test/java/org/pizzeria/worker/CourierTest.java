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
import org.pizzeria.worker.courier.Courier;

@TestInstance(Lifecycle.PER_CLASS)
public class CourierTest {

    Courier courier;
    IBlockingQueue<Order> storage;

    @BeforeAll
    public void setup() {

        storage = new BlockingQueue<>(5);
        courier = new Courier(
            "John",
            5, 
            5,
            new Logger(getClass(), "John"),
            storage
        );
    }

    private void putOneOrder() {
        try {
            storage.put(new Order(1, "null"));
        } catch (InterruptedException ignored) { }
    }

    private void waitForCourier() {
        synchronized (storage) {
            try {
                storage.wait();
            } catch (InterruptedException ignored) { }
        }
    }

    @Test
    public void testBakeOrder() {
        Thread thread = new Thread(courier);
        thread.start();
        waitForCourier();
        putOneOrder();
        waitForCourier();
        putOneOrder();
        waitForCourier();

        courier.stopWorking();
        try {
            thread.join();
        } catch (InterruptedException ignored) { }

        assertTrue(storage.isEmpty());
    }
}