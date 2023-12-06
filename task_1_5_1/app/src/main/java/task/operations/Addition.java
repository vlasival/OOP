package task.operations;

import java.util.EmptyStackException;
import java.util.Stack;
import task.CalculationException;

/**
 * Class implements addition of two numbers in stack.
 */
public final class Addition implements Operation {
    @Override
    public double apply(Stack<Double> stack) {
        try {
            return stack.pop() + stack.pop();
        } catch (EmptyStackException e) {
            throw new CalculationException("Incorrect expression!");
        }
    }
}
