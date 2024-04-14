package org.pizzeria.worker.baker;

import org.pizzeria.customQueue.IBlockingQueue;
import org.pizzeria.io.logger.ILogger;
import org.pizzeria.state.Order;
import org.pizzeria.worker.Worker;

/**
 * Baker class that extends the Worker class.
 */
public class Baker extends Worker {

    private final IBlockingQueue<Order> orders;
    private final IBlockingQueue<Order> storage;

    /**
     * Constructor for creating a Baker object.
     *
     * @param name the name of the baker worker
     * @param workingExperience the time taken to bake an order
     * @param logger the logger used for logging messages
     * @param orders the queue of orders to be baked
     * @param storage the queue for storing the baked orders
     */
    public Baker(String name, int workingExperience, ILogger logger,
                 IBlockingQueue<Order> orders, IBlockingQueue<Order> storage) {
        super(name, workingExperience, logger);
        this.orders = orders;
        this.storage = storage;
    }

    /**
     * Calculates sleep time based on working experience and random value.
     *
     * @return calculated sleep time
     */
    private int calculateSleepTime() {
        return (maxWorkingTime + random.nextInt(maxWorkingTime)) / workingExperience;
    }

    /**
     * Bakes an order and puts it in the storage queue.
     *
     * @param order the order to be baked
     * @throws InterruptedException if the thread is interrupted while sleeping
     */
    private void bakeOrder(Order order) throws InterruptedException {
        logger.log("Baking " + order.name() + " with id: " + order.id());
        Thread.sleep(calculateSleepTime());
        logger.log("Baked " + order.name() + " with id: " + order.id());
        storage.put(order);
        logger.log("Ready to take order.");
    }

    /**
     * The running method that is executed when the thread starts.
     * It continuously checks for orders in the queue and bakes them.
     */
    @Override
    public void run() {
        try {
            while (canWork) {
                if (orders.isEmpty()) {
                    continue;
                }
                Order order = orders.get();
                bakeOrder(order);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.log("Free");
    }
    
}