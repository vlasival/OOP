package task;

/**
 * Custom exception class for handling exceptions in the GradeBook system.
 */
public class GradeException extends Exception {
    /**
     * Constructs a GradeException with the specified error message.
     *
     * @param message The detail message.
     */
    public GradeException(String message) {
        super(message);
    }
}
