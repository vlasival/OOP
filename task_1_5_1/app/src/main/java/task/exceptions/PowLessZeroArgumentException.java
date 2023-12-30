package task.exceptions;

/**
 * Custom exception class for handling incorrect argument in pow method.
 */
public class PowLessZeroArgumentException extends CalculationException {
    /**
     * Constructs a PowLessZeroArgumentException without parameters.
     */
    public PowLessZeroArgumentException() {
        super();
    }

    /**
     * Constructs a PowLessZeroArgumentException with the specified error message.
     *
     * @param message is a detailed message.
     */
    public PowLessZeroArgumentException(String message) {
        super(message);
    }
}
