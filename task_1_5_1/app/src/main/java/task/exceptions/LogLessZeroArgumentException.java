package task.exceptions;

/**
 * Custom exception class for handling incorrect log arguments.
 */
public class LogLessZeroArgumentException extends CalculationException {
    
    /**
     * Constructs a LogLessZeroArgumentException without parameters.
     */
    public LogLessZeroArgumentException() {
        super();
    }

    /**
     * Constructs a LogLessZeroArgumentException with the specified error message.
     *
     * @param message is a detailed message.
     */
    public LogLessZeroArgumentException(String message) {
        super(message);
    }
}
