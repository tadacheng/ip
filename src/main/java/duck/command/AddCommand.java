package duck.command;

import duck.exception.DuckException;
import duck.task.Task;
import duck.ui.Storage;
import duck.ui.TaskList;

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
    public void execute(TaskList tasks, Storage storage) throws DuckException {
        tasks.addTask(this.task);
        storage.save(tasks.getAllTasks());
        this.hasExecuted = true;
        this.executedResponse = "Got it. I've added this task:\n"
                + "  " + task.toString() + "\n"
                + "Now you have " + tasks.size() + " tasks in the list.";
    }
}
