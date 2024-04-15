package org.pizzeria.worker;

import org.pizzeria.io.json.WorkerConfig;
import org.pizzeria.io.logger.Logger;
import org.pizzeria.queue.InBlockingQueue;
import org.pizzeria.state.Order;
import org.pizzeria.worker.baker.Baker;
import org.pizzeria.worker.courier.Courier;

/**
 * WorkerFactory.
 */
public class WorkerFactory {

    /**
     * Method create worker.
     *
     * @param type worker type
     * @param config config
     * @param orders orders deq
     * @param storage storage deq
     * @return Worker
     */
    public Worker createWorker(
            WorkerType type,
            WorkerConfig config,
            InBlockingQueue<Order> orders, 
            InBlockingQueue<Order> storage
    ) {
        String name = config.getName();
        int workingExperience = config.getWorkingExperience();
        int capacity = config.getCapacity();
        return createWorker(type, name, workingExperience, capacity, orders, storage);
    }

    /**
     * Full method.
     *
     * @param type type
     * @param name name
     * @param workingExperience time
     * @param capacity cap
     * @param orders ord
     * @param storage storage
     * @return Worker
     */
    public Worker createWorker(
            WorkerType type,
            String name,
            int workingExperience,
            int capacity,
            InBlockingQueue<Order> orders, 
            InBlockingQueue<Order> storage
    ) {

        if (type == null) {
            return null;
        }
        
        Worker worker;

        switch (type) {
            case Baker:
                worker = new Baker(name, workingExperience, 
                                new Logger(Baker.class, name), orders, storage);
                break;
            case Courier:
                worker = new Courier(name, workingExperience, capacity, 
                                new Logger(Courier.class, name), storage);
                break;
            default:
                worker = null;
        }
        return worker;
    }

}
