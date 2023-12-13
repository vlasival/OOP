package task.Exceptions;

/**
 * Custom exception class for handling incorrect expression.
 */
public class IncorrectExpressionException extends CalculationException {

    /**
     * Constructs a IncorrectExpressionException without parameters.
     */
    public IncorrectExpressionException() {
        super();
    }

    /**
     * Constructs a IncorrectExpressionException with the specified error message.
     *
     * @param message is a detailed message.
     */
    public IncorrectExpressionException(String message) {
        super(message);
    }
    
}
