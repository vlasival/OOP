
package task;

import java.util.Stack;

public class Calculator {

    public static double calculateFromInput(String in) {
        String[] substrings = in.split(" ");

        Stack<Double> stack = new Stack<>();
        double result;
        double op1;
        double op2;

        for (int i = substrings.length - 1; i >= 0; i--) {
            if (isOperator(substrings[i])) {
                op1 = stack.pop();
                switch (substrings[i]) {
                    case "+": op2 = stack.pop(); result = op1 + op2; break;
                    case "-": op2 = stack.pop(); result = op1 - op2; break;
                    case "*": op2 = stack.pop(); result = op1 * op2; break;
                    case "/": op2 = stack.pop(); result = op1 / op2; break;
                    case "sin": result = Math.sin(i);
                    case "cos": result = Math.cos(i);
                    case "log": result = Math.log(i);
                    default:
                        break;
                }
            }
        }
        return 0;
    }

    private static boolean isOperator(String elem) {
        String ops = "sin cos log pow sqrt + - * /";
        if (ops.indexOf(elem) != -1) {
            return true;
        }
        return false;
    }
}
