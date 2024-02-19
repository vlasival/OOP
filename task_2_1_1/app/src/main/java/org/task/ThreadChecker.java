package org.task;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The ThreadChecker class extends the Checker abstract class and provides a method for checking
 * a list of numbers for the presence of at least one non-prime number using multiple threads.
 */
public class ThreadChecker extends Checker {

    /** The number of threads to use for checking. */
    private final int threadCount;

    /**
     * Constructs a new ThreadChecker object with the specified number of threads.
     *
     * @param threadCount the number of threads to use for checking
     * @throws IllegalArgumentException if the specified thread count is less than 1
     */
    public ThreadChecker(int threadCount) {
        if (threadCount < 1) {
            throw new IllegalArgumentException("Thread count must be > 1");
        }
        this.threadCount = threadCount;
    }

    /**
     * Checks a list of numbers for the presence of at least 
     * one non-prime number using multiple threads.
     *
     * @param numbers the list of numbers to check
     * @return true if the list contains at least one non-prime number, otherwise false
     */
    @Override
    public boolean hasNonPrime(List<Integer> numbers) {
        AtomicBoolean flag = new AtomicBoolean(false);
        Thread[] threads = new Thread[threadCount];
        int chunkSize = numbers.size() / threadCount;
        int overflow = numbers.size() % threadCount;
        int startIndex = 0;
        for (int i = 0; i < threadCount; i++) {
            final int start = startIndex;
            final int end = overflow > 0 ? startIndex + chunkSize + 1 : startIndex + chunkSize;
            overflow--;
            startIndex = end;
            threads[i] = new Thread(() -> {
                for (int j = start; j < end; j++) {
                    if (!isPrime(numbers.get(j))) {
                        flag.set(true);
                        break;
                    }
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return flag.get();
    }
    
}
