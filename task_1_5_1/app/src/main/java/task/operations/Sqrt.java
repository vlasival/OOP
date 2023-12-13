package task.operations;

import java.util.EmptyStackException;
import java.util.Stack;

import task.Calculator;
import task.Exceptions.IncorrectExpressionException;
import task.Exceptions.SqrtLessZeroArgumentException;

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
            throw new IncorrectExpressionException();
        }
        if (op < Calculator.zeroValue) {
            throw new SqrtLessZeroArgumentException();
        }
        return Math.sqrt(op);
    }
}
