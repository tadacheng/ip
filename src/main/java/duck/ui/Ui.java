package duck.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Handles interactions with the user through the console.
 * This class is responsible for displaying messages and reading user input.
 */
public class Ui {
    private final BufferedReader br;

    /**
     * Initializes the user interface to interact with the console.
     */
    public Ui() {
        this.br = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Displays a welcome message when the user starts the application.
     */
    public void showWelcome() {
        System.out.println("____________________________________________________________\n"
                + "Hello! I'm Duck\nWhat can I do for you?\n"
                + "____________________________________________________________");
    }

    /**
     * Displays a goodbye message when the user exits the application.
     */
    public void showBye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Displays a line separator.
     */
    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Reads a command input from the user.
     *
     * @return The user's input as a string.
     * @throws IOException If an error occurs while reading the input.
     */
    public String readCommand() throws IOException {
        return br.readLine();
    }

    /**
     * Displays an error message when loading the file fails.
     * Informs the user that the task list is starting empty.
     */
    public void showLoadingError() {
        System.out.println("Error loading file. Starting with an empty task list.");
    }

    /**
     * Displays a custom error message.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        System.out.println(message);
    }

    /**
     * Closes the BufferedReader to release system resources.
     *
     * @throws IOException If an error occurs while closing the reader.
     */
    public void close() throws IOException {
        br.close();
    }
}
