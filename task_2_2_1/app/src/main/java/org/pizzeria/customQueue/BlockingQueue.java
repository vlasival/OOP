package org.pizzeria.customQueue;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Thread-safe custom dequeue. It uses a semaphore for synchronization.
 */
public class BlockingQueue<T> implements IBlockingQueue<T> {

    private final Deque<T> deque;
    private final Semaphore freeSpace;
    private final Semaphore occupiedSpace;

    /**
     * Constructor.
     *
     * @param size size of the queue
     */
    public BlockingQueue(int size) {
        deque = new ArrayDeque<>(size);
        freeSpace = new Semaphore(size);
        occupiedSpace = new Semaphore(0);
    }

    /**
     * Method puts an element into the queue. Blocks if the queue is full.
     *
     * @param item element to be added to the queue
     */
    @Override
    public void put(T item) throws InterruptedException {
        freeSpace.acquire();
        synchronized(deque) {
            deque.addLast(item);
        }
        occupiedSpace.release();
    }

    /**
     * Method gets an element from the queue.
     *
     * @return element from the queue
     * If the queue is empty, it waits until an element is available
     * If the queue is not empty, it returns the first element
     */
    @Override
    public T get() throws InterruptedException {
        occupiedSpace.acquire();
        T item = null;
        synchronized(deque) {
            item = deque.removeFirst();
        }
        freeSpace.release();
        return item;
    }

    /**
     * Method puts an element into the queue. Blocks if the queue is full.
     *
     * @param item element to be added to the queue
     */
    @Override
    public List<T> getSome(int n) throws InterruptedException {
        occupiedSpace.acquire();
        List<T> items = new ArrayList<>();
        synchronized(deque) {
            for (int i = 0; i < n; i++) {
                if (deque.size() == 0) {
                    break;
                }
                items.add(deque.removeFirst());
            }
        }
        freeSpace.release();
        return items;
    }

    /**
     * Method returns the size of the queue.
     */
    @Override
    public int getSize() {
        return deque.size();
    }

}
