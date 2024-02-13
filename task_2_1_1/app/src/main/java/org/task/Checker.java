package org.task;

import java.util.List;

/**
 * The abstract class Checker provides methods for working with numbers.
 */
public abstract class Checker {

    /**
     * Checks if the given number is prime.
     *
     * @param number the number to check
     * @return true if the number is prime, otherwise false
     */
    public static boolean isPrime(int number) {
        if (number <= 1)
            return false;
        for (int i = 2; i * i <= number; i++) {
            if (number % i == 0)
                return false;
        }
        return true;
    }

    /**
     * Abstract method that must be implemented in subclasses.
     * Checks a list of numbers for the presence of at least one non-prime number.
     *
     * @param numbers the list of numbers to check
     * @return true if the list contains at least one non-prime number, otherwise false
     */
    public abstract boolean hasNonPrime(List<Integer> numbers);
}
