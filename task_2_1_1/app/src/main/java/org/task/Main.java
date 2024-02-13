package org.task;

import java.util.ArrayList;
import java.util.List;

/**
 * The Main class contains the main method to execute the program 
 * for testing different Checker implementations.
 */
public class Main {

    /**
     * The main method to execute the program.
     *
     * @param args (not used)
     */
    @ExcludeFromJacocoGeneratedReport
    public static void main(String[] args) {
        int prime = 1000000007;
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            numbers.add(prime);
        }
        // numbers.add(200);

        long startTime;
        long endTime;

        SequentalChecker checker1 = new SequentalChecker();
        startTime = System.currentTimeMillis();
        boolean resultSequential = checker1.hasNonPrime(new ArrayList<>());
        endTime = System.currentTimeMillis();
        System.out.println("Sequential time: " + (endTime - startTime) + " milliseconds");
        System.out.println("Has non-prime number (Sequential): " + resultSequential);

        ParallelStreamChecker checker2 = new ParallelStreamChecker();
        startTime = System.currentTimeMillis();
        boolean resultParallelStream = checker2.hasNonPrime(numbers);
        endTime = System.currentTimeMillis();
        System.out.println("ParallelStream time: " + (endTime - startTime) + " milliseconds");
        System.out.println("Has non-prime number (ParallelStream): " + resultParallelStream);

        ThreadChecker checker3 = new ThreadChecker(4);
        startTime = System.currentTimeMillis();
        boolean resultThreads = checker3.hasNonPrime(numbers);
        endTime = System.currentTimeMillis();
        System.out.println("Threads time: " + (endTime - startTime) + " milliseconds");
        System.out.println("Has non-prime number (Threads): " + resultThreads);
    }
}
