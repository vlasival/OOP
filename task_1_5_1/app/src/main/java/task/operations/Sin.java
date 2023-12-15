package task.operations;

import java.util.EmptyStackException;
import java.util.Stack;
import task.exceptions.IncorrectExpressionException;

/**
 * Class implements sinus calculations.
 */
public final class Sin implements Operation {
    @Override
    public double apply(Stack<Double> stack) {
        try {
            return Math.sin(stack.pop());
        } catch (EmptyStackException e) {
            throw new IncorrectExpressionException();
        }
    }
    
}
