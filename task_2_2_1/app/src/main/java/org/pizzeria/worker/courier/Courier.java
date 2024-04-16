package org.pizzeria.worker.courier;

import org.pizzeria.io.logger.InLogger;
import org.pizzeria.queue.InBlockingQueue;
import org.pizzeria.state.Order;
import org.pizzeria.worker.Worker;

/**
 * The Courier class represents a worker who takes orders from storage and puts them in a queue.
 */
public class Courier extends Worker {
    private final InBlockingQueue<Order> storage;
    private final int capacity;

    /**
     * Constructs a new Courier with the name, working time, capacity, logger, and storage.
     *
     * @param name the name of the courier
     * @param workingExperience the working experience of the worker
     * @param capacity the maximum number of orders the courier can hold in the bag
     * @param logger the logger to log messages
     * @param storage the storage from which the courier retrieves orders
     */
    public Courier(
        String name, 
        int workingExperience, 
        int capacity, 
        InLogger logger, 
        InBlockingQueue<Order> storage
    ) {
        super(name, workingExperience, logger);
        this.storage = storage;
        this.capacity = capacity;
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
     * Delivers an order by logging a message, 
     * waiting for the specified working time, and then logging another message.
     *
     * @throws InterruptedException if the thread is interrupted while waiting
     */
    private void deliverOrder() throws InterruptedException {
        logger.log("Delivering.");
        Thread.sleep(calculateSleepTime());
        logger.log("Ready to take delivery.");
    }

    /**
     * Generator log based on current order and available space in bag.
     *
     * @param order current order
     * @param bag available space in bag
     * @return string of log message
     */
    private String generateCurrentLog(Order order, int bag) {
        return "Got " + order.name() + " with id: " + order.id() + ". Bag space remain: " + bag;
    }

    /**
     * Overrides the run method of the Worker class.
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
                    logger.log(generateCurrentLog(order, bag));
                }
                deliverOrder();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.log("Free");
    }
}