
package task;

import java.util.Stack;

import task.exceptions.IncorrectExpressionException;
import task.factory.OperationFactory;
import task.operations.Operation;

/**
 * A class that implements methods for calculating the value of an arithmetic expression.
 */
public class Calculator {
    /**
     * Equals to zero value using in functionalities class.
     */
    public static double zeroValue = 0.000000001;

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

    /**
     * Static method realises calculating.
     *
     * @param input input string of expression.
     * @return result of expression if possible.
     */
    public static double calculate(String input) {
        String[] substrings = input.split(" ");

        Stack<Double> stack = new Stack<>();

        for (int i = substrings.length - 1; i >= 0; i--) {
            if (isOperator(substrings[i])) {
                Operation op = OperationFactory.create(substrings[i]);
                stack.push(op.apply(stack));
            } else {
                try {
                    stack.push(Double.parseDouble(substrings[i]));
                } catch (NumberFormatException e) {
                    throw new IncorrectExpressionException();
                }
            }
        }
        return stack.pop();
    }
}
