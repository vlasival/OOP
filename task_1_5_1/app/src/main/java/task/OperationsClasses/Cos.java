package task.OperationsClasses;

import java.util.EmptyStackException;
import java.util.Stack;

import task.CalculationException;

/**
 * Class implements cosinus calculations.
 */
public final class Cos implements Operation {
    @Override
    public double apply(Stack<Double> stack) {
        try {
            return Math.cos(stack.pop());
        } catch (EmptyStackException e) {
            throw new CalculationException("Incorrect expression!");
        }
    }
}
