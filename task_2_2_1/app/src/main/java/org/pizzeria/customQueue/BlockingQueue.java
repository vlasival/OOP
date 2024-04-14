package org.pizzeria.customQueue;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Semaphore;

/**
 * Thread-safe custom blocking queue implementation.
 */
public class BlockingQueue<T> implements IBlockingQueue<T> {

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
            queue.add(item);
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
        spaceAvailable.release();
        return item;
    }

    /**
     * Gets a specified number of elements from the queue. Blocks if the queue is empty.
     *
     * @param n the number of elements to get from the queue
     * @return list of elements from the queue
     * @throws InterruptedException if the thread is interrupted while waiting to get elements from the queue
     */
    @Override
    public List<T> getSome(int n) throws InterruptedException {
        spaceOccupied.acquire();
        List<T> items = new ArrayList<>();
        synchronized (queue) {
            while (n > 0 && !queue.isEmpty()) {
                items.add(queue.poll());
                n--;
            }
        }
        spaceAvailable.release();
        return items;
    }

    /**
     * Checks whether the queue is empty or not.
     *
     * @return true if the queue is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        synchronized (queue) {
            return queue.isEmpty();
        }
    }
}