package task.exceptions;

/**
 * Custom exception class for handling division by zero.
 */
public class ZeroDivisionException extends CalculationException {

    /**
     * Constructs a ZeroDivisionException without parameters.
     */
    public ZeroDivisionException() {
        super();
    }

    /**
     * Constructs a ZeroDivisionException with the specified error message.
     *
     * @param message is a detailed message.
     */
    public ZeroDivisionException(String message) {
        super(message);
    }
}
