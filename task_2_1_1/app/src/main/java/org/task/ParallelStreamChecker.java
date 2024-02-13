package org.task;

import java.util.List;

/**
 * The ParallelStreamChecker class extends the Checker abstract class 
 * and provides a method for using parallel streams
 * to check a list of numbers for the presence of at least one non-prime number.
 */
public class ParallelStreamChecker extends Checker {

    /**
     * Checks a list of numbers using parallel streams 
     * for the presence of at least one non-prime number.
     *
     * @param numbers the list of numbers to check
     * @return true if the list contains at least one non-prime number, otherwise false
     */
    @Override
    public boolean hasNonPrime(List<Integer> numbers) {
        return numbers.parallelStream().anyMatch(num -> !isPrime(num));
    }
    
}
