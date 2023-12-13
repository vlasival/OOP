package task.operations;

import java.util.EmptyStackException;
import java.util.Stack;
import task.Calculator;
import task.Exceptions.IncorrectExpressionException;
import task.Exceptions.PowLessZeroArgumentException;

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
            throw new IncorrectExpressionException();
        }
        if (op1 < Calculator.zeroValue) {
            throw new PowLessZeroArgumentException();
        }
        return Math.pow(op1, op2);
    }
}
