package org.pizzeria.worker.baker;

import org.pizzeria.customQueue.IBlockingQueue;
import org.pizzeria.io.logger.ILogger;
import org.pizzeria.state.Order;
import org.pizzeria.worker.Worker;

/**
 * Baker class.
 */
public class Baker extends Worker {

    private final IBlockingQueue<Order> orders;
    private final IBlockingQueue<Order> storage;

    /**
     * Constructor.
     *
     * @param name worker name
     * @param workingTime time
     * @param logger logger
     * @param orders orders
     * @param storage storage
     */
    public Baker(String name,
                long workingTime, 
                ILogger logger,
                IBlockingQueue<Order> orders, 
                IBlockingQueue<Order> storage) {
        super(name, workingTime, logger);
        this.orders = orders;
        this.storage = storage;
    }

    /**
     * Running method.
     */
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
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.log("Free");
    }
    
}
