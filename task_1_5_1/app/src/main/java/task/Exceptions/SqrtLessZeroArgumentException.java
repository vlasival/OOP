package task.exceptions;

/**
 * Custom exception class for handling incorrect argument in sqrt method.
 */
public class SqrtLessZeroArgumentException extends CalculationException {
    /**
     * Constructs a SqrtLessZeroArgumentException without parameters.
     */
    public SqrtLessZeroArgumentException() {
        super();
    }

    /**
     * Constructs a SqrtLessZeroArgumentException with the specified error message.
     *
     * @param message is a detailed message.
     */
    public SqrtLessZeroArgumentException(String message) {
        super(message);
    }
}
