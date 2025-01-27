package duck.exception;

/**
 * A custom exception for the Duck application.
 * Formats error messages for better readability.
 */
public class DuckException extends Exception{
    public DuckException(String message) {
        super("____________________________________________________________\nError: " +
                message +
                "\n____________________________________________________________\n");
    }
}
