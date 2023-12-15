package task.operations;

import java.util.EmptyStackException;
import java.util.Stack;
import task.exceptions.IncorrectExpressionException;

/**
 * Class implements substraction of two numbers in stack.
 */
public final class Substraction implements Operation {
    @Override
    public double apply(Stack<Double> stack) {
        try {
            return stack.pop() - stack.pop();
        } catch (EmptyStackException e) {
            throw new IncorrectExpressionException();
        }
    }
}
