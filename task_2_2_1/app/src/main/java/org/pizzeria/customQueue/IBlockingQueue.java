package org.pizzeria.customQueue;

import java.util.List;

/**
 * Interface for a custom blocking queue.
 */
public interface IBlockingQueue<T> {

    /**
     * Puts an item into the queue.
     *
     * @param item The item to put into the queue.
     * @throws InterruptedException if the thread was interrupted.
     */
    void put(T item) throws InterruptedException;

    /**
     * Gets an item from the queue.
     *
     * @return The item from the queue.
     * @throws InterruptedException if the thread was interrupted.
     */
    T get() throws InterruptedException;

    /**
     * Gets some items from the queue.
     *
     * @return A list of the items from the queue.
     * @throws InterruptedException if the thread was interrupted.
     */
    List<T> getSome(int n) throws InterruptedException;

    /**
     * Gets the size of the queue.
     *
     * @return size of the queue.
     */
    int getSize();
}
