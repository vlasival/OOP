package org.pizzeria.worker.courier;

import org.pizzeria.customQueue.IBlockingQueue;
import org.pizzeria.io.logger.ILogger;
import org.pizzeria.state.Order;
import org.pizzeria.worker.Worker;

/**
 * Class Courier is a Worker that takes orders from the storage and puts them in the queue.
 */
public class Courier extends Worker {
    private final IBlockingQueue<Order> storage;
    private final int capacity;

    /**
     * Courier constructor.
     *
     * @param name courier name.
     * @param workingTime time to work in miliseconds.
     * @param capacity bag capacity.
     * @param logger logger.
     * @param storage storage deq.
     */
    public Courier(String name,
                long workingTime,
                int capacity,
                ILogger logger,
                IBlockingQueue<Order> storage) {
        super(name, workingTime, logger);
        this.storage = storage;
        this.capacity = capacity;
    }

    /**
     * Run method.
     */
    @Override
    public void run() {
        try {
            while (canWork) {
                logger.log("Ready to take delivery.");
                
                Order order = storage.get();

                logger.log("Got " + order.name() + " with id: " + order.id());
                
                logger.log("Delivering.");
                Thread.sleep(workingTime);
            }
        } catch (InterruptedException e) {
            logger.log("Free");
        }
    }
}
