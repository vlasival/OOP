package org.pizzeria.customQueue;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Semaphore;

/**
 * Thread-safe custom blocking queue implementation.
 */
public class BlockingQueue<T> implements IBlockingQueue<T>, Serializable {

    private final Queue<T> queue;
    private final Semaphore spaceAvailable;
    private final Semaphore spaceOccupied;

    /**
     * Constructor.
     *
     * @param size size of the queue
     */
    public BlockingQueue(int size) {
        queue = new ArrayDeque<>(size);
        spaceAvailable = new Semaphore(size);
        spaceOccupied = new Semaphore(0);
    }

    /**
     * Puts an element into the queue. Blocks if the queue is full.
     *
     * @param item element to be added to the queue
     * @throws InterruptedException if the thread is interrupted while waiting to put an item into the queue
     */
    @Override
    public void put(T item) throws InterruptedException {
        spaceAvailable.acquire();
        synchronized (queue) {
            queue.offer(item);
        }
        spaceOccupied.release();
    }

    /**
     * Gets an element from the queue. Blocks if the queue is empty.
     *
     * @return element from the queue
     * @throws InterruptedException if the thread is interrupted while waiting to get an item from the queue
     */
    @Override
    public T get() throws InterruptedException {
        spaceOccupied.acquire();
        T item;
        synchronized (queue) {
            item = queue.poll();
        }
        synchronized (this) {
            notifyAll();
        }
        spaceAvailable.release();
        return item;
    }

    /**
     * Checks whether the queue is empty or not.
     *
     * @return true if the queue is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        synchronized (this) {
            notifyAll();
        }
        synchronized (queue) {
            return queue.isEmpty();
        }
    }

    /**
     * Returns the number of elements in the queue.
     *
     * @return the number of elements in the queue
     */
    public int size() {
        synchronized (queue) {
            return queue.size();
        }
    }
}