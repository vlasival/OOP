
package task;

import java.util.Stack;

import task.operations.Operation;
import task.operations.OprationFactory;

/**
 * A class that implements methods for calculating the value of an arithmetic expression.
 */
public class Calculator {

    /**
     * Static method realises calculating.
     *
     * @param in input string of expression.
     * @return result of expression if possible.
     */
    public static double calculate(String in) {
        String[] substrings = in.split(" ");

        Stack<Double> stack = new Stack<>();

        for (int i = substrings.length - 1; i >= 0; i--) {
            if (isOperator(substrings[i])) {
                Operation op = OprationFactory.create(substrings[i]);
                stack.push(op.apply(stack));
            } else {
                try {
                    stack.push(Double.parseDouble(substrings[i]));
                } catch (NumberFormatException e) {
                    throw new CalculationException("Incorrect or non-existent operator!");
                }
            }
        }
        return stack.pop();
    }

    /**
     * Method-helper to determine operator.
     *
     * @param elem parsed token.
     * @return true if this is an operator.
     */
    private static boolean isOperator(String elem) {
        String ops = "sin cos log pow sqrt + - * /";
        if (ops.indexOf(elem) != -1) {
            return true;
        }
        return false;
    }
}
