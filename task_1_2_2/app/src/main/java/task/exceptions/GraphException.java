package task.exceptions;

/**
 * Custom exception class for graph-related errors.
 */
public class GraphException extends RuntimeException {

    /**
     * Constructs a new GraphException with no detail message.
     */
    public GraphException() {
        super();
    }

    /**
     * Constructs a new GraphException with the specified detail message.
     *
     * @param message the detail message
     */
    public GraphException(String message) {
        super(message);
    }
}
