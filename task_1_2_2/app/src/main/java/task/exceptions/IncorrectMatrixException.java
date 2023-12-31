package task.exceptions;

/**
 * Custom exception class for errors related to an incorrect matrix format.
 * Extends the GraphException class.
 */
public class IncorrectMatrixException extends GraphException {

    /**
     * Constructs a new IncorrectMatrixException with no detail message.
     */
    public IncorrectMatrixException() {
        super();
    }

    /**
     * Constructs a new IncorrectMatrixException with the specified detail message.
     *
     * @param message the detail message
     */
    public IncorrectMatrixException(String message) {
        super(message);
    }

    /**
     * Constructs a new IncorrectMatrixException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause previous exception
     */
    public IncorrectMatrixException(String message, Throwable cause) {
        super(message, cause);
    }
}
