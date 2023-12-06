package task.operations;

import java.util.EmptyStackException;
import java.util.Stack;
import task.CalculationException;

/**
 * Class implements pow calculating.
 */
public final class Pow implements Operation {
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
        if (op1 < 0.000000001) {
            throw new CalculationException("The base of the pow function is less than zero!");
        }
        return Math.pow(op1, op2);
    }
}
