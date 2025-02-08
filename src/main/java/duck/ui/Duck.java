package duck.ui;

import duck.command.Command;
import duck.exception.DuckException;

/**
 * The main class of the Duck application, responsible for initializing and running the program.
 * It handles interactions between the user interface, task list, and storage components.
 */
public class Duck {
    private static final String DEFAULT_FILE_PATH = "./data/duck.txt";
    private final Storage storage;
    private TaskList tasks;

    /**
     * Initializes the Duck application with a specified file path for data storage.
     *
     * @param filePath The file path to load and save task data.
     */
    public Duck(String filePath) {
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (DuckException e) {
            tasks = new TaskList();
        }
    }

    /**
     * Initializes the Duck application with a default file path for data storage.
     */
    public Duck() {
        this(DEFAULT_FILE_PATH);
    }

    /**
     * Generates a response for the user's chat message.
     *
     * @param input The input from users.
     * @return The message after command is executed.
     */
    public String getResponse(String input) {
        try {
            Command c = Parser.parse(input);
            c.execute(tasks, storage);
            return c.getString();
        } catch (DuckException e) {
            return e.getMessage();
        }
    }
}
