package org.pizzeria.state;

import org.pizzeria.customQueue.BlockingQueue;

/**
 * Class representing state of the pizzeria ordering system.
 * It contains queues for orders and storage.
 */
public class State {

    private BlockingQueue<Order> orders;
    private BlockingQueue<Order> storage;

    /**
     * Constructor for State class.
     *
     * @param storageSize size of storage queue.
     * @param queueSize size of orders queue.
     */
    public State(int storageSize, int queueSize) {
        orders = new BlockingQueue<Order>(queueSize);
        storage = new BlockingQueue<Order>(storageSize);
    }

    /**
     * Getter for orders queue.
     */
    public BlockingQueue<Order> getOrders() {
        return orders;
    }

    /**
     * Getter for storage queue.
     */
    public BlockingQueue<Order> getStorage() {
        return storage;
    }
}
