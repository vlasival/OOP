package task.operations;

import java.util.EmptyStackException;
import java.util.Stack;
import task.CalculationException;

/**
 * Class implements division of two numbers in stack.
 */
public final class Division implements Operation {
    @Override
    public double apply(Stack<Double> stack) {
        double op1;
        double op2;
        try {
            op1 = stack.pop();
            op2 = stack.pop();
        } catch (EmptyStackException e) {
            throw new CalculationException("Incorrect expression!");
        }
        if (Math.abs(op2) < 0.000000001) {
            throw new CalculationException("Division by zero!");
        }
        return op1 / op2;
    }
}
