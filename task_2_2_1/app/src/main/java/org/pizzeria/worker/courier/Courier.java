package org.pizzeria.worker.courier;

import org.pizzeria.customQueue.IBlockingQueue;
import org.pizzeria.logger.ILogger;
import org.pizzeria.state.Order;
import org.pizzeria.worker.Worker;

public class Courier extends Worker {
    private final IBlockingQueue<Order> storage;
    private final int capacity;

    public Courier(String name,
                long workingTime,
                int capacity,
                ILogger logger,
                IBlockingQueue<Order> storage) {
        super(name, workingTime, logger);
        this.storage = storage;
        this.capacity = capacity;
    }

    @Override
    public void run() {
        try {
            while (true) {
                logger.log("Ready to take order.");
                for (int i = 0; i < capacity; i++) {
                    Order order = storage.get();
                    logger.log("Got " + order.name() + " with id: " + order.id());
                }
                Thread.sleep(workingTime);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.log("Free");
    }
}
