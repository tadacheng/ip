package duck.command;

import duck.exception.DuckException;
import duck.ui.Storage;
import duck.task.Task;
import duck.ui.TaskList;
import duck.ui.Ui;

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
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DuckException {
        Task removedTask = tasks.deleteTask(this.id);
        storage.save(tasks.getAllTasks());
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + removedTask);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
    }
}
