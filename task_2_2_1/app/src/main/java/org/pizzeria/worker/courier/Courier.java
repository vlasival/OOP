package org.pizzeria.worker.courier;

import org.pizzeria.customQueue.IBlockingQueue;
import org.pizzeria.io.logger.ILogger;
import org.pizzeria.state.Order;
import org.pizzeria.worker.Worker;

/**
 * The Courier class represents a worker who takes orders from storage and puts them in a queue.
 */
public class Courier extends Worker {
    private final IBlockingQueue<Order> storage;
    private final int capacity;

    /**
     * Constructs a new Courier with the name, working time, capacity, logger, and storage.
     * 
     * @param name the name of the courier
     * @param workingTime the time it takes for the courier to complete a delivery
     * @param capacity the maximum number of orders the courier can hold in the bag
     * @param logger the logger to log messages
     * @param storage the storage from which the courier retrieves orders
     */
    public Courier(
        String name, 
        long workingTime, 
        int capacity, 
        ILogger logger, 
        IBlockingQueue<Order> storage
    ) {
        super(name, workingTime, logger);
        this.storage = storage;
        this.capacity = capacity;
    }

    /**
     * Delivers an order by logging a message, 
     * waiting for the specified working time, and then logging another message.
     * 
     * @throws InterruptedException if the thread is interrupted while waiting
     */
    private void deliverOrder() throws InterruptedException {
        logger.log("Delivering.");
        Thread.sleep(workingTime);
        logger.log("Ready to take delivery.");
    }

    /**
     * Overrides the run method of the Worker class.
     * 
     * Gets orders from the storage until the courier can work.
     * For each order, logs a message that includes the order's name, id, the remaining bag space.
     * Delivers the order after the bag is full or there are no more orders in the storage.
     * Logs a message when all deliveries are completed and the courier is free.
     */
    @Override
    public void run() {
        try {
            while (canWork) {
                if (storage.isEmpty()) {
                    continue;
                }
                int bag = capacity;
                while (bag > 0 && !storage.isEmpty()) {
                    Order order = storage.get();
                    bag--;
                    logger.log("Got " + order.name() + " with id: " + order.id()
                                + ". Bag space remain: " + bag);
                }
                deliverOrder();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.log("Free");
    }
}