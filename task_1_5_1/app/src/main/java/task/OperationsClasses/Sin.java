package task.operationsClasses;

import java.util.EmptyStackException;
import java.util.Stack;
import task.CalculationException;

/**
 * Class implements sinus calculations.
 */
public final class Sin implements Operation {
    @Override
    public double apply(Stack<Double> stack) {
        try {
            return Math.sin(stack.pop());
        } catch (EmptyStackException e) {
            throw new CalculationException("Incorrect expression!");
        }
    }
    
}
