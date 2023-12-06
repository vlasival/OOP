package task.OperationsClasses;

import java.util.EmptyStackException;
import java.util.Stack;

import task.CalculationException;

/**
 * Class implements sqrt calculations.
 */
public final class Sqrt implements Operation {
    @Override
    public double apply(Stack<Double> stack) {
        double op;
        try {
            op = stack.pop();
        } catch (EmptyStackException e) {
            throw new CalculationException("Incorrect expression!");
        }
        if (op < 0.0000000001) {
            throw new CalculationException("The argument of the sqrt function is less than zero!");
        }
        return Math.sqrt(op);
    }
}
