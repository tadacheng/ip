package duck.exception;

public class DuckException extends Exception {
    public DuckException(String message) {
        super("____________________________________________________________\nError: " +
                message +
                "\n____________________________________________________________\n");
    }
}
