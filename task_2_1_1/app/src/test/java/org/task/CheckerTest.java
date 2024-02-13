package org.task;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

@TestInstance(Lifecycle.PER_CLASS)
public class CheckerTest {
    
    List<Integer> numbers;

    static class CheckerArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                        Arguments.of(new SequentalChecker()),
                        Arguments.of(new ParallelStreamChecker()),
                        Arguments.of(new ThreadChecker(4))
                );
        }
    }

    @BeforeAll
    void fillingPrimeNumbersList() {
        int prime = 1000000007;
        numbers = new ArrayList<>();
        for(int i = 0; i < 100000; i++) {
            numbers.add(prime);
        }
    }

    @ParameterizedTest
    @ArgumentsSource(CheckerArgumentsProvider.class)
    void bigPrimeMassiveTest(Checker checker) {
        assertFalse(checker.hasNonPrime(numbers));
    }

    
}
