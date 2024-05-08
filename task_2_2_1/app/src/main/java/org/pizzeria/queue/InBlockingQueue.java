package org.pizzeria.queue;

/**
 * Interface for a custom blocking queue.
 */
public interface InBlockingQueue<T> {

    /**
     * Puts an item into the queue.
     *
     * @param item The item to put into the queue
     * @throws InterruptedException if the thread was interrupted
     */
    void put(T item) throws InterruptedException;

    /**
     * Gets an item from the queue.
     *
     * @return The item from the queue
     * @throws InterruptedException if the thread was interrupted
     */
    T get() throws InterruptedException;

    /**
     * Checks whether the queue is empty or not.
     *
     * @return true if the queue is empty, false otherwise
     */
    public boolean isEmpty();

    /**
     * Returns the number of elements in the queue.
     *
     * @return the number of elements in the queue
     */
    public int size();
}