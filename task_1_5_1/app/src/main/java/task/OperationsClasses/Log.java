package task.operationsClasses;

import java.util.EmptyStackException;
import java.util.Stack;

import task.CalculationException;

/**
 * Class implements log calculations.
 */
public final class Log implements Operation {
    @Override
    public double apply(Stack<Double> stack) {
        double op = 0d;
        try {
            op = stack.pop();
        } catch (EmptyStackException e) {
            throw new CalculationException("Incorrect expression!");
        }
        if (op < 0.000000001) {
            throw new CalculationException("The argument of the log function is less than zero!");
        }
        return Math.log(op);
    }
}
