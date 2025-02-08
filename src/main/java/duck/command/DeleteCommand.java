package duck.command;

import duck.exception.DuckException;
import duck.task.Task;
import duck.ui.Storage;
import duck.ui.TaskList;

/**
 * Represents a command that deletes a task from the task list.
 * This command takes a task, delete it from the task list, saves the updated list to storage,
 * and prints a confirmation message to the user.
 */
public class DeleteCommand extends Command {
    private final int id;

    public DeleteCommand(int id) {
        this.id = id;
    }

    @Override
    public void execute(TaskList tasks, Storage storage) throws DuckException {
        Task removedTask = tasks.deleteTask(this.id);
        storage.save(tasks.getAllTasks());
        this.hasExecuted = true;
        this.executedResponse = "Noted. I've removed this task:\n"
                + "  " + removedTask + "\n"
                + "Now you have " + tasks.size() + " tasks in the list.";
    }
}
