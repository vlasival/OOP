package org.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

/**
 * Testing class.
 */
@TestInstance(Lifecycle.PER_CLASS)
public class CheckerTest {
    
    List<Integer> primeNumbers;

    List<Integer> randomNumbers;

    static class CheckerArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                        Arguments.of(new SequentalChecker()),
                        Arguments.of(new ParallelStreamChecker()),
                        Arguments.of(new ThreadChecker(4)),
                        Arguments.of(new ThreadChecker(1))
                );
        }
    }

    @BeforeAll
    void fillingPrimeNumbersList() {
        final int prime = 1000000007;
        primeNumbers = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            primeNumbers.add(prime);
        }

        Random random = new Random();
        randomNumbers = new ArrayList<>();
        randomNumbers.add(123);
        for (int i = 0; i < 123; i++) {
            int randomNumber = random.nextInt(prime);
            randomNumbers.add(randomNumber);
        }
    }

    @ParameterizedTest
    @ArgumentsSource(CheckerArgumentsProvider.class)
    void randomNumbersTest(Checker checker) {
        assertTrue(checker.hasNonPrime(randomNumbers));
    }

    @ParameterizedTest
    @ArgumentsSource(CheckerArgumentsProvider.class)
    void bigPrimeMassiveTest(Checker checker) {
        assertFalse(checker.hasNonPrime(primeNumbers));
    }

    @ParameterizedTest
    @ArgumentsSource(CheckerArgumentsProvider.class)
    void lastNonPrimeInBigMassiveTest(Checker checker) {
        primeNumbers.add(2222222);
        assertTrue(checker.hasNonPrime(primeNumbers));
    }
    
    @ParameterizedTest
    @ArgumentsSource(CheckerArgumentsProvider.class)
    void zeroElementsTest(Checker checker) {
        List<Integer> test = new ArrayList<>();
        assertFalse(checker.hasNonPrime(test));
    }

    @ParameterizedTest
    @ArgumentsSource(CheckerArgumentsProvider.class)
    void unoPrimeElementTest(Checker checker) {
        List<Integer> test = new ArrayList<>();
        test.add(7);
        assertFalse(checker.hasNonPrime(test));
    }

    @ParameterizedTest
    @ArgumentsSource(CheckerArgumentsProvider.class)
    void unoNonPrimeElementTest(Checker checker) {
        List<Integer> test = new ArrayList<>();
        test.add(123);
        assertTrue(checker.hasNonPrime(test));
    }

    @ParameterizedTest
    @ArgumentsSource(CheckerArgumentsProvider.class)
    void lessThanZeroNumbersTest(Checker checker) {
        List<Integer> test = new ArrayList<>();
        for (int i = 0; i > -100; i--) {
            test.add(i);
        }
        assertTrue(checker.hasNonPrime(test));
    }

    @Test
    void lessThanOneThreadTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            ThreadChecker checker = new ThreadChecker(0);
            checker.hasNonPrime(randomNumbers);
        });
    }
}
