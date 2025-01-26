package duck.ui;

import duck.exception.DuckException;
import duck.command.Command;

import java.io.IOException;

public class Duck {
    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;

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
