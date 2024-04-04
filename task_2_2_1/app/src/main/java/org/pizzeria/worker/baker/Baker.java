package org.pizzeria.worker.baker;

import org.pizzeria.customQueue.IBlockingQueue;
import org.pizzeria.logger.ILogger;
import org.pizzeria.state.Order;
import org.pizzeria.worker.Worker;

public class Baker extends Worker {

    private final IBlockingQueue<Order> orders;
    private final IBlockingQueue<Order> storage;

    public Baker(String name,
                long workingTime, 
                ILogger logger,
                IBlockingQueue<Order> orders, 
                IBlockingQueue<Order> storage) {
        super(name, workingTime, logger);
        this.orders = orders;
        this.storage = storage;
    }

    @Override
    public void run() {
        try {
            while (canWork) {
                logger.log("Ready to take order.");
                Order order = orders.get();
                logger.log("Baking " + order.name() + " with id: " + order.id());
                Thread.sleep(workingTime);
                logger.log("Baked " + order.name() + " with id: " + order.id());
                storage.put(order);
                logger.log(order.name() + " with id: " + order.id() + " in storage");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.log("Free");
    }
    
}
