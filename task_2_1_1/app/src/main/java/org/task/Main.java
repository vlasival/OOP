package org.task;

import java.util.ArrayList;
import java.util.List;

/**
 * The Main class contains the main method to execute the program 
 * for testing different Checker implementations.
 */
public class Main {

    /**
     * The timer for any type of checker.
     *
     * @param checker is object of Checker subclass.
     * @return time of checking (in milliseconds).
     */
    @ExcludeFromJacocoGeneratedReport
    public static long timerChecker(Checker checker, List<Integer> numbers) {
        long startTime;
        long endTime;
        startTime = System.currentTimeMillis();
        checker.hasNonPrime(numbers);
        endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    /**
     * The main method to execute the program.
     *
     * @param args (not used)
     */
    @ExcludeFromJacocoGeneratedReport
    public static void main(String[] args) {
        final int prime = 1000000007;
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            numbers.add(prime);
        }

        SequentalChecker checker1 = new SequentalChecker();
        System.out.println("Sequential time: " 
                        + timerChecker(checker1, numbers) 
                        + " milliseconds");

        ParallelStreamChecker checker2 = new ParallelStreamChecker();
        System.out.println("ParallelStream time: " 
                        + timerChecker(checker2, numbers) 
                        + " milliseconds");

        ThreadChecker checker3 = new ThreadChecker(4);
        System.out.println("Thread time: " 
                        + timerChecker(checker3, numbers) 
                        + " milliseconds");
    }
}
