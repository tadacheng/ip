package duck.ui;

import java.io.IOException;

import duck.command.Command;
import duck.exception.DuckException;


/**
 * The main class of the Duck application, responsible for initializing and running the program.
 * It handles interactions between the user interface, task list, and storage components.
 */
public class Duck {
    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;

    /**
     * Initializes the Duck application with a specified file path for data storage.
     *
     * @param filePath The file path to load and save task data.
     */
    public Duck(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (DuckException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main loop of the Duck application, handling user input and executing commands.
     *
     * @throws IOException If an error occurs while saving or loading data.
     */
    public void run() throws IOException {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (DuckException e) {
                ui.showError(e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                ui.showLine();
            }
        }
        ui.close();
    }

    public static void main(String[] args) throws IOException {
        new Duck("./data/duck.txt").run();
    }
}
