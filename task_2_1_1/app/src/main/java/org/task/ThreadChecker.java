package org.task;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The ThreadChecker class extends the Checker abstract class and provides a method for checking
 * a list of numbers for the presence of at least one non-prime number using multiple threads.
 */
public class ThreadChecker extends Checker {

    /** The number of threads to use for checking. */
    int threadCount;

    /**
     * Constructs a new ThreadChecker object with the specified number of threads.
     *
     * @param threadCount the number of threads to use for checking
     */
    public ThreadChecker(int threadCount) {
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
        if (threadCount < 1) {
            System.err.println("Thread count < 1");
            return false;
        }

        AtomicBoolean flag = new AtomicBoolean(false);
        Thread[] threads = new Thread[threadCount];
        int chunkSize = numbers.size() / threadCount;
        int overflow = numbers.size() % threadCount;
        int tmp = 0;
        for (int i = 0; i < threadCount; i++) {
            final int start = tmp;
            final int end = overflow > 0 ? tmp + chunkSize + 1 : tmp + chunkSize;
            overflow--;
            tmp = end;
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
