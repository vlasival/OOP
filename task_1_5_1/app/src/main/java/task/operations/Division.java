package task.operations;

import java.util.EmptyStackException;
import java.util.Stack;
import task.Calculator;
import task.exceptions.IncorrectExpressionException;
import task.exceptions.ZeroDivisionException;

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
            throw new IncorrectExpressionException();
        }
        if (Math.abs(op2) < Calculator.zeroValue) {
            throw new ZeroDivisionException();
        }
        return op1 / op2;
    }
}
