package duck.command;

import duck.exception.DuckException;
import duck.ui.Storage;
import duck.task.Task;
import duck.ui.TaskList;
import duck.ui.Ui;

/**
 * Represents a command that adds a task to the task list.
 * This command takes a task, adds it to the task list, saves the updated list to storage,
 * and prints a confirmation message to the user.
 */
public class AddCommand extends Command {
    private final Task task;

    public AddCommand(Task task) {
        this.task = task;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DuckException {
        tasks.addTask(this.task);
        storage.save(tasks.getAllTasks());
        System.out.println("Got it. I've added this task:\n");
        System.out.println("  " + task.toString());
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
    }
}
