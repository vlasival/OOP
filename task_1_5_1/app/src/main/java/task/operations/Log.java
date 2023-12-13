package task.operations;

import java.util.EmptyStackException;
import java.util.Stack;
import task.CalculationException;
import task.Calculator;

/**
 * Class implements log calculations.
 */
public final class Log implements Operation {
    @Override
    public double apply(Stack<Double> stack) {
        double op;
        try {
            op = stack.pop();
        } catch (EmptyStackException e) {
            throw new CalculationException("Incorrect expression!");
        }
        if (op < Calculator.zeroValue) {
            throw new CalculationException("The argument of the log function is less than zero!");
        }
        return Math.log(op);
    }
}
