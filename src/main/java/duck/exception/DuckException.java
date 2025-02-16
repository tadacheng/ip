package duck.exception;

/**
 * A custom exception for the Duck application.
 * Formats error messages for better readability.
 */
public class DuckException extends Exception {
    /**
     * Exception for Duck application with dividers.
     *
     * @param message The error message to be shown
     */
    public DuckException(String message) {
        super("__________________________________\nError: "
                + message
                + "\n__________________________________\n");
    }
}
