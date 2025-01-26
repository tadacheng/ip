
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
//
//        while (true) {
//            userInput = br.readLine();
//            try {
//                if (userInput.equals("help")) {
//                    System.out.print(DIVIDER);
//                    System.out.println("""
//                            Usage:\s
//                            list - Show tasklist
//                            bye - Quit
//                            todo [description] - Create Todo Task
//                            deadline [description] /by [date/time] - Create Deadline Task
//                            event [description] /from [start] /to [end] - Create Event Task
//                            mark [task_id] - Set Task as Done
//                            unmark [task_id] - Set Task as Not Done
//                            delete [task_id] - Delete Task""");
//                    System.out.print(DIVIDER);
//                }
//            } catch (NumberFormatException e) {
//                System.out.println("Task id must be an integer.");
//            } catch (DuckException e) {
//                System.out.println(e.getMessage());
//            } catch (Exception e) {
//                System.out.println(DIVIDER + "An unexpected error occurred: " + e.getMessage() + DIVIDER);
//            }
//        }
}
