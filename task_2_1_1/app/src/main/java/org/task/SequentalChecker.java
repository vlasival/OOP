package org.task;

import java.util.List;

/**
 * The SequentalChecker class extends the Checker abstract class 
 * and provides a sequential method for checking
 * a list of numbers for the presence of at least one non-prime number.
 */
public class SequentalChecker extends Checker {

    /**
     * Checks a list of numbers sequentially for the presence of at least one non-prime number.
     *
     * @param numbers the list of numbers to check
     * @return true if the list contains at least one non-prime number, otherwise false
     */
    public boolean hasNonPrime(List<Integer> numbers) {
        for (int number : numbers) {
            if (!isPrime(number))
                return true;
        }
        return false;
    }

}
