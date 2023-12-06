package task;

/**
 * Custom exception class for handling calculating exceptions.
 */
public class CalculationException extends ArithmeticException {
    /**
     * Constructs a CalculationException with the specified error message.
     *
     * @param message is a detailed message.
     */
    public CalculationException(String message) {
        super(message);
    }
}
